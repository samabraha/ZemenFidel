package model

interface GameType

open class Round<G : GameType> {
    open var gameStatus: GameStatus = GameStatus.NotStarted
}

open class Session<G : GameType, R : Round<G>> {
    open var round: R? = null
    val gameStatus: GameStatus get() = round?.gameStatus ?: GameStatus.NotStarted
    val playedRounds: MutableList<R> = mutableListOf()
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

enum class GameStatus { Playing, Won, Lost, Drawn, NotStarted }