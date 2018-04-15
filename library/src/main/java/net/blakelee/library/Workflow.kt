package net.blakelee.library

import io.reactivex.Maybe
import io.reactivex.Observable

interface Workflow<in I, out R> {
    fun screen(): Observable<WorkflowScreen<*,*>> = Observable.empty()
    fun abort() { viewFactory.abort() }
    fun start(input: I) { viewFactory.start(this) }
    fun result(): Maybe<out R> = Maybe.never()
    fun back() {}
    val viewFactory: AbstractViewFactory
}