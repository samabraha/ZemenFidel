package ui.spelling_sprint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import model.spelling_sprint.SpellingSprintEvent
import model.spelling_sprint.SpellingSprintState
import ui.util.ItemizedWord
import ui.util.WordInputBox
import util.Log
import vm.Screen
import vm.SpellingSprintRoundUIState
import vm.SpellingSprintViewModel

@Composable
fun SpellingSprintHomeUI(
    sprintViewModel: SpellingSprintViewModel, modifier: Modifier = Modifier, navigate: (Screen) -> Unit
) {
    val sessionUIState = sprintViewModel.sessionUIState
    val roundUIState = sprintViewModel.roundUIState

    val navigateHome = { navigate(Screen.Home) }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.onKeyEvent({ ke ->
            if (ke.key == Key.Escape) {
                sprintViewModel.handleEvent(SpellingSprintEvent.Freeze)
                true
            } else false
        })
    ) {
        when (roundUIState.gameStatus) {
            SpellingSprintState.Playing -> {
                SpellingSprintPane(
                    roundUIState = roundUIState, takeSSAction = sprintViewModel::handleEvent, modifier = modifier
                )
            }

            SpellingSprintState.Won -> {
                Text(text = "ተዓወት!")
            }

            SpellingSprintState.Lost -> {
                Text(text = "ደጊምና ንፈትን!")
            }

            else -> {}
        }

        HorizontalDivider()
        OutlinedButton(onClick = { sprintViewModel.handleEvent(SpellingSprintEvent.Start) }) {
            Text(text = "Start!")
        }
        HorizontalDivider()
        ElevatedButton(onClick = { navigateHome() }) {
            Text(text = "ቻው")
        }
    }
}

@Composable
fun SpellingSprintPane(
    roundUIState: SpellingSprintRoundUIState,
    takeSSAction: (SpellingSprintEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.fillMaxSize()
    ) {
        Log.info("wordSnapshot") { roundUIState.wordSnapshot.toString() }
        ItemizedWord(roundUIState.wordSnapshot)

        var value by remember { mutableStateOf(value = "") }

        val takeAGuess = {
            takeSSAction(SpellingSprintEvent.Guess(value))
            value = ""
        }

        WordInputBox(value = value, onValueChange = { value = it }, enterAction = { takeAGuess() })
        Row {
            if (roundUIState.isShowing) {
                Button(onClick = { takeSSAction(SpellingSprintEvent.Freeze) }) {
                    Text("Freeze")
                }
            } else {
                Button(onClick = { takeSSAction(SpellingSprintEvent.ShowHints) }) {
                    Text("Show Hints")
                }
                Button(onClick = { takeAGuess() }) {
                    Text("Guess")
                }
            }
        }
    }
}

