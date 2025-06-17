package vm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import model.memo_challenger.MemoryChallenger
import model.memo_challenger.MemoryChallengerEvent
import model.memo_challenger.MemoryChallengerRound
import model.memo_challenger.MemoryChallengerSession
import kotlin.time.Duration.Companion.seconds


class MemoryChallengerViewModel(override var session: MemoryChallengerSession) :
    GameViewModel<MemoryChallenger, MemoryChallengerEvent, MemoryChallengerRound, MemoryChallengerSession> {
    override val sessionUIState: MemoryChallengerSessionUIState by mutableStateOf(MemoryChallengerSessionUIState())
    override var roundUIState: MemoryChallengerRoundUIState by mutableStateOf(MemoryChallengerRoundUIState())

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    var job: Job? = null

    override fun handleEvent(event: MemoryChallengerEvent) {
        when (event) {
            is MemoryChallengerEvent.Guess -> guess(event.guess)
            MemoryChallengerEvent.ShowWords -> showWords()
            MemoryChallengerEvent.Start -> start()
        }
    }

    fun start() {
        session.start()
    }

    fun guess(guess: String) {
        session.guess(guess = guess)
    }

    fun showWords() {
        session.round?.let {
            if (job?.isActive == true) return
            job = scope.launch {
                while (it.isShowing) {
                    it.showNext()
                    delay(session.delayTime.seconds)
                }
            }
        }
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

data class MemoryChallengerSessionUIState(val name: String = "") : SessionUIState<MemoryChallenger>
data class MemoryChallengerRoundUIState(val playedWords: List<String> = emptyList(), val isShowing: Boolean = false) :
    RoundUIState<MemoryChallenger>