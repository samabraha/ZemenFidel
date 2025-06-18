package model.hangman

import model.GameStatus
import model.Round
import util.Log
import vm.HangmanRoundUIState

class HangmanRound(config: HangmanConfig, val word: String) : Round<Hangman>(config = config) {
    override var gameStatus: GameStatus = GameStatus.Playing
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

        if (guessLetters.none { it == ' ' }) gameStatus = GameStatus.Won
        if (lives == 0) gameStatus = GameStatus.Lost

        Log.info("guess") { "guess: '$guess', gl: $guessLetters" }
    }

    fun snapRUIState(currentState: HangmanRoundUIState): HangmanRoundUIState {
        return currentState.copy(
            word = word,
            lives = lives,
            gameStatus = gameStatus,
            guesses = guessLetters.toList(),
            tries = incorrectLetters.toList()
        )
    }
}