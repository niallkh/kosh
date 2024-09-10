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
public fun NetworksEmpty(
    upperClothes: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    downClothes: Color = MaterialTheme.colorScheme.secondary,
    details: Color = MaterialTheme.colorScheme.tertiary,
    secondaryDetails: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    tertiaryDetails: Color = MaterialTheme.colorScheme.background,
): ImageVector = remember(upperClothes, downClothes, details, secondaryDetails, tertiaryDetails) {
    Builder(
        name = "NetworksEmpty", defaultWidth = 88.0.dp, defaultHeight =
        83.0.dp, viewportWidth = 88.0f, viewportHeight = 83.0f
    ).apply {
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(77.23f, 24.724f)
            reflectiveCurveToRelative(-0.644f, 3.947f, -0.455f, 7.178f)
            curveToRelative(0.032f, 0.633f, 0.102f, 1.24f, 0.216f, 1.784f)
            horizontalLineToRelative(-7.426f)
            reflectiveCurveToRelative(-0.443f, -0.753f, -0.974f, -1.784f)
            arcToRelative(24.264f, 24.264f, 0.0f, false, true, -1.696f, -4.313f)
            horizontalLineToRelative(-5.692f)
            reflectiveCurveToRelative(0.57f, -1.72f, 1.493f, -3.656f)
            curveToRelative(1.03f, -2.151f, 3.074f, -3.644f, 5.433f, -4.023f)
            lineToRelative(5.358f, -0.866f)
            arcToRelative(7.551f, 7.551f, 0.0f, false, true, 5.958f, 1.594f)
            curveToRelative(1.246f, 1.012f, 2.593f, 2.385f, 3.479f, 4.086f)
            horizontalLineToRelative(-5.693f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFCC80)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(76.314f, 9.312f)
            curveToRelative(-0.6f, 0.0f, -1.1f, 0.414f, -1.24f, 0.972f)
            horizontalLineToRelative(-0.769f)
            arcToRelative(2.594f, 2.594f, 0.0f, false, false, -2.825f, -4.22f)
            arcToRelative(4.56f, 4.56f, 0.0f, false, false, -8.741f, 1.825f)
            arcTo(4.562f, 4.562f, 0.0f, false, false, 67.3f, 12.45f)
            curveToRelative(0.387f, 0.0f, 0.401f, 0.037f, 0.76f, -0.054f)
            curveToRelative(-0.025f, 0.16f, 0.309f, 0.23f, 0.309f, 0.396f)
            arcToRelative(3.25f, 3.25f, 0.0f, false, false, 6.5f, 0.0f)
            curveToRelative(0.0f, -0.707f, -0.232f, -1.357f, -0.615f, -1.89f)
            horizontalLineToRelative(0.82f)
            arcToRelative(1.28f, 1.28f, 0.0f, true, false, 1.24f, -1.59f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(70.874f, 22.586f)
            curveToRelative(-3.093f, -0.354f, -2.202f, -3.068f, -2.202f, -3.068f)
            reflectiveCurveToRelative(0.178f, -0.734f, -0.056f, -2.038f)
            arcToRelative(9.653f, 9.653f, 0.0f, false, false, -0.405f, -1.555f)
            curveToRelative(-3.593f, 0.664f, -3.98f, -3.435f, -3.638f, -5.914f)
            curveToRelative(0.728f, 0.222f, 1.86f, -0.386f, 2.182f, -1.214f)
            curveToRelative(0.039f, 0.588f, 0.645f, 1.271f, 1.234f, 1.252f)
            curveToRelative(-0.341f, 1.271f, 0.443f, 2.486f, 0.443f, 2.486f)
            curveToRelative(0.728f, -2.404f, 3.492f, -1.094f, 1.733f, 0.949f)
            curveToRelative(1.436f, 1.733f, 3.416f, 5.527f, 3.416f, 5.527f)
            curveToRelative(0.614f, 0.76f, 0.158f, 3.903f, -2.707f, 3.575f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(69.17f, 12.585f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.07f, -0.115f)
            curveToRelative(0.172f, -0.344f, 0.597f, -0.617f, 0.99f, -0.634f)
            curveToRelative(0.045f, 0.004f, 0.08f, 0.032f, 0.082f, 0.075f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.075f, 0.083f)
            curveToRelative(-0.335f, 0.015f, -0.71f, 0.255f, -0.857f, 0.547f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.07f, 0.044f)
            close()
            moveTo(65.384f, 11.404f)
            arcToRelative(0.07f, 0.07f, 0.0f, false, true, -0.04f, -0.011f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.027f, -0.11f)
            curveToRelative(0.299f, -0.496f, 1.183f, -0.72f, 1.792f, -0.612f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.064f, 0.092f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.093f, 0.064f)
            curveToRelative(-0.556f, -0.1f, -1.371f, 0.109f, -1.629f, 0.539f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.067f, 0.038f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(66.503f, 14.267f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.042f, -0.146f)
            curveToRelative(0.239f, -0.155f, 0.385f, -0.358f, 0.373f, -0.518f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.072f, -0.085f)
            curveToRelative(0.042f, -0.006f, 0.082f, 0.03f, 0.085f, 0.073f)
            curveToRelative(0.017f, 0.22f, -0.153f, 0.474f, -0.445f, 0.663f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.043f, 0.013f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(64.945f, 12.068f)
            arcToRelative(6.723f, 6.723f, 0.0f, false, false, -0.939f, 1.32f)
            arcToRelative(0.209f, 0.209f, 0.0f, false, false, 0.156f, 0.304f)
            curveToRelative(0.381f, 0.053f, 0.843f, 0.072f, 0.843f, 0.072f)
            lineToRelative(-0.06f, -1.696f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(71.72f, 13.55f)
            arcToRelative(0.485f, 0.485f, 0.0f, true, true, -0.97f, 0.0f)
            arcToRelative(0.485f, 0.485f, 0.0f, false, true, 0.97f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(67.401f, 11.972f)
            arcToRelative(1.205f, 1.205f, 0.0f, false, true, -1.998f, 0.594f)
            curveToRelative(0.102f, -0.437f, 0.437f, -0.771f, 0.873f, -0.879f)
            arcToRelative(0.943f, 0.943f, 0.0f, false, true, 0.303f, -0.044f)
            curveToRelative(0.316f, 0.0f, 0.6f, 0.127f, 0.822f, 0.329f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(66.535f, 12.459f)
            curveToRelative(0.0f, 0.146f, -0.026f, 0.284f, -0.07f, 0.41f)
            arcToRelative(1.198f, 1.198f, 0.0f, false, true, -1.062f, -0.304f)
            curveToRelative(0.102f, -0.436f, 0.437f, -0.77f, 0.873f, -0.878f)
            curveToRelative(0.163f, 0.215f, 0.259f, 0.48f, 0.259f, 0.772f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(74.393f, 14.1f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.045f, -0.014f)
            arcToRelative(3.744f, 3.744f, 0.0f, false, true, -1.636f, -3.25f)
            arcToRelative(3.746f, 3.746f, 0.0f, false, true, 1.915f, -3.092f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.107f, 0.03f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.03f, 0.108f)
            arcToRelative(3.586f, 3.586f, 0.0f, false, false, -1.835f, 2.962f)
            arcToRelative(3.587f, 3.587f, 0.0f, false, false, 1.567f, 3.111f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.043f, 0.144f)
            close()
            moveTo(68.23f, 11.449f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.076f, -0.105f)
            arcToRelative(7.24f, 7.24f, 0.0f, false, true, 5.62f, -4.868f)
            curveToRelative(0.04f, -0.01f, 0.083f, 0.022f, 0.09f, 0.064f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.064f, 0.093f)
            arcToRelative(7.08f, 7.08f, 0.0f, false, false, -5.495f, 4.761f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.075f, 0.055f)
            close()
            moveTo(65.197f, 7.58f)
            arcToRelative(5.742f, 5.742f, 0.0f, false, true, -2.16f, -0.42f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.043f, -0.102f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.103f, -0.044f)
            curveToRelative(2.587f, 1.048f, 5.597f, 0.021f, 7.003f, -2.389f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.108f, -0.028f)
            curveToRelative(0.038f, 0.022f, 0.05f, 0.07f, 0.028f, 0.108f)
            curveToRelative(-1.063f, 1.822f, -3.02f, 2.876f, -5.039f, 2.876f)
            close()
            moveTo(65.728f, 5.014f)
            curveToRelative(-0.477f, 0.0f, -0.953f, -0.054f, -1.422f, -0.167f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.058f, -0.096f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.096f, -0.057f)
            arcToRelative(5.956f, 5.956f, 0.0f, false, false, 4.69f, -0.837f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.11f, 0.022f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.021f, 0.11f)
            arcToRelative(6.13f, 6.13f, 0.0f, false, true, -3.395f, 1.025f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(62.386f, 25.288f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.026f, -0.154f)
            arcToRelative(9.767f, 9.767f, 0.0f, false, false, 5.648f, -4.986f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.105f, -0.037f)
            curveToRelative(0.04f, 0.019f, 0.056f, 0.067f, 0.037f, 0.106f)
            arcToRelative(9.923f, 9.923f, 0.0f, false, true, -5.738f, 5.067f)
            arcToRelative(0.111f, 0.111f, 0.0f, false, true, -0.026f, 0.004f)
            close()
            moveTo(76.83f, 25.62f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.055f, -0.021f)
            arcToRelative(6.33f, 6.33f, 0.0f, false, true, -1.778f, -6.348f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.152f, 0.043f)
            arcToRelative(6.172f, 6.172f, 0.0f, false, false, 1.734f, 6.189f)
            curveToRelative(0.032f, 0.03f, 0.034f, 0.08f, 0.004f, 0.112f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.058f, 0.026f)
            close()
            moveTo(76.708f, 27.406f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.069f, -0.038f)
            arcToRelative(7.165f, 7.165f, 0.0f, false, false, -6.077f, -3.464f)
            horizontalLineToRelative(-0.034f)
            arcToRelative(7.167f, 7.167f, 0.0f, false, false, -6.077f, 3.403f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, -0.135f, -0.083f)
            arcToRelative(7.323f, 7.323f, 0.0f, false, true, 6.212f, -3.479f)
            horizontalLineToRelative(0.035f)
            arcToRelative(7.323f, 7.323f, 0.0f, false, true, 6.212f, 3.541f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.027f, 0.109f)
            arcToRelative(0.073f, 0.073f, 0.0f, false, true, -0.04f, 0.011f)
            close()
            moveTo(72.802f, 33.465f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, -0.08f)
            arcToRelative(4.05f, 4.05f, 0.0f, false, true, 3.767f, -4.075f)
            curveToRelative(0.045f, -0.008f, 0.081f, 0.03f, 0.085f, 0.073f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.074f, 0.084f)
            arcToRelative(3.893f, 3.893f, 0.0f, false, false, -3.62f, 3.918f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.08f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(76.99f, 33.687f)
            horizontalLineToRelative(-7.426f)
            reflectiveCurveToRelative(-0.443f, -0.753f, -0.974f, -1.784f)
            horizontalLineToRelative(8.185f)
            curveToRelative(0.031f, 0.632f, 0.101f, 1.24f, 0.215f, 1.784f)
            close()
            moveTo(77.102f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(2.398f, -2.542f, 7.116f, -4.3f)
            curveToRelative(1.17f, -0.438f, 1.955f, -0.981f, 2.46f, -1.544f)
            curveToRelative(0.633f, -0.702f, 0.848f, -1.436f, 0.88f, -2.068f)
            arcToRelative(3.561f, 3.561f, 0.0f, false, false, -0.279f, -1.594f)
            lineToRelative(4.333f, 0.36f)
            lineToRelative(0.423f, 1.638f)
            lineToRelative(0.69f, 2.67f)
            lineToRelative(1.247f, 4.838f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(77.102f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(0.729f, -0.771f, 2.17f, -1.759f)
            arcToRelative(23.088f, 23.088f, 0.0f, false, true, 4.947f, -2.542f)
            curveToRelative(1.17f, -0.437f, 1.955f, -0.98f, 2.46f, -1.543f)
            curveToRelative(1.221f, 1.201f, 4.1f, 1.783f, 6.048f, 1.006f)
            lineToRelative(0.79f, 3.08f)
            lineToRelative(0.455f, 1.758f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(77.102f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(0.729f, -0.771f, 2.17f, -1.759f)
            horizontalLineToRelative(14.245f)
            lineToRelative(0.455f, 1.76f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(65.572f, 80.584f)
            lineToRelative(-0.032f, -0.114f)
            curveToRelative(-0.002f, -0.005f, -0.151f, -0.52f, -0.63f, -0.796f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.03f, -0.108f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.108f, -0.029f)
            curveToRelative(0.4f, 0.232f, 0.592f, 0.602f, 0.666f, 0.784f)
            curveToRelative(0.633f, -0.468f, 1.499f, -0.795f, 2.336f, -1.114f)
            curveToRelative(1.1f, -0.417f, 2.138f, -0.81f, 2.649f, -1.48f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.125f, 0.096f)
            curveToRelative(-0.538f, 0.706f, -1.597f, 1.107f, -2.718f, 1.532f)
            curveToRelative(-0.867f, 0.328f, -1.76f, 0.668f, -2.383f, 1.156f)
            lineToRelative(-0.091f, 0.073f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(75.166f, 75.02f)
            curveToRelative(-1.232f, 0.26f, -3.333f, 0.039f, -4.477f, -0.404f)
            arcToRelative(3.562f, 3.562f, 0.0f, false, false, -0.279f, -1.594f)
            lineToRelative(4.333f, 0.36f)
            lineToRelative(0.424f, 1.638f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(85.188f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(2.398f, -2.542f, 7.116f, -4.3f)
            curveToRelative(1.17f, -0.438f, 1.954f, -0.981f, 2.46f, -1.544f)
            curveToRelative(0.633f, -0.702f, 0.848f, -1.436f, 0.879f, -2.068f)
            arcToRelative(3.56f, 3.56f, 0.0f, false, false, -0.278f, -1.594f)
            lineToRelative(4.333f, 0.36f)
            lineToRelative(0.423f, 1.638f)
            lineToRelative(0.69f, 2.67f)
            lineToRelative(1.247f, 4.838f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(85.188f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(0.728f, -0.771f, 2.17f, -1.759f)
            arcToRelative(23.075f, 23.075f, 0.0f, false, true, 4.946f, -2.542f)
            curveToRelative(1.17f, -0.437f, 1.954f, -0.98f, 2.46f, -1.543f)
            curveToRelative(1.22f, 1.201f, 4.1f, 1.783f, 6.047f, 1.006f)
            lineToRelative(0.791f, 3.08f)
            lineToRelative(0.456f, 1.758f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(85.188f, 82.528f)
            horizontalLineToRelative(-16.87f)
            reflectiveCurveToRelative(0.728f, -0.771f, 2.17f, -1.759f)
            horizontalLineToRelative(14.244f)
            lineToRelative(0.456f, 1.76f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(73.657f, 80.584f)
            lineToRelative(-0.032f, -0.114f)
            curveToRelative(-0.002f, -0.005f, -0.15f, -0.519f, -0.63f, -0.796f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.028f, -0.108f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.109f, -0.029f)
            curveToRelative(0.4f, 0.232f, 0.59f, 0.602f, 0.664f, 0.784f)
            curveToRelative(0.635f, -0.468f, 1.499f, -0.795f, 2.338f, -1.114f)
            curveToRelative(1.098f, -0.416f, 2.137f, -0.81f, 2.648f, -1.48f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.126f, 0.096f)
            curveToRelative(-0.539f, 0.706f, -1.599f, 1.107f, -2.72f, 1.532f)
            curveToRelative(-0.865f, 0.328f, -1.76f, 0.668f, -2.383f, 1.156f)
            lineToRelative(-0.092f, 0.073f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(83.252f, 75.02f)
            curveToRelative(-1.234f, 0.26f, -3.333f, 0.039f, -4.478f, -0.404f)
            arcToRelative(3.561f, 3.561f, 0.0f, false, false, -0.278f, -1.594f)
            lineToRelative(4.333f, 0.36f)
            lineToRelative(0.423f, 1.638f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(68.667f, 82.272f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.054f, -0.138f)
            curveToRelative(0.36f, -0.327f, 0.97f, -0.842f, 1.829f, -1.43f)
            arcToRelative(23.192f, 23.192f, 0.0f, false, true, 4.963f, -2.551f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.102f, 0.046f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.047f, 0.102f)
            arcToRelative(22.937f, 22.937f, 0.0f, false, false, -4.929f, 2.534f)
            arcToRelative(16.93f, 16.93f, 0.0f, false, false, -1.811f, 1.416f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.053f, 0.02f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(69.565f, 33.687f)
            curveToRelative(-1.558f, 1.43f, -6.162f, 13.152f, -8.057f, 18.09f)
            arcToRelative(7.117f, 7.117f, 0.0f, false, false, -0.23f, 4.398f)
            lineToRelative(4.974f, 18.52f)
            lineToRelative(9.223f, -0.958f)
            reflectiveCurveToRelative(-3.53f, -14.534f, -4.539f, -17.4f)
            verticalLineToRelative(-1.776f)
            horizontalLineToRelative(-0.767f)
            reflectiveCurveToRelative(7.55f, -14.496f, 3.23f, -20.874f)
            horizontalLineToRelative(-3.834f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(74.22f, 73.872f)
            lineToRelative(-5.96f, -19.616f)
            lineToRelative(2.498f, -8.018f)
            lineToRelative(4.717f, 27.5f)
            lineToRelative(-1.256f, 0.134f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(71.41f, 33.687f)
            curveToRelative(-0.81f, 0.742f, -2.655f, 4.159f, 0.252f, 8.399f)
            curveToRelative(-0.748f, 3.19f, -1.566f, 7.43f, -2.026f, 9.884f)
            arcToRelative(8.261f, 8.261f, 0.0f, false, false, 0.211f, 3.912f)
            lineToRelative(5.69f, 18.734f)
            lineToRelative(8.862f, -0.96f)
            lineToRelative(-6.157f, -21.326f)
            curveToRelative(1.09f, -5.127f, 4.763f, -16.068f, -1.253f, -18.643f)
            horizontalLineToRelative(-5.58f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(70.615f, 73.743f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.051f, -0.02f)
            curveToRelative(-2.203f, -1.902f, -5.023f, -6.341f, -6.476f, -9.535f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.04f, -0.104f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.104f, 0.039f)
            curveToRelative(1.447f, 3.178f, 4.249f, 7.593f, 6.436f, 9.48f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.053f, 0.14f)
            close()
            moveTo(79.166f, 73.743f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.052f, -0.02f)
            curveToRelative(-2.202f, -1.903f, -5.021f, -6.341f, -6.475f, -9.535f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.04f, -0.104f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.104f, 0.039f)
            curveToRelative(1.447f, 3.178f, 4.25f, 7.593f, 6.436f, 9.48f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.008f, 0.112f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.061f, 0.028f)
            close()
            moveTo(64.755f, 49.659f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.076f, -0.1f)
            curveToRelative(0.809f, -2.937f, 3.187f, -9.39f, 3.211f, -9.454f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.102f, -0.047f)
            curveToRelative(0.04f, 0.015f, 0.062f, 0.06f, 0.046f, 0.101f)
            curveToRelative(-0.024f, 0.065f, -2.4f, 6.51f, -3.208f, 9.442f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.075f, 0.058f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(81.979f, 43.648f)
            curveToRelative(0.0f, 0.57f, -0.547f, 1.096f, -1.056f, 1.096f)
            curveToRelative(-0.51f, 0.0f, -0.787f, -0.526f, -0.787f, -1.096f)
            curveToRelative(0.0f, -0.57f, 0.432f, -1.093f, 0.941f, -1.093f)
            curveToRelative(0.51f, 0.0f, 0.902f, 0.523f, 0.902f, 1.093f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(72.259f, 49.861f)
            lineToRelative(-0.009f, -0.001f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.07f, -0.087f)
            curveToRelative(0.302f, -3.03f, 1.558f, -9.792f, 1.57f, -9.86f)
            curveToRelative(0.009f, -0.044f, 0.055f, -0.073f, 0.092f, -0.064f)
            curveToRelative(0.044f, 0.009f, 0.072f, 0.05f, 0.064f, 0.093f)
            curveToRelative(-0.014f, 0.068f, -1.268f, 6.822f, -1.57f, 9.847f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.077f, 0.072f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(82.923f, 24.723f)
            lineToRelative(4.805f, 9.207f)
            arcToRelative(2.375f, 2.375f, 0.0f, false, true, -0.201f, 2.526f)
            curveToRelative(-2.612f, 3.474f, -6.357f, 4.932f, -5.902f, 7.962f)
            curveToRelative(0.242f, -0.255f, 1.19f, -0.345f, 1.382f, 0.987f)
            curveToRelative(0.122f, 0.848f, -0.335f, 1.338f, -0.928f, 1.489f)
            curveToRelative(-1.074f, 0.275f, -2.21f, -0.192f, -2.853f, -1.095f)
            curveToRelative(-0.425f, -0.6f, -0.793f, -1.474f, -0.793f, -2.705f)
            curveToRelative(0.0f, -2.938f, 1.424f, -2.685f, 4.489f, -7.924f)
            lineTo(77.23f, 24.723f)
            horizontalLineToRelative(5.693f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(81.78f, 45.505f)
            arcToRelative(0.567f, 0.567f, 0.0f, false, true, -0.409f, -0.208f)
            arcToRelative(0.837f, 0.837f, 0.0f, false, true, -0.198f, -0.648f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.06f, -0.068f)
            curveToRelative(0.176f, -0.04f, 0.498f, -0.324f, 0.62f, -0.73f)
            arcToRelative(0.945f, 0.945f, 0.0f, false, false, -0.235f, -0.965f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.003f, -0.112f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.112f, 0.003f)
            curveToRelative(0.382f, 0.406f, 0.359f, 0.834f, 0.272f, 1.12f)
            curveToRelative(-0.121f, 0.398f, -0.435f, 0.728f, -0.68f, 0.821f)
            arcToRelative(0.674f, 0.674f, 0.0f, false, false, 0.165f, 0.473f)
            curveToRelative(0.087f, 0.098f, 0.193f, 0.154f, 0.29f, 0.154f)
            curveToRelative(0.133f, 0.0f, 0.233f, -0.056f, 0.283f, -0.158f)
            curveToRelative(0.072f, -0.15f, 0.027f, -0.363f, -0.117f, -0.547f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.013f, -0.11f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.111f, 0.013f)
            curveToRelative(0.183f, 0.234f, 0.235f, 0.508f, 0.136f, 0.713f)
            curveToRelative(-0.077f, 0.159f, -0.232f, 0.249f, -0.426f, 0.249f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(61.69f, 27.589f)
            lineToRelative(-2.15f, 7.189f)
            reflectiveCurveToRelative(-6.566f, 4.196f, -10.018f, 7.496f)
            curveToRelative(-1.06f, 0.969f, -0.106f, 1.406f, 0.257f, 1.083f)
            curveToRelative(-0.465f, 0.545f, -0.121f, 0.868f, 0.545f, 0.525f)
            curveToRelative(-0.323f, 0.666f, 0.02f, 0.707f, 0.464f, 0.505f)
            curveToRelative(-0.323f, 0.686f, 0.283f, 0.758f, 1.01f, 0.106f)
            curveToRelative(0.726f, -0.652f, 1.03f, -1.056f, 3.048f, -2.226f)
            curveToRelative(1.434f, -0.831f, 5.117f, -2.823f, 7.131f, -3.909f)
            arcToRelative(4.479f, 4.479f, 0.0f, false, false, 2.004f, -2.198f)
            curveToRelative(1.132f, -2.675f, 2.694f, -6.71f, 2.914f, -8.571f)
            horizontalLineToRelative(-5.206f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.994f, 49.423f)
            curveToRelative(0.2f, 0.258f, -1.01f, 1.53f, -2.704f, 2.841f)
            curveToRelative(-1.694f, 1.312f, -3.228f, 2.166f, -3.428f, 1.908f)
            curveToRelative(-0.2f, -0.258f, 1.012f, -1.53f, 2.705f, -2.841f)
            curveToRelative(1.693f, -1.312f, 3.228f, -2.166f, 3.427f, -1.908f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(46.73f, 42.637f)
            lineToRelative(-6.13f, 4.749f)
            lineToRelative(5.25f, 6.78f)
            lineToRelative(6.132f, -4.748f)
            lineToRelative(-5.252f, -6.78f)
            close()
            moveTo(37.67f, 51.44f)
            curveToRelative(0.048f, -0.208f, 0.054f, -0.385f, 0.022f, -0.451f)
            lineToRelative(0.002f, -0.007f)
            lineToRelative(-1.162f, -2.368f)
            lineToRelative(-1.138f, 4.854f)
            lineToRelative(2.093f, -1.605f)
            lineToRelative(0.002f, -0.008f)
            curveToRelative(0.058f, -0.045f, 0.132f, -0.207f, 0.18f, -0.415f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.293f, 51.118f)
            curveToRelative(-0.32f, 1.368f, -0.764f, 2.434f, -0.992f, 2.38f)
            curveToRelative(-0.228f, -0.054f, -0.153f, -1.206f, 0.167f, -2.573f)
            curveToRelative(0.321f, -1.368f, 0.765f, -2.434f, 0.993f, -2.38f)
            curveToRelative(0.228f, 0.054f, 0.152f, 1.206f, -0.168f, 2.573f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(45.776f, 52.9f)
            lineToRelative(-8.255f, -1.933f)
            lineToRelative(-0.187f, 0.8f)
            lineToRelative(8.255f, 1.934f)
            lineToRelative(0.187f, -0.8f)
            close()
            moveTo(51.117f, 48.29f)
            curveToRelative(0.666f, -1.224f, 1.589f, -3.078f, 1.089f, -4.772f)
            curveToRelative(-0.575f, -1.942f, -3.996f, -2.856f, -5.016f, -0.298f)
            lineToRelative(0.607f, 0.806f)
            curveToRelative(0.028f, -0.17f, 0.074f, -0.342f, 0.14f, -0.508f)
            curveToRelative(0.164f, -0.41f, 0.394f, -0.664f, 0.635f, -0.82f)
            curveToRelative(0.272f, -0.176f, 0.607f, -0.257f, 0.972f, -0.24f)
            curveToRelative(0.466f, 0.022f, 0.944f, 0.202f, 1.312f, 0.49f)
            curveToRelative(0.297f, 0.232f, 0.493f, 0.512f, 0.578f, 0.797f)
            curveToRelative(0.11f, 0.371f, 0.134f, 0.766f, 0.09f, 1.181f)
            curveToRelative(-0.111f, 1.047f, -0.638f, 2.107f, -1.113f, 2.978f)
            lineToRelative(0.706f, 0.386f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(46.742f, 42.643f)
            curveToRelative(0.2f, 0.257f, -1.011f, 1.53f, -2.705f, 2.841f)
            curveToRelative(-1.693f, 1.311f, -3.227f, 2.165f, -3.427f, 1.908f)
            curveToRelative(-0.2f, -0.258f, 1.011f, -1.53f, 2.705f, -2.842f)
            curveToRelative(1.693f, -1.311f, 3.227f, -2.165f, 3.427f, -1.907f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(65.006f, 13.842f)
            horizontalLineToRelative(-0.003f)
            curveToRelative(-0.005f, 0.0f, -0.471f, -0.02f, -0.851f, -0.072f)
            arcToRelative(0.287f, 0.287f, 0.0f, false, true, -0.215f, -0.42f)
            curveToRelative(0.234f, -0.447f, 0.59f, -0.945f, 0.95f, -1.337f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.112f, -0.004f)
            curveToRelative(0.032f, 0.03f, 0.034f, 0.08f, 0.004f, 0.112f)
            curveToRelative(-0.351f, 0.382f, -0.698f, 0.868f, -0.928f, 1.303f)
            arcToRelative(0.132f, 0.132f, 0.0f, false, false, 0.0f, 0.12f)
            curveToRelative(0.019f, 0.037f, 0.055f, 0.063f, 0.096f, 0.07f)
            curveToRelative(0.373f, 0.051f, 0.832f, 0.071f, 0.836f, 0.071f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.075f, 0.083f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.076f, 0.074f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(66.895f, 27.667f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.077f, -0.058f)
            lineToRelative(-1.067f, -3.965f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.056f, -0.097f)
            curveToRelative(0.04f, -0.012f, 0.085f, 0.014f, 0.097f, 0.056f)
            lineToRelative(1.067f, 3.964f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.056f, 0.098f)
            curveToRelative(-0.007f, 0.002f, -0.013f, 0.002f, -0.02f, 0.002f)
            close()
            moveTo(62.754f, 27.667f)
            horizontalLineToRelative(-1.55f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.076f, -0.106f)
            curveToRelative(0.293f, -0.814f, 0.952f, -2.567f, 1.36f, -3.377f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.107f, -0.035f)
            curveToRelative(0.04f, 0.02f, 0.055f, 0.067f, 0.034f, 0.106f)
            curveToRelative(-0.388f, 0.77f, -1.007f, 2.406f, -1.312f, 3.255f)
            horizontalLineToRelative(1.436f)
            curveToRelative(0.044f, 0.0f, 0.079f, 0.035f, 0.079f, 0.078f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(79.494f, 24.802f)
            horizontalLineToRelative(-3.42f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.074f, -0.05f)
            curveToRelative(-0.965f, -2.372f, -0.673f, -3.997f, 0.842f, -4.697f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.067f, 0.143f)
            curveToRelative(-1.783f, 0.824f, -1.416f, 2.862f, -0.782f, 4.446f)
            horizontalLineToRelative(3.368f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.079f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.08f, 0.08f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(69.078f, 54.044f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, -0.096f)
            lineToRelative(3.385f, -15.56f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.094f, -0.062f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.06f, 0.094f)
            lineToRelative(-3.385f, 15.561f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.076f, 0.063f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.213f, 40.884f)
            curveToRelative(-0.863f, 0.227f, -1.604f, 0.15f, -1.604f, 0.15f)
            reflectiveCurveToRelative(-0.94f, 0.289f, -1.65f, 0.273f)
            curveToRelative(0.0f, 0.0f, -0.137f, 0.832f, 1.59f, 0.576f)
            curveToRelative(1.725f, -0.258f, 1.998f, -0.167f, 1.998f, -0.167f)
            lineToRelative(-0.334f, -0.832f)
            close()
            moveTo(50.492f, 42.14f)
            curveToRelative(0.126f, 0.146f, 0.016f, 0.449f, -0.247f, 0.677f)
            curveToRelative(-0.263f, 0.227f, -0.578f, 0.294f, -0.706f, 0.148f)
            curveToRelative(-0.126f, -0.146f, -0.016f, -0.45f, 0.247f, -0.677f)
            curveToRelative(0.264f, -0.229f, 0.58f, -0.295f, 0.706f, -0.148f)
            close()
            moveTo(51.071f, 42.586f)
            curveToRelative(0.127f, 0.146f, 0.017f, 0.45f, -0.246f, 0.677f)
            curveToRelative(-0.263f, 0.228f, -0.579f, 0.294f, -0.706f, 0.148f)
            curveToRelative(-0.128f, -0.146f, -0.017f, -0.449f, 0.246f, -0.677f)
            curveToRelative(0.263f, -0.227f, 0.579f, -0.294f, 0.706f, -0.148f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.56f, 43.03f)
            curveToRelative(0.137f, 0.137f, 0.05f, 0.447f, -0.197f, 0.694f)
            curveToRelative(-0.246f, 0.246f, -0.557f, 0.334f, -0.694f, 0.197f)
            curveToRelative(-0.136f, -0.136f, -0.048f, -0.447f, 0.198f, -0.693f)
            curveToRelative(0.246f, -0.247f, 0.557f, -0.334f, 0.694f, -0.198f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.943f, 43.676f)
            curveToRelative(0.127f, 0.146f, 0.016f, 0.45f, -0.247f, 0.677f)
            curveToRelative(-0.263f, 0.228f, -0.578f, 0.294f, -0.706f, 0.148f)
            curveToRelative(-0.126f, -0.146f, -0.016f, -0.449f, 0.247f, -0.677f)
            curveToRelative(0.263f, -0.228f, 0.578f, -0.294f, 0.706f, -0.148f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.53f, 44.556f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.063f, -0.033f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.017f, -0.11f)
            curveToRelative(0.14f, -0.102f, 0.49f, -0.415f, 0.424f, -0.631f)
            curveToRelative(-0.014f, -0.044f, -0.038f, -0.071f, -0.073f, -0.086f)
            curveToRelative(-0.117f, -0.048f, -0.338f, 0.049f, -0.407f, 0.086f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.103f, -0.024f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.013f, -0.104f)
            curveToRelative(0.003f, -0.002f, 0.282f, -0.248f, 0.197f, -0.509f)
            curveToRelative(-0.013f, -0.04f, -0.035f, -0.065f, -0.07f, -0.078f)
            curveToRelative(-0.123f, -0.048f, -0.365f, 0.062f, -0.44f, 0.104f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.104f, -0.024f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.016f, -0.106f)
            curveToRelative(0.043f, -0.035f, 0.155f, -0.15f, 0.156f, -0.256f)
            curveToRelative(0.001f, -0.05f, -0.024f, -0.095f, -0.074f, -0.137f)
            curveToRelative(-0.217f, -0.186f, -0.582f, 0.11f, -0.586f, 0.112f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.106f, -0.004f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.007f, -0.105f)
            curveToRelative(0.132f, -0.168f, 0.154f, -0.351f, 0.128f, -0.428f)
            curveToRelative(-0.011f, -0.033f, -0.041f, -0.058f, -0.087f, -0.07f)
            curveToRelative(-0.048f, -0.014f, -0.234f, -0.042f, -0.526f, 0.195f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.11f, -0.011f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.01f, -0.11f)
            curveToRelative(0.242f, -0.196f, 0.485f, -0.277f, 0.67f, -0.225f)
            curveToRelative(0.095f, 0.028f, 0.165f, 0.09f, 0.193f, 0.171f)
            curveToRelative(0.03f, 0.087f, 0.023f, 0.201f, -0.015f, 0.317f)
            curveToRelative(0.161f, -0.068f, 0.37f, -0.105f, 0.539f, 0.038f)
            curveToRelative(0.108f, 0.094f, 0.13f, 0.193f, 0.13f, 0.261f)
            arcToRelative(0.36f, 0.36f, 0.0f, false, true, -0.03f, 0.134f)
            curveToRelative(0.1f, -0.026f, 0.21f, -0.038f, 0.3f, -0.002f)
            curveToRelative(0.08f, 0.03f, 0.136f, 0.092f, 0.164f, 0.177f)
            arcToRelative(0.555f, 0.555f, 0.0f, false, true, -0.058f, 0.444f)
            arcToRelative(0.473f, 0.473f, 0.0f, false, true, 0.266f, 0.01f)
            curveToRelative(0.08f, 0.031f, 0.138f, 0.096f, 0.165f, 0.185f)
            curveToRelative(0.113f, 0.364f, -0.42f, 0.762f, -0.482f, 0.806f)
            arcToRelative(0.096f, 0.096f, 0.0f, false, true, -0.046f, 0.013f)
            close()
            moveTo(79.31f, 45.986f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.062f, -0.028f)
            curveToRelative(-0.034f, -0.04f, -0.823f, -1.009f, -0.893f, -2.862f)
            curveToRelative(-0.065f, -1.684f, 0.713f, -2.656f, 1.4f, -3.515f)
            lineToRelative(0.24f, -0.302f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.124f, 0.097f)
            lineToRelative(-0.241f, 0.304f)
            curveToRelative(-0.702f, 0.878f, -1.428f, 1.786f, -1.366f, 3.41f)
            curveToRelative(0.069f, 1.796f, 0.849f, 2.758f, 0.856f, 2.768f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.01f, 0.11f)
            arcToRelative(0.067f, 0.067f, 0.0f, false, true, -0.049f, 0.018f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(35.442f, 44.806f)
            lineToRelative(-1.24f, -0.7f)
            lineToRelative(-12.993f, 23.009f)
            lineToRelative(1.24f, 0.7f)
            lineToRelative(12.993f, -23.01f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(22.287f, 66.632f)
            arcToRelative(37.989f, 37.989f, 0.0f, false, true, 2.353f, -21.364f)
            arcToRelative(30.839f, 30.839f, 0.0f, false, true, -1.474f, 21.364f)
            horizontalLineToRelative(-0.88f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(11.126f, 39.782f)
            lineToRelative(-1.322f, 0.53f)
            lineToRelative(11.101f, 27.685f)
            lineToRelative(1.322f, -0.53f)
            lineToRelative(-11.1f, -27.685f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(22.67f, 24.604f)
            horizontalLineToRelative(-1.423f)
            verticalLineTo(66.63f)
            horizontalLineToRelative(1.424f)
            verticalLineTo(24.603f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(34.521f, 28.395f)
            lineToRelative(-0.362f, -0.76f)
            lineToRelative(-12.243f, 5.834f)
            lineToRelative(0.363f, 0.76f)
            lineToRelative(12.242f, -5.834f)
            close()
            moveTo(21.832f, 30.77f)
            lineToRelative(0.247f, -0.806f)
            lineToRelative(-15.867f, -4.871f)
            lineToRelative(-0.247f, 0.805f)
            lineToRelative(15.867f, 4.871f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.526f, 82.53f)
            arcToRelative(30.723f, 30.723f, 0.0f, false, false, 4.32f, -15.898f)
            horizontalLineTo(13.163f)
            arcToRelative(30.734f, 30.734f, 0.0f, false, false, 4.32f, 15.897f)
            horizontalLineToRelative(9.043f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.968f, 12.516f)
            curveToRelative(0.0f, 6.912f, -5.603f, 12.515f, -12.515f, 12.515f)
            curveToRelative(-6.913f, 0.0f, -12.516f, -5.603f, -12.516f, -12.515f)
            curveTo(8.937f, 5.603f, 14.54f, 0.0f, 21.453f, 0.0f)
            curveToRelative(6.912f, 0.0f, 12.515f, 5.603f, 12.515f, 12.516f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.981f, 12.516f)
            curveToRelative(0.0f, 6.912f, -5.603f, 12.515f, -12.515f, 12.515f)
            curveToRelative(-6.913f, 0.0f, -12.516f, -5.603f, -12.516f, -12.515f)
            curveTo(9.95f, 5.603f, 15.553f, 0.0f, 22.466f, 0.0f)
            curveTo(29.377f, 0.0f, 34.98f, 5.603f, 34.98f, 12.516f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.6f, strokeAlpha
            = 0.6f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(22.466f, 23.603f)
            curveToRelative(-6.113f, 0.0f, -11.087f, -4.974f, -11.087f, -11.087f)
            curveToRelative(0.0f, -6.114f, 4.974f, -11.087f, 11.087f, -11.087f)
            curveToRelative(6.113f, 0.0f, 11.087f, 4.973f, 11.087f, 11.087f)
            curveToRelative(0.0f, 6.113f, -4.974f, 11.087f, -11.087f, 11.087f)
            close()
            moveTo(22.466f, 1.587f)
            curveToRelative(-6.026f, 0.0f, -10.929f, 4.902f, -10.929f, 10.93f)
            curveToRelative(0.0f, 6.026f, 4.903f, 10.929f, 10.929f, 10.929f)
            curveToRelative(6.026f, 0.0f, 10.93f, -4.904f, 10.93f, -10.93f)
            curveToRelative(-0.001f, -6.027f, -4.904f, -10.929f, -10.93f, -10.929f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.64f, 25.835f)
            arcToRelative(5.378f, 5.378f, 0.0f, true, true, -10.756f, 0.0f)
            arcToRelative(5.378f, 5.378f, 0.0f, false, true, 10.756f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.074f, 25.835f)
            arcToRelative(5.378f, 5.378f, 0.0f, true, true, -10.755f, 0.0f)
            arcToRelative(5.378f, 5.378f, 0.0f, false, true, 10.755f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.506f, 40.325f)
            arcToRelative(4.942f, 4.942f, 0.0f, true, true, -9.884f, 0.0f)
            arcToRelative(4.942f, 4.942f, 0.0f, false, true, 9.884f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.906f, 40.325f)
            arcToRelative(4.941f, 4.941f, 0.0f, true, true, -9.883f, 0.0f)
            arcToRelative(4.942f, 4.942f, 0.0f, false, true, 9.883f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(6.498f, 24.34f)
            arcTo(3.25f, 3.25f, 0.0f, true, true, 0.0f, 24.338f)
            arcToRelative(3.25f, 3.25f, 0.0f, false, true, 6.498f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(6.761f, 24.34f)
            arcToRelative(3.25f, 3.25f, 0.0f, true, true, -6.499f, -0.001f)
            arcToRelative(3.25f, 3.25f, 0.0f, false, true, 6.5f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(13.24f, 36.389f)
            arcToRelative(4.485f, 4.485f, 0.0f, true, true, -8.97f, 0.0f)
            arcToRelative(4.485f, 4.485f, 0.0f, false, true, 8.97f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(13.603f, 36.389f)
            arcToRelative(4.485f, 4.485f, 0.0f, true, true, -8.97f, 0.0f)
            arcToRelative(4.485f, 4.485f, 0.0f, false, true, 8.97f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(7.537f, 28.916f)
            arcToRelative(1.362f, 1.362f, 0.0f, true, true, -2.723f, 0.0f)
            arcToRelative(1.362f, 1.362f, 0.0f, false, true, 2.723f, 0.0f)
            close()
            moveTo(10.49f, 22.568f)
            arcToRelative(1.476f, 1.476f, 0.0f, true, true, -2.95f, 0.0f)
            arcToRelative(1.476f, 1.476f, 0.0f, false, true, 2.95f, 0.0f)
            close()
            moveTo(32.125f, 25.815f)
            arcToRelative(1.211f, 1.211f, 0.0f, true, true, -2.423f, 0.0f)
            arcToRelative(1.211f, 1.211f, 0.0f, false, true, 2.423f, 0.0f)
            close()
            moveTo(34.7f, 31.507f)
            arcToRelative(1.424f, 1.424f, 0.0f, true, true, -2.849f, 0.0f)
            arcToRelative(1.424f, 1.424f, 0.0f, false, true, 2.848f, 0.0f)
            close()
            moveTo(8.568f, 16.277f)
            arcToRelative(1.12f, 1.12f, 0.0f, true, true, -2.24f, 0.0f)
            arcToRelative(1.12f, 1.12f, 0.0f, false, true, 2.24f, 0.0f)
            close()
            moveTo(37.515f, 16.958f)
            arcToRelative(1.075f, 1.075f, 0.0f, true, true, -2.15f, 0.0f)
            arcToRelative(1.075f, 1.075f, 0.0f, false, true, 2.15f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(21.357f, 66.632f)
            arcTo(47.971f, 47.971f, 0.0f, false, false, 3.028f, 52.945f)
            arcToRelative(60.927f, 60.927f, 0.0f, false, false, 17.4f, 13.687f)
            horizontalLineToRelative(0.93f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.965f, 66.632f)
            arcToRelative(21.055f, 21.055f, 0.0f, false, false, -9.124f, -2.745f)
            arcToRelative(19.342f, 19.342f, 0.0f, false, true, -7.368f, 1.978f)
            arcToRelative(14.728f, 14.728f, 0.0f, false, true, 5.712f, -5.37f)
            arcToRelative(18.167f, 18.167f, 0.0f, false, true, 11.648f, 6.136f)
            horizontalLineToRelative(-0.868f)
            verticalLineToRelative(0.001f)
            close()
            moveTo(23.166f, 66.631f)
            arcToRelative(30.803f, 30.803f, 0.0f, false, true, 10.85f, -9.81f)
            arcToRelative(15.113f, 15.113f, 0.0f, false, true, 5.207f, 0.505f)
            curveToRelative(-1.672f, 0.269f, -3.275f, 0.858f, -4.724f, 1.736f)
            arcToRelative(29.756f, 29.756f, 0.0f, false, true, -10.708f, 7.57f)
            horizontalLineToRelative(-0.625f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(23.791f, 66.63f)
            arcToRelative(24.073f, 24.073f, 0.0f, false, true, 12.505f, -4.642f)
            arcToRelative(36.033f, 36.033f, 0.0f, false, true, -12.505f, 4.643f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(21.207f, 66.631f)
            arcToRelative(57.149f, 57.149f, 0.0f, false, true, -4.32f, -22.809f)
            arcToRelative(36.489f, 36.489f, 0.0f, false, true, 5.248f, 22.81f)
            horizontalLineToRelative(-0.928f)
            close()
            moveTo(23.498f, 66.632f)
            arcToRelative(34.058f, 34.058f, 0.0f, false, false, 5.545f, -24.98f)
            arcToRelative(64.467f, 64.467f, 0.0f, false, false, -6.225f, 24.98f)
            horizontalLineToRelative(0.68f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(21.692f, 66.631f)
            arcTo(40.694f, 40.694f, 0.0f, false, false, 6.904f, 44.184f)
            arcTo(45.436f, 45.436f, 0.0f, false, false, 20.833f, 66.63f)
            horizontalLineToRelative(0.859f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(28.9f, 76.248f)
            horizontalLineTo(15.108f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.0f, -0.157f)
            horizontalLineTo(28.9f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, 0.157f)
            close()
            moveTo(28.268f, 77.843f)
            horizontalLineTo(15.74f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(12.527f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, 0.157f)
            close()
            moveTo(27.636f, 79.438f)
            horizontalLineTo(16.374f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(11.262f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.0f, 0.158f)
            close()
            moveTo(23.418f, 65.288f)
            lineToRelative(-0.014f, -0.001f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.065f, -0.091f)
            curveToRelative(0.323f, -1.95f, 1.186f, -4.63f, 2.101f, -7.468f)
            curveToRelative(1.524f, -4.727f, 3.252f, -10.085f, 3.227f, -14.159f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.078f, -0.08f)
            horizontalLineToRelative(0.002f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.036f, 0.078f, 0.079f)
            curveToRelative(0.025f, 4.099f, -1.707f, 9.47f, -3.234f, 14.209f)
            curveToRelative(-0.914f, 2.832f, -1.776f, 5.507f, -2.096f, 7.445f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.077f, 0.066f)
            close()
            moveTo(21.196f, 66.096f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.074f, -0.053f)
            curveToRelative(-1.015f, -2.87f, -4.566f, -7.709f, -7.7f, -11.978f)
            curveToRelative(-2.667f, -3.634f, -4.97f, -6.771f, -5.502f, -8.331f)
            arcToRelative(0.08f, 0.08f, 0.0f, true, true, 0.15f, -0.05f)
            curveToRelative(0.525f, 1.536f, 2.931f, 4.816f, 5.48f, 8.288f)
            curveToRelative(3.14f, 4.277f, 6.698f, 9.126f, 7.722f, 12.017f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.076f, 0.107f)
            close()
            moveTo(21.57f, 66.014f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.08f, -0.079f)
            curveToRelative(0.0f, -5.014f, -2.652f, -16.291f, -4.192f, -19.995f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.042f, -0.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.104f, 0.042f)
            curveToRelative(1.544f, 3.714f, 4.205f, 15.026f, 4.205f, 20.055f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.08f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.544f, 78.713f)
            horizontalLineTo(31.047f)
            verticalLineToRelative(3.815f)
            horizontalLineToRelative(10.497f)
            verticalLineToRelative(-3.815f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.046f, 79.438f)
            horizontalLineToRelative(-1.423f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(1.423f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.079f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.079f)
            close()
            moveTo(33.046f, 80.07f)
            horizontalLineToRelative(-1.423f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(1.423f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.079f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.078f)
            close()
            moveTo(33.046f, 80.7f)
            horizontalLineToRelative(-1.423f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(1.423f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.079f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.078f)
            close()
            moveTo(33.046f, 81.331f)
            horizontalLineToRelative(-1.423f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(1.423f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.078f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.08f)
            close()
            moveTo(33.046f, 81.961f)
            horizontalLineToRelative(-1.423f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, -0.079f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, -0.08f)
            horizontalLineToRelative(1.422f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.08f, 0.08f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.08f)
            close()
            moveTo(40.777f, 79.438f)
            horizontalLineToRelative(-6.429f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(6.43f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.035f, 0.078f, 0.079f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(40.777f, 80.07f)
            horizontalLineToRelative(-6.429f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(6.43f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.035f, 0.078f, 0.079f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(40.777f, 80.7f)
            horizontalLineToRelative(-6.429f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.158f)
            horizontalLineToRelative(6.43f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.035f, 0.078f, 0.079f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(40.777f, 81.331f)
            horizontalLineToRelative(-6.429f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(6.43f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.035f, 0.078f, 0.078f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.08f)
            close()
            moveTo(40.777f, 81.961f)
            horizontalLineToRelative(-6.429f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, -0.079f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.078f, -0.08f)
            horizontalLineToRelative(6.43f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.036f, 0.078f, 0.08f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.08f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.712f, 82.375f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-3.28f)
            arcToRelative(0.079f, 0.079f, 0.0f, true, true, 0.158f, 0.0f)
            verticalLineToRelative(3.28f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.079f, 0.08f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(39.95f, 77.44f)
            horizontalLineToRelative(-4.502f)
            verticalLineToRelative(1.273f)
            horizontalLineToRelative(4.502f)
            verticalLineTo(77.44f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(39.222f, 76.17f)
            horizontalLineToRelative(-4.501f)
            verticalLineToRelative(1.272f)
            horizontalLineToRelative(4.501f)
            verticalLineTo(76.17f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(22.254f, 6.21f)
            verticalLineToRelative(4.593f)
            lineToRelative(3.622f, 1.734f)
            lineToRelative(-3.622f, -6.326f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(22.254f, 6.21f)
            lineToRelative(-3.622f, 6.327f)
            lineToRelative(3.622f, -1.734f)
            verticalLineTo(6.21f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(22.254f, 15.512f)
            verticalLineToRelative(3.12f)
            lineToRelative(3.624f, -5.373f)
            lineToRelative(-3.624f, 2.253f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(22.254f, 18.632f)
            verticalLineToRelative(-3.12f)
            lineToRelative(-3.622f, -2.253f)
            lineToRelative(3.622f, 5.373f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(22.254f, 14.79f)
            lineToRelative(3.622f, -2.253f)
            lineToRelative(-3.622f, -1.733f)
            verticalLineToRelative(3.986f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(18.632f, 12.537f)
            lineToRelative(3.622f, 2.253f)
            verticalLineToRelative(-3.986f)
            lineToRelative(-3.622f, 1.733f)
            close()
        }
    }
        .build()
}
