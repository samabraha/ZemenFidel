package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.GameOutcome
import model.LifecycleState
import model.hangman.Hangman
import model.hangman.HangmanEvent
import model.hangman.HangmanRound
import model.hangman.HangmanSession
import util.Log

class HangmanViewModel(override var session: HangmanSession) :
    GameViewModel<Hangman, HangmanEvent, HangmanRound, HangmanSession> {
    override val sessionUIState: HangmanSessionUIState by mutableStateOf(HangmanSessionUIState())
    override var roundUIState: HangmanRoundUIState by mutableStateOf(HangmanRoundUIState())

    init {
        Log.info("HangmanViewModel", { "initialized with session: $session" })
        session.start()
    }

    override fun handleEvent(event: HangmanEvent) {
        when (event) {
            is HangmanEvent.Guess -> guess(event)
            HangmanEvent.Start -> start()
        }

        setRoundUIState()
    }

    fun start() {
        session.start()
    }

    fun guess(event: HangmanEvent.Guess) {
        session.guess(event.letter)

    }

    private fun setRoundUIState() {
        session.round?.let {
            val newState = it.snapRUIState(roundUIState)
            if (newState != roundUIState) {
                roundUIState = newState
            }
        }
    }
}

data class HangmanSessionUIState(val name: String = "") : SessionUIState<Hangman>

data class HangmanRoundUIState(
    val word: String = "",
    val guesses: List<Char> = emptyList(),
    val lives: Int = 0,
    val gameStatus: LifecycleState = LifecycleState.NotStarted,
    val outcome: GameOutcome = GameOutcome.Ongoing,
    val tries: List<Char> = emptyList()
) : RoundUIState<Hangman>