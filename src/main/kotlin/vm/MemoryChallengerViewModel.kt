package vm

import model.memo_challenger.MemoryChallenger
import model.memo_challenger.MemoryChallengerEvent
import model.memo_challenger.MemoryChallengerRound
import model.memo_challenger.MemoryChallengerSession


class MemoryChallengerViewModel(override var session: MemoryChallengerSession) :
    GameViewModel<MemoryChallenger, MemoryChallengerEvent, MemoryChallengerRound, MemoryChallengerSession> {
    override val sessionUIState: MemoryChallengerSessionUIState
        get() = TODO("Not yet implemented")
    override val roundUIState: MemoryChallengerRoundUIState
        get() = TODO("Not yet implemented")

    override fun handleEvent(event: MemoryChallengerEvent) {
        TODO("Not yet implemented")
    }
}

data class MemoryChallengerSessionUIState(val name: String) : SessionUIState<MemoryChallenger>
data class MemoryChallengerRoundUIState(val name: String) : RoundUIState<MemoryChallenger>