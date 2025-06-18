package model.spelling_sprint

import model.Session

class SpellingSprintSession(val words: List<String>) : Session<SpellingSprint, SpellingSprintRound>() {
    override var round: SpellingSprintRound? = null
    override val config: SpellingSprintConfig = SpellingSprintConfig()

    fun start() {
        round = SpellingSprintRound(config = config, word = words.random())
        round?.start()
    }

    fun freeze() {
        round?.freeze()
    }

    fun revealLetter() {
        round?.revealLetter()
    }

    fun guess(guess: String) {
        round?.guess(guess = guess)
    }
}
