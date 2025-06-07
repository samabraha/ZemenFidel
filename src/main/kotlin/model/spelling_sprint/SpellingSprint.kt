package model.spelling_sprint

import model.GameEvent
import model.GameType

interface SpellingSprint : GameType

sealed class SpellingSprintEvent : GameEvent<SpellingSprint> {
}