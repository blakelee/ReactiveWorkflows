package net.blakelee.reactiveworkflows.tictactoe.gameover

import io.reactivex.Observable
import net.blakelee.library.Key
import net.blakelee.library.WorkflowScreen

class GameOverScreen(
        handler: Events
) : WorkflowScreen<Unit, GameOverScreen.Events>(KEY, Observable.just(Unit), handler) {

    companion object { val KEY = Key(this) }
    interface Events
}