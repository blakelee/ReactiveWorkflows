package net.blakelee.reactiveworkflows.tictactoe.gameplay

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class GamePlayScreen(
        handler: Events
) : WorkflowScreen<Unit, GamePlayScreen.Events>(KEY, Observable.just(Unit), handler) {

    companion object { val KEY = Key(this) }
    interface Events {
        fun makeMove()
    }
}