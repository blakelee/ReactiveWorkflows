package net.blakelee.reactiveworkflows.auth.secondfactor

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class SecondFactorScreen(
        title: Observable<String>,
        handler: Events
) : WorkflowScreen<String, SecondFactorScreen.Events>(KEY, title, handler) {

    companion object { val KEY = Key(SecondFactorScreen) }
    interface Events {
        fun onSecondFactor(event: SecondFactor)
    }

    data class SecondFactor(val token: String)
}