package net.blakelee.reactiveworkflows.test.second

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class SecondScreen(
        title: Observable<String>,
        handler: Events
) : WorkflowScreen<String, SecondScreen.Events>(KEY, title, handler) {

    companion object {
        val KEY = Key(this)
    }

    interface Events {
        fun secondTest()
    }

    init { viewMode = ViewMode.Push }
}
