package kosh.ui.component.refresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun PullRefreshBox(
    state: PullToRefreshState,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    content: @Composable BoxScope.() -> Unit,
) {
    val threshold = PullToRefreshDefaults.PositionalThreshold

    Box {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(containerColor)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    val height = (threshold * state.distanceFraction).roundToPx()
                    layout(placeable.width, height) {
                        placeable.place(0, 20.dp.roundToPx())
                    }
                },
            contentAlignment = Alignment.TopCenter
        ) {
            Crossfade(
                targetState = isRefreshing,
                animationSpec = tween(durationMillis = 100)
            ) {
                if (it) {
                    CircularProgressIndicator()
                } else {
                    CircularProgressIndicator(
                        progress = { state.distanceFraction.coerceIn(0f, 1f) },
                        trackColor = Color.Transparent,
                    )
                }
            }
        }

        Box(
            Modifier.offset { IntOffset(0, (threshold * state.distanceFraction).roundToPx()) }
        ) {
            content()
        }
    }

    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(Unit) {
        snapshotFlow { state.distanceFraction }
            .map { it > 1f }
            .distinctUntilChanged()
            .collect {
                if (it) {
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
    }
}
