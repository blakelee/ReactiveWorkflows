package net.blakelee.archtest

import android.app.Activity
import android.os.Bundle
import net.blakelee.archtest.test.TestWorkflow
import net.blakelee.library.App


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        App.container = findViewById(R.id.container)

        TestWorkflow().start(Unit)
    }
}