package model.jumble

import model.Round
import vm.JumbleRoundUIState

class JumbleRound(val word: String) : Round<Jumble> {
    var jumbledWord: String = word.toList().shuffled().joinToString("")
    private var guess: String = ""
    private var attempts: Int = 0

    fun guess(guess: String): Boolean {
        attempts++
        this.guess = guess
        reset()
        return guess.equals(word, ignoreCase = true)
    }

    fun reset() {
        jumbledWord = word.toList().shuffled().joinToString("")
        guess = ""
    }


    fun snapRUIState() = JumbleRoundUIState(
        jumbledWord = jumbledWord,
        correctWord = word,
        guess = guess,
        attempts = attempts,
    )
}