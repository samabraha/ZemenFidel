package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import vm.HomeViewModel
import vm.Screen

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeUI(homeViewModel: HomeViewModel, navigate: (Screen) -> Unit, modifier: Modifier = Modifier) {
    FlowRow(
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(.5f)
    ) {
        homeViewModel.gameInfoList.forEach {
            FilledTonalButton(onClick = {
                homeViewModel.startGame(it.screen)
                navigate(it.screen)
            }, modifier = Modifier.padding(6.dp)) { Text(text = it.name) }
        }
    }
}
