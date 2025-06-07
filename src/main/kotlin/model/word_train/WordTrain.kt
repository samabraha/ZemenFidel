package model.word_train

import model.GameEvent
import model.GameType

interface WordTrain : GameType

sealed class WordTrainEvent : GameEvent<WordTrain> {
}