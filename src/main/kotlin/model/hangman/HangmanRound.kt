package model.hangman

import model.GameOutcome
import model.LifecycleState
import model.Round
import util.GeezUtil.isSiblingsWith
import util.Log
import vm.HangmanRoundUIState

class HangmanRound(config: HangmanConfig, val word: String) : Round<Hangman>(config = config) {
    var gameState: LifecycleState = LifecycleState.Started
    var outcome: GameOutcome = GameOutcome.Ongoing
    val guessLetters = MutableList(word.length) { ' ' }
    var lives = word.length * 3
    val incorrectLetters = MutableList(lives) { ' ' }

    fun guess(guess: Char) {
        if (guess in guessLetters) return
        if (guess in incorrectLetters) return
        if (word.any { guess.isSiblingsWith(it) }) {
            word.forEachIndexed { i, l ->
                if (guess.isSiblingsWith(l)) {
                    guessLetters[i] = word[i]
                }
            }
        } else {
            incorrectLetters[incorrectLetters.size - lives--] = guess
        }

        if (guessLetters.none { it == ' ' }) {
            gameState = LifecycleState.Ended
            outcome = GameOutcome.Won
        }
        if (lives == 0) {
            gameState = LifecycleState.Ended
            outcome = GameOutcome.Lost
        }

        Log.info("guess()") { "guess: '$guess', gl: $guessLetters" }
    }


    fun snapRUIState(currentState: HangmanRoundUIState): HangmanRoundUIState {
        return currentState.copy(
            word = word,
            lives = lives,
            gameStatus = gameState,
            outcome = outcome,
            guesses = guessLetters.toList(),
            tries = incorrectLetters.toList()
        )
    }
}