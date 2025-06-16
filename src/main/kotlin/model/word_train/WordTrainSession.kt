package model.word_train

import model.Round
import model.Session

class WordTrainSession(val words: List<String>) : Session<WordTrain, WordTrainRound>() {
    override var round: WordTrainRound? = null
        get() = TODO("Not yet implemented")


}