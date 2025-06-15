package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import logger
import model.GameStatus
import model.hangman.Hangman
import model.hangman.HangmanEvent
import model.hangman.HangmanSession

class HangmanViewModel(override var session: HangmanSession) : GameViewModel<Hangman, HangmanEvent, HangmanSession> {
    override val sessionUIState: HangmanSessionUIState by mutableStateOf(HangmanSessionUIState.DEFAULT)
    override var roundUIState: HangmanRoundUIState by mutableStateOf(HangmanRoundUIState.DEFAULT)

    init {
        logger.info("HangmanViewModel initialized with session: $session")
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
        logger.info("setting uiState")
        session.currentRound?.let {
            roundUIState = it.snapRUIState(roundUIState)
        }
    }
}

data class HangmanSessionUIState(val name: String = "") : SessionUIState<Hangman> {
    companion object {
        val DEFAULT = HangmanSessionUIState()
    }
}

data class HangmanRoundUIState(
    val word: String,
    val guessWord: List<Char>,
    val lives: Int = 0,
    val gameStatus: GameStatus = GameStatus.NotStarted
) : RoundUIState<Hangman> {
    companion object {
        val DEFAULT = HangmanRoundUIState(word = "", guessWord = emptyList())
    }
}