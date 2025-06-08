package model.deduction

import model.Round

class DeductionRound(val word: String) : Round<Deduction> {
    fun guess(guess: String): DeductionWordScore {
        require(guess.isNotEmpty()) {
            throw IllegalArgumentException("Guess cannot be empty")
        }

        require(guess.length == word.length) {
            throw IllegalArgumentException("Guess must be the same length as the word")
        }

        val status = MutableList(guess.length) { MatchStatus.Incorrect }
        val usedInWord = BooleanArray(word.length) { false }

        for (i in guess.indices) {
            if (i < word.length && guess[i] == word[i]) {
                status[i] = MatchStatus.Correct
                usedInWord[i] = true
            }
        }

        for (i in guess.indices) {
            if (status[i] != MatchStatus.Correct) {
                val index = word.indices.firstOrNull {
                    !usedInWord[it] && word[it] == guess[i]
                }
                if (index != null) {
                    status[i] = MatchStatus.Misplaced
                    usedInWord[index] = true
                }
            }
        }

        val correct = status.count { it == MatchStatus.Correct }
        val misplaced = status.count { it == MatchStatus.Misplaced }
        val incorrect = status.count { it == MatchStatus.Incorrect }

        return DeductionWordScore(word, guess, correct, misplaced, incorrect, status)
    }
}

enum class MatchStatus { Correct, Misplaced, Incorrect }

data class DeductionWordScore(
    val word: String,
    val guess: String,
    val correct: Int,
    val misplaced: Int,
    val incorrect: Int,
    val status: List<MatchStatus> = emptyList()
)