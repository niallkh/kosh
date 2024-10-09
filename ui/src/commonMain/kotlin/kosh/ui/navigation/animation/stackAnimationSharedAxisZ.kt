package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation

@Composable
fun <C : Any, T : Any> stackAnimationSharedAxisZ(): StackAnimation<C, T> = remember {
    MaterialStackAnimation(
        pushTransform = {
            ContentTransform(
                targetContentEnter = sharedAxisZIn(0.8f),
                initialContentExit = sharedAxisZOut(1.1f),
                targetContentZIndex = 1f
            )
        },
        popTransform = {
            ContentTransform(
                targetContentEnter = sharedAxisZIn(1.1f),
                initialContentExit = sharedAxisZOut(0.8f),
                targetContentZIndex = -1f
            )
        },
    )
}
