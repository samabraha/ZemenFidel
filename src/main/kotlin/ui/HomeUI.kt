package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vm.HomeViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeUI(homeViewModel: HomeViewModel, modifier: Modifier = Modifier) {
    FlowRow(
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        homeViewModel.gameInfoList.forEach {
            FilledTonalButton(onClick = {
                homeViewModel.startGame(it.screen)
            }) { Text(text = it.name) }
        }
    }
}
