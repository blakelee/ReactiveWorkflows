package net.blakelee.archtest

import android.app.Activity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.coordinators.Coordinators
import kotlinx.android.synthetic.main.main_layout.*
import java.util.*

class MainActivity : Activity() {

    private lateinit var container: ViewGroup
    private val stack = Stack<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        container = findViewById(R.id.container)

        Coordinators.installBinder(container, {
            when(it.tag) {
                R.layout.test_layout_one -> FirstCoordinator()
                R.layout.test_layout_four -> SecondCoordinator()
                else -> null
            }
        })

        first.setOnClickListener {
            if (checkBox.isChecked)
                container.removeAllViews()

            pushView(R.layout.test_layout_one)
        }
        second.setOnClickListener {
            if (checkBox.isChecked)
                container.removeAllViews()

            pushView(R.layout.test_layout_two)
        }
        third.setOnClickListener {
            if (checkBox.isChecked)
                container.removeAllViews()

            replaceView(R.layout.test_layout_three)
        }

    }

    private fun createView(@LayoutRes id: Int): View {
        val v = LayoutInflater.from(container.context).inflate(id, container, false)
        v.tag = id
        return v
    }

    fun pushView(@LayoutRes id: Int) {
        val v = createView(id)
        stack.push(v)
        container.addView(v)
    }

    fun replaceView(@LayoutRes id: Int) {
        val v = createView(id)

        if (stack.isNotEmpty()) {
            val prev = stack.peek()
            container.removeView(prev)
            stack.pop()
        }

        stack.push(v)
        container.addView(v)
    }

    fun popCurrent() {
        if (stack.isNotEmpty()) {
            val cur = stack.peek()
            container.removeView(cur)
            stack.pop()
        }
    }
}