package ui.deduction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import model.LifecycleState
import model.deduction.DeductionEvent
import ui.util.WordInputBox
import vm.DeductionRoundUIState
import vm.DeductionSessionUIState
import vm.DeductionViewModel
import vm.Screen

@Composable
fun DeductionHomeUI(
    deductionViewModel: DeductionViewModel, modifier: Modifier = Modifier, navigate: (Screen) -> Unit
) {
    val roundUIState = deductionViewModel.roundUIState
    val sessionUIState = deductionViewModel.sessionUIState
    Column(modifier = modifier) {

        if (sessionUIState.status != LifecycleState.Started) {
            NoGamePane(
                sessionUIState = sessionUIState,
                deductionAction = deductionViewModel::handleEvent,
                modifier = modifier,
                navigate = navigate
            )
        } else {
            DeductionPane(
                deductionState = roundUIState,
                takeAction = deductionViewModel::handleEvent,
            )

        }
        OutlinedButton(onClick = { navigate(Screen.Home) }) {
            Text(text = "Quit")
        }
    }
}

@Composable
fun NoGamePane(
    sessionUIState: DeductionSessionUIState,
    deductionAction: (DeductionEvent) -> Unit,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {
    Column {
        Text(text = "Welcome to Deduction Game!")
        Text(text = "Press 'Start Deduction' to begin.")

        OutlinedButton(
            onClick = { deductionAction(DeductionEvent.Start) },
        ) {
            Text(text = "Start Deduction")
        }
    }
}

@Composable
fun DeductionPane(
    deductionState: DeductionRoundUIState, takeAction: (DeductionEvent) -> Unit, modifier: Modifier = Modifier
) {

    val style = MaterialTheme.typography.bodyLarge
    Column(modifier = modifier) {
        DeductionScore(
            deductionState = deductionState,
        )

        var guess by remember { mutableStateOf("") }

        val takeAction: () -> Unit = {
            takeAction(DeductionEvent.Guess(guess))
            guess = ""
        }

        WordInputBox(
            value = guess,
            onValueChange = {
                if (it.length <= deductionState.word.length) {
                    guess = it
                }
            },
            enterAction = takeAction
        )

        if (guess.length == deductionState.word.length) {
            OutlinedButton(onClick = takeAction) {
                Text(text = "Submit Guess")
            }
        }
    }
}

@Composable
fun DeductionScore(
    deductionState: DeductionRoundUIState, modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = "Guess: ${deductionState.guess}")
        Text(text = "Att: ${deductionState.attempts}")
        Text(text = "Len: ${deductionState.word.length}")
        Row {
            Text(text = "Cor: ${deductionState.correct}")
            Text(text = "Mis: ${deductionState.misplaced}")
            Text(text = "Inc: ${deductionState.incorrect}")
        }
    }
}