package model

interface GameType

interface Round<G : GameType>
interface Session<G : GameType> {
    var round: Round<in G>?
}

interface GameEvent<G : GameType>

interface GameConfig<G : GameType>

