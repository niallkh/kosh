package kosh.ui.resources.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

public val OpenSea: ImageVector
    get() {
        if (_opensea != null) {
            return _opensea!!
        }
        _opensea = Builder(
            name = "OpenSea", defaultWidth = 100.0.dp, defaultHeight = 100.0.dp,
            viewportWidth = 100.0f, viewportHeight = 100.0f
        ).apply {

            path(
                fill = SolidColor(Color(0xFF2081E2)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(50.4f, 0.5f)
                curveTo(22.8f, 0.3f, 0.3f, 22.8f, 0.5f, 50.4f)
                curveToRelative(0.2f, 26.9f, 22.2f, 48.8f, 49.1f, 49.1f)
                curveToRelative(27.7f, 0.2f, 50.2f, -22.3f, 49.9f, -49.9f)
                curveTo(99.3f, 22.7f, 77.3f, 0.7f, 50.4f, 0.5f)
                close()
            }

            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(35.6f, 25.9f)
                curveToRelative(3.1f, 3.9f, 4.9f, 8.8f, 4.9f, 14.2f)
                curveToRelative(0.0f, 4.6f, -1.4f, 8.9f, -3.7f, 12.5f)
                horizontalLineTo(20.2f)
                lineTo(35.6f, 25.9f)
                lineTo(35.6f, 25.9f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(86.3f, 58.5f)
                curveToRelative(0.0f, 0.2f, -0.1f, 0.4f, -0.3f, 0.5f)
                curveToRelative(-1.1f, 0.5f, -4.8f, 2.2f, -6.4f, 4.4f)
                curveToRelative(-4.0f, 5.5f, -7.0f, 14.3f, -13.8f, 14.3f)
                horizontalLineTo(37.3f)
                curveToRelative(-10.1f, 0.0f, -18.5f, -8.0f, -18.4f, -18.6f)
                curveToRelative(0.0f, -0.3f, 0.2f, -0.5f, 0.5f, -0.5f)
                horizontalLineToRelative(13.4f)
                curveToRelative(0.5f, 0.0f, 0.8f, 0.4f, 0.8f, 0.8f)
                verticalLineToRelative(2.6f)
                curveToRelative(0.0f, 1.4f, 1.1f, 2.5f, 2.5f, 2.5f)
                horizontalLineToRelative(10.2f)
                verticalLineToRelative(-5.9f)
                horizontalLineToRelative(-7.0f)
                curveToRelative(4.0f, -5.1f, 6.4f, -11.5f, 6.4f, -18.5f)
                curveToRelative(0.0f, -7.8f, -3.0f, -14.9f, -7.9f, -20.2f)
                curveToRelative(3.0f, 0.3f, 5.8f, 0.9f, 8.4f, 1.7f)
                verticalLineToRelative(-1.7f)
                curveToRelative(0.0f, -1.7f, 1.4f, -3.1f, 3.1f, -3.1f)
                curveToRelative(1.7f, 0.0f, 3.1f, 1.4f, 3.1f, 3.1f)
                verticalLineToRelative(4.0f)
                curveToRelative(9.5f, 4.4f, 15.8f, 11.8f, 15.8f, 20.2f)
                curveToRelative(0.0f, 4.9f, -2.1f, 9.5f, -5.8f, 13.3f)
                curveToRelative(-0.7f, 0.7f, -1.7f, 1.1f, -2.7f, 1.1f)
                horizontalLineToRelative(-7.2f)
                verticalLineToRelative(5.9f)
                horizontalLineToRelative(9.0f)
                curveToRelative(1.9f, 0.0f, 5.4f, -3.7f, 7.1f, -5.9f)
                curveToRelative(0.0f, 0.0f, 0.1f, -0.1f, 0.3f, -0.2f)
                curveToRelative(0.2f, -0.1f, 16.6f, -3.8f, 16.6f, -3.8f)
                curveToRelative(0.3f, -0.1f, 0.7f, 0.2f, 0.7f, 0.5f)
                lineTo(86.3f, 58.5f)
                lineTo(86.3f, 58.5f)
                close()
            }
        }
            .build()
        return _opensea!!
    }

private var _opensea: ImageVector? = null
