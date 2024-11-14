package kosh.ui.navigation.stack

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import kosh.presentation.core.LocalPresentationContext
import kosh.presentation.core.PresentationContext
import kosh.ui.navigation.animation.backStackAnimation
import kosh.ui.navigation.routes.Route

@Composable
inline fun <R : Route> StackHost(
    stackRouter: StackRouter<R>,
    noinline onBack: () -> Unit,
    animation: StackAnimation<R, PresentationContext>? = null,
    crossinline content: @Composable (R) -> Unit,
) {
    Children(
        stack = stackRouter.stack,
        animation = animation ?: backStackAnimation(
            backHandler = stackRouter.backHandler,
            onBack = onBack,
        ),
    ) { child ->
        CompositionLocalProvider(LocalPresentationContext provides child.instance) {
            content(child.configuration)
        }
    }
}
