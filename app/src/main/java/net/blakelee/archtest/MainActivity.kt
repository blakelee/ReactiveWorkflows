package net.blakelee.archtest

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import net.blakelee.archtest.test.TestWorkflow
import java.util.*

class MainActivity : Activity() {

    private val testWorkflow = TestWorkflow()

    private lateinit var container: ViewGroup
    private val stack = Stack<View>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        container = findViewById(R.id.container)

        testWorkflow.create(container)
    }
}