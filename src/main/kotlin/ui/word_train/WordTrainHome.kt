package ui.word_train

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import model.word_train.WordTrainEvent
import model.word_train.WordTrainState
import vm.Screen
import vm.WordTrainRoundUIState
import vm.WordTrainSessionUIState
import vm.WordTrainViewModel

@Composable
fun WordTrainHomeUI(
    wordTrainViewModel: WordTrainViewModel,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {
    val sessionUIState = wordTrainViewModel.sessionUIState
    val roundUIState = wordTrainViewModel.roundUIState
    if (sessionUIState.status == WordTrainState.NotStarted) {
        NoGameWordTrainPane(
            sessionUIState = sessionUIState,
            wordTrainAction = wordTrainViewModel::handleEvent,
            modifier = modifier,
            navigate = navigate
        )
    } else {
        WordTrainPane(
            wordTrainState = roundUIState,
            takeAction = wordTrainViewModel::handleEvent,
            modifier = modifier
        )
    }

}

@Composable
fun NoGameWordTrainPane(
    sessionUIState: WordTrainSessionUIState,
    wordTrainAction: (WordTrainEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {
    Text(text = "Welcome to Word Train Game!", modifier = modifier)
    Text(text = "Press 'Start Word Train' to begin.")

    // Button to start the game
    OutlinedButton(onClick = { wordTrainAction(WordTrainEvent.Start) }) {
        Text(text = "Start Word Train")
    }
}

@Composable
fun WordTrainPane(
    wordTrainState: WordTrainRoundUIState,
    takeAction: (WordTrainEvent) -> Unit, modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Word Train Pane", modifier = modifier)

    }
}

