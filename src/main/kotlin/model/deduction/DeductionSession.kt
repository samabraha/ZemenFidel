package model.deduction

import model.Round
import model.Session

class DeductionSession(val words: List<String>) : Session<Deduction> {
    override var round: Round<in Deduction>? = null
    val currentRound get() = round as? DeductionRound

    fun startNewRound() {
        round = DeductionRound(words.first())
    }

    fun guess(guess: String) {
        currentRound?.guess(guess)
    }


}