package vm

import data.WordsRepository
import model.deduction.Deduction
import model.deduction.DeductionSession
import model.hangman.Hangman
import model.hangman.HangmanSession
import model.jumble.Jumble
import model.jumble.JumbleSession
import model.memo_challenger.MemoryChallenger
import model.memo_challenger.MemoryChallengerSession
import model.spelling_sprint.SpellingSprint
import model.spelling_sprint.SpellingSprintSession
import model.word_train.WordTrain
import model.word_train.WordTrainSession

class HomeViewModel(wordsRepository: WordsRepository = WordsRepository()) {
    val words = wordsRepository.words
    var currentScreen = Screen.Home

    val hangmanVMProvider: () -> HangmanViewModel =
        { HangmanViewModel(HangmanSession(words)) }
    val jumbleVMProvider: () -> JumbleViewModel =
        { JumbleViewModel(JumbleSession(words)) }
    val spellingSprintVMProvider: () -> SpellingSprintViewModel =
        { SpellingSprintViewModel(SpellingSprintSession(words)) }
    val memoryChallengerVMProvider: () -> MemoryChallengerViewModel =
        { MemoryChallengerViewModel(MemoryChallengerSession(words)) }
    val deductionVMProvider: () -> DeductionViewModel =
        { DeductionViewModel(DeductionSession(words)) }
    val wordTrainVMProvider: () -> WordTrainViewModel =
        { WordTrainViewModel(WordTrainSession(words)) }

    val gameInfoList = listOf(
        GameInfo<Hangman>(name = "Hangman", screen = Screen.Hangman),
        GameInfo<Jumble>(name = "Jumble", screen = Screen.Jumble),
        GameInfo<SpellingSprint>(name = "Spelling Sprint", screen = Screen.SpellingSprint),
        GameInfo<MemoryChallenger>(name = "Memory Challenger", screen = Screen.MemoryChallenger),
        GameInfo<Deduction>(name = "Deduction", screen = Screen.Deduction),
        GameInfo<WordTrain>(name = "Word Train", screen = Screen.WordTrain),
    )

    init {
        println("HomeViewModel initialized with words: ${words.size} words loaded")
    }

    fun startGame(screen: Screen): GameViewModel<*, *, *, *> {
        println("Starting game for screen: $screen")
        currentScreen = screen
        return when (screen) {
            Screen.Hangman -> hangmanVMProvider()
            Screen.Jumble -> jumbleVMProvider()
            Screen.SpellingSprint -> spellingSprintVMProvider()
            Screen.MemoryChallenger -> memoryChallengerVMProvider()
            Screen.Deduction -> deductionVMProvider()
            Screen.WordTrain -> wordTrainVMProvider()
            else -> throw IllegalArgumentException("Unknown screen: $screen")
        }
    }
}

