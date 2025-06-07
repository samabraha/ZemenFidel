package model.spelling_sprint

import model.Round
import model.Session

class SpellingSprintSession(val words: List<String>) : Session<SpellingSprint> {
    override var round: Round<in SpellingSprint>? = null
        get() = TODO("Not yet implemented")

    val currentRound get() = round as SpellingSprintRound?
}