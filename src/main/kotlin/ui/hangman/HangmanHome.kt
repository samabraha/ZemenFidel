package ui.hangman

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import model.GameStatus
import model.hangman.HangmanEvent
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
            GameStatus.Playing -> {}
            GameStatus.Won -> {
                Text(text = "ተዓወት!!!", style = style)
                Text(text = roundUIState.word, style = style)
            }

            GameStatus.Lost -> {
                Text(text = listOf("ክላእ", "ክላሌዓለም", "ኣሕ!").random(), style = style)
                Text(text = roundUIState.word, style = style)
            }

            GameStatus.Drawn -> {}
            GameStatus.NotStarted -> {}
        }

        if (roundUIState.gameStatus != GameStatus.Playing) {
            Button(onClick = { takeAction(HangmanEvent.Start) }) {
                Text(text = "Start")
            }
        } else {
            HangmanPane(
                rouUIState = roundUIState,
                takeAction = takeAction
            )

        }

        Button(onClick = { navigate(Screen.Home) }) {
            Text(text = "Back to Home")
        }
    }
}

@Composable
fun HangmanPane(rouUIState: HangmanRoundUIState, takeAction: (HangmanEvent) -> Unit, modifier: Modifier = Modifier) {
    val guessW = rouUIState.guessWord
    val style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = rouUIState.lives.toString(), style = style)
        Row {
            guessW.forEach {
                ElevatedButton(
                    onClick = {},
                    colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Yellow),
                    modifier = modifier.padding(10.dp)
                ) {
                    Text(text = it.toString(), style = style)
                }
            }
        }
        var value by remember { mutableStateOf("") }

        TextField(
            value = value,
            onValueChange = { value = it },
            textStyle = style,
            singleLine = true,
        )

        OutlinedButton(onClick = {
            for (i in value) {
                takeAction(HangmanEvent.Guess(i))
            }
            value = ""
        }) {
            Text(text = "Guess")
        }
    }
}