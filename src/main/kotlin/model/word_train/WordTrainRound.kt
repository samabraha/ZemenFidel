package model.word_train

import model.GameStatus
import model.Round

class WordTrainRound(config: WordTrainConfig, val word: String) : Round<WordTrain>(config = config) {
    override var gameStatus: GameStatus = GameStatus.Playing
}