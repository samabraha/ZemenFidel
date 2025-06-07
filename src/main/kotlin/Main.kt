import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.*
import ui.*
import ui.deduction.DeductionHomeUI
import ui.hangman.HangmanHomeUI
import ui.jumble.JumbleHomeUI
import ui.memo_challenger.MemoryChallengerHomeUI
import ui.spelling_sprint.SpellingSprintHomeUI
import ui.word_train.WordTrainHomeUI
import vm.*
import java.util.logging.Logger


val logger: Logger = Logger.getLogger("com.develogica.zemen_fidel")

@Composable
@Preview
fun App() {
    val homeViewModel = remember { HomeViewModel() }

    val screen = homeViewModel.currentScreen


    MaterialTheme {
        Surface {
            Column {
                when (screen) {
                    Screen.Home -> HomeUI(homeViewModel)
                    Screen.Hangman -> HangmanHomeUI(homeViewModel.hangmanVMProvider())
                    Screen.Jumble -> JumbleHomeUI(homeViewModel.jumbleVMProvider())
                    Screen.SpellingSprint -> SpellingSprintHomeUI(homeViewModel.spellingSprintVMProvider())
                    Screen.MemoryChallenger -> MemoryChallengerHomeUI(homeViewModel.memoryChallengerVMProvider())
                    Screen.Deduction -> DeductionHomeUI(homeViewModel.deductionVMProvider())
                    Screen.WordTrain -> WordTrainHomeUI(homeViewModel.wordTrainVMProvider())
                }

                OutlinedButton(onClick = {
                    homeViewModel.currentScreen = Screen.Home
                }) {
                    Text(text = "Back to Home")
                }
            }
        }
    }
}


fun main() = application {
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating,
        position = WindowPosition(alignment = Alignment.Center)
    )

    Window(
        state = windowState,
        onCloseRequest = ::exitApplication
    ) {
        App()
    }
}
