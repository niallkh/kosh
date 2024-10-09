package kosh.ui.navigation.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.PredictiveBackAnimatable
import com.arkivanov.essenty.backhandler.BackEvent

data class StackAnimationData(
    val pushEnter: EnterTransition,
    val pushExit: ExitTransition,
    val popEnter: EnterTransition,
    val popExit: ExitTransition,
    val predictiveBack: (BackEvent) -> PredictiveBackAnimatable,
)

fun Density.sharedAxisX(
    slide: Int = 30.dp.roundToPx(),
): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisXIn(slide, pop = false),
    pushExit = sharedAxisXOut(slide, pop = false),
    popEnter = sharedAxisXIn(slide, pop = true),
    popExit = sharedAxisXOut(slide, pop = true),
    predictiveBack = { MaterialSharedAxisXAnimatable(it, slide) }
)

fun Density.sharedAxisY(
    slide: Int = 30.dp.roundToPx(),
): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisYIn(slide, pop = false),
    pushExit = sharedAxisYOut(slide, pop = false),
    popEnter = sharedAxisYIn(slide, pop = true),
    popExit = sharedAxisYOut(slide, pop = true),
    predictiveBack = { MaterialSharedAxisYAnimatable(it, slide) }
)

fun sharedAxisZ(): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisZIn(0.8f),
    pushExit = sharedAxisZOut(1.1f),
    popEnter = sharedAxisZIn(1.1f),
    popExit = sharedAxisZOut(0.8f),
    predictiveBack = { MaterialSharedAxisZAnimatable(it) }
)
