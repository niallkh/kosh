package kosh.ui.navigation.slot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositeKeyHash
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

class DefaultSlotRouter<R : Route>(
    uiContext: UiContext,
    start: R?,
    serializer: KSerializer<R>,
    key: String,
) : SlotRouter<R>, UiContext by uiContext, SlotNavigation<R> by SlotNavigation() {

    override val slot = childSlot(
        source = this,
        key = "SlotRouter_$key",
        handleBackButton = true,
        serializer = serializer,
        childFactory = { _, ctx -> ctx },
        initialConfiguration = { start }
    )
}

@Composable
inline fun <reified R : Route> rememberSlotRouter(
    initial: R? = null,
    serializer: KSerializer<R> = serializer(),
    key: String = currentCompositeKeyHash.toString(36),
): SlotRouter<R> {
    val uiContext = LocalUiContext.current

    return rememberRetained {
        DefaultSlotRouter(
            uiContext = uiContext,
            start = initial,
            serializer = serializer,
            key = key,
        )
    }
}
