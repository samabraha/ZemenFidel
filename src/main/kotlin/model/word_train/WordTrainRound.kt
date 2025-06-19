package model.word_train

import model.Round

class WordTrainRound(config: WordTrainConfig, val word: String) : Round<WordTrain>(config = config) {
    var gameStatus: WordTrainState = WordTrainState.Playing
}