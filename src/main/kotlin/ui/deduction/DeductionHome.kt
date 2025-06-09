package ui.deduction

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import model.GameStatus
import model.deduction.DeductionEvent
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
    if (sessionUIState.status == GameStatus.NotStarted) {
        NoGamePane(
            sessionUIState = sessionUIState,
            deductionAction = deductionViewModel::handleEvent,
            modifier = modifier,
            navigate = navigate
        )
    }

    Column(modifier = modifier) {
        DeductionPane(
            deductionState = roundUIState,
            takeAction = deductionViewModel::handleEvent,
        )

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
    if (sessionUIState.status == GameStatus.NotStarted) {
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
}

@Composable
fun DeductionPane(
    deductionState: DeductionRoundUIState, takeAction: (DeductionEvent) -> Unit, modifier: Modifier = Modifier
) {
    println("DeductionPane called with state: $deductionState")

    Column(modifier = modifier) {
        Text(text = "Word: ${deductionState.word}")
        Text(
            text = "Correct: ${deductionState.correct}, " + "Misplaced: ${deductionState.misplaced}, " + "Incorrect: ${deductionState.incorrect}"
        )

        var guess by remember { mutableStateOf("") }

        TextField(value = guess, onValueChange = { guess = it }, label = { Text("Your Guess") })

        if (guess.length == deductionState.word.length) {
            OutlinedButton(onClick = {
                takeAction(DeductionEvent.Guess(guess))
                guess = ""
            }) {
                Text(text = "Submit Guess")
            }
        }
    }
}
