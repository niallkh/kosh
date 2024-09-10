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

public val PersonalSignature: ImageVector
    get() {
        if (_personalSignature != null) {
            return _personalSignature!!
        }
        _personalSignature = Builder(
            name = "PersonalSignature", defaultWidth = 24.0.dp,
            defaultHeight = 24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.6318f, 14.4196f)
                curveTo(15.9053f, 13.4554f, 16.8997f, 12.3973f, 17.615f, 11.2455f)
                curveTo(18.3302f, 10.0938f, 18.6879f, 8.9464f, 18.6879f, 7.8036f)
                curveTo(18.6879f, 7.2321f, 18.5963f, 6.8125f, 18.4131f, 6.5446f)
                curveTo(18.2299f, 6.2768f, 17.9813f, 6.1429f, 17.6673f, 6.1429f)
                curveTo(16.8474f, 6.1429f, 16.1234f, 6.8527f, 15.4953f, 8.2723f)
                curveTo(14.8673f, 9.692f, 14.5533f, 11.2946f, 14.5533f, 13.0804f)
                curveTo(14.5533f, 13.3304f, 14.5576f, 13.567f, 14.5664f, 13.7902f)
                curveTo(14.5751f, 14.0134f, 14.5969f, 14.2232f, 14.6318f, 14.4196f)
                close()
                moveTo(16.071f, 19.0f)
                curveTo(15.5477f, 19.0f, 15.0679f, 18.8973f, 14.6318f, 18.692f)
                curveTo(14.1956f, 18.4866f, 13.8206f, 18.1518f, 13.5065f, 17.6875f)
                curveTo(13.0704f, 17.9375f, 12.6212f, 18.1607f, 12.1589f, 18.3571f)
                curveTo(11.6966f, 18.5536f, 11.2212f, 18.75f, 10.7327f, 18.9464f)
                lineTo(10.0f, 16.9375f)
                curveTo(10.4885f, 16.7589f, 10.9551f, 16.567f, 11.4f, 16.3616f)
                curveTo(11.8449f, 16.1562f, 12.2766f, 15.9375f, 12.6953f, 15.7054f)
                curveTo(12.6081f, 15.3125f, 12.5427f, 14.8839f, 12.4991f, 14.4196f)
                curveTo(12.4555f, 13.9554f, 12.4336f, 13.4554f, 12.4336f, 12.9196f)
                curveTo(12.4336f, 10.3482f, 12.9308f, 8.2188f, 13.9252f, 6.5313f)
                curveTo(14.9196f, 4.8438f, 16.167f, 4.0f, 17.6673f, 4.0f)
                curveTo(18.5745f, 4.0f, 19.3159f, 4.3438f, 19.8916f, 5.0313f)
                curveTo(20.4673f, 5.7188f, 20.7551f, 6.6786f, 20.7551f, 7.9107f)
                curveTo(20.7551f, 9.4464f, 20.2798f, 10.9643f, 19.329f, 12.4643f)
                curveTo(18.3782f, 13.9643f, 17.0567f, 15.3125f, 15.3645f, 16.5089f)
                curveTo(15.4866f, 16.6339f, 15.6131f, 16.7277f, 15.7439f, 16.7902f)
                curveTo(15.8748f, 16.8527f, 16.01f, 16.8839f, 16.1495f, 16.8839f)
                curveTo(16.6031f, 16.8839f, 17.1308f, 16.5893f, 17.7327f, 16.0f)
                curveTo(18.3346f, 15.4107f, 18.8798f, 14.6339f, 19.3682f, 13.6696f)
                lineTo(21.2785f, 14.5804f)
                curveTo(21.1564f, 14.8839f, 21.0604f, 15.25f, 20.9907f, 15.6786f)
                curveTo(20.9209f, 16.1071f, 20.9296f, 16.4821f, 21.0168f, 16.8036f)
                curveTo(21.1913f, 16.7143f, 21.3963f, 16.5625f, 21.6318f, 16.3482f)
                curveTo(21.8673f, 16.1339f, 22.1072f, 15.8661f, 22.3514f, 15.5446f)
                lineTo(24.0f, 16.8571f)
                curveTo(23.5464f, 17.5f, 23.0231f, 18.0179f, 22.4299f, 18.4107f)
                curveTo(21.8368f, 18.8036f, 21.2872f, 19.0f, 20.7813f, 19.0f)
                curveTo(20.415f, 19.0f, 20.0879f, 18.8884f, 19.8f, 18.6652f)
                curveTo(19.5122f, 18.442f, 19.2723f, 18.0982f, 19.0804f, 17.6339f)
                curveTo(18.5919f, 18.0804f, 18.0947f, 18.4196f, 17.5888f, 18.6518f)
                curveTo(17.0829f, 18.8839f, 16.5769f, 19.0f, 16.071f, 19.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                strokeLineWidth = 1.8f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(7.1f, 15.0f)
                curveTo(7.1f, 16.7121f, 5.7121f, 18.1f, 4.0f, 18.1f)
                curveTo(2.2879f, 18.1f, 0.9f, 16.7121f, 0.9f, 15.0f)
                curveTo(0.9f, 13.2879f, 2.2879f, 11.9f, 4.0f, 11.9f)
                curveTo(5.7121f, 11.9f, 7.1f, 13.2879f, 7.1f, 15.0f)
                close()
            }
        }
            .build()
        return _personalSignature!!
    }

private var _personalSignature: ImageVector? = null
