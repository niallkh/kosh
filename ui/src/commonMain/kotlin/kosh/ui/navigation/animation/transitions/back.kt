package kosh.ui.navigation.animation.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun backSharedAxisXIn(
    slide: Int,
): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
) + slideInHorizontally(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) { -slide } + scaleIn(
    initialScale = 0.92f,
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
)

fun backSharedAxisXOut(
    slide: Int,
): ExitTransition = fadeOut(
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )
) + slideOutHorizontally(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) { slide } + scaleOut(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    ),
    targetScale = 0.92f
)
