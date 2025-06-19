package model.deduction

import model.Session

class DeductionSession(
    val words: List<String>
) : Session<Deduction, DeductionRound>() {
    override var round: DeductionRound? = null
    override val config: DeductionConfig = DeductionConfig()

    fun startNewRound() {
        round = DeductionRound(config = config, word = words.random())

    }

    fun guess(guess: String) {
        round?.guess(guess)
        if (round?.gameStatus != DeductionState.Playing) {
            startNewRound()
        }
    }
}