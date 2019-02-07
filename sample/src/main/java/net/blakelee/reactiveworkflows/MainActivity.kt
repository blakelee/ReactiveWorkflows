package net.blakelee.reactiveworkflows

import android.app.Activity
import android.os.Bundle
import android.util.Log
import net.blakelee.library.App
import net.blakelee.reactiveworkflows.auth.AuthWorkflow

class MainActivity : Activity() {

    var workflow: AuthWorkflow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        App.container = findViewById(R.id.container)

        workflow = AuthWorkflow()
        workflow?.start(Unit)
        workflow?.result()?.subscribe {
            Log.i("RESULT", it)
            workflow = null
        }
    }

    override fun onBackPressed() {
        workflow?.back()
    }
}