package model.deduction

import model.GameStatus
import model.Round
import model.Session

class DeductionSession(val words: List<String>) : Session<Deduction> {
    override var round: Round<in Deduction>? = null
    val currentRound get() = round as? DeductionRound
    val status get() = currentRound?.gameStatus ?: GameStatus.NotStarted

    fun startNewRound() {
        round = DeductionRound(words.random())

    }

    fun guess(guess: String) {
        currentRound?.guess(guess)
        if (currentRound?.gameStatus != GameStatus.Playing) {
            startNewRound()
        }
    }
}