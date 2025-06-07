package model.deduction

import model.GameEvent
import model.GameType

interface Deduction : GameType

sealed class DeductionEvent : GameEvent<Deduction> {
    data class Guess(val guess: String) : DeductionEvent()
}
