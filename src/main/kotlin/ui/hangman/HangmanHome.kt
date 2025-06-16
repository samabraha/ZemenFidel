package ui.hangman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight
import model.GameStatus
import model.hangman.HangmanEvent
import ui.util.ItemizedWord
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
            GameStatus.Won -> {
                Text(text = listOf("ተዓወት!", "ቅኑዕ መልሲ!").random(), style = style)
                Text(text = roundUIState.word, style = style)
            }

            GameStatus.Lost -> {
                Text(text = listOf("ክላእ", "ክላሌዓለም", "ኣሕ!").random(), style = style)
                Text(text = roundUIState.word, style = style)
            }

            GameStatus.Playing, GameStatus.Drawn, GameStatus.NotStarted -> {}
        }

        if (roundUIState.gameStatus != GameStatus.Playing) {
            Button(onClick = { takeAction(HangmanEvent.Start) }) {
                Text(text = "Start")
            }
        } else {
            HangmanPane(
                rouUIState = roundUIState,
                takeAction = takeAction,
                modifier = modifier
            )
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

        TextField(
            value = value,
            onValueChange = { value = it },
            textStyle = style,
            singleLine = true,
            modifier = Modifier.onKeyEvent({
                if (it.key == Key.Enter) {
                    guess()
                }
                return@onKeyEvent true
            })
        )

        OutlinedButton(onClick = {
            guess()
        }) {
            Text(text = "Guess")
        }
    }
}
