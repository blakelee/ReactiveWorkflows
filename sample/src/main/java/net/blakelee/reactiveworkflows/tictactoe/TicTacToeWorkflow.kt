package net.blakelee.reactiveworkflows.tictactoe

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.BehaviorSubject
import net.blakelee.library.AbstractViewFactory
import net.blakelee.library.Workflow
import net.blakelee.library.WorkflowScreen
import net.blakelee.reactiveworkflows.tictactoe.confirmquit.ConfirmQuitScreen
import net.blakelee.reactiveworkflows.tictactoe.gameover.GameOverScreen
import net.blakelee.reactiveworkflows.tictactoe.gameplay.GamePlayScreen
import net.blakelee.reactiveworkflows.tictactoe.newgame.NewGameScreen
import java.util.*

class TicTacToeWorkflow(
        gameRunner: GameRunner
) : Workflow<Unit, GameState>,
    NewGameScreen.Events, GamePlayScreen.Events,
    ConfirmQuitScreen.Events, GameOverScreen.Events {

    private val gameStates: Observable<GameState> =
            gameRunner.gameState().startWith(NO_GAME)

    private val quitting = BehaviorSubject.createDefault(false)

    companion object {
        private fun FAKE_ID() = UUID.randomUUID().toString()

        private val NO_GAME = GameState.newGame(FAKE_ID(),
                Player(FAKE_ID(), "X"), Player(FAKE_ID(), "O"))
    }

    val screen = Observables.combineLatest(gameStates, quitting) {
        gameStates, quitting -> update(gameStates, quitting) }.replay(1)

    private fun update(gameState: GameState, quitting: Boolean): WorkflowScreen<*,*> {
        if (quitting) return ConfirmQuitScreen(this)
        if (gameState == NO_GAME) NewGameScreen(this)

        return when(gameState.stateOfPlay) {
            StateOfPlay.PLAYING -> GamePlayScreen(this)
            StateOfPlay.VICTORY, StateOfPlay.DRAW -> GameOverScreen(this)
        }
    }

    override fun makeMove() {

    }

    override fun screen(): Observable<WorkflowScreen<*, *>> = screen

    override val viewFactory: AbstractViewFactory = TicTacToeViewFactory()

}