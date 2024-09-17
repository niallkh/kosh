package kosh.ui.resources.illustrations

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
public fun NftEmpty(
    background: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    sun: Color = MaterialTheme.colorScheme.surfaceContainerHigh,
    mountainBackward: Color = MaterialTheme.colorScheme.surfaceContainer,
    mountainForward: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    outline: Color = MaterialTheme.colorScheme.primary,
): ImageVector = remember(background, sun, mountainBackward, mountainForward, outline) {
    Builder(
        name = "NftEmpty", defaultWidth = 128.0.dp, defaultHeight = 128.0.dp,
        viewportWidth = 128.0f, viewportHeight = 128.0f
    ).apply {
        path(
            fill = SolidColor(background),
            stroke = null,
            strokeLineWidth = 0.0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(0.0f, 0.0f)
            horizontalLineToRelative(128.0f)
            verticalLineToRelative(128.0f)
            horizontalLineToRelative(-128.0f)
            close()
        }
        path(
            fill = SolidColor(mountainBackward),
            stroke = null,
            strokeLineWidth = 0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(80.581f, 44.931f)
            lineTo(-8.0f, 129.0f)
            horizontalLineTo(185.0f)
            curveTo(172.29f, 116.138f, 104.984f, 50.154f, 99.353f, 44.931f)
            curveTo(93.721f, 39.707f, 85.86f, 39.673f, 80.581f, 44.931f)
            close()
        }
        path(
            fill = SolidColor(mountainForward),
            stroke = null,
            strokeLineWidth = 0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.173f, 67.859f)
            lineTo(-39.0f, 129.0f)
            horizontalLineTo(103.0f)
            curveTo(93.648f, 119.646f, 44.128f, 71.658f, 39.985f, 67.859f)
            curveTo(35.841f, 64.06f, 30.058f, 64.035f, 26.173f, 67.859f)
            close()
        }
        path(
            fill = SolidColor(sun),
            stroke = null,
            strokeLineWidth = 0f,
            strokeLineCap = Butt,
            strokeLineJoin = Miter,
            strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(32.0f, 32.0f)
            moveToRelative(-15.95f, 0.0f)
            arcToRelative(15.95f, 15.95f, 0.0f, true, true, 31.9f, 0.0f)
            arcToRelative(15.95f, 15.95f, 0.0f, true, true, -31.9f, 0.0f)
        }
    }
        .build()
}

