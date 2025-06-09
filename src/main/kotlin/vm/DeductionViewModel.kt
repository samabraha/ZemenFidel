package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.GameStatus
import model.deduction.Deduction
import model.deduction.DeductionEvent
import model.deduction.DeductionSession


class DeductionViewModel(override var session: DeductionSession) :
    GameViewModel<Deduction, DeductionEvent, DeductionSession> {
    override var sessionUIState: DeductionSessionUIState by mutableStateOf(DeductionSessionUIState())
    override var roundUIState: DeductionRoundUIState by mutableStateOf(DeductionRoundUIState.DEFAULT)

    override fun handleEvent(event: DeductionEvent) {
        println("Handling DeductionEvent: $event")
        when (event) {
            DeductionEvent.Start -> {
                session.startNewRound()
                sessionUIState = sessionUIState.copy(status = GameStatus.Playing)
            }

            is DeductionEvent.Guess -> session.guess(event.guess)
        }
        session.currentRound?.let {
            roundUIState = it.score?.snapRUIState() ?: DeductionRoundUIState.DEFAULT
        }
    }
}

data class DeductionSessionUIState(
    val status: GameStatus = GameStatus.NotStarted,
    val rounds: List<DeductionRoundUIState> = emptyList(),
) : SessionUIState<Deduction>

data class DeductionRoundUIState(
    val word: String,
    val guess: String,
    val correct: Int = 0,
    val misplaced: Int = 0,
    val incorrect: Int = 0,
    val attempts: Int = 0,
) : RoundUIState<Deduction> {
    companion object {
        val DEFAULT = DeductionRoundUIState("", "")
    }
}
