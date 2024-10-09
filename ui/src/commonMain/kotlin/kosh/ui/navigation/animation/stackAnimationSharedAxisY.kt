package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation

@Composable
fun <C : Any, T : Any> stackAnimationSharedAxisY(
    slide: Int = LocalDensity.current.run { 30.dp.roundToPx() },
): StackAnimation<C, T> = remember {
    MaterialStackAnimation(
        pushTransform = {
            ContentTransform(
                targetContentEnter = sharedAxisYIn(slide, pop = false),
                initialContentExit = sharedAxisYOut(slide, pop = false),
                targetContentZIndex = 1f
            )
        },
        popTransform = {
            ContentTransform(
                targetContentEnter = sharedAxisYIn(slide, pop = true),
                initialContentExit = sharedAxisYOut(slide, pop = true),
                targetContentZIndex = -1f
            )
        },
    )
}
