package model

interface GameType

interface Round<G : GameType> {
    var gameStatus: GameStatus
}

interface Session<G : GameType> {
    var round: Round<in G>?
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

enum class GameStatus { Playing, Won, Lost, Drawn, NotStarted }