package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation

fun <C : Any, T : Any> stackAnimationFadeThrough(): StackAnimation<C, T> =
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
