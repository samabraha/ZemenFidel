package vm

import model.word_train.*


class WordTrainViewModel(override var session: WordTrainSession) :
    GameViewModel<WordTrain, WordTrainEvent, WordTrainRound, WordTrainSession> {
    override val sessionUIState: WordTrainSessionUIState
        get() = TODO("Not yet implemented")
    override val roundUIState = WordTrainRoundUIState()

    override fun handleEvent(event: WordTrainEvent) {
        TODO("Not yet implemented")
    }
}

data class WordTrainSessionUIState(
    val status: WordTrainState = WordTrainState.NotStarted,
    val rounds: List<WordTrainRoundUIState> = emptyList(),
) : SessionUIState<WordTrain>

data class WordTrainRoundUIState(val points: Int = 0) : RoundUIState<WordTrain>