package model

interface GameType

open class Round<G : GameType>(
    open val config: GameConfig<G>
) {
    open var gameStatus: GameStatus = GameStatus.NotStarted
}

abstract class Session<G : GameType, R : Round<G>> {
    open var round: R? = null
    abstract val config: GameConfig<G>
    val gameStatus: GameStatus get() = round?.gameStatus ?: GameStatus.NotStarted
    val playedRounds: MutableList<R> = mutableListOf()
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

enum class GameStatus { Playing, Won, Lost, Drawn, NotStarted }