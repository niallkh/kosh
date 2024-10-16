package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation
import kosh.ui.navigation.animation.transitions.fadeThroughIn
import kosh.ui.navigation.animation.transitions.fadeThroughOut

@Composable
fun <C : Any, T : Any> stackAnimationFadeThrough(): StackAnimation<C, T> = remember {
    KoshStackAnimation(
        pushTransform = {
            ContentTransform(
                targetContentEnter = fadeThroughIn(),
                initialContentExit = fadeThroughOut(),
                targetContentZIndex = 1f
            )
        },
        popTransform = {
            ContentTransform(
                targetContentEnter = fadeThroughIn(),
                initialContentExit = fadeThroughOut(),
                targetContentZIndex = -1f
            )
        },
    )
}
