package kosh.ui.navigation.slot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kosh.presentation.core.LocalUiContext

@Composable
fun SlotHost(
    slotRouter: SlotRouter = rememberSlotRouter(),
    content: @Composable SlotRouter.() -> Unit,
) {
    val slot by slotRouter.slot.subscribeAsState()

    slot.child?.let {
        CompositionLocalProvider(LocalUiContext provides it.instance) {
            slotRouter.content()
        }
    }
}
