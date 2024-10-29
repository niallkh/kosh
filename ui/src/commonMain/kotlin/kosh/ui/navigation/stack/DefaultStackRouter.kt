package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.currentCompositeKeyHash
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.isDeeplink
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultStackRouter<R : Route>(
    uiContext: UiContext,
    serializer: KSerializer<R>,
    private val start: () -> R,
    link: R?,
    key: String,
    private val onResult: StackRouter<R>.(RouteResult<R>) -> Unit,
) : StackRouter<R>, StackNavigation<R> by StackNavigation(), UiContext by uiContext {

    override val stack: Value<ChildStack<R, UiContext>> = childStack(
        source = this,
        serializer = serializer,
        initialStack = {
            if (link.isDeeplink()) {
                setOfNotNull(link).toList()
            } else {
                setOfNotNull(start(), link).toList()
            }
        },
        key = "StackRouter_$key",
        childFactory = { _, ctx -> ctx },
        handleBackButton = true,
    )

    override fun pop(result: RouteResult<R>) {
        pop { if (!it) onResult(result) }
    }

    override fun pop() {
        pop(RouteResult.Result())
    }

    override fun result(redirect: String?) {
        pop { if (!it) onResult(RouteResult.Result(redirect)) }
    }

    override fun navigateUp() {
        pop { if (!it) onResult(RouteResult.Up(start())) }
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberStackRouter(
    noinline start: () -> R,
    link: R? = null,
    serializer: KSerializer<R> = serializer(),
    key: String = currentCompositeKeyHash.toString(36),
    noinline onResult: @DisallowComposableCalls StackRouter<R>.(RouteResult<R>) -> Unit,
): StackRouter<R> {
    val uiContext = LocalUiContext.current

    return rememberRetained {
        DefaultStackRouter(
            uiContext = uiContext,
            serializer = serializer,
            start = start,
            link = link,
            key = key,
            onResult = onResult,
        )
    }
}
