package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.jumble.Jumble
import model.jumble.JumbleEvent
import model.jumble.JumbleSession

class JumbleViewModel(override var session: JumbleSession) : GameViewModel<Jumble, JumbleEvent, JumbleSession> {
    override val sessionUIState: JumbleSessionUIState
        get() = TODO("Not yet implemented")
    override var roundUIState: JumbleRoundUIState by mutableStateOf((session.currentRound.snapRUIState()))

    override fun handleEvent(event: JumbleEvent) {
        when (event) {
            is JumbleEvent.Guess -> {
                // Handle the guess event
                session.handleGuess(event.guess)
            }

            JumbleEvent.Shuffle -> {
                // Handle the start event
                roundUIState = roundUIState.copy(guess = roundUIState.guess?.toList()?.shuffled()?.joinToString(""))
            }
        }
    }
}


data class JumbleSessionUIState(val name: String) : SessionUIState<Jumble>
data class JumbleRoundUIState(
    val jumbledWord: String,
    val correctWord: String,
    val guess: String?,
    val attempts: Int,
) : RoundUIState<Jumble> {

}