package vm

import model.GameEvent
import model.GameType
import model.Round
import model.Session

interface GameViewModel<G : GameType, E : GameEvent<G>, R : Round<G>, S : Session<G, R>> {
    var session: S
    val sessionUIState: SessionUIState<G>
    val roundUIState: RoundUIState<G>

    fun handleEvent(event: E)
}

data class GameInfo<G : GameType>(
    val name: String,
    val screen: Screen,
)

enum class Screen {
    Home, Hangman, Jumble, SpellingSprint, MemoryChallenger, Deduction, WordTrain
}

interface SessionUIState<G : GameType>
interface RoundUIState<G : GameType>
