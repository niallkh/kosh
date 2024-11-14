package kosh.ui.navigation.listdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.children.SimpleNavigation
import com.arkivanov.decompose.router.children.children
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultListDetailRouter<R : Route>(
    presentationContext: PresentationContext,
    serializer: KSerializer<R>,
    initial: () -> ListDetailState<R>,
) : ListDetailRouter<R>, PresentationContext by presentationContext {

    private val navigation = SimpleNavigation<ListDetailEvent<R>>()

    private val backCallback = BackCallback {
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
                list = children[0] as Child.Created<R, PresentationContext>,
                detail = children.getOrNull(1) as Child.Created<R, PresentationContext>?,
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

    override fun reset(link: R?) {
        TODO("Not yet implemented")
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberListDetailRouter(
    link: R? = null,
    serializer: KSerializer<R> = serializer(),
    noinline list: @DisallowComposableCalls () -> R,
): ListDetailRouter<R> {
    val presentationContext = LocalPresentationContext.current

    val stackRouter = rememberRetained {
        DefaultListDetailRouter(
            presentationContext = presentationContext,
            serializer = serializer,
            initial = {
                ListDetailState(
                    list = list(),
                    detail = link.takeIf { it != list() }
                )
            },
        )
    }

    return stackRouter
}
