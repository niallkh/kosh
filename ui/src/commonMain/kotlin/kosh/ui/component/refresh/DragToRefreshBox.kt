package kosh.ui.component.refresh

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun DragToRefreshBox(
    isRefreshing: Boolean,
    enabled: Boolean,
    onRefresh: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    state: PullToRefreshState = rememberPullToRefreshState(),
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLow,
    content: @Composable BoxScope.() -> Unit,
) {
    val threshold = PullToRefreshDefaults.PositionalThreshold

    Box(
        modifier
            .pullToRefresh(
                isRefreshing = isRefreshing,
                state = state,
                enabled = enabled,
                threshold = threshold
            ) { onRefresh() }
            .let { m -> scrollBehavior?.let { m.nestedScroll(it.nestedScrollConnection) } ?: m }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .padding(vertical = 20.dp)
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    val height = threshold * state.distanceFraction
                    layout(placeable.width, height.roundToPx()) {
                        placeable.place(0, 0)
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
                    hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                }
            }
    }
}
