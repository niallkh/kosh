package kosh.ui.navigation.animation.transitions

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn

fun fadeIn(): EnterTransition = fadeIn(
    tween(
        durationMillis = 45,
        easing = LinearOutSlowInEasing
    ),
) + scaleIn(
    tween(
        durationMillis = 150,
        easing = LinearOutSlowInEasing
    ),
    initialScale = 0.8f
)

fun fadeOut(): ExitTransition = fadeOut(
    animationSpec = tween(
        durationMillis = 75,
        easing = FastOutLinearInEasing
    )
)
