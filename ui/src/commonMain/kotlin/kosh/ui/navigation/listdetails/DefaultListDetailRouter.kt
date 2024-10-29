package kosh.ui.navigation.listdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultListDetailRouter<R : Route>(
    uiContext: UiContext,
    serializer: KSerializer<R>,
    initial: () -> ListDetailState<R>,
    private val onResult: ListDetailRouter<R>.(RouteResult<R>) -> Unit,
) : ListDetailRouter<R>, UiContext by uiContext {

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
                list = children[0] as Child.Created<R, UiContext>,
                detail = children.getOrNull(1) as Child.Created<R, UiContext>?,
            )
        },
        childFactory = { _, ctx -> ctx },
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
        navigation.popOr { onResult(RouteResult.Result()) }
    }

    override fun result(redirect: String?) {
        onResult(RouteResult.Result())
    }

    override fun navigateUp() {
        onResult(RouteResult.Up(null))
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberListDetailRouter(
    link: R? = null,
    noinline onResult: ListDetailRouter<R>.(RouteResult<R>) -> Unit,
    serializer: KSerializer<R> = serializer(),
    noinline list: @DisallowComposableCalls () -> R,
): ListDetailRouter<R> {
    val uiContext = LocalUiContext.current

    val stackRouter = rememberRetained {
        DefaultListDetailRouter(
            uiContext = uiContext,
            serializer = serializer,
            initial = {
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
