package net.blakelee.library

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.coordinators.Coordinator
import com.squareup.coordinators.Coordinators
import io.reactivex.disposables.Disposable
import java.util.*

open class AbstractViewFactory(screens: List<LayoutHolder>) {

    data class LayoutHolder(val key: String, @LayoutRes val id: Int, val screen: (WorkflowScreen<*, *>) -> Coordinator)
    private data class ScreenInfo(@LayoutRes val id: Int, val screen: (WorkflowScreen<*, *>) -> Coordinator)

    private val screenMap: Map<String, ScreenInfo> = screens.associate { it.key to ScreenInfo(it.id, it.screen) }

    private val viewStack = Stack<View>()
    private val keyStack = Stack<String>()

    private lateinit var current: WorkflowScreen<*,*>
    private lateinit var container: ViewGroup
    private var disposable: Disposable? = null

    companion object {
        fun bindLayout(
                key: String,
                @LayoutRes id: Int,
                screen: (WorkflowScreen<*, *>) -> Coordinator
        ) = LayoutHolder(key, id, screen)
    }

    fun start(workflow: Workflow<*,*>) {
        this.container = App.container
        disposable = workflow.screen().subscribe {
            current = it

            // if the stack already contains this key, no need to re-inflate layout, just bring it
            // to the front of the stack
            if (keyStack.contains(it.key)) {
                val index = keyStack.indexOf(it.key)

                val v = viewStack[index]
                v.bringToFront()

                keyStack.removeElementAt(index)
                viewStack.removeElementAt(index)

                keyStack.push(it.key)
                viewStack.push(v)
            } else {
                when (it.viewMode) {
                    WorkflowScreen.ViewMode.Push -> pushView(it.key)
                    WorkflowScreen.ViewMode.Replace -> replaceView(it.key)
                }
            }
        }

        Coordinators.installBinder(this.container, {
            screenMap[it.tag]?.screen?.invoke(current)
        })
    }

    fun abort() {
        disposable?.dispose()
        while(viewStack.isNotEmpty()) {
            popCurrent()
        }
    }

    private fun createView(key: String): View? =
        screenMap[key]?.id?.let {
            val v = LayoutInflater.from(container.context).inflate(it, container, false)
            v.tag = key
            return v
        }

    private fun pushView(key: String): Boolean = createView(key)?.let { view ->
        addView(view, key)
    } ?: false

    private fun replaceView(key: String): Boolean = createView(key)?.let { view ->
        popCurrent()
        addView(view, key)
    } ?: false

    private fun addView(view: View, key: String): Boolean {
        viewStack.push(view)
        keyStack.push(key)
        container.addView(view)
        return true
    }

    private fun popCurrent() {
        if (viewStack.isNotEmpty()) {
            val cur = viewStack.peek()
            container.removeView(cur)
            viewStack.pop()
            keyStack.pop()
        }
    }
}