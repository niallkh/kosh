package kosh.ui.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.currentCompositeKeyHash
import com.arkivanov.decompose.router.children.ChildNavState.Status
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultPagesRouter<R : Route>(
    uiContext: UiContext,
    serializer: KSerializer<R>,
    private val initial: () -> Pages<R>,
    key: String,
    private val onResult: PagesRouter<R>.(RouteResult<R>) -> Unit,
) : PagesRouter<R>, PagesNavigation<R> by PagesNavigation(), UiContext by uiContext {

    override val pages: Value<ChildPages<R, UiContext>> = childPages(
        source = this,
        serializer = serializer,
        initialPages = { initial() },
        key = "PagesRouter_$key",
        pageStatus = { index, pages ->
            when (index) {
                pages.selectedIndex -> Status.RESUMED
                else -> Status.CREATED
            }
        },
        childFactory = { _, ctx -> ctx },
    )

    override fun pop(result: RouteResult<R>) {
        onResult(result)
    }

    override fun pop() {
        onResult(RouteResult.Result())
    }

    override fun result(redirect: String?) {
        onResult(RouteResult.Result(redirect = redirect))
    }

    override fun navigateUp() {
        onResult(RouteResult.Up(null))
    }

    override fun handle(link: R?) {
        if (link != null) {
            navigate { current ->
                val items = current.items.toMutableList()
                items[current.selectedIndex] = link
                Pages(items.toList(), 0)
            }
        }
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberPagesRouter(
    noinline initial: () -> Pages<R>,
    serializer: KSerializer<R> = serializer(),
    key: String = currentCompositeKeyHash.toString(36),
    noinline onResult: @DisallowComposableCalls PagesRouter<R>.(RouteResult<R>) -> Unit,
): PagesRouter<R> {
    val uiContext = LocalUiContext.current

    return rememberRetained(key = "StackRouter") {
        DefaultPagesRouter(
            uiContext = uiContext,
            serializer = serializer,
            initial = initial,
            onResult = onResult,
            key = key,
        )
    }
}
