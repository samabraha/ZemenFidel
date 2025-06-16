package model.jumble

import model.Session
import util.Log

class JumbleSession(val words: List<String>) : Session<Jumble, JumbleRound>() {
    override var round: JumbleRound? = null
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
            Log.info("startNewRound", { "Ending current Jumble round with word: ${it.word}" })
            playedRounds.add(it)
        }

        round = JumbleRound(words.random())
    }
}