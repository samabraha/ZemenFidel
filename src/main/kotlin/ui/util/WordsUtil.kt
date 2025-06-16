package ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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