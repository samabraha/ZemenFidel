package ui.hangman

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vm.HangmanViewModel
import vm.Screen

@Composable
fun HangmanHomeUI(
    hangmanViewModel: HangmanViewModel,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {

    Text(text = "Hangman Home UI", modifier = modifier)
    Button(onClick = { navigate(Screen.Home) }) {
        Text(text = "Back to Home")
    }
}