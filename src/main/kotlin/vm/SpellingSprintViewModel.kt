package vm

import model.spelling_sprint.SpellingSprint
import model.spelling_sprint.SpellingSprintEvent
import model.spelling_sprint.SpellingSprintSession

class SpellingSprintViewModel(override var session: SpellingSprintSession) :
    GameViewModel<SpellingSprint, SpellingSprintEvent, SpellingSprintSession> {
    override val sessionUIState: SpellingSprintSessionUIState
        get() = TODO("Not yet implemented")
    override val roundUIState: SpellingSprintRoundUIState
        get() = TODO("Not yet implemented")

    override fun handleEvent(event: SpellingSprintEvent) {
        TODO("Not yet implemented")
    }
}

data class SpellingSprintSessionUIState(val name: String) : SessionUIState<SpellingSprint>
data class SpellingSprintRoundUIState(val name: String) : RoundUIState<SpellingSprint>