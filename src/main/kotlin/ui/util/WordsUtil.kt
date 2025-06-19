package ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ItemizedWord(
    letters: List<Char>,
    clickedLetter: (Char) -> Unit = {},
    clickedIndex: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val letterStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)

    Row(modifier = modifier) {
        letters.forEachIndexed { index, letter ->
            OutlinedButton(onClick = {
                clickedLetter(letter)
                clickedIndex(index)
            }) {
                Text(text = letter.toString(), style = letterStyle)
            }
        }
    }
}

@Composable
fun WordInputBox(
    value: String, onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enterAction: () -> Unit = {},
    escapeAction: () -> Unit = {}
) {
    val style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
    TextField(
        value = value, onValueChange = onValueChange,
        singleLine = true,
        textStyle = style,
        modifier = modifier.onKeyEvent({ ke ->
            when (ke.key) {
                Key.Enter -> enterAction()
                Key.Escape -> escapeAction()
            }
            true
        })
    )
}