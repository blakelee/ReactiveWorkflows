package net.blakelee.reactiveworkflows

import android.app.Activity
import android.os.Bundle
import android.util.Log
import net.blakelee.library.App
import net.blakelee.reactiveworkflows.auth.AuthWorkflow

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        App.container = findViewById(R.id.container)

        val workflow = AuthWorkflow()
        workflow.start(Unit)
        workflow.result().subscribe {
            Log.i("RESULT", it)

        }
    }
}