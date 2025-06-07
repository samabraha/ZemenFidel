package ui.util

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ItemizedWord(word: String, clickedLetter: (Char) -> Unit) {
    Row {
        word.forEach { letter ->
            OutlinedButton(onClick = { clickedLetter(letter) }) {
                Text(text = letter.toString())
            }
        }
    }
}