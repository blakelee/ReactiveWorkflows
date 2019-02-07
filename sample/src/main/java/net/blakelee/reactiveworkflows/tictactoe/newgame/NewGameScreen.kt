package net.blakelee.reactiveworkflows.tictactoe.newgame

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class NewGameScreen(
        handler: Events
) : WorkflowScreen<Unit, NewGameScreen.Events>(KEY, Observable.just(Unit), handler) {

    companion object { val KEY = Key(this) }
    interface Events
}