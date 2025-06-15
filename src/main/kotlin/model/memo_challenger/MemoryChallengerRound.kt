package model.memo_challenger

import model.GameStatus
import model.Round

class MemoryChallengerRound(val words: List<String>) : Round<MemoryChallenger>() {
    override var gameStatus: GameStatus = GameStatus.Playing

}