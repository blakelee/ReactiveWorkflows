package net.blakelee.reactiveworkflows.tictactoe

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.subjects.BehaviorSubject
import java.util.*

class GameRunner {
    sealed class Command {

        data class NewGame(val xPlayer: String, val oPlayer: String) : Command()

        data class RestoreGame(val id: String) : Command()

        data class TakeSquare(val row: Int, val col: Int) : Command()

        object End : Command()
    }

    private val currentState: BehaviorSubject<GameState> = BehaviorSubject.create()

    private val transformer: ObservableTransformer<Command, GameState> =
            ObservableTransformer {
                it.map {
                    when(it) {
                        is Command.NewGame -> newGame(it)
                        is Command.RestoreGame -> restoreGame(it)
                        is Command.TakeSquare -> takeSquare(it)
                        is Command.End -> end(it)
                    }
                }
            }

    fun gameState(): Observable<GameState> = currentState

    fun <T: Observable<GameState>>startWith(gameState: GameState): Observable<GameState> {
        currentState.onNext(gameState)
        return gameState()
    }

    private fun newGame(): GameState {
        fun FAKE_ID() = UUID.randomUUID().toString()

        return GameState.newGame(FAKE_ID(),
                Player(FAKE_ID(), "X"), Player(FAKE_ID(), "O"))
    }

    private fun restoreGame(restoreGame: Command.RestoreGame) = newGame()

    private fun takeSquare(takeSquare: Command.TakeSquare): GameState {
        return GameState.newGame("", Player("", ""), Player("", ""))
    }

    private fun newGame(newGame: Command.NewGame): GameState {
        return GameState.newGame("", Player("", ""), Player("", ""))
    }

    private fun end(end: Command.End): GameState {
        return GameState.newGame("", Player("", ""), Player("", ""))
    }


    fun asTransformer(): ObservableTransformer<Command, GameState> = transformer
}