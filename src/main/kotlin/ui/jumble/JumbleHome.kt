package ui.jumble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.jumble.JumbleEvent
import ui.util.ItemizedWord
import vm.JumbleRoundUIState
import vm.JumbleViewModel

@Composable
fun JumbleHomeUI(jumbleViewModel: JumbleViewModel, modifier: Modifier = Modifier) {
    val jumbleState = jumbleViewModel.roundUIState
    val takeAction: (JumbleEvent) -> Unit = { event ->
        jumbleViewModel.handleEvent(event)
    }
    JumblePane(jumbleState = jumbleState, takeAction = takeAction, modifier = modifier)
}

@Composable
fun JumblePane(
    jumbleState: JumbleRoundUIState,
    takeAction: (JumbleEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        Text(text = "Attempts: ${jumbleState.attempts}")
        ItemizedWord(letters = jumbleState.guess.toList(), clickedLetter = { takeAction(JumbleEvent.Undo) })
        ItemizedWord(
            letters = jumbleState.jumbledWord.toList(),
            clickedIndex = { takeAction(JumbleEvent.MoveLetter(it)) })
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
