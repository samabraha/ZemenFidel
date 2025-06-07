package model.jumble

import model.Round
import vm.JumbleRoundUIState

class JumbleRound(val word: String) : Round<Jumble> {
    val jumbledWord: String = word.toList().shuffled().joinToString("")
    private var attempts: Int = 0
    private var latestGuess: String? = null
    fun guess(guess: String): Boolean {
        attempts++
        latestGuess = guess
        return guess.equals(word, ignoreCase = true)
    }


    fun snapRUIState() = JumbleRoundUIState(
        jumbledWord = jumbledWord,
        correctWord = word,
        guess = latestGuess ?: "",
        attempts = attempts,
    )
}