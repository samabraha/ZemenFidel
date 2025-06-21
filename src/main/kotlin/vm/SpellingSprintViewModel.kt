package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import model.spelling_sprint.*
import util.Log

class SpellingSprintViewModel(override var session: SpellingSprintSession) :
    GameViewModel<SpellingSprint, SpellingSprintEvent, SpellingSprintRound, SpellingSprintSession> {
    override val sessionUIState: SpellingSprintSessionUIState by mutableStateOf(SpellingSprintSessionUIState())
    override var roundUIState: SpellingSprintRoundUIState by mutableStateOf(SpellingSprintRoundUIState())
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    var revealJob: Job? = null

    override fun handleEvent(event: SpellingSprintEvent) {
        Log.info("SpellingSprintEvent") { "$event" }
        when (event) {
            SpellingSprintEvent.Start -> start()
            SpellingSprintEvent.ShowHints -> showHints()
            SpellingSprintEvent.Freeze -> freeze()
            is SpellingSprintEvent.Guess -> guess(event.guess)
        }

        setRUIState()
    }

    fun start() {
        session.start()
        showHints()
    }

    fun showHints() {
        session.round?.let {
            if (revealJob?.isActive == true) return

            revealJob = scope.launch {
                while (it.isShowing && it.gameStatus == SpellingSprintState.Playing) {
                    Log.info("ShowLetter") { "isShowing:${it.isShowing}, ${it.wordSnapshot}" }
                    delay(session.config.viewTime)
                    session.revealLetter()
                    setRUIState()
                }
            }
        }
    }

    fun freeze() {
        session.freeze()
        scope.cancel()
        revealJob?.cancel()
    }

    fun guess(guess: String) {
        session.guess(guess = guess)
    }

    fun setRUIState() {
        session.round?.let {
            val newState = it.snapRUIState(roundUIState)
            if (newState != roundUIState) {
                roundUIState = newState
            }
        }
    }
}


data class SpellingSprintSessionUIState(val name: String = "") : SessionUIState<SpellingSprint>
data class SpellingSprintRoundUIState(
    val wordSnapshot: List<Char> = emptyList(),
    val points: Int = 0,
    val isShowing: Boolean = false,
    val gameStatus: SpellingSprintState = SpellingSprintState.NotStarted
) : RoundUIState<SpellingSprint>