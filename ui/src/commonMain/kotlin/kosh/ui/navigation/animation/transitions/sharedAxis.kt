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
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

fun sharedAxisXIn(
    slide: Int,
    pop: Boolean,
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
) { if (!pop) slide else -slide }

fun sharedAxisXOut(
    slide: Int,
    pop: Boolean,
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
) { if (!pop) -slide else slide }

fun sharedAxisYIn(
    slide: Int,
    pop: Boolean,
): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
) + slideInVertically(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) { if (!pop) slide else -slide }

fun sharedAxisYOut(
    slide: Int,
    pop: Boolean,
): ExitTransition = fadeOut(
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )
) + slideOutVertically(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    )
) { if (!pop) -slide else slide }

fun sharedAxisZIn(
    scale: Float,
): EnterTransition = fadeIn(
    tween(
        delayMillis = 90,
        durationMillis = 210,
        easing = LinearOutSlowInEasing
    ),
) + scaleIn(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    ),
    initialScale = scale
)

fun sharedAxisZOut(
    scale: Float,
): ExitTransition = fadeOut(
    animationSpec = tween(
        delayMillis = 0,
        durationMillis = 90,
        easing = FastOutLinearInEasing
    )
) + scaleOut(
    tween(
        delayMillis = 0,
        durationMillis = 300,
        easing = FastOutSlowInEasing
    ),
    targetScale = scale
)
