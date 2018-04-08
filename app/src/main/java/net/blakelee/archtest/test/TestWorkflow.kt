package net.blakelee.archtest.test

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.archtest.*
import net.blakelee.archtest.FiniteStateMachine.Companion.onEntry
import net.blakelee.archtest.FiniteStateMachine.Companion.transition
import net.blakelee.archtest.test.first.FirstScreen
import net.blakelee.archtest.test.second.SecondScreen

internal enum class State {
    FIRST_SCREEN, SECOND_SCREEN
}

class TestWorkflow : Workflow<Unit, Unit>,
    FirstScreen.Events, SecondScreen.Events {



    override var viewFactory: AbstractViewFactory = TestViewFactory()

    private val currentScreen = BehaviorSubject.create<String>()

    private val firstMessage = BehaviorSubject.create<String>()
    private val secondMessage = BehaviorSubject.create<String>()

    override var stateMachine = FiniteStateMachine(
            State.FIRST_SCREEN,

            transition(State.FIRST_SCREEN, Nothing::class, State.SECOND_SCREEN),
            onEntry(State.FIRST_SCREEN) { currentScreen.onNext(FirstScreen.KEY) }
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
        currentScreen.onNext(SecondScreen.KEY)
    }

    override fun secondTest() {
        currentScreen.onNext(FirstScreen.KEY)
    }
}