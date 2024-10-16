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
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

fun predictiveSharedAxisXIn(
    slide: Int,
): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
) + scaleIn(
    initialScale = 0.92f,
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) + slideInHorizontally(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) { -slide }

fun predictiveSharedAxisXOut(
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
) { slide }
