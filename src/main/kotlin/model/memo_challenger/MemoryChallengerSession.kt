package model.memo_challenger

import model.Round
import model.Session

class MemoryChallengerSession(val words: List<String>) : Session<MemoryChallenger, MemoryChallengerRound>() {
    override var round: MemoryChallengerRound? = null
        get() = TODO("Not yet implemented")

    val currentRound get() = round as MemoryChallengerRound?

}