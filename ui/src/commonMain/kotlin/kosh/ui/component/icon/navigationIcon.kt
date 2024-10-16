package kosh.ui.component.icon

import androidx.compose.animation.core.AnimationConstants.DefaultDurationMillis
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun navigationIcon(
    isTop: Boolean = true,
): Painter = rememberVectorPainter(
    name = "NavigationIcon",
    defaultHeight = 24.dp,
    defaultWidth = 24.dp,
    viewportHeight = 24f,
    viewportWidth = 24f,
    autoMirror = true,
) { width, height ->
    val transition = updateTransition(targetState = isTop, label = null)

    val rotation by transition.animateFloat(
        label = "Rotation",
        transitionSpec = { tween(DefaultDurationMillis) }
    ) {
        if (it) -180f else 0f
    }

    val fraction by transition.animateFloat(
        label = "PathNodes",
        transitionSpec = { tween(DefaultDurationMillis) }
    ) {
        if (it) 0f else 1f
    }

    Group(
        name = "GroupMenuOrArrowBack",
        rotation = rotation,
        pivotX = width / 2,
        pivotY = height / 2,
    ) {
        Path(
            pathData = pathNodes(fraction),
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 2.0f,
            strokeLineCap = StrokeCap.Square,
            strokeAlpha = 1.0f,
        )
    }
}

@Composable
private fun pathNodes(
    fraction: Float,
): List<PathNode> {

    val menuPathNodes = remember {
        listOf(
            PathNode.MoveTo(3f, 7f),
            PathNode.LineTo(21f, 7f),
            PathNode.Close,

            PathNode.MoveTo(3f, 12f),
            PathNode.LineTo(21f, 12f),
            PathNode.Close,

            PathNode.MoveTo(3f, 17f),
            PathNode.LineTo(21f, 17f),
            PathNode.Close,
        )
    }

    val arrowBackPathNodes = remember {
        listOf(
            PathNode.MoveTo(4.70711f, 12.7071f),
            PathNode.LineTo(12.7071f, 4.70711f),
            PathNode.Close,

            PathNode.MoveTo(6f, 12f),
            PathNode.LineTo(20f, 12f),
            PathNode.Close,

            PathNode.MoveTo(4.70711f, 11.2929f),
            PathNode.LineTo(12.7071f, 19.2929f),
            PathNode.Close,
        )
    }

    return remember(fraction) {
        lerp(menuPathNodes, arrowBackPathNodes, fraction)
    }
}

private fun lerp(start: List<PathNode>, stop: List<PathNode>, fraction: Float): List<PathNode> =
    start.zip(stop) { a, b -> lerp(a, b, fraction) }

private fun lerp(start: PathNode, stop: PathNode, fraction: Float): PathNode = when (start) {
    is PathNode.MoveTo -> {
        require(stop is PathNode.MoveTo)
        PathNode.MoveTo(
            lerp(start.x, stop.x, fraction),
            lerp(start.y, stop.y, fraction)
        )
    }

    is PathNode.LineTo -> {
        require(stop is PathNode.LineTo)
        PathNode.LineTo(
            lerp(start.x, stop.x, fraction),
            lerp(start.y, stop.y, fraction)
        )
    }

    is PathNode.Close -> {
        require(stop is PathNode.Close)
        PathNode.Close
    }

    else -> error("Invalid path node")
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float =
    (1 - fraction) * start + fraction * stop
