package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import logger
import model.jumble.Jumble
import model.jumble.JumbleEvent
import model.jumble.JumbleRound
import model.jumble.JumbleSession

class JumbleViewModel(override var session: JumbleSession) : GameViewModel<Jumble, JumbleEvent, JumbleSession> {
    override var sessionUIState: JumbleSessionUIState by mutableStateOf(snapSUIState())
    override var roundUIState: JumbleRoundUIState by mutableStateOf(
        session.currentRound?.snapRUIState() ?: JumbleRoundUIState.EMPTY
    )

    init {
        logger.info("JumbleViewModel initialized with session: $session")
        session.startNewRound()
        session.currentRound?.let {
            roundUIState = it.snapRUIState()
        }
    }

    override fun handleEvent(event: JumbleEvent) {
        println("Handling JumbleEvent: $event")
        when (event) {
            is JumbleEvent.Guess -> {
                session.handleGuess(event.guess)
                session.currentRound?.let {
                    roundUIState = it.snapRUIState()
                }
                sessionUIState = snapSUIState()
            }

            JumbleEvent.Shuffle -> {
                roundUIState =
                    roundUIState.copy(jumbledWord = roundUIState.jumbledWord.toList().shuffled().joinToString(""))
            }

            is JumbleEvent.MoveLetter -> {
                val letter = roundUIState.jumbledWord[event.index]
                roundUIState = roundUIState.copy(
                    jumbledWord = roundUIState.jumbledWord.removeRange(event.index, event.index + 1),
                    guess = roundUIState.guess + letter,
                )
                if (roundUIState.jumbledWord.isEmpty()) {
                    handleEvent(JumbleEvent.Guess(roundUIState.guess))
                }
            }

            JumbleEvent.Undo -> {
                if (roundUIState.guess.isNotEmpty()) {
                    roundUIState = roundUIState.copy(
                        jumbledWord = roundUIState.jumbledWord + roundUIState.guess.last(),
                        guess = roundUIState.guess.dropLast(1),
                    )
                }
            }
        }
    }

    fun snapSUIState(): JumbleSessionUIState {
        return JumbleSessionUIState(rounds = session.rounds.map(JumbleRound::snapRUIState))
    }
}

data class JumbleSessionUIState(val rounds: List<JumbleRoundUIState>) : SessionUIState<Jumble>

data class JumbleRoundUIState(
    val jumbledWord: String,
    val correctWord: String,
    val guess: String = "",
    val attempts: Int,
) : RoundUIState<Jumble> {
    companion object {
        val EMPTY = JumbleRoundUIState("", "", "", 0)
    }
}
