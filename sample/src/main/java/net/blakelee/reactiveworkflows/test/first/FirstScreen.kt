package net.blakelee.reactiveworkflows.test.first

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen
import kotlin.reflect.jvm.jvmName

class FirstScreen(
        title: Observable<String>,
        handler: Events
) : WorkflowScreen<String, FirstScreen.Events>(KEY, title, handler) {

    companion object { val KEY = Key(this) }

    interface Events {
        fun firstEvents()
    }

    init { viewMode = ViewMode.Push }
}
