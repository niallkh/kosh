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

public val KoshForeground: ImageVector
    get() {
        if (koshForeground != null) {
            return koshForeground!!
        }
        koshForeground = Builder(
            name = "KoshForeground",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF448AFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.3019f, 19.9525f)
                lineTo(12.4335f, 23.821f)
                curveTo(12.1944f, 24.0601f, 11.8067f, 24.0601f, 11.5676f, 23.821f)
                lineTo(9.2356f, 21.489f)
                curveTo(9.1208f, 21.3742f, 9.0563f, 21.2185f, 9.0562f, 21.0561f)
                lineTo(9.0554f, 13.3183f)
                curveTo(9.0553f, 12.7728f, 9.7148f, 12.4996f, 10.1005f, 12.8853f)
                lineTo(16.3019f, 19.0867f)
                curveTo(16.541f, 19.3258f, 16.541f, 19.7134f, 16.3019f, 19.9525f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF448AFF)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.3014f, 4.0478f)
                lineTo(12.4329f, 0.1793f)
                curveTo(12.1938f, -0.0598f, 11.8062f, -0.0598f, 11.5671f, 0.1793f)
                lineTo(9.2346f, 2.5118f)
                curveTo(9.1197f, 2.6267f, 9.0552f, 2.7825f, 9.0552f, 2.9449f)
                lineTo(9.057f, 10.6801f)
                curveTo(9.0571f, 11.2255f, 9.7165f, 11.4986f, 10.1022f, 11.1129f)
                lineTo(16.3014f, 4.9137f)
                curveTo(16.5405f, 4.6746f, 16.5405f, 4.2869f, 16.3014f, 4.0478f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF00BCD4)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.9724f, 6.5845f)
                curveTo(18.2115f, 6.3454f, 18.5991f, 6.3454f, 18.8382f, 6.5845f)
                lineTo(23.8207f, 11.567f)
                curveTo(24.0598f, 11.8061f, 24.0598f, 12.1937f, 23.8207f, 12.4328f)
                lineTo(18.8382f, 17.4153f)
                curveTo(18.5991f, 17.6544f, 18.2115f, 17.6544f, 17.9724f, 17.4153f)
                lineTo(12.9899f, 12.4328f)
                curveTo(12.7508f, 12.1937f, 12.7508f, 11.8061f, 12.9899f, 11.567f)
                lineTo(17.9724f, 6.5845f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF00BCD4)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.6635f, 6.0828f)
                lineTo(0.1793f, 11.567f)
                curveTo(-0.0598f, 11.8061f, -0.0598f, 12.1938f, 0.1793f, 12.4329f)
                lineTo(5.6635f, 17.917f)
                curveTo(6.0492f, 18.3027f, 6.7087f, 18.0296f, 6.7087f, 17.4841f)
                verticalLineTo(6.5158f)
                curveTo(6.7087f, 5.9703f, 6.0492f, 5.6972f, 5.6635f, 6.0828f)
                close()
            }
        }
            .build()
        return koshForeground!!
    }

private var koshForeground: ImageVector? = null
