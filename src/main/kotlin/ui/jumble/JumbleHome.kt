package ui.jumble

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import model.jumble.JumbleEvent
import ui.util.ItemizedWord
import vm.JumbleRoundUIState
import vm.JumbleSessionUIState
import vm.JumbleViewModel
import vm.Screen

@Composable
fun JumbleHomeUI(jumbleViewModel: JumbleViewModel, modifier: Modifier = Modifier, navigate: (Screen) -> Unit) {
    val sessionState = jumbleViewModel.sessionUIState
    val jumbleState = jumbleViewModel.roundUIState
    val takeAction: (JumbleEvent) -> Unit = { event ->
        jumbleViewModel.handleEvent(event)
    }

    Row(modifier.fillMaxSize()) {
        JumblePane(jumbleState = jumbleState, takeAction = takeAction, modifier = Modifier.fillMaxWidth(.5f))
        JumbleHistoryPane(jumbleSession = sessionState)
    }
}

@Composable
fun JumblePane(
    jumbleState: JumbleRoundUIState, takeAction: (JumbleEvent) -> Unit, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Attempts: ${jumbleState.attempts}")
        ItemizedWord(letters = jumbleState.guess.toList(), clickedLetter = { takeAction(JumbleEvent.Undo) })
        ItemizedWord(
            letters = jumbleState.jumbledWord.toList(), clickedIndex = { takeAction(JumbleEvent.MoveLetter(it)) })
        Row {
            if (jumbleState.jumbledWord.length > 1) {
                OutlinedButton(onClick = {
                    takeAction(JumbleEvent.Shuffle)
                }) {
                    Text(text = "Shuffle")
                }
            }
        }
    }
}

@Composable
fun JumbleHistoryPane(
    jumbleSession: JumbleSessionUIState, modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier.fillMaxSize().background(Color.Red)) {
        item {
            Text(text = "History")
        }
        items(jumbleSession.rounds) { historyItem ->
            JumbleHistoryItem(
                round = historyItem
            )
        }
    }
}

@Composable
fun JumbleHistoryItem(
    round: JumbleRoundUIState, modifier: Modifier = Modifier
) {
    val style = MaterialTheme.typography.titleLarge
    ElevatedCard(modifier = modifier.fillMaxSize().clickable { println("Clicked on round: ${round.jumbledWord}") }) {
        Text(text = "Jumbled: ${round.jumbledWord}", style = style.copy(fontWeight = FontWeight.Bold))
        Text(text = "Correct: ${round.correctWord}", style = style)
        Text(text = "Attempts: ${round.attempts}", style = style)
    }
}

