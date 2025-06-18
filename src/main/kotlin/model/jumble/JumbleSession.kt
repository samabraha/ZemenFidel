package model.jumble

import model.Session
import util.Log

class JumbleSession(
    val words: List<String>
) : Session<Jumble, JumbleRound>() {
    override var round: JumbleRound? = null
    override val config = JumbleConfig()

    fun handleGuess(guess: String) {
        round?.let {
            val isCorrect = it.guess(guess)
            if (isCorrect) {
                startNewRound()
            }
        }
    }

    fun startNewRound() {
        round?.let {
            Log.info("startNewRound", { "Ending current Jumble round with word: ${it.word}" })
            playedRounds.add(it)
        }

        round = JumbleRound(config = config, word = words.random())
    }
}