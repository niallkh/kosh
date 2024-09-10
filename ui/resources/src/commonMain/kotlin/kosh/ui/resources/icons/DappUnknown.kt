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

public val DappUnknown: ImageVector
    get() {
        if (_dappUnknown != null) {
            return _dappUnknown!!
        }
        _dappUnknown = Builder(
            name = "DappUnknown",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f,
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.8533f, 3.3963f)
                curveTo(12.4634f, 2.7582f, 11.5366f, 2.7582f, 11.1467f, 3.3963f)
                lineTo(7.4298f, 9.4785f)
                curveTo(7.0226f, 10.1449f, 7.5021f, 11.0f, 8.2831f, 11.0f)
                horizontalLineTo(15.7169f)
                curveTo(16.4979f, 11.0f, 16.9774f, 10.1449f, 16.5702f, 9.4785f)
                lineTo(12.8533f, 3.3963f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.5f, 22.0f)
                curveTo(19.9853f, 22.0f, 22.0f, 19.9853f, 22.0f, 17.5f)
                curveTo(22.0f, 15.0147f, 19.9853f, 13.0f, 17.5f, 13.0f)
                curveTo(15.0147f, 13.0f, 13.0f, 15.0147f, 13.0f, 17.5f)
                curveTo(13.0f, 19.9853f, 15.0147f, 22.0f, 17.5f, 22.0f)
                close()
            }
            path(
                fill = SolidColor(Color.Black),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(3.0f, 14.5f)
                curveTo(3.0f, 13.9477f, 3.4477f, 13.5f, 4.0f, 13.5f)
                horizontalLineTo(10.0f)
                curveTo(10.5523f, 13.5f, 11.0f, 13.9477f, 11.0f, 14.5f)
                verticalLineTo(20.5f)
                curveTo(11.0f, 21.0523f, 10.5523f, 21.5f, 10.0f, 21.5f)
                horizontalLineTo(4.0f)
                curveTo(3.4477f, 21.5f, 3.0f, 21.0523f, 3.0f, 20.5f)
                verticalLineTo(14.5f)
                close()
            }
        }
            .build()
        return _dappUnknown!!
    }

private var _dappUnknown: ImageVector? = null
