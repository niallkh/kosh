package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.backStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import kosh.presentation.core.RouteContext
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.rememberOnRoute
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.isDeeplink
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultStackRouter<R : Route>(
    routeContext: RouteContext,
    serializer: KSerializer<R>,
    private val start: R?,
    link: R?,
    private val onResult: (RouteResult<R>) -> Unit,
) : StackRouter<R>, RouteContext by routeContext, StackNavigation<R> by StackNavigation() {

    private val backCallback = BackCallback {
        pop()
    }

    init {
        backHandler.register(backCallback)
    }

    override val stack: Value<ChildStack<R, RouteContext>> = childStack(
        source = this,
        serializer = serializer,
        initialStack = { listOfNotNull(start, link) },
        key = "StackRouter",
        childFactory = { _, ctx -> DefaultRouteContext(ctx) },
    )

    override fun pop(result: RouteResult<R>) {
        pop { if (it.not()) onResult(result) }
    }

    override fun pop() {
        pop(RouteResult.Canceled)
    }

    override fun finish() {
        onResult(RouteResult.Finished)
    }

    override fun navigateUp() {
        if (stack.backStack.isNotEmpty() && start != null) {
            replaceAll(start)
        } else {
            onResult(RouteResult.Up(start))
        }
    }

    override fun handle(link: R) {
        if (link.isDeeplink() || start == null) {
            replaceAll(link)
        } else {
            replaceAll(start, link)
        }
    }

    override fun reset() {
        if (start != null) {
            replaceAll(start)
        }
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberStackRouter(
    start: R?,
    link: R? = null,
    serializer: KSerializer<R> = serializer(),
    noinline onResult: (RouteResult<R>) -> Unit,
): StackRouter<R> {
    val stackRouter = rememberOnRoute<StackRouter<R>> {
        DefaultStackRouter(
            routeContext = this,
            serializer = serializer,
            start = start,
            link = link,
            onResult = onResult,
        )
    }

    return stackRouter
}
