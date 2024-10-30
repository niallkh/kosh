package kosh.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.arkivanov.essenty.backhandler.BackCallback
import kosh.presentation.core.LocalPresentationContext

@Composable
fun BackHandler(
    enabled: Boolean = true,
    onBack: () -> Unit,
) {
    val backHandler = LocalPresentationContext.current.backHandler

    val currentOnBack by rememberUpdatedState(onBack)

    val backCallback = remember {
        BackCallback(enabled) {
            currentOnBack()
        }
    }

    DisposableEffect(backHandler) {
        backHandler.register(backCallback)
        onDispose { backHandler.unregister(backCallback) }
    }
}
