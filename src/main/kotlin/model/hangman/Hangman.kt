package model.hangman

import model.GameEvent
import model.GameType

interface Hangman : GameType

sealed class HangmanEvent : GameEvent<Hangman> {
    data object Start : HangmanEvent()
    data class Guess(val letter: Char) : HangmanEvent()

}