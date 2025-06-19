package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import model.memo_challenger.*
import util.Log


class MemoryChallengerViewModel(override var session: MemoryChallengerSession) :
    GameViewModel<MemoryChallenger, MemoryChallengerEvent, MemoryChallengerRound, MemoryChallengerSession> {
    override val sessionUIState: MemoryChallengerSessionUIState by mutableStateOf(MemoryChallengerSessionUIState())
    override var roundUIState: MemoryChallengerRoundUIState by mutableStateOf(MemoryChallengerRoundUIState())

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    var job: Job? = null

    override fun handleEvent(event: MemoryChallengerEvent) {
        Log.info("MemoryChallengerEvent") { "$event" }
        when (event) {
            MemoryChallengerEvent.Start -> start()
            MemoryChallengerEvent.ShowWords -> showWords()
            is MemoryChallengerEvent.Guess -> guess(event.guess)
        }

        setRUIState()
    }

    fun start() {
        session.start()
    }

    fun showWords() {
        Log.info("showWords") { "${session.round}" }
        session.round?.let {
            if (job?.isActive == true) return
            job = scope.launch {
                while (it.isShowing) {
                    it.showNext()
                    setRUIState()
                    delay(session.config.showTime)
                }
                setRUIState()
            }
        }
    }

    fun guess(guess: String) {
        session.guess(guess = guess)
        session.round?.let {
            if (it.isShowing) {

                showWords()
            }
        }
    }

    fun setRUIState() {
        Log.info { "MCVM: $roundUIState" }
        session.round?.let {
            val newState = it.snapRUIState(roundUIState)
            if (newState != roundUIState) {
                roundUIState = newState
            }
        }
    }
}

data class MemoryChallengerSessionUIState(val name: String = "") : SessionUIState<MemoryChallenger>
data class MemoryChallengerRoundUIState(
    val playedWords: List<String> = emptyList(),
    val isShowing: Boolean = false,
    val gameStatus: MemoryChallengerState = MemoryChallengerState.NotStarted,
    val currentWord: String = "",
    val position: Int = 0
) : RoundUIState<MemoryChallenger>