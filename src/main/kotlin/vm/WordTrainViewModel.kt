package vm

import model.word_train.WordTrain
import model.word_train.WordTrainEvent
import model.word_train.WordTrainSession


class WordTrainViewModel(override var session: WordTrainSession) : GameViewModel<WordTrain, WordTrainEvent, WordTrainSession> {
    override val sessionUIState: WordTrainSessionUIState
        get() = TODO("Not yet implemented")
    override val roundUIState: WordTrainRoundUIState
        get() = TODO("Not yet implemented")

    override fun handleEvent(event: WordTrainEvent) {
        TODO("Not yet implemented")
    }
}

data class WordTrainSessionUIState(val name: String) : SessionUIState<WordTrain>
data class WordTrainRoundUIState(val name: String) : RoundUIState<WordTrain>