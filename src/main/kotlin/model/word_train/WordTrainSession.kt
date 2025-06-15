package model.word_train

import model.Round
import model.Session

class WordTrainSession(val words: List<String>) : Session<WordTrain>() {
    override var round: Round<in WordTrain>? = null
        get() = TODO("Not yet implemented")

    val currentRound get() = round as? WordTrainRound

}