package model.hangman

import model.GameStatus
import model.Round

class HangmanRound(val word: String) : Round<Hangman> {
    override var gameStatus: GameStatus = GameStatus.Playing
}