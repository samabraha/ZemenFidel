package model.spelling_sprint

import model.Round
import util.Log
import vm.SpellingSprintRoundUIState
import kotlin.random.Random

class SpellingSprintRound(
    config: SpellingSprintConfig,
    val word: String
) : Round<SpellingSprint>(config = config) {
    var gameStatus: SpellingSprintState = SpellingSprintState.NotStarted

    val wordSnapshot: MutableList<Char> = MutableList(word.length) { ' ' }
    var points = word.length

    var isShowing = false

    fun start() {
        gameStatus = SpellingSprintState.Playing
        isShowing = true
    }

    fun revealLetter() {
        if (points <= 0) return

        var position = Random.Default.nextInt(word.length)

        while (wordSnapshot[position] != ' ') {
            position = Random.Default.nextInt(word.length)
        }

        wordSnapshot[position] = word[position]
        points--

        if (' ' !in wordSnapshot) gameStatus = SpellingSprintState.Lost
    }

    fun freeze() {
        isShowing = false
    }

    fun snapRUIState(roundUIState: SpellingSprintRoundUIState) = roundUIState.copy(
        wordSnapshot = wordSnapshot.toList(),
        points = points,
        isShowing = isShowing,
        gameStatus = gameStatus
    ).also { Log.info("Creating state:") { it.toString() } }

    fun guess(guess: String) {
        if (guess == word) {
            gameStatus = SpellingSprintState.Won
        }
    }
}
