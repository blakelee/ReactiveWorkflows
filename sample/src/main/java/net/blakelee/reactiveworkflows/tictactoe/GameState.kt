package net.blakelee.reactiveworkflows.tictactoe

import java.util.*

enum class StateOfPlay {
    PLAYING, VICTORY, DRAW
}

enum class Mark {
    EMPTY, X, O
}

data class Player(
        val id: String,
        val name: String
)

data class GameState(
        val id: String,
        val playerX: Player,
        val playerO: Player,
        val stateOfPlay: StateOfPlay,
        val grid: List<List<Mark>>,
        val activePlayerId: String
) {
    companion object {
        fun newGame(id: String, playerX: Player, playerO: Player): GameState {

            val players = listOf(playerX, playerO)
            val randomPerson = players[(Random().nextInt(players.size))]

            val list = listOf(Mark.EMPTY, Mark.EMPTY, Mark.EMPTY)

            return GameState(id, playerX, playerO, StateOfPlay.PLAYING,
                    listOf(list, list, list), randomPerson.id)
        }
    }
}