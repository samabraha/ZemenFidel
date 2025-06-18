package model.memo_challenger

import model.GameStatus
import model.Round
import util.Log
import vm.MemoryChallengerRoundUIState

class MemoryChallengerRound(
    val words: List<String>,
    config: MemoryChallengerConfig
) : Round<MemoryChallenger>(config = config) {
    override var gameStatus: GameStatus = GameStatus.NotStarted
    val playedWords = mutableListOf<String>()
    val answers = mutableListOf<String>()
    var currentWord = ""
    var isShowing = true
    var position = 0
    var maxPosition = 0
    var tries = config.maxTries

    fun start() {
        Log.info { "isShowing: $isShowing cw:$currentWord" }
        addNewWord()
        isShowing = true
        gameStatus = GameStatus.Playing
    }

    fun addNewWord() {
        Log.info("addNW") { "adding new word" }
        if (playedWords.size >= words.size) {
            gameStatus = GameStatus.Won
            return
        }

        var newWord = words.random()
        while (newWord in playedWords) {
            newWord = words.random()
        }
        playedWords += newWord
        maxPosition++
    }

    fun showNext() {
        Log.info { "showNext()" }
        if (position >= maxPosition) {
            switchMode()
            return
        }

        currentWord = playedWords[position++]
    }

    fun guess(guess: String) {
        Log.info("MC-Guessing") { "guess:${guess}, tr:$tries" }
        if (tries == 0) {
            gameStatus = GameStatus.Lost
            return
        }

        if (guess in answers) return

        if (position < maxPosition && guess == playedWords[position]) {
            answers += guess
            position++

            if (position >= maxPosition) {
                switchMode()
            }
        } else {
            tries--
        }
    }

    fun switchMode() {
        Log.info { "$isShowing" }
        if (!isShowing) {
            addNewWord()
            answers.clear()
        }

        isShowing = !isShowing
        position = 0
    }

    fun snapRUIState(
        roundUIState: MemoryChallengerRoundUIState
    ) = roundUIState.copy(
        gameStatus = gameStatus,
        playedWords = playedWords,
        isShowing = isShowing,
        currentWord = currentWord,
        position = position
    ).also {
        Log.info("MC UIState") { "$it" }
    }
}
