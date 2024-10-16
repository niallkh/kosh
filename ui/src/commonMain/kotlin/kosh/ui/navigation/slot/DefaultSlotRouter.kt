package kosh.ui.navigation.slot

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.di.rememberRetained

class DefaultSlotRouter(
    uiContext: UiContext,
    start: Slot?,
) : SlotRouter, UiContext by uiContext, SlotNavigation<Slot> by SlotNavigation() {
    override val slot = childSlot(
        source = this,
        key = "SlotRouter",
        handleBackButton = true,
        serializer = Slot.serializer(),
        childFactory = { _, ctx -> ctx },
        initialConfiguration = { start }
    )

    override fun activate() {
        activate(Slot)
    }
}

@Composable
fun rememberSlotRouter(
    initial: Slot? = null,
): SlotRouter {
    val uiContext = LocalUiContext.current

    return rememberRetained {
        DefaultSlotRouter(
            uiContext = uiContext,
            start = initial,
        )
    }
}
