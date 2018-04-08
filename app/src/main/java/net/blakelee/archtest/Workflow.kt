package net.blakelee.archtest

import io.reactivex.Maybe
import io.reactivex.Observable

interface Workflow<in I, out R> {
    fun screen(): Observable<WorkflowScreen<*,*>>
    fun abort() { viewFactory.abort() }
    fun start(input: I) { viewFactory.start(this) }
    fun result(): Maybe<out R> = Maybe.empty()
    var stateMachine: FiniteStateMachine
    var viewFactory: AbstractViewFactory
}