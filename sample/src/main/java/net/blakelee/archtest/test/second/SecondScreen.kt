package net.blakelee.archtest.test.second

import io.reactivex.Observable
import net.blakelee.library.WorkflowScreen
import kotlin.reflect.jvm.jvmName

class SecondScreen(
        title: Observable<String>,
        handler: Events
) : WorkflowScreen<String, SecondScreen.Events>(KEY, title, handler) {

    companion object {
        val KEY = SecondScreen::class.jvmName
    }

    interface Events {
        fun secondTest()
    }

    init { viewMode = ViewMode.Push }
}
