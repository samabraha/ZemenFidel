
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.*
import ui.HomeUI
import ui.deduction.DeductionHomeUI
import ui.hangman.HangmanHomeUI
import ui.jumble.JumbleHomeUI
import ui.memo_challenger.MemoryChallengerHomeUI
import ui.spelling_sprint.SpellingSprintHomeUI
import ui.word_train.WordTrainHomeUI
import vm.HomeViewModel
import vm.Screen
import java.util.logging.Logger



fun main() = application {
    val windowState = rememberWindowState(
        placement = WindowPlacement.Floating, position = WindowPosition(alignment = Alignment.Center)
    )

    Window(
        state = windowState, onCloseRequest = ::exitApplication
    ) {
        App(homeViewModel = AppManager.homeViewModel)
    }
}

@Composable
fun App(homeViewModel: HomeViewModel) {
    var screen by remember { mutableStateOf(homeViewModel.currentScreen) }
    val navigate: (Screen) -> Unit = { nextScreen ->
        screen = nextScreen
    }

    MaterialTheme {
        Surface {
            Column(modifier = Modifier.fillMaxSize().background(Color(.5f, .8f, .9f))) {
                when (screen) {
                    Screen.Home ->
                        HomeUI(homeViewModel, navigate = navigate, modifier = Modifier)

                    Screen.Hangman ->
                        HangmanHomeUI(homeViewModel.hangmanVMProvider(), navigate = navigate)

                    Screen.Jumble ->
                        JumbleHomeUI(homeViewModel.jumbleVMProvider(), navigate = navigate)

                    Screen.SpellingSprint ->
                        SpellingSprintHomeUI(homeViewModel.spellingSprintVMProvider(), navigate = navigate)

                    Screen.MemoryChallenger ->
                        MemoryChallengerHomeUI(homeViewModel.memoryChallengerVMProvider(), navigate = navigate)

                    Screen.Deduction ->
                        DeductionHomeUI(homeViewModel.deductionVMProvider(), navigate = navigate)

                    Screen.WordTrain ->
                        WordTrainHomeUI(homeViewModel.wordTrainVMProvider(), navigate = navigate)

                }

                OutlinedButton(onClick = {
                    homeViewModel.currentScreen = Screen.Home
                    screen = Screen.Home
                }) {
                    Text(text = "Back to Home")
                }
            }
        }
    }
}

