package model.spelling_sprint

import model.GameStatus
import model.Round

class SpellingSprintRound(val word: String) : Round<SpellingSprint> {
    override var gameStatus: GameStatus = GameStatus.Playing

}