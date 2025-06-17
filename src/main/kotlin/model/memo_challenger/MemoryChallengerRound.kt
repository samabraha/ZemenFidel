package model.memo_challenger

import model.GameStatus
import model.Round
import vm.MemoryChallengerRoundUIState

class MemoryChallengerRound(val words: List<String>) : Round<MemoryChallenger>() {
    override var gameStatus: GameStatus = GameStatus.Playing
    val playedWords = mutableListOf<String>()
    val answers = mutableListOf<String>()
    var isShowing = false
    val position = 0
    var maxPosition = 0
    var tries = 10

    fun start() {
        isShowing = true
        gameStatus = GameStatus.Playing
    }

    fun showNext() {
        if ( position <= maxPosition) {
            playedWords[position] = words[position]
        }
    }

    fun guess(guess: String) {
        if (guess in answers) return
        if (guess == playedWords[position]) {
            maxPosition++
            answers += guess
        } else {
            tries--
        }
    }

    fun snapRUIState(roundUIState: MemoryChallengerRoundUIState) = roundUIState.copy(
        playedWords = playedWords,
        isShowing = isShowing
    )

}