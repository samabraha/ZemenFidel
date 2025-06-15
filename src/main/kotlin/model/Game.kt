package model

interface GameType

open class Round<G : GameType> {
    open var gameStatus: GameStatus = GameStatus.NotStarted
}

open class Session<G : GameType> {
    open var round: Round<in G>? = null
    val gameStatus: GameStatus get() = round?.gameStatus ?: GameStatus.NotStarted
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

enum class GameStatus { Playing, Won, Lost, Drawn, NotStarted }