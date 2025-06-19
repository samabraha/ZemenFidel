package model.hangman

import model.Round
import util.Log
import vm.HangmanRoundUIState

class HangmanRound(config: HangmanConfig, val word: String) : Round<Hangman>(config = config) {
    var gameState: HangmanState = HangmanState.Playing
    val guessLetters = MutableList(word.length) { ' ' }
    var lives = word.length * 3
    val incorrectLetters = MutableList(lives) { ' ' }

    fun guess(guess: Char) {
        if (guess in guessLetters) return
        if (guess in incorrectLetters) return
        if (guess in word) {
            word.forEachIndexed { i, l ->
                if (guess == l) {
                    guessLetters[i] = l
                }
            }
        } else {
            incorrectLetters[incorrectLetters.size - lives--] = guess
        }

        if (guessLetters.none { it == ' ' }) gameState = HangmanState.Won
        if (lives == 0) gameState = HangmanState.Lost

        Log.info("guess()") { "guess: '$guess', gl: $guessLetters" }
    }

    fun snapRUIState(currentState: HangmanRoundUIState): HangmanRoundUIState {
        return currentState.copy(
            word = word,
            lives = lives,
            gameStatus = gameState,
            guesses = guessLetters.toList(),
            tries = incorrectLetters.toList()
        )
    }
}