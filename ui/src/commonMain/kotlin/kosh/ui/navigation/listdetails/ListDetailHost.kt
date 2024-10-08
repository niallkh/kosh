package kosh.ui.navigation.listdetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import kosh.presentation.core.RouteContext
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisX
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.serializer

@Composable
inline fun <reified R : Route> ListDetailHost(
    link: R?,
    crossinline list: @DisallowComposableCalls () -> R,
    noinline onResult: ListDetailRouter<R>.(RouteResult<R>) -> Unit,
    animation: StackAnimation<R, RouteContext> = stackAnimationSharedAxisX(),
    content: @Composable ListDetailRouter<R>.(R) -> Unit,
) {
    val listDetailRouter = rememberListDetailRouter(
        link = link,
        onResult = { onResult(it) },
        serializer = serializer()
    ) {
        list()
    }
}
