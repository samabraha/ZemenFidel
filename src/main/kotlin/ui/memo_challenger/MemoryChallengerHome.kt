package ui.memo_challenger

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import vm.MemoryChallengerViewModel
import vm.Screen

@Composable
fun MemoryChallengerHomeUI(
    memoryChallengerViewModel: MemoryChallengerViewModel,
    modifier: Modifier = Modifier,
    navigate: (Screen) -> Unit
) {

    val sessionUIState = memoryChallengerViewModel.sessionUIState
    val roundUIState = memoryChallengerViewModel.roundUIState

    navigate(Screen.Home)



}