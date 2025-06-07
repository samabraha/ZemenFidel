package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import model.deduction.Deduction
import model.deduction.DeductionEvent
import model.deduction.DeductionSession


class DeductionViewModel(override var session: DeductionSession) :
    GameViewModel<Deduction, DeductionEvent, DeductionSession> {
    override val sessionUIState: DeductionSessionUIState by mutableStateOf(DeductionSessionUIState())
    override val roundUIState: DeductionRoundUIState by mutableStateOf(DeductionRoundUIState("abc", "def"))

    init {
        session.startNewRound()
    }

    override fun handleEvent(event: DeductionEvent) {
        when (event) {
            is DeductionEvent.Guess -> session.guess(event.guess)
        }
    }
}

data class DeductionSessionUIState(val name: String = "DeductionSUISName") : SessionUIState<Deduction>
data class DeductionRoundUIState(
    val word: String,
    val guess: String,
    val correct: Int = 0,
    val misplaced: Int = 0,
    val incorrect: Int = 0,
) : RoundUIState<Deduction>
