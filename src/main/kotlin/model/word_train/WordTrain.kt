package model.word_train

import model.GameConfig
import model.GameEvent
import model.GameType

interface WordTrain : GameType

sealed class WordTrainEvent : GameEvent<WordTrain> {
    data object Start : WordTrainEvent()
    data class Guess(val letter: Char) : WordTrainEvent()
}

data class WordTrainConfig(val name: String = "") : GameConfig<WordTrain>
