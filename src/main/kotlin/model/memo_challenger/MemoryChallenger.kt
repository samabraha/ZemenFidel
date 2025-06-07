package model.memo_challenger

import model.GameEvent
import model.GameType

interface MemoryChallenger : GameType

sealed class MemoryChallengerEvent : GameEvent<MemoryChallenger> {

}