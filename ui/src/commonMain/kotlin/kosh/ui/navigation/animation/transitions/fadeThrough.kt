package kosh.ui.navigation.animation.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut

fun fadeThroughIn(): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
)

fun fadeThroughOut(): ExitTransition = fadeOut(
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )
)
