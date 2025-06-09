package model.word_train

import model.GameStatus
import model.Round

class WordTrainRound(val word: String) : Round<WordTrain> {
    override var gameStatus: GameStatus = GameStatus.Playing

}