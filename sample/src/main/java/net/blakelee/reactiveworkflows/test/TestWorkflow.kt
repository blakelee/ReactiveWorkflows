package net.blakelee.reactiveworkflows.test

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.library.*
import net.blakelee.reactiveworkflows.test.first.FirstScreen
import net.blakelee.reactiveworkflows.test.second.SecondScreen
import net.blakelee.library.FiniteStateMachine.Companion.onEntry
import net.blakelee.library.FiniteStateMachine.Companion.transition

internal enum class State {
    FIRST_SCREEN, SECOND_SCREEN
}

class TestWorkflow : Workflow<Unit, Unit>,
    FirstScreen.Events, SecondScreen.Events {

    override var viewFactory: AbstractViewFactory = TestViewFactory()
    override var currentScreen: BehaviorSubject<Key> = BehaviorSubject.create()

    private val firstMessage = BehaviorSubject.create<String>()
    private val secondMessage = BehaviorSubject.create<String>()

    override var stateMachine = FiniteStateMachine(
            transition(State.FIRST_SCREEN, Integer::class, State.SECOND_SCREEN) ,
            transition(State.SECOND_SCREEN, Integer::class, State.FIRST_SCREEN),
            onEntry(State.FIRST_SCREEN) { currentScreen.onNext(FirstScreen.KEY) },
            onEntry(State.SECOND_SCREEN) { currentScreen.onNext(SecondScreen.KEY) }
    )

    override fun screen(): Observable<WorkflowScreen<*, *>> =
            currentScreen.map {
                when(it) {
                    FirstScreen.KEY -> FirstScreen(firstMessage, this)
                    SecondScreen.KEY -> SecondScreen(secondMessage, this)
                    else -> throw IllegalArgumentException("Unknown key $it")
                }
            }

    override fun firstEvents() {
        event(1)
    }

    override fun secondTest() {
        event(2)
    }
}