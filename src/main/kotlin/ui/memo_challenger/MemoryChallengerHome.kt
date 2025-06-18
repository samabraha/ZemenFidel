package ui.memo_challenger

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import model.GameStatus
import model.memo_challenger.MemoryChallengerEvent
import ui.util.WordInputBox
import vm.MemoryChallengerRoundUIState
import vm.MemoryChallengerViewModel
import vm.Screen

@Composable
fun MemoryChallengerHomeUI(
    memoryChallengerViewModel: MemoryChallengerViewModel, modifier: Modifier = Modifier, navigate: (Screen) -> Unit
) {

    val sessionUIState = memoryChallengerViewModel.sessionUIState
    val roundUIState = memoryChallengerViewModel.roundUIState

    when (roundUIState.gameStatus) {
        GameStatus.Playing -> MemoryChallengerPane(
            roundUIState = roundUIState, handleEvent = memoryChallengerViewModel::handleEvent
        )

        GameStatus.Won -> {}
        GameStatus.Lost -> {}
        GameStatus.Drawn -> {}
        GameStatus.NotStarted -> {
            OutlinedButton(onClick = {
                memoryChallengerViewModel.handleEvent(MemoryChallengerEvent.Start)
            }) {
                Text(text = "ንጀምር!")
            }
        }
    }
}

@Composable
fun MemoryChallengerPane(
    roundUIState: MemoryChallengerRoundUIState,
    handleEvent: (MemoryChallengerEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val style = MaterialTheme.typography.headlineMedium

    val handleMCEvent: (MemoryChallengerEvent) -> Unit = {
        handleEvent(it)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
    ) {

        if (roundUIState.isShowing) {
            if (roundUIState.currentWord.isNotBlank()) {
                Text(text = ">> ${roundUIState.position + 1}", style = style.copy(fontWeight = FontWeight.Bold))
                Text(text = roundUIState.currentWord, style = style.copy(fontWeight = FontWeight.Bold))
            }
        } else {
            var value by remember { mutableStateOf("") }

            Text(text = "|| ${roundUIState.position + 1}", style = style.copy(fontWeight = FontWeight.Bold))
            WordInputBox(value = value, onValueChange = { value = it }, enterAction = {
                handleMCEvent(MemoryChallengerEvent.Guess(value))
                value = ""
            }, escapeAction = { value = "" })
        }
    }

    OutlinedButton(onClick = { handleEvent(MemoryChallengerEvent.ShowWords) }) {
        Text(text = "ቃላት!", style = style)
    }
}