package model.deduction

import model.Round

class DeductionRound(val word: String) : Round<Deduction> {
    fun guess(guess: String): DeductionWordScore {
        return TODO()
    }
}

data class DeductionWordScore(
    val word: String,
    val guess: String,
    val correct: Int,
    val misplaced: Int,
    val incorrect: Int
) {

}