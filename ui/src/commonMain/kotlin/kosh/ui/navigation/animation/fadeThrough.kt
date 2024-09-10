package kosh.ui.navigation.animation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn

fun fadeThroughIn(): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
) + scaleIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
    initialScale = 0.92f
)

fun fadeThroughOut(): ExitTransition = fadeOut(
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )
)
