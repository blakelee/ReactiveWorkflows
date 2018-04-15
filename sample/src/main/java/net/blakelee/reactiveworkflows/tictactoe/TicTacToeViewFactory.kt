package net.blakelee.reactiveworkflows.tictactoe

import net.blakelee.library.AbstractViewFactory
import net.blakelee.reactiveworkflows.R.*
import net.blakelee.reactiveworkflows.tictactoe.gameover.GameOverCoordinator
import net.blakelee.reactiveworkflows.tictactoe.gameover.GameOverScreen
import net.blakelee.reactiveworkflows.tictactoe.gameplay.GamePlayCoordinator
import net.blakelee.reactiveworkflows.tictactoe.gameplay.GamePlayScreen
import net.blakelee.reactiveworkflows.tictactoe.newgame.NewGameCoordinator
import net.blakelee.reactiveworkflows.tictactoe.newgame.NewGameScreen

class TicTacToeViewFactory : AbstractViewFactory(listOf(

        bindLayout(NewGameScreen.KEY, layout.new_game_layout) { screen ->
            NewGameCoordinator(screen as NewGameScreen)
        },

        bindLayout(GamePlayScreen.KEY, layout.game_play_layout) { screen ->
            GamePlayCoordinator(screen as GamePlayScreen)
        },

        bindLayout(GameOverScreen.KEY, layout.game_play_layout) { screen ->
            GameOverCoordinator(screen as GameOverScreen)
        }

))