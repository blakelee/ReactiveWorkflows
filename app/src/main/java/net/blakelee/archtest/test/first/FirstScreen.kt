package net.blakelee.archtest.test.first

import io.reactivex.Observable
import net.blakelee.archtest.WorkflowScreen
import kotlin.reflect.jvm.jvmName

class FirstScreen(
        title: Observable<String>,
        handler: Events
) : WorkflowScreen<String, FirstScreen.Events>(KEY, title, handler) {

    companion object {
        val KEY = FirstScreen::class.jvmName
    }

    interface Events {
        fun firstEvents()
    }
}
