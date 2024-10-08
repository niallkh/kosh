package kosh.ui.navigation.listdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import kosh.presentation.core.RouteContext
import kosh.presentation.di.DefaultRouteContext
import kosh.presentation.di.rememberOnRoute
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultListDetailRouter<R : Route>(
    routeContext: RouteContext,
    serializer: KSerializer<R>,
    initial: () -> ListDetailState<R>,
    private val onResult: ListDetailRouter<R>.(RouteResult<R>) -> Unit,
) : ListDetailRouter<R>, RouteContext by routeContext {

    private val navigation = SimpleNavigation<ListDetailEvent<R>>()

    private val backCallback = BackCallback {
        pop()
    }

    init {
        backHandler.register(backCallback)
    }

    override val children: Value<ListDetailRouter.Children<R>> = children(
        source = navigation,
        stateSerializer = ListDetailState.serializer(serializer),
        initialState = { initial() },
        key = "ListDetailRouter",
        navTransformer = { navState, event -> event.transform(navState) },
        onEventComplete = { event, newState, oldState -> event.onComplete(newState, oldState) },
        stateMapper = { navState, children ->
            ListDetailRouter.Children(
                multipane = navState.multipane,
                list = children[0] as Child.Created<R, RouteContext>,
                detail = children.getOrNull(1) as Child.Created<R, RouteContext>?,
            )
        },
        childFactory = { _, ctx -> DefaultRouteContext(ctx) },
    )

    override fun multipane(multipane: Boolean) {
        navigation.multipane(multipane)
    }

    override fun push(route: R) {
        navigation.push(route)
    }

    override fun pop(result: RouteResult<R>) {
        navigation.popOr { onResult(result) }
    }

    override fun pop() {
        navigation.popOr { onResult(RouteResult.Canceled) }
    }

    override fun finish() {
        onResult(RouteResult.Finished)
    }

    override fun navigateUp() {
        onResult(RouteResult.Up(null))
    }

    override fun handle(link: R) {
    }

    override fun reset() {
    }
}


@Composable
inline fun <reified R : @Serializable Route> rememberListDetailRouter(
    link: R? = null,
    noinline onResult: ListDetailRouter<R>.(RouteResult<R>) -> Unit,
    serializer: KSerializer<R> = serializer(),
    noinline list: @DisallowComposableCalls () -> R,
): ListDetailRouter<R> {
    val stackRouter = rememberOnRoute<ListDetailRouter<R>> {
        DefaultListDetailRouter(
            routeContext = this,
            serializer = serializer,
            initial = {
                // TODO check if deeplink
                ListDetailState(
                    list = list(),
                    detail = link.takeIf { it != list() }
                )
            },
            onResult = { onResult(it) },
        )
    }

    return stackRouter
}
