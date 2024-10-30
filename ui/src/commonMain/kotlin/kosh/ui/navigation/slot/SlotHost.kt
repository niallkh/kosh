package kosh.ui.navigation.slot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kosh.presentation.core.LocalPresentationContext
import kosh.ui.navigation.routes.Route

@Composable
inline fun <reified R : Route> SlotHost(
    slotRouter: SlotRouter<R> = rememberSlotRouter<R>(),
    crossinline content: @Composable SlotRouter<R>.(R) -> Unit,
) {
    val slot by slotRouter.slot.subscribeAsState()

    slot.child?.let {
        CompositionLocalProvider(LocalPresentationContext provides it.instance) {
            slotRouter.content(it.configuration)
        }
    }
}
