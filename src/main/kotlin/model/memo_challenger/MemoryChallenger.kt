package model.memo_challenger

import model.GameEvent
import model.GameType

interface MemoryChallenger : GameType

sealed class MemoryChallengerEvent : GameEvent<MemoryChallenger> {
    data object Start : MemoryChallengerEvent()
    data object ShowWords : MemoryChallengerEvent()
    data class Guess(val guess: String) : MemoryChallengerEvent()
}