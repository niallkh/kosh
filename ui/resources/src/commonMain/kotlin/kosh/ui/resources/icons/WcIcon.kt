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

public val WcIcon: ImageVector
    get() {
        if (_wcIcon != null) {
            return _wcIcon!!
        }
        _wcIcon = Builder(
            name = "Wc",
            defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp,
            viewportWidth = 24.0f,
            viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF141414)),
                stroke = null,
                strokeLineWidth = 0.0f,
                strokeLineCap = Butt,
                strokeLineJoin = Miter,
                strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(4.9133f, 7.8625f)
                curveTo(8.8271f, 4.0458f, 15.1729f, 4.0458f, 19.0867f, 7.8625f)
                lineTo(19.5577f, 8.3218f)
                curveTo(19.7534f, 8.5126f, 19.7534f, 8.8221f, 19.5577f, 9.0128f)
                lineTo(17.9464f, 10.5842f)
                curveTo(17.8485f, 10.6796f, 17.6899f, 10.6796f, 17.5921f, 10.5842f)
                lineTo(16.9439f, 9.9521f)
                curveTo(14.2134f, 7.2895f, 9.7866f, 7.2895f, 7.0561f, 9.9521f)
                lineTo(6.3619f, 10.629f)
                curveTo(6.2641f, 10.7244f, 6.1055f, 10.7244f, 6.0076f, 10.629f)
                lineTo(4.3963f, 9.0577f)
                curveTo(4.2006f, 8.8669f, 4.2006f, 8.5574f, 4.3963f, 8.3666f)
                lineTo(4.9133f, 7.8625f)
                close()
                moveTo(22.4191f, 11.1121f)
                lineTo(23.8532f, 12.5106f)
                curveTo(24.0489f, 12.7014f, 24.0489f, 13.0108f, 23.8532f, 13.2016f)
                lineTo(17.3867f, 19.5074f)
                curveTo(17.1911f, 19.6983f, 16.8738f, 19.6983f, 16.6781f, 19.5074f)
                lineTo(12.0886f, 15.032f)
                curveTo(12.0397f, 14.9843f, 11.9604f, 14.9843f, 11.9115f, 15.032f)
                lineTo(7.322f, 19.5074f)
                curveTo(7.1264f, 19.6983f, 6.8091f, 19.6983f, 6.6134f, 19.5074f)
                lineTo(0.1468f, 13.2015f)
                curveTo(-0.0489f, 13.0107f, -0.0489f, 12.7013f, 0.1468f, 12.5105f)
                lineTo(1.5809f, 11.112f)
                curveTo(1.7766f, 10.9211f, 2.0938f, 10.9211f, 2.2895f, 11.112f)
                lineTo(6.8791f, 15.5875f)
                curveTo(6.928f, 15.6352f, 7.0073f, 15.6352f, 7.0562f, 15.5875f)
                lineTo(11.6456f, 11.112f)
                curveTo(11.8412f, 10.9211f, 12.1585f, 10.9211f, 12.3542f, 11.112f)
                lineTo(16.9438f, 15.5875f)
                curveTo(16.9927f, 15.6352f, 17.072f, 15.6352f, 17.1209f, 15.5875f)
                lineTo(21.7105f, 11.1121f)
                curveTo(21.9061f, 10.9212f, 22.2234f, 10.9212f, 22.4191f, 11.1121f)
                close()
            }
        }
            .build()
        return _wcIcon!!
    }

private var _wcIcon: ImageVector? = null
