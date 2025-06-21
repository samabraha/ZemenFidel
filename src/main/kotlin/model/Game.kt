package model

interface GameType

open class Round<G : GameType>(
    open val config: GameConfig<G>
)

abstract class Session<G : GameType, R : Round<G>> {
    open var round: R? = null
    abstract val config: GameConfig<G>
    val playedRounds: MutableList<R> = mutableListOf()
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

sealed interface LifecycleState {
    object NotStarted : LifecycleState
    object Started : LifecycleState
    object Showing : LifecycleState
    object Guessing : LifecycleState
    object Ended : LifecycleState
}

sealed interface GameOutcome {
    object Ongoing : GameOutcome
    object Won : GameOutcome
    object Lost : GameOutcome
    object Drawn : GameOutcome
}

