package model.deduction

import model.GameStatus
import model.Round
import vm.DeductionRoundUIState

class DeductionRound(val word: String) : Round<Deduction>() {
    override var gameStatus: GameStatus = GameStatus.Playing
    var score: DeductionWordScore = DeductionWordScore(
        word = word,
        guess = "",
        status = emptyList()
    )
    var attempts = 0

    init {
        require(word.isNotEmpty()) { "Word cannot be empty" }
        require(word.all { it.isLetter() }) { "Word must contain only letters" }
        require(word.length in 3..12) { "Word must be between 3 and 10 characters long" }
    }

    fun guess(guess: String) {
        attempts++
        this.score = DeductionWordScore.fromGuess(word = word, guess = guess)
        if (this.score.correct == word.length) {
            gameStatus = GameStatus.Won
        }
    }

    enum class MatchStatus { Correct, Misplaced, Incorrect }

    data class DeductionWordScore(
        val word: String,
        val guess: String,
        val correct: Int = 0,
        val misplaced: Int = 0,
        val incorrect: Int = 0,
        val status: List<MatchStatus>
    ) {
        fun snapRUIState(roundUIState: DeductionRoundUIState) = roundUIState.copy(
            word = word,
            guess = guess,
            correct = correct,
            misplaced = misplaced,
            incorrect = incorrect
        )

        companion object {
            fun fromGuess(word: String, guess: String): DeductionWordScore {
                require(guess.isNotEmpty()) { "Guess cannot be empty" }
                require(guess.length == word.length) {
                    "Guess must be the same length as the word\n" +
                            "word:$word guess.length: ${guess.length}, word.length: ${word.length}"
                }

                if (guess == word) {
                    return DeductionWordScore(
                        word = word,
                        guess = guess,
                        correct = word.length,
                        misplaced = 0,
                        incorrect = 0,
                        status = List(word.length) { MatchStatus.Correct })
                }

                var (statusList, usedInWord) = createLists(guess = guess, word = word)

                if (statusList.all { it == MatchStatus.Incorrect }) {
                    return DeductionWordScore(
                        word = word,
                        guess = guess,
                        correct = 0,
                        misplaced = 0,
                        incorrect = word.length,
                        status = statusList
                    )
                }

                statusList = statusList.map { if (it == MatchStatus.Misplaced) MatchStatus.Incorrect else it }
                    .toMutableList()

                for (i in guess.indices) {
                    if (statusList[i] != MatchStatus.Correct) {
                        val index = word.indices.firstOrNull { !usedInWord[it] && word[it] == guess[i] }

                        if (index != null) {
                            statusList[i] = MatchStatus.Misplaced
                            usedInWord[index] = true
                        }
                    }
                }

                val correct = statusList.count { it == MatchStatus.Correct }
                val misplaced = statusList.count { it == MatchStatus.Misplaced }
                val incorrect = statusList.count { it == MatchStatus.Incorrect }

                return DeductionWordScore(
                    word = word,
                    guess = guess,
                    correct = correct,
                    misplaced = misplaced,
                    incorrect = incorrect,
                    status = statusList
                )
            }


            private fun createLists(
                guess: String,
                word: String
            ): Pair<MutableList<MatchStatus>, BooleanArray> {
                val statusList = MutableList(guess.length) { MatchStatus.Misplaced }
                val usedInWord = BooleanArray(word.length) { false }

                for (i in guess.indices) {
                    if (guess[i] !in word) {
                        statusList[i] = MatchStatus.Incorrect
                    }

                    if (guess[i] == word[i]) {
                        statusList[i] = MatchStatus.Correct
                        usedInWord[i] = true
                    }
                }
                return Pair(statusList, usedInWord)
            }

            val DEFAULT = DeductionWordScore(
                word = "",
                guess = "",
                correct = 0,
                misplaced = 0,
                incorrect = 0,
                status = emptyList()
            )
        }
    }
}
