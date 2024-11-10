package kosh.ui.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import androidx.compose.runtime.currentCompositeKeyHash
import androidx.compose.runtime.remember
import com.arkivanov.decompose.router.children.ChildNavState.Status
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.navigate
import com.arkivanov.decompose.router.pages.selectPrev
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackCallback
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.presentation.core.getOrCreate
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.Router
import kosh.ui.navigation.isDeeplink
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
) : PagesRouter<R>,
    PagesNavigation<R> by PagesNavigation(),
    PresentationContext by presentationContext {

    private val backCallback = BackCallback {
        selectPrev()
    }

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

    init {
        lifecycle.doOnCreate { backHandler.register(backCallback) }
        lifecycle.doOnDestroy { backHandler.unregister(backCallback) }

        val cancellation = pages.subscribe {
            backCallback.isEnabled = it.selectedIndex > 0 &&
                    !it.items[it.selectedIndex].configuration.isDeeplink()
        }

        lifecycle.doOnDestroy { cancellation.cancel() }
    }

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

    override fun reset(link: R?) {
        pages.value.items.asSequence()
            .flatMap { it.instance?.container?.values ?: listOf() }
            .filterIsInstance<Router<*>>()
            .forEach { it.reset() }

        navigate { initial().copy(selectedIndex = it.selectedIndex) }
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

    return remember {
        presentationContext.getOrCreate(key) {
            DefaultPagesRouter(
                presentationContext = presentationContext,
                serializer = serializer,
                initial = initial,
                onResult = onResult,
                key = "StackRouter_$key",
            )
        }
    }
}
