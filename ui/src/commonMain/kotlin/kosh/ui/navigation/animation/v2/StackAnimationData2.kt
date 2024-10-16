package kosh.ui.navigation.animation.v2

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex

data class StackAnimationData2(
    val pushEnter: @Composable Transition<Boolean>.() -> Modifier,
    val pushExit: @Composable Transition<Boolean>.() -> Modifier,
    val popEnter: @Composable Transition<Boolean>.() -> Modifier,
    val popExit: @Composable Transition<Boolean>.() -> Modifier,

    val backEnter: @Composable Transition<Boolean>.(BackTransition) -> Modifier,
    val backExit: @Composable Transition<Boolean>.(BackTransition) -> Modifier,
)

private fun sharedAxisX2(): StackAnimationData2 = StackAnimationData2(
    pushEnter = { sharedAxisEnter(pop = false) },
    pushExit = { sharedAxisExit(pop = false) },
    popEnter = { sharedAxisEnter(pop = true) },
    popExit = { sharedAxisExit(pop = true) },
    backEnter = { backEnter(it) },
    backExit = { backExit(it) }
)

@Composable
private fun Transition<Boolean>.sharedAxisEnter(
    slide: Dp = 30.dp,
    pop: Boolean,
): Modifier {
    val fade by animateFloat(
        {
            tween()
        },
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

@Composable
private fun Transition<Boolean>.backExit(
    backTransition: BackTransition,
    slide: Dp = 90.dp,
    layoutShape: Shape = MaterialTheme.shapes.large,
): Modifier {
    val fade by animateFloat(
        {
            tween(
                delayMillis = 90,
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
                delayMillis = 90,
                durationMillis = 210,
                easing = FastOutSlowInEasing
            )
        }
    ) {
        if (it) 0.dp else slide
    }

    val scaleAnim = remember { Animatable(1f) }
    val scale by scaleAnim.asState()

    val downScale by animateFloat(
        {
            tween(
                durationMillis = 90,
            )
        }
    ) {
        if (it) 1f else 0.9f
    }

    LaunchedEffect(backTransition.released) {
        if (backTransition.released) {
            scaleAnim.animateTo(1f)
        } else {
            snapshotFlow { downScale }.collect {
                scaleAnim.animateTo(it)
            }
        }
    }

    return Modifier
        .zIndex(1f)
        .graphicsLayer {
            alpha = fade
            translationX = slide1.toPx()
            scaleX = scale
            scaleY = scale
            shape = layoutShape
            clip = true
            transformOrigin = TransformOrigin(
                pivotFractionX = if (backTransition.leftEdge) 0.75f else 0.5f,
                pivotFractionY = backTransition.touchY / size.height
            )
        }
}

@Composable
private fun Transition<Boolean>.backEnter(
    backTransition: BackTransition,
    slide: Dp = 90.dp,
): Modifier {
    val scrim by animateFloat(
        {
            tween(
                delayMillis = 90,
                durationMillis = 90,
                easing = LinearOutSlowInEasing
            )
        }
    ) {
        if (it) 0f else 0.25f
    }

    val slide1 by animateDp(
        {
            tween(
                delayMillis = 90,
                durationMillis = 210,
                easing = FastOutSlowInEasing
            )
        }
    ) {
        if (it) 0.dp else -slide
    }

    val scaleAnim = remember { Animatable(1f) }
    val scale by scaleAnim.asState()

    val downScale by animateFloat(
        {
            tween(
                durationMillis = 90,
            )
        }
    ) {
        if (it) 0.9f else 1f
    }

    LaunchedEffect(backTransition.released) {
        if (backTransition.released) {
            scaleAnim.animateTo(1f)
        } else {
            snapshotFlow { downScale }.collect {
                scaleAnim.animateTo(it)
            }
        }
    }

    return Modifier
        .zIndex(-1f)
        .drawWithContent {
            drawContent()
            drawRect(color = Color.Black.copy(alpha = scrim))
        }
        .graphicsLayer {
            translationX = slide1.toPx()
            scaleX = scale
            scaleY = scale
        }
}
