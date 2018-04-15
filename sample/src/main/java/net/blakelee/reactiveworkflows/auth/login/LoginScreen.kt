package net.blakelee.reactiveworkflows.auth.login

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class LoginScreen(
        errorMessage: Observable<String>,
        errorHandler: Events
) : WorkflowScreen<String, LoginScreen.Events>(KEY, errorMessage, errorHandler) {

    companion object {
        val KEY = Key(this)
    }

    interface Events {
        fun onLogin(event: SubmitLogin)
    }

    data class SubmitLogin(val email: String, val password: String)
}