package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
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
    private val start: R?,
    link: R?,
    private val onResult: StackRouter<R>.(RouteResult<R>) -> Unit,
) : StackRouter<R>, StackNavigation<R> by StackNavigation(), UiContext by uiContext {

    override val stack: Value<ChildStack<R, UiContext>> = childStack(
        source = this,
        serializer = serializer,
        initialStack = {
            if (link.isDeeplink()) {
                listOfNotNull(link)
            } else {
                listOfNotNull(start, link)
            }
        },
        key = "StackRouter",
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
        onResult(RouteResult.Result(redirect = redirect))
    }

    override fun navigateUp() {
        if (stack.backStack.isNotEmpty()) {
            pop { if (!it) onResult(RouteResult.Up(start)) }
        } else {
            onResult(RouteResult.Up(start))
        }
    }

    override fun handle(link: R?) {
        if (link != null) {
            if (link.isDeeplink() || start == null) {
                replaceAll(link)
            } else {
                replaceAll(start, link)
            }
        } else {
            start?.let { replaceAll(start) }
        }
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberStackRouter(
    start: R?,
    link: R? = null,
    serializer: KSerializer<R> = serializer(),
    noinline onResult: @DisallowComposableCalls StackRouter<R>.(RouteResult<R>) -> Unit,
): StackRouter<R> {
    val uiContext = LocalUiContext.current

    return rememberRetained(key = "StackRouter") {
        DefaultStackRouter(
            uiContext = uiContext,
            serializer = serializer,
            start = start,
            link = link,
            onResult = onResult,
        )
    }
}
