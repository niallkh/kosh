package kosh.ui.navigation.slot

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.getOrCreate
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

class DefaultSlotRouter<R : Route>(
    presentationContext: PresentationContext,
    start: R?,
    serializer: KSerializer<R>,
    key: String,
) : SlotRouter<R>,
    PresentationContext by presentationContext,
    SlotNavigation<R> by SlotNavigation() {

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
    val presentationContext = LocalPresentationContext.current

    return remember {
        presentationContext.getOrCreate(key) {
            DefaultSlotRouter(
                presentationContext = presentationContext,
                start = initial,
                serializer = serializer,
                key = key,
            )
        }
    }
}
