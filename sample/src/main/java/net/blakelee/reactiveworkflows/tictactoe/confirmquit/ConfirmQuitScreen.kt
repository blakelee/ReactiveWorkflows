package net.blakelee.reactiveworkflows.tictactoe.confirmquit

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class ConfirmQuitScreen(
        handler: Events
) : WorkflowScreen<Unit, ConfirmQuitScreen.Events>(KEY, Observable.just(Unit), handler) {

    companion object { val KEY = Key(this) }
    interface Events
}