package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisallowComposableCalls
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.backStackAnimation
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.serializer

@Composable
inline fun <R : Route> StackHost(
    stackRouter: StackRouter<R>,
    animation: StackAnimation<R, UiContext>? = null,
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    Children(
        stack = stackRouter.stack,
        animation = animation ?: backStackAnimation(
            backHandler = stackRouter.backHandler,
            onBack = stackRouter::pop,
        ),
    ) { child ->
        CompositionLocalProvider(LocalUiContext provides child.instance) {
            stackRouter.content(child.configuration)
        }
    }
}

@Composable
inline fun <reified R : Route> StackHost(
    start: R,
    link: R?,
    noinline onResult: @DisallowComposableCalls StackRouter<R>.(RouteResult<R>) -> Unit,
    animation: StackAnimation<R, UiContext>? = null,
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    val stackRouter = rememberStackRouter<R>(
        start = start,
        link = link,
        onResult = { onResult(it) },
        serializer = serializer()
    )

    StackHost(
        stackRouter = stackRouter,
        animation = animation,
        content = content,
    )
}

@Composable
inline fun <reified R : Route> StackHost(
    link: R,
    noinline onResult: @DisallowComposableCalls StackRouter<R>.(RouteResult<R>) -> Unit,
    animation: StackAnimation<R, UiContext>? = null,
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    val stackRouter = rememberStackRouter<R>(
        start = null,
        link = link,
        onResult = { onResult(it) },
        serializer = serializer()
    )

    StackHost(
        stackRouter = stackRouter,
        animation = animation,
        content = content,
    )
}
