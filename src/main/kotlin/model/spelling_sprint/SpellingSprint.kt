package model.spelling_sprint

import model.GameEvent
import model.GameType

interface SpellingSprint : GameType

sealed class SpellingSprintEvent : GameEvent<SpellingSprint> {
    data object Start : SpellingSprintEvent()
    data object ShowHints : SpellingSprintEvent()
    data object Freeze : SpellingSprintEvent()
    data class Guess(val guess: String) : SpellingSprintEvent()
}
