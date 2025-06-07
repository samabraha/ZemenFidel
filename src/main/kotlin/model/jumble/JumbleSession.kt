package model.jumble

import logger
import model.Round
import model.Session

class JumbleSession(val words: List<String>) : Session<Jumble> {
    val rounds = mutableListOf<JumbleRound>()

    override var round: Round<in Jumble>? = null

    val currentRound get() = round as? JumbleRound

    fun handleGuess(guess: String) {
        currentRound?.let {
            val isCorrect = it.guess(guess)
            if (isCorrect) {
                startNewRound()
            }
        }
    }

    fun startNewRound() {
        currentRound?.let {
            logger.info("Ending current Jumble round with word: ${it.word}")
            rounds.add(it)
        }

        round = JumbleRound(words.random())
        logger.info("Starting new Jumble round with word: ${words.first()}")
    }
}