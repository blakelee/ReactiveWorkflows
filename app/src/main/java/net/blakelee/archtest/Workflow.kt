package net.blakelee.archtest

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

interface Workflow<in I, out R> {
    fun screen(): Observable<WorkflowScreen<*,*>>
    fun abort() { viewFactory.abort() }
    fun start(input: I) { viewFactory.start(this) }
    fun result(): Maybe<out R> = Maybe.empty()
    fun event(event: Any): Boolean = stateMachine.event(event)
    var stateMachine: FiniteStateMachine
    var viewFactory: AbstractViewFactory
    var currentScreen: BehaviorSubject<String>
}