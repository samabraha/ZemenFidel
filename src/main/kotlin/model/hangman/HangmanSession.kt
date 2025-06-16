package model.hangman

import model.Round
import model.Session
import model.jumble.JumbleRound

class HangmanSession(val words: List<String>) : Session<Hangman, HangmanRound>() {
    override var round: HangmanRound? = null

    val currentRound get() = round as HangmanRound?

    fun start() {
        round = HangmanRound(words.random())
    }

    fun guess(letter: Char) {
        currentRound?.guess(letter)
    }
}