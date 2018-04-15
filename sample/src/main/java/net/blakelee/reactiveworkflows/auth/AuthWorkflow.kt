package net.blakelee.reactiveworkflows.auth

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.MaybeSubject
import net.blakelee.library.*
import net.blakelee.library.FiniteStateMachine.Companion.onEntry
import net.blakelee.library.FiniteStateMachine.Companion.transition
import net.blakelee.reactiveworkflows.auth.authorizing.AuthorizingScreen
import net.blakelee.reactiveworkflows.auth.login.LoginScreen
import net.blakelee.reactiveworkflows.auth.secondfactor.SecondFactorScreen
import java.util.concurrent.TimeUnit

private val email = "blake@blakelee.net"
private val password = "password"

class AuthWorkflow : Workflow<Unit, String>,
    LoginScreen.Events, SecondFactorScreen.Events {

    private val currentScreen: BehaviorSubject<Key> = BehaviorSubject.create()

    private val loginMessage = BehaviorSubject.createDefault("")
    private val authorizingMessage = BehaviorSubject.create<String>()
    private val secondFactorMessage = BehaviorSubject.create<String>()

    private val result = MaybeSubject.create<String>()

    override fun result(): Maybe<out String> = result

    override fun screen(): Observable<WorkflowScreen<*, *>> =
            currentScreen.map { key ->
                when(key) {
                    LoginScreen.KEY -> LoginScreen(loginMessage, this)
                    AuthorizingScreen.KEY -> AuthorizingScreen(authorizingMessage)
                    SecondFactorScreen.KEY -> SecondFactorScreen(secondFactorMessage, this)
                    else -> throw IllegalArgumentException("Unknown key $key")
                }
            }

    internal enum class State {
        LOGIN_PROMPT, AUTHORIZING, SECOND_FACTOR_PROMPT, DONE
    }

    override fun start(input: Unit) {
        super.start(input)
        stateMachine.start(State.LOGIN_PROMPT)
    }

    override fun abort() {
        super.abort()
        result.onSuccess("Workflow complete")
    }

    private var stateMachine = FiniteStateMachine(
            onEntry(State.LOGIN_PROMPT) { currentScreen.onNext(LoginScreen.KEY) },
            onEntry(State.AUTHORIZING) { currentScreen.onNext(AuthorizingScreen.KEY) },
            onEntry(State.SECOND_FACTOR_PROMPT) { currentScreen.onNext(SecondFactorScreen.KEY) },
            onEntry(State.DONE) { abort() },
            transition(State.LOGIN_PROMPT, LoginScreen.SubmitLogin::class, State.AUTHORIZING)
                    .doAction { doLogin(it) },
            transition(State.AUTHORIZING, AuthResponse::class, State.LOGIN_PROMPT)
                    .onlyIf { isLoginFailure(it) }
                    .doAction { response ->
                        val errorMessage = response.errorMessage
                        loginMessage.onNext(errorMessage)
                    },
            transition(State.AUTHORIZING, Unit::class, State.SECOND_FACTOR_PROMPT),
            transition(State.SECOND_FACTOR_PROMPT, SecondFactorScreen.SecondFactor::class, State.DONE)
                    .onlyIf { isSecondFactorSuccess(it) }

    )

    private fun doLogin(submitLogin: LoginScreen.SubmitLogin) {
        val d = Observable.interval(0, 333, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val dots = ".".repeat(it.toInt() % 4)
                    authorizingMessage.onNext("logging in$dots")
                }

        Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            if (submitLogin.email == email && submitLogin.password == password) {
                stateMachine.event(AuthResponse("success"))
            } else {
                stateMachine.event(AuthResponse("wrong username/password"))
            }
            d.dispose()
        }
    }

    private data class AuthResponse(val errorMessage: String)
    private fun isLoginFailure(authResponse: AuthResponse): Boolean {
        return if (authResponse.errorMessage == "success") {
            !stateMachine.event(Unit)
        } else {
            true
        }
    }

    private fun isSecondFactorSuccess(secondFactor: SecondFactorScreen.SecondFactor): Boolean {
        return if (secondFactor.token == "0000")
            true
        else {
            secondFactorMessage.onNext("token invalid")
            false
        }
    }

    override var viewFactory: AbstractViewFactory = AuthViewFactory()

    override fun onLogin(event: LoginScreen.SubmitLogin) { stateMachine.event(event) }
    override fun onSecondFactor(event: SecondFactorScreen.SecondFactor) { stateMachine.event(event) }
}

