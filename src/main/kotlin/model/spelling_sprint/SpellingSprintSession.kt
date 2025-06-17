package model.spelling_sprint

import model.Session

class SpellingSprintSession(val words: List<String>) : Session<SpellingSprint, SpellingSprintRound>() {
    override var round: SpellingSprintRound? = null

    val delayTime: Int = 3

    fun start() {
        round = SpellingSprintRound(word = words.random())
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