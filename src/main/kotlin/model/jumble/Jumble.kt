package model.jumble

import model.GameEvent
import model.GameType

interface Jumble : GameType

sealed class JumbleEvent : GameEvent<Jumble> {
    data class Guess(val guess: String) : JumbleEvent()
    data object Shuffle : JumbleEvent()
    data object Undo : JumbleEvent()
    data class MoveLetter(val index: Int) : JumbleEvent()
}