package kosh.ui.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.currentCompositeKeyHash
import com.arkivanov.decompose.router.children.ChildNavState.Status
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.di.rememberRetained
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

class DefaultPagesRouter<R : Route>(
    presentationContext: PresentationContext,
    serializer: KSerializer<R>,
    private val initial: () -> Pages<R>,
    key: String,
    private val onResult: PagesRouter<R>.(RouteResult<R>) -> Unit,
) : PagesRouter<R>, PagesNavigation<R> by PagesNavigation(),
    PresentationContext by presentationContext {

    override val pages: Value<ChildPages<R, PresentationContext>> = childPages(
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
        onResult(RouteResult.Finished())
    }

    override fun result(redirect: String?) {
        onResult(RouteResult.Finished(redirect = redirect))
    }

    override fun navigateUp() {
        onResult(RouteResult.Up(null))
    }
}

@Composable
inline fun <reified R : @Serializable Route> rememberPagesRouter(
    noinline initial: () -> Pages<R>,
    serializer: KSerializer<R> = serializer(),
    key: String = currentCompositeKeyHash.toString(36),
    noinline onResult: @DisallowComposableCalls PagesRouter<R>.(RouteResult<R>) -> Unit,
): PagesRouter<R> {
    val presentationContext = LocalPresentationContext.current

    return rememberRetained {
        DefaultPagesRouter(
            presentationContext = presentationContext,
            serializer = serializer,
            initial = initial,
            onResult = onResult,
            key = key,
        )
    }
}
