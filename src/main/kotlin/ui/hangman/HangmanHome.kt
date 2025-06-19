package ui.hangman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import model.hangman.HangmanEvent
import model.hangman.HangmanState
import ui.util.ItemizedWord
import ui.util.WordInputBox
import vm.HangmanRoundUIState
import vm.HangmanViewModel
import vm.Screen

@Composable
fun HangmanHomeUI(
    hangmanViewModel: HangmanViewModel,
    navigate: (Screen) -> Unit,
    modifier: Modifier = Modifier,
) {
    val sessionUIState = hangmanViewModel.sessionUIState
    val roundUIState = hangmanViewModel.roundUIState
    val takeAction: (HangmanEvent) -> Unit = {
        hangmanViewModel.handleEvent(event = it)
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        when (roundUIState.gameStatus) {
            HangmanState.Won -> {
                Text(text = listOf("ተዓወት!", "ቅኑዕ መልሲ!").random(), style = style)
                Text(text = roundUIState.word, style = style)
            }

            HangmanState.Lost -> {
                Text(text = listOf("ክላእ", "ክላሌዓለም", "ኣሕ!").random(), style = style)
                Text(text = roundUIState.word, style = style)
            }

            HangmanState.Playing -> {
                HangmanPane(
                    rouUIState = roundUIState,
                    takeAction = takeAction,
                    modifier = modifier
                )
            }

            else -> {
                Button(onClick = { takeAction(HangmanEvent.Start) }) {
                    Text(text = "Start")
                }
            }
        }


        Button(onClick = { navigate(Screen.Home) }) {
            Text(text = "Back to Home")
        }
    }
}

@Composable
fun HangmanPane(rouUIState: HangmanRoundUIState, takeAction: (HangmanEvent) -> Unit, modifier: Modifier = Modifier) {
    val style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)

    var value by remember { mutableStateOf("") }
    val guess: () -> Unit = {
        for (i in value) {
            takeAction(HangmanEvent.Guess(i))
        }
        value = ""
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = rouUIState.lives.toString(), style = style)

        val tries = rouUIState.tries
        val guesses = rouUIState.guesses

        ItemizedWord(letters = guesses)
        ItemizedWord(letters = tries)

        WordInputBox(
            value = value,
            onValueChange = { value = it },
            enterAction = guess,
            escapeAction = { value = "" }
        )

        OutlinedButton(onClick = {
            guess()
        }) {
            Text(text = "Guess")
        }
    }
}
