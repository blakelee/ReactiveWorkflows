package net.blakelee.reactiveworkflows.auth

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.library.*
import net.blakelee.library.FiniteStateMachine.Companion.onEntry
import net.blakelee.reactiveworkflows.auth.authorizing.AuthorizingScreen
import net.blakelee.reactiveworkflows.test.first.FirstScreen
import net.blakelee.reactiveworkflows.auth.login.LoginScreen
import net.blakelee.reactiveworkflows.auth.secondfactor.SecondFactorScreen

class AuthWorkflow : Workflow<Unit, String>,
    LoginScreen.Events, SecondFactorScreen.Events {

    internal enum class State {
        LOGIN_PROMPT, AUTHORIZING, SECOND_FACTOR_PROMPT, DONE
    }

    override var stateMachine = FiniteStateMachine(
            onEntry(State.LOGIN_PROMPT) { currentScreen.onNext(FirstScreen.KEY) }
    )

    override var currentScreen: BehaviorSubject<Key> = BehaviorSubject.create()
    override var viewFactory: AbstractViewFactory = AuthViewFactory()

    override fun onLogin(event: LoginScreen.SubmitLogin) {}

    override fun screen(): Observable<WorkflowScreen<*, *>> =
            currentScreen.map { key ->
                when(key) {
                    LoginScreen.KEY -> LoginScreen(firstScreen, this)
                    AuthorizingScreen.KEY -> AuthorizingScreen(authorizingScreen)
                    else -> throw IllegalArgumentException("Unknown key $key")
                }
            }

    private var firstScreen: BehaviorSubject<String> = BehaviorSubject.create()
    private var authorizingScreen: BehaviorSubject<String> = BehaviorSubject.create()
}

