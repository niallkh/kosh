package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation

@Composable
fun <C : Any, T : Any> stackAnimationFadeThrough(): StackAnimation<C, T> = remember {
    MaterialStackAnimation(
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
