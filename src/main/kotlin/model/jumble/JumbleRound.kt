package model.jumble

import model.Round
import vm.JumbleRoundUIState

class JumbleRound(val word: String, config: JumbleConfig) : Round<Jumble>(config = config) {
    var gameStatus: JumbleState = JumbleState.Playing
    var jumbledWord: String
    private var guess: String = ""
    private var attempts: Int = 0

    init {
        jumbledWord = jumbleWord()
    }

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

    fun jumbleWord(): String {
        var jumble = word.toList().shuffled().joinToString("")
        while (jumble == word) {
            jumble = word.toList().shuffled().joinToString("")
        }
        return jumble
    }

}