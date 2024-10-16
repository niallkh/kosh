package kosh.ui.navigation.animation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class StackAnimationData2(
    val pushEnter: @Composable Transition<Boolean>.() -> Modifier,
    val pushExit: @Composable Transition<Boolean>.() -> Modifier,
    val popEnter: @Composable Transition<Boolean>.() -> Modifier,
    val popExit: @Composable Transition<Boolean>.() -> Modifier,
    val backEnter: @Composable Transition<Boolean>.() -> Modifier,
    val backExit: @Composable Transition<Boolean>.() -> Modifier,
)

fun sharedAxisX2(): StackAnimationData2 = StackAnimationData2(
    pushEnter = { sharedAxisEnter(pop = false) },
    pushExit = { sharedAxisExit(pop = false) },
    popEnter = { sharedAxisEnter(pop = true) },
    popExit = { sharedAxisExit(pop = true) },
    backEnter = { sharedAxisEnter(pop = true) },
    backExit = { sharedAxisExit(pop = true) }
)

@Composable
private fun Transition<Boolean>.sharedAxisEnter(
    slide: Dp = 30.dp,
    pop: Boolean,
): Modifier {
    val fade by animateFloat(
        {
            tween(
                delayMillis = 90,
                durationMillis = 210,
                easing = LinearOutSlowInEasing
            )
        }
    ) {
        if (it) 1f else 0f
    }

    val slide1 by animateDp(
        {
            tween(
                delayMillis = 0,
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        }
    ) {
        if (it) 0.dp else if (pop) -slide else slide
    }

    return Modifier.graphicsLayer {
        alpha = fade
        translationX = slide1.toPx()
    }
}

@Composable
private fun Transition<Boolean>.sharedAxisExit(
    slide: Dp = 30.dp,
    pop: Boolean,
): Modifier {
    val fade by animateFloat(
        {
            tween(
                delayMillis = 0,
                durationMillis = 90,
                easing = FastOutLinearInEasing
            )
        }
    ) {
        if (it) 1f else 0f
    }

    val slide1 by animateDp(
        {
            tween(
                delayMillis = 0,
                durationMillis = 300,
                easing = FastOutSlowInEasing
            )
        }
    ) {
        if (it) 0.dp else if (pop) slide else -slide
    }

    return Modifier.graphicsLayer {
        alpha = fade
        translationX = slide1.toPx()
    }
}
