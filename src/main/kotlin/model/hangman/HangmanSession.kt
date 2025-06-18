package model.hangman

import model.Session

class HangmanSession(
    val words: List<String>
) : Session<Hangman, HangmanRound>() {
    override var round: HangmanRound? = null
    override val config = HangmanConfig()


    fun start() {
        round = HangmanRound(config = config, word = words.random())
    }

    fun guess(letter: Char) {
        round?.guess(letter)
    }
}