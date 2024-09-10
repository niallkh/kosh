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

public val Assets: ImageVector
    get() {
        if (_assets != null) {
            return _assets!!
        }
        _assets = Builder(
            name = "Assets", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.0f, 20.0f)
                curveTo(12.7667f, 20.0f, 10.875f, 19.225f, 9.325f, 17.675f)
                curveTo(7.775f, 16.125f, 7.0f, 14.2333f, 7.0f, 12.0f)
                curveTo(7.0f, 9.7667f, 7.775f, 7.875f, 9.325f, 6.325f)
                curveTo(10.875f, 4.775f, 12.7667f, 4.0f, 15.0f, 4.0f)
                curveTo(17.2333f, 4.0f, 19.125f, 4.775f, 20.675f, 6.325f)
                curveTo(22.225f, 7.875f, 23.0f, 9.7667f, 23.0f, 12.0f)
                curveTo(23.0f, 14.2333f, 22.225f, 16.125f, 20.675f, 17.675f)
                curveTo(19.125f, 19.225f, 17.2333f, 20.0f, 15.0f, 20.0f)
                close()
                moveTo(5.625f, 19.25f)
                curveTo(4.225f, 18.6f, 3.1042f, 17.625f, 2.2625f, 16.325f)
                curveTo(1.4208f, 15.025f, 1.0f, 13.5833f, 1.0f, 12.0f)
                curveTo(1.0f, 10.4167f, 1.4208f, 8.975f, 2.2625f, 7.675f)
                curveTo(3.1042f, 6.375f, 4.225f, 5.4f, 5.625f, 4.75f)
                curveTo(5.975f, 4.5833f, 6.2917f, 4.5958f, 6.575f, 4.7875f)
                curveTo(6.8583f, 4.9792f, 7.0f, 5.2917f, 7.0f, 5.725f)
                curveTo(7.0f, 5.8917f, 6.9458f, 6.0542f, 6.8375f, 6.2125f)
                curveTo(6.7292f, 6.3708f, 6.5917f, 6.4917f, 6.425f, 6.575f)
                curveTo(5.375f, 7.0583f, 4.5417f, 7.7875f, 3.925f, 8.7625f)
                curveTo(3.3083f, 9.7375f, 3.0f, 10.8167f, 3.0f, 12.0f)
                curveTo(3.0f, 13.1833f, 3.3083f, 14.2625f, 3.925f, 15.2375f)
                curveTo(4.5417f, 16.2125f, 5.375f, 16.9417f, 6.425f, 17.425f)
                curveTo(6.5917f, 17.5083f, 6.7292f, 17.625f, 6.8375f, 17.775f)
                curveTo(6.9458f, 17.925f, 7.0f, 18.0917f, 7.0f, 18.275f)
                curveTo(7.0f, 18.6917f, 6.8583f, 19.0f, 6.575f, 19.2f)
                curveTo(6.2917f, 19.4f, 5.975f, 19.4167f, 5.625f, 19.25f)
                close()
                moveTo(15.0f, 18.0f)
                curveTo(16.6667f, 18.0f, 18.0833f, 17.4167f, 19.25f, 16.25f)
                curveTo(20.4167f, 15.0833f, 21.0f, 13.6667f, 21.0f, 12.0f)
                curveTo(21.0f, 10.3333f, 20.4167f, 8.9167f, 19.25f, 7.75f)
                curveTo(18.0833f, 6.5833f, 16.6667f, 6.0f, 15.0f, 6.0f)
                curveTo(13.3333f, 6.0f, 11.9167f, 6.5833f, 10.75f, 7.75f)
                curveTo(9.5833f, 8.9167f, 9.0f, 10.3333f, 9.0f, 12.0f)
                curveTo(9.0f, 13.6667f, 9.5833f, 15.0833f, 10.75f, 16.25f)
                curveTo(11.9167f, 17.4167f, 13.3333f, 18.0f, 15.0f, 18.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.0f, 12.0f)
                moveToRelative(-8.0f, 0.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, true, true, 16.0f, 0.0f)
                arcToRelative(8.0f, 8.0f, 0.0f, true, true, -16.0f, 0.0f)
            }
        }
            .build()
        return _assets!!
    }

private var _assets: ImageVector? = null
