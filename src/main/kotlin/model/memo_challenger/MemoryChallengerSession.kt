package model.memo_challenger

import model.Session

class MemoryChallengerSession(val words: List<String>) : Session<MemoryChallenger, MemoryChallengerRound>() {
    override var round: MemoryChallengerRound? = null
    var delayTime = 4

    fun start() {
        round = MemoryChallengerRound(words.shuffled())
        round?.start()
    }

    fun guess(guess: String) {
        round?.guess(guess = guess)
    }
}