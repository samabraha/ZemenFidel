package ui.spelling_sprint

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import model.GameStatus
import model.spelling_sprint.SpellingSprintEvent
import ui.util.ItemizedWord
import ui.util.WordInputBox
import util.Log
import vm.Screen
import vm.SpellingSprintRoundUIState
import vm.SpellingSprintViewModel

@Composable
fun SpellingSprintHomeUI(
    sprintViewModel: SpellingSprintViewModel,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {
    val sessionUIState = sprintViewModel.sessionUIState
    val roundUIState = sprintViewModel.roundUIState

    val navigateHome = { navigate(Screen.Home) }

    Column {
        NoGameSpellingSprint(
            roundUIState = roundUIState,
            takeAction = sprintViewModel::handleEvent,
            modifier = modifier
        )
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

        var value by remember { mutableStateOf("") }

        WordInputBox(value = value, onValueChange = { value = it })

        Row {
            if (roundUIState.isShowing) {
                Button(onClick = { takeSSAction(SpellingSprintEvent.Freeze) }) {
                    Text("Freeze")
                }
            } else {
                Button(onClick = { takeSSAction(SpellingSprintEvent.ShowHints) }) {
                    Text("Start")
                }

                Button(onClick = {
                    takeSSAction(SpellingSprintEvent.Guess(value))
                    value = ""
                }) {
                    Text("Guess")
                }
            }
        }
    }
}

@Composable
fun NoGameSpellingSprint(
    takeAction: (SpellingSprintEvent) -> Unit,
    roundUIState: SpellingSprintRoundUIState,
    modifier: Modifier = Modifier
) {
    Column {

        when (roundUIState.gameStatus) {
            GameStatus.Won -> {
                Text(text = "ተዓወት!")
            }

            GameStatus.Lost -> {
                Text(text = "ደጊምና ንፈትን!")
            }

            GameStatus.NotStarted -> {
                OutlinedButton(onClick = { takeAction(SpellingSprintEvent.Start) }) {
                    Text(text = "Start!")
                }

            }

            else -> {
                SpellingSprintPane(
                    roundUIState = roundUIState,
                    takeSSAction = takeAction,
                    modifier = modifier
                )
            }
        }
    }
}