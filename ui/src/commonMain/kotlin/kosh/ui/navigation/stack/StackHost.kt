package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import kosh.presentation.core.RouteContext
import kosh.presentation.di.LocalRouteContext
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.animation.stackAnimationSharedAxisX
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.serializer

@Composable
inline fun <R : Route> StackHost(
    stackRouter: StackRouter<R>,
    animation: StackAnimation<R, RouteContext> = stackAnimationSharedAxisX(),
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    Children(
        stack = stackRouter.stack,
        animation = animation,
    ) { child ->
        CompositionLocalProvider(LocalRouteContext provides child.instance) {
            stackRouter.content(child.configuration)
        }
    }
}

@Composable
inline fun <reified R : Route> StackHost(
    start: R,
    link: R?,
    noinline onResult: (RouteResult<R>) -> Unit,
    animation: StackAnimation<R, RouteContext> = stackAnimationSharedAxisX(),
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    val stackRouter = rememberStackRouter<R>(
        start = start,
        link = link,
        onResult = onResult,
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
    noinline onResult: (RouteResult<R>) -> Unit,
    animation: StackAnimation<R, RouteContext> = stackAnimationSharedAxisX(),
    crossinline content: @Composable StackRouter<R>.(R) -> Unit,
) {
    val stackRouter = rememberStackRouter<R>(
        start = null,
        link = link,
        onResult = onResult,
        serializer = serializer()
    )

    StackHost(
        stackRouter = stackRouter,
        animation = animation,
        content = content,
    )
}
