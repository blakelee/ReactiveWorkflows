package net.blakelee.reactiveworkflows

import android.app.Activity
import android.os.Bundle
import net.blakelee.reactiveworkflows.test.TestWorkflow
import net.blakelee.library.App


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        App.container = findViewById(R.id.container)

        TestWorkflow().start(Unit)
    }
}