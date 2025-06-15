package model.hangman

import model.Round
import model.Session

class HangmanSession(val words: List<String>) : Session<Hangman>() {
    override var round: Round<in Hangman>? = null

    val currentRound get() = round as HangmanRound?

    fun start() {
        round = HangmanRound(words.random())
    }

    fun guess(letter: Char) {
        currentRound?.guess(letter)
    }
}