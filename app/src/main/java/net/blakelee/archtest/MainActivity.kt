package net.blakelee.archtest

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import net.blakelee.archtest.test.TestWorkflow

@Suppress("StaticFieldLeak")
object App {
    lateinit var container: ViewGroup
}

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        App.container = findViewById(R.id.container)

        TestWorkflow().start(Unit)
    }
}