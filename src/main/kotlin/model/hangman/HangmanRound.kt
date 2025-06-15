package model.hangman

import logger
import model.GameStatus
import model.Round
import vm.HangmanRoundUIState

class HangmanRound(val word: String) : Round<Hangman>() {
    override var gameStatus: GameStatus = GameStatus.Playing
    val guessLetters = MutableList(word.length) { ' ' }
    var lives = word.length * 3


    fun guess(guess: Char) {
        if (guess in guessLetters) return
        if (guess in word) {
            word.forEachIndexed { i, l ->
                if (guess == l) {
                    guessLetters[i] = l
                }
            }
        } else {
            lives--
        }

        if (guessLetters.none { it == ' ' }) gameStatus = GameStatus.Won
        if (lives == 0) gameStatus = GameStatus.Lost

        logger.info("guess: '$guess', gl: $guessLetters")
    }

    fun snapRUIState(currentState: HangmanRoundUIState): HangmanRoundUIState {
        return currentState.copy(word = word, guessWord = guessLetters, lives = lives, gameStatus = gameStatus)
    }
}