package model.memo_challenger

import model.GameConfig
import model.GameEvent
import model.GameType
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface MemoryChallenger : GameType

sealed class MemoryChallengerEvent : GameEvent<MemoryChallenger> {
    data object Start : MemoryChallengerEvent()
    data object ShowWords : MemoryChallengerEvent()
    data class Guess(val guess: String) : MemoryChallengerEvent()
}

data class MemoryChallengerConfig(
    val allowShuffledEntry: Boolean = false,
    val showTime: Duration = 2.seconds,
    val maxTries: Int = 5
) : GameConfig<MemoryChallenger>

enum class MemoryChallengerState { Won, Lost, Playing, NotStarted }