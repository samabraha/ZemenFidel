package model.spelling_sprint

import model.Round
import model.Session

class SpellingSprintSession(val words: List<String>) : Session<SpellingSprint, SpellingSprintRound>() {
    override var round: SpellingSprintRound? = null
        get() = TODO("Not yet implemented")

}