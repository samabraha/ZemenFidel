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
