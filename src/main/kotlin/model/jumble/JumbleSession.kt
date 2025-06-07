package model.jumble

import logger
import model.Round
import model.Session

class JumbleSession(val words: List<String>) : Session<Jumble> {
    override var round: Round<in Jumble>? = null

    val currentRound get() = round as? JumbleRound

    fun handleGuess(guess: String) {
        val isCorrect = currentRound?.guess(guess)
    }

    fun startNewRound() {
        round = JumbleRound(words.first())
        logger.info("Starting new Jumble round with word: ${words.first()}")
    }
}