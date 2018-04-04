package net.blakelee.archtest

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.coordinators.Coordinator
import com.squareup.coordinators.Coordinators
import java.util.*

open class AbstractViewFactory(screens: List<LayoutHolder>) {

    data class LayoutHolder(val key: String, @LayoutRes val id: Int, val screen: (WorkflowScreen<*, *>) -> Coordinator)
    private data class ScreenInfo(@LayoutRes val id: Int, val screen: (WorkflowScreen<*, *>) -> Coordinator)

    private val screenMap: MutableMap<String, ScreenInfo> = mutableMapOf()
    private val idMap: MutableMap<Int, String> = mutableMapOf()
    private val stack = Stack<View>()

    private lateinit var current: WorkflowScreen<*,*>
    private lateinit var container: ViewGroup

    init {
        screens.forEach {
            screenMap[it.key] = ScreenInfo(it.id, it.screen)
            idMap[it.id] = it.key
        }
    }

    companion object {
        fun bindLayout(
                key: String,
                @LayoutRes id: Int,
                screen: (WorkflowScreen<*, *>) -> Coordinator
        ) = LayoutHolder(key, id, screen)
    }

    fun create(workflow: Workflow<*,*>, container: ViewGroup) {
        this.container = container
        workflow.screen().subscribe {
            current = it
            pushView(screenMap[it.key]?.id)
        }

        Coordinators.installBinder(this.container, {
            screenMap[idMap[it.tag]]?.screen?.invoke(current)
        })
    }

    private fun createView(@LayoutRes id: Int): View {
        val v = LayoutInflater.from(container.context).inflate(id, container, false)
        v.tag = id
        return v
    }

    private fun pushView(@LayoutRes id: Int?) {
        id?.let {
            val v = createView(id)
            stack.push(v)
            container.addView(v)
        }
    }

    private fun replaceView(@LayoutRes id: Int?) {
        id?.let {
            val v = createView(id)

            if (stack.isNotEmpty()) {
                val prev = stack.peek()
                container.removeView(prev)
                stack.pop()
            }

            stack.push(v)
            container.addView(v)
        }
    }

    private fun popCurrent() {
        if (stack.isNotEmpty()) {
            val cur = stack.peek()
            container.removeView(cur)
            stack.pop()
        }
    }
}