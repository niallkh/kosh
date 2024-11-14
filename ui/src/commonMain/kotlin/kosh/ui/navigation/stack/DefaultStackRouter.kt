package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.navigate
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.getOrCreate
import kosh.ui.navigation.Router
import kosh.ui.navigation.isDeeplink
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultStackRouter<R : Route>(
    presentationContext: PresentationContext,
    serializer: KSerializer<R>,
    private val start: () -> R,
    link: R?,
    key: String,
) : StackRouter<R>,
    StackNavigation<R> by StackNavigation(),
    PresentationContext by presentationContext {

    override val stack: Value<ChildStack<R, PresentationContext>> = childStack(
        source = this,
        serializer = serializer,
        initialStack = {
            when {
                link.isDeeplink() -> setOfNotNull(link).toList()
                link?.link != null -> setOfNotNull(link).toList()
                else -> setOfNotNull(start(), link).toList()
            }
        },
        key = "StackRouter_$key",
        childFactory = { _, ctx -> ctx },
        handleBackButton = true,
    )

    override fun reset(link: R?) {
        stack.value.items.firstOrNull()
            ?.takeIf { it.configuration == start() }
            ?.instance?.container?.values
            ?.filterIsInstance<Router<*>>()
            ?.forEach { it.reset() }

        if (link.isDeeplink()) {
            navigate { setOfNotNull(link).toList() }
        } else {
            navigate { setOfNotNull(start(), link).toList() }
        }
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberStackRouter(
    noinline start: () -> R,
    initialLink: R? = null,
    serializer: KSerializer<R> = serializer(),
    key: String = currentCompositeKeyHash.toString(36),
): StackRouter<R> {
    val presentationContext = LocalPresentationContext.current

    return remember {
        presentationContext.getOrCreate(key) {
            DefaultStackRouter(
                presentationContext = presentationContext,
                serializer = serializer,
                start = start,
                link = initialLink,
                key = key,
            )
        }
    }
}
