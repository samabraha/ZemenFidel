package model.hangman

import model.Round
import model.Session

class HangmanSession(val words: List<String>) : Session<Hangman> {
    override var round: Round<in Hangman>?
        get() = TODO("Not yet implemented")
        set(value) {}

    val currentRound get() = round as HangmanRound?
}