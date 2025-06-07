package model.memo_challenger

import model.Round
import model.Session

class MemoryChallengerSession(val words: List<String>) : Session<MemoryChallenger> {
    override var round: Round<in MemoryChallenger>? = null
        get() = TODO("Not yet implemented")

    val currentRound get() = round as MemoryChallengerRound?

}