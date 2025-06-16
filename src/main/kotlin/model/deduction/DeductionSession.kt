package model.deduction

import model.GameStatus
import model.Session

class DeductionSession(val words: List<String>) : Session<Deduction, DeductionRound>() {
    override var round: DeductionRound? = null
    val status get() = round?.gameStatus ?: GameStatus.NotStarted

    fun startNewRound() {
        round = DeductionRound(words.random())

    }

    fun guess(guess: String) {
        round?.guess(guess)
        if (round?.gameStatus != GameStatus.Playing) {
            startNewRound()
        }
    }
}