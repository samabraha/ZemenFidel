package vm

import model.GameEvent
import model.hangman.Hangman
import model.hangman.HangmanEvent
import model.hangman.HangmanSession

class HangmanViewModel(override var session: HangmanSession) : GameViewModel<Hangman, HangmanEvent, HangmanSession> {
    override val sessionUIState: HangmanSessionUIState
        get() = TODO("Not yet implemented")
    override val roundUIState: HangmanRoundUIState
        get() = TODO("Not yet implemented")

    override fun handleEvent(event: HangmanEvent) {
        when (event) {
            is HangmanEvent.Guess -> TODO(  )
            HangmanEvent.Start -> TODO()
        }
    }
}

data class HangmanSessionUIState(val name: String) : SessionUIState<Hangman>
data class HangmanRoundUIState(val name: String) : RoundUIState<Hangman>