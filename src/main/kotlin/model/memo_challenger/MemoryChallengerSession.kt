package model.memo_challenger

import model.Session

class MemoryChallengerSession(
    val words: List<String>
) : Session<MemoryChallenger, MemoryChallengerRound>() {
    override var round: MemoryChallengerRound? = null
    override val config = MemoryChallengerConfig()

    fun start() {
        round = MemoryChallengerRound(config = config, words = words.shuffled())
        round?.start()
    }

    fun guess(guess: String) {
        round?.guess(guess = guess)
    }
}