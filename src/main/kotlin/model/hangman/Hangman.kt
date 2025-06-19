package model.hangman

import model.GameConfig
import model.GameEvent
import model.GameType

interface Hangman : GameType

sealed class HangmanEvent : GameEvent<Hangman> {
    data object Start : HangmanEvent()
    data class Guess(val letter: Char) : HangmanEvent()

}

data class HangmanConfig(val name: String = "") : GameConfig<Hangman>

enum class HangmanState { Playing, Lost, Won , NotStarted}
