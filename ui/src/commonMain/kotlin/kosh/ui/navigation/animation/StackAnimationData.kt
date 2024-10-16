package kosh.ui.navigation.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import kosh.ui.navigation.animation.transitions.backSharedAxisXIn
import kosh.ui.navigation.animation.transitions.backSharedAxisXOut
import kosh.ui.navigation.animation.transitions.sharedAxisXIn
import kosh.ui.navigation.animation.transitions.sharedAxisXOut
import kosh.ui.navigation.animation.transitions.sharedAxisYIn
import kosh.ui.navigation.animation.transitions.sharedAxisYOut
import kosh.ui.navigation.animation.transitions.sharedAxisZIn
import kosh.ui.navigation.animation.transitions.sharedAxisZOut

data class StackAnimationData(
    val pushEnter: EnterTransition,
    val pushExit: ExitTransition,
    val popEnter: EnterTransition,
    val popExit: ExitTransition,
    val backEnter: EnterTransition? = null,
    val backExit: ExitTransition? = null,
)

fun Density.sharedAxisX(
    slide: Int = 30.dp.roundToPx(),
): StackAnimationData = StackAnimationData(
    pushEnter = sharedAxisXIn(slide, pop = false),
    pushExit = sharedAxisXOut(slide, pop = false),
    popEnter = sharedAxisXIn(slide, pop = true),
    popExit = sharedAxisXOut(slide, pop = true),
    backEnter = backSharedAxisXIn(slide),
    backExit = backSharedAxisXOut(slide),
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
