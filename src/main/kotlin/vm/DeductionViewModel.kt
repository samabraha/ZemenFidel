package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.GameOutcome
import model.LifecycleState
import model.deduction.Deduction
import model.deduction.DeductionEvent
import model.deduction.DeductionRound
import model.deduction.DeductionSession


class DeductionViewModel(override var session: DeductionSession) :
    GameViewModel<Deduction, DeductionEvent, DeductionRound, DeductionSession> {
    override var sessionUIState: DeductionSessionUIState by mutableStateOf(DeductionSessionUIState())
    override var roundUIState: DeductionRoundUIState by mutableStateOf(DeductionRoundUIState())

    override fun handleEvent(event: DeductionEvent) {
        println("Handling DeductionEvent: $event")
        when (event) {
            DeductionEvent.Start -> {
                session.startNewRound()

                sessionUIState = sessionUIState.copy(status = LifecycleState.Started)
            }

            is DeductionEvent.Guess -> session.guess(event.guess)
        }

        setUIState()
        println("Updated DeductionViewModel: roundUIState=$roundUIState")
    }

    fun setUIState() {
        session.round?.let {
            val newState = it.snapRUIState(roundUIState)
            if (newState != roundUIState) {
                roundUIState = newState
            }
        }
    }
}

data class DeductionSessionUIState(
    val status: LifecycleState = LifecycleState.NotStarted,
    val rounds: List<DeductionRoundUIState> = emptyList(),
) : SessionUIState<Deduction>

data class DeductionRoundUIState(
    val word: String = "",
    val guess: String = "",
    val correct: Int = 0,
    val misplaced: Int = 0,
    val incorrect: Int = 0,
    val attempts: Int = 0,
    val statuses: List<DeductionRound.MatchStatus> = emptyList(),
    val state: LifecycleState = LifecycleState.NotStarted,
    val outcome: GameOutcome = GameOutcome.Ongoing
) : RoundUIState<Deduction>
