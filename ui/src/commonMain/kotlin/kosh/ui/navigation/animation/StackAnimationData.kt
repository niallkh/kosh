package kosh.ui.navigation.animation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.animation.StackAnimation

data class StackAnimationData(
    val pushEnter: EnterTransition,
    val pushExit: ExitTransition,
    val popEnter: EnterTransition,
    val popExit: ExitTransition,
)

@Composable
fun <C : Any, T : Any> materialStackAnimation(
    animation: Density.(C) -> StackAnimationData,
): StackAnimation<C, T> {
    val density = LocalDensity.current
    return MaterialStackAnimation(
        pushTransform = {
            ContentTransform(
                targetContentEnter = animation(density, it).pushEnter,
                initialContentExit = animation(density, it).pushExit,
                targetContentZIndex = 1f
            )
        },
        popTransform = {
            ContentTransform(
                targetContentEnter = animation(density, it).popEnter,
                initialContentExit = animation(density, it).popExit,
                targetContentZIndex = -1f
            )
        },
    )
}


fun Density.sharedAxisX(
    slide: Int = 30.dp.roundToPx(),
): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisXIn(slide, pop = false),
    pushExit = sharedAxisXOut(slide, pop = false),
    popEnter = sharedAxisXIn(slide, pop = true),
    popExit = sharedAxisXOut(slide, pop = true),
)

fun Density.sharedAxisY(
    slide: Int = 30.dp.roundToPx(),
): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisYIn(slide, pop = false),
    pushExit = sharedAxisYOut(slide, pop = false),
    popEnter = sharedAxisYIn(slide, pop = true),
    popExit = sharedAxisYOut(slide, pop = true),
)

fun sharedAxisZ(): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisZIn(0.8f),
    pushExit = sharedAxisZOut(1.1f),
    popEnter = sharedAxisZIn(1.1f),
    popExit = sharedAxisZOut(0.8f),
)

