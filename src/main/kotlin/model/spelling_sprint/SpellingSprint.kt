package model.spelling_sprint

import model.GameConfig
import model.GameEvent
import model.GameType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface SpellingSprint : GameType

sealed class SpellingSprintEvent : GameEvent<SpellingSprint> {
    data object Start : SpellingSprintEvent()
    data object ShowHints : SpellingSprintEvent()
    data object Freeze : SpellingSprintEvent()
    data class Guess(val guess: String) : SpellingSprintEvent()
}

data class SpellingSprintConfig(val viewTime: Duration = 3.seconds) : GameConfig<SpellingSprint>

enum class SpellingSprintState { Won, Lost, Playing, NotStarted }
