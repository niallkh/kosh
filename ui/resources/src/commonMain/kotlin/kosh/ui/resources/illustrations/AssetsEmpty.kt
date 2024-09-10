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
public fun AssetsEmpty(
    upperClothes: Color = MaterialTheme.colorScheme.primary,
    downClothes: Color = MaterialTheme.colorScheme.secondary,
    details: Color = MaterialTheme.colorScheme.tertiary,
    secondaryDetails: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    tertiaryDetails: Color = MaterialTheme.colorScheme.background,
): ImageVector = remember(upperClothes, downClothes, details, secondaryDetails, tertiaryDetails) {
    Builder(
        name = "AssetsEmpty", defaultWidth = 100.0.dp, defaultHeight =
        80.0.dp, viewportWidth = 100.0f, viewportHeight = 80.0f
    ).apply {
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.7f, strokeAlpha
            = 0.7f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(92.008f, 60.43f)
            curveToRelative(2.681f, -10.419f, -3.591f, -21.039f, -14.01f, -23.72f)
            curveToRelative(-10.42f, -2.681f, -21.039f, 3.592f, -23.72f, 14.01f)
            curveToRelative(-2.681f, 10.42f, 3.592f, 21.04f, 14.01f, 23.72f)
            curveToRelative(10.42f, 2.682f, 21.04f, -3.591f, 23.72f, -14.01f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(94.953f, 59.594f)
            curveToRelative(2.22f, -10.528f, -4.515f, -20.861f, -15.043f, -23.08f)
            curveToRelative(-10.527f, -2.22f, -20.861f, 4.515f, -23.08f, 15.042f)
            curveToRelative(-2.22f, 10.528f, 4.515f, 20.862f, 15.043f, 23.081f)
            curveToRelative(10.527f, 2.22f, 20.86f, -4.515f, 23.08f, -15.043f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(75.888f, 72.318f)
            curveToRelative(-9.234f, 0.0f, -16.745f, -7.511f, -16.745f, -16.744f)
            reflectiveCurveToRelative(7.511f, -16.745f, 16.745f, -16.745f)
            curveToRelative(9.233f, 0.0f, 16.744f, 7.512f, 16.744f, 16.745f)
            curveToRelative(0.001f, 9.233f, -7.51f, 16.744f, -16.744f, 16.744f)
            close()
            moveTo(75.888f, 38.991f)
            curveToRelative(-9.144f, 0.0f, -16.583f, 7.44f, -16.583f, 16.583f)
            curveToRelative(0.0f, 9.143f, 7.44f, 16.582f, 16.583f, 16.582f)
            curveToRelative(9.143f, 0.0f, 16.582f, -7.438f, 16.582f, -16.581f)
            curveToRelative(0.0f, -9.144f, -7.438f, -16.584f, -16.582f, -16.584f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.75f, 60.994f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.073f, -0.046f)
            arcToRelative(13.873f, 13.873f, 0.0f, false, true, -1.379f, -7.083f)
            curveToRelative(0.003f, -0.044f, 0.038f, -0.075f, 0.087f, -0.075f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.075f, 0.087f)
            curveToRelative(-0.171f, 2.41f, 0.3f, 4.829f, 1.363f, 7.0f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.073f, 0.117f)
            close()
            moveTo(51.316f, 60.525f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.07f, -0.04f)
            arcToRelative(10.13f, 10.13f, 0.0f, false, true, -1.35f, -4.272f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.074f, -0.088f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.088f, 0.075f)
            arcToRelative(9.973f, 9.973f, 0.0f, false, false, 1.328f, 4.203f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.028f, 0.112f)
            arcToRelative(0.102f, 0.102f, 0.0f, false, true, -0.042f, 0.01f)
            close()
            moveTo(97.175f, 60.994f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.074f, -0.115f)
            arcToRelative(14.742f, 14.742f, 0.0f, false, false, 1.116f, -8.118f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.068f, -0.092f)
            curveToRelative(0.048f, -0.009f, 0.086f, 0.024f, 0.092f, 0.07f)
            curveToRelative(0.407f, 2.79f, 0.018f, 5.629f, -1.127f, 8.207f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.075f, 0.048f)
            close()
            moveTo(99.042f, 60.214f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.075f, -0.113f)
            curveToRelative(0.47f, -1.146f, 0.763f, -2.354f, 0.87f, -3.59f)
            curveToRelative(0.005f, -0.045f, 0.044f, -0.071f, 0.088f, -0.074f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.075f, 0.09f)
            arcToRelative(12.453f, 12.453f, 0.0f, false, true, -0.883f, 3.637f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.075f, 0.05f)
            close()
            moveTo(58.485f, 6.369f)
            curveToRelative(2.448f, 0.728f, 2.904f, -2.553f, 0.724f, -4.069f)
            curveToRelative(-2.18f, -1.515f, -6.293f, -3.842f, -6.958f, -0.851f)
            curveToRelative(-2.638f, -0.893f, -3.946f, 2.418f, -2.097f, 4.532f)
            curveToRelative(-3.573f, 0.37f, -0.833f, 3.758f, 1.744f, 5.516f)
            curveToRelative(1.89f, -1.803f, 6.587f, -5.128f, 6.587f, -5.128f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.485f, 6.369f)
            curveToRelative(-0.774f, 3.364f, -0.462f, 8.576f, -4.217f, 6.794f)
            lineToRelative(-0.345f, 1.042f)
            lineToRelative(-0.886f, 2.674f)
            horizontalLineToRelative(-5.044f)
            lineToRelative(4.009f, -7.224f)
            reflectiveCurveToRelative(-1.666f, -0.957f, -0.938f, -2.33f)
            curveToRelative(0.729f, -1.367f, 1.849f, 0.124f, 1.849f, 0.124f)
            reflectiveCurveToRelative(0.696f, -1.015f, 0.533f, -2.48f)
            arcToRelative(0.356f, 0.356f, 0.0f, false, true, 0.533f, -0.35f)
            curveToRelative(0.632f, 0.37f, 1.23f, 0.449f, 1.66f, 0.28f)
            curveToRelative(0.273f, -0.111f, 0.586f, -0.066f, 0.742f, 0.181f)
            curveToRelative(0.626f, 0.99f, 1.785f, 1.4f, 2.104f, 1.289f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.542f, 8.073f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.027f, -0.004f)
            lineToRelative(-1.018f, -0.353f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.05f, -0.104f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.103f, -0.05f)
            lineToRelative(1.018f, 0.353f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.05f, 0.103f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.076f, 0.055f)
            close()
            moveTo(57.194f, 6.702f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.05f, -0.018f)
            arcToRelative(2.892f, 2.892f, 0.0f, false, false, -2.525f, -0.525f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.1f, -0.058f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.059f, -0.1f)
            arcToRelative(3.059f, 3.059f, 0.0f, false, true, 2.668f, 0.556f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.013f, 0.114f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.065f, 0.03f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(54.597f, 10.296f)
            arcToRelative(5.737f, 5.737f, 0.0f, false, false, 1.952f, 0.81f)
            arcToRelative(1.11f, 1.11f, 0.0f, false, true, -1.952f, -0.81f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(57.718f, 8.76f)
            lineToRelative(0.866f, 1.402f)
            arcToRelative(0.253f, 0.253f, 0.0f, false, true, -0.287f, 0.375f)
            lineToRelative(-1.312f, -0.39f)
            lineToRelative(0.733f, -1.386f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(56.761f, 8.994f)
            horizontalLineToRelative(-1.73f)
            arcToRelative(0.434f, 0.434f, 0.0f, false, true, -0.433f, -0.433f)
            verticalLineToRelative(-0.816f)
            curveToRelative(0.0f, -0.24f, 0.194f, -0.433f, 0.433f, -0.433f)
            horizontalLineToRelative(1.73f)
            curveToRelative(0.239f, 0.0f, 0.433f, 0.194f, 0.433f, 0.433f)
            verticalLineToRelative(0.816f)
            arcToRelative(0.433f, 0.433f, 0.0f, false, true, -0.433f, 0.433f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(56.55f, 8.047f)
            curveToRelative(0.0f, 0.274f, -0.058f, 0.496f, -0.132f, 0.496f)
            curveToRelative(-0.073f, 0.0f, -0.132f, -0.222f, -0.132f, -0.496f)
            reflectiveCurveToRelative(0.059f, -0.496f, 0.132f, -0.496f)
            curveToRelative(0.073f, 0.0f, 0.133f, 0.222f, 0.133f, 0.496f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(48.505f, 9.861f)
            arcToRelative(0.209f, 0.209f, 0.0f, false, true, 0.015f, -0.278f)
            lineToRelative(0.226f, -0.226f)
            arcToRelative(0.211f, 0.211f, 0.0f, false, true, 0.157f, -0.061f)
            arcToRelative(0.205f, 0.205f, 0.0f, false, true, 0.151f, 0.074f)
            lineToRelative(0.948f, 1.125f)
            arcToRelative(0.209f, 0.209f, 0.0f, false, true, -0.02f, 0.29f)
            lineToRelative(-0.266f, 0.237f)
            arcToRelative(0.21f, 0.21f, 0.0f, false, true, -0.303f, -0.025f)
            lineToRelative(-0.908f, -1.136f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(48.784f, 11.225f)
            arcToRelative(10.405f, 10.405f, 0.0f, false, true, -6.37f, 5.014f)
            arcToRelative(0.247f, 0.247f, 0.0f, false, true, -0.299f, -0.301f)
            arcToRelative(7.964f, 7.964f, 0.0f, false, true, 5.961f, -5.59f)
            arcToRelative(0.249f, 0.249f, 0.0f, false, true, 0.241f, 0.083f)
            lineToRelative(0.44f, 0.517f)
            arcToRelative(0.247f, 0.247f, 0.0f, false, true, 0.027f, 0.277f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(50.494f, 9.943f)
            lineToRelative(-0.008f, -0.001f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.073f, -0.088f)
            curveToRelative(0.034f, -0.344f, 0.187f, -0.67f, 0.43f, -0.917f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.114f, 0.0f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.001f, 0.114f)
            curveToRelative(-0.217f, 0.22f, -0.353f, 0.51f, -0.384f, 0.817f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.075f)
            close()
            moveTo(57.805f, 6.063f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.06f, -0.026f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.004f, -0.115f)
            curveToRelative(0.511f, -0.476f, 0.677f, -0.986f, 0.478f, -1.476f)
            curveToRelative(-0.317f, -0.78f, -1.424f, -1.291f, -2.282f, -1.291f)
            lineToRelative(-0.105f, 0.001f)
            curveToRelative(-1.03f, 0.0f, -2.063f, -0.603f, -2.37f, -1.393f)
            curveToRelative(-0.214f, -0.552f, -0.035f, -1.115f, 0.503f, -1.585f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.115f, 0.007f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.008f, 0.115f)
            curveToRelative(-0.49f, 0.429f, -0.648f, 0.913f, -0.458f, 1.404f)
            curveToRelative(0.278f, 0.718f, 1.267f, 1.289f, 2.22f, 1.289f)
            lineToRelative(0.102f, -0.001f)
            curveToRelative(0.912f, 0.0f, 2.093f, 0.55f, 2.435f, 1.392f)
            curveToRelative(0.159f, 0.388f, 0.197f, 0.992f, -0.518f, 1.656f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.056f, 0.023f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(55.148f, 4.776f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.08f, -0.064f)
            curveToRelative(-0.008f, -0.037f, -0.207f, -0.9f, -1.26f, -1.09f)
            curveToRelative(-0.475f, -0.086f, -0.86f, -0.41f, -1.083f, -0.91f)
            curveToRelative(-0.298f, -0.671f, -0.23f, -1.525f, 0.171f, -2.173f)
            arcToRelative(0.081f, 0.081f, 0.0f, true, true, 0.139f, 0.085f)
            curveToRelative(-0.376f, 0.604f, -0.439f, 1.398f, -0.16f, 2.021f)
            curveToRelative(0.2f, 0.45f, 0.542f, 0.74f, 0.962f, 0.817f)
            curveToRelative(1.163f, 0.21f, 1.388f, 1.205f, 1.39f, 1.215f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.063f, 0.097f)
            lineToRelative(-0.016f, 0.002f)
            close()
            moveTo(48.995f, 8.233f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.072f, -0.119f)
            curveToRelative(0.453f, -0.857f, 0.967f, -1.045f, 1.512f, -1.244f)
            curveToRelative(0.372f, -0.136f, 0.757f, -0.276f, 1.186f, -0.63f)
            curveToRelative(0.706f, -0.583f, 1.027f, -1.79f, 0.716f, -2.69f)
            curveToRelative(-0.295f, -0.857f, -1.109f, -1.345f, -2.289f, -1.374f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.08f, -0.084f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.082f, -0.08f)
            horizontalLineToRelative(0.002f)
            curveToRelative(1.254f, 0.032f, 2.12f, 0.559f, 2.44f, 1.485f)
            curveToRelative(0.332f, 0.96f, -0.012f, 2.246f, -0.766f, 2.867f)
            curveToRelative(-0.451f, 0.372f, -0.868f, 0.524f, -1.234f, 0.658f)
            curveToRelative(-0.538f, 0.197f, -1.002f, 0.366f, -1.425f, 1.168f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.072f, 0.043f)
            close()
            moveTo(42.5f, 15.446f)
            arcToRelative(0.07f, 0.07f, 0.0f, false, true, -0.04f, -0.011f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.03f, -0.112f)
            curveToRelative(0.569f, -0.989f, 1.67f, -1.643f, 2.733f, -2.277f)
            curveToRelative(1.197f, -0.712f, 2.328f, -1.384f, 2.697f, -2.475f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.103f, -0.051f)
            curveToRelative(0.042f, 0.014f, 0.065f, 0.06f, 0.05f, 0.103f)
            curveToRelative(-0.388f, 1.147f, -1.543f, 1.836f, -2.767f, 2.563f)
            curveToRelative(-1.045f, 0.623f, -2.127f, 1.267f, -2.675f, 2.219f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.071f, 0.041f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.37f, 10.629f)
            arcToRelative(0.311f, 0.311f, 0.0f, false, true, -0.096f, -0.015f)
            lineToRelative(-1.311f, -0.39f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.055f, -0.101f)
            curveToRelative(0.012f, -0.043f, 0.059f, -0.066f, 0.101f, -0.055f)
            lineToRelative(1.312f, 0.391f)
            arcToRelative(0.167f, 0.167f, 0.0f, false, false, 0.184f, -0.06f)
            arcToRelative(0.168f, 0.168f, 0.0f, false, false, 0.01f, -0.194f)
            lineToRelative(-0.865f, -1.401f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.027f, -0.112f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.112f, 0.027f)
            lineToRelative(0.866f, 1.401f)
            arcToRelative(0.33f, 0.33f, 0.0f, false, true, -0.02f, 0.379f)
            arcToRelative(0.337f, 0.337f, 0.0f, false, true, -0.265f, 0.13f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(71.183f, 18.528f)
            curveToRelative(1.105f, -1.12f, 4.206f, -3.19f, 4.617f, -2.534f)
            curveToRelative(0.412f, 0.655f, -0.427f, 0.754f, -1.054f, 1.068f)
            curveToRelative(-0.69f, 0.346f, -2.422f, 1.462f, -3.099f, 2.446f)
            curveToRelative(-0.675f, 0.985f, -0.465f, -0.98f, -0.465f, -0.98f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(54.369f, 20.192f)
            lineToRelative(-3.876f, 5.429f)
            reflectiveCurveToRelative(3.453f, 4.294f, 7.067f, 6.155f)
            curveToRelative(0.985f, 0.507f, 2.132f, 0.585f, 3.134f, 0.175f)
            curveToRelative(2.992f, -1.222f, 6.758f, -3.742f, 10.654f, -7.385f)
            curveToRelative(1.01f, -0.944f, 1.494f, -2.297f, 1.474f, -3.7f)
            curveToRelative(-0.012f, -0.862f, 0.185f, -1.586f, 0.986f, -2.192f)
            curveToRelative(1.374f, -1.039f, 2.533f, -1.026f, 2.326f, -1.834f)
            curveToRelative(-0.206f, -0.81f, -4.335f, 0.97f, -5.108f, 1.652f)
            curveToRelative(-0.772f, 0.68f, -1.434f, 3.326f, -1.434f, 3.326f)
            reflectiveCurveToRelative(-5.892f, 4.625f, -9.319f, 5.655f)
            curveToRelative(0.002f, 0.0f, -3.568f, -3.049f, -5.904f, -7.281f)
            close()
            moveTo(26.629f, 68.324f)
            curveToRelative(-0.085f, -1.087f, 0.234f, -2.356f, 1.51f, -3.58f)
            lineToRelative(-2.994f, -2.577f)
            arcToRelative(53.571f, 53.571f, 0.0f, false, false, -1.588f, 2.037f)
            arcToRelative(56.915f, 56.915f, 0.0f, false, false, -4.458f, 6.99f)
            lineToRelative(5.14f, 3.885f)
            horizontalLineToRelative(9.41f)
            reflectiveCurveToRelative(-1.152f, -3.762f, -5.766f, -3.762f)
            curveToRelative(0.002f, 0.0f, -1.124f, -1.276f, -1.254f, -2.993f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.651f, 75.078f)
            horizontalLineToRelative(-9.41f)
            lineTo(19.1f, 71.193f)
            curveToRelative(0.24f, -0.443f, 0.481f, -0.873f, 0.728f, -1.301f)
            arcToRelative(56.566f, 56.566f, 0.0f, false, true, 3.729f, -5.688f)
            curveToRelative(0.377f, 2.16f, 1.886f, 3.547f, 3.072f, 4.12f)
            curveToRelative(0.13f, 1.716f, 1.255f, 2.993f, 1.255f, 2.993f)
            curveToRelative(2.733f, 0.0f, 4.25f, 1.32f, 5.038f, 2.401f)
            curveToRelative(0.54f, 0.735f, 0.73f, 1.36f, 0.73f, 1.36f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.651f, 75.078f)
            horizontalLineToRelative(-9.41f)
            lineTo(19.1f, 71.193f)
            curveToRelative(0.24f, -0.443f, 0.481f, -0.873f, 0.728f, -1.301f)
            lineToRelative(4.849f, 3.826f)
            horizontalLineToRelative(8.245f)
            curveToRelative(0.54f, 0.735f, 0.73f, 1.36f, 0.73f, 1.36f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(28.947f, 73.517f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, -0.075f)
            arcToRelative(1.775f, 1.775f, 0.0f, false, true, 0.761f, -1.59f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.113f, 0.021f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.02f, 0.113f)
            curveToRelative(-0.47f, 0.322f, -0.735f, 0.875f, -0.693f, 1.442f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.075f, 0.088f)
            lineToRelative(-0.006f, 0.001f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(29.066f, 72.758f)
            lineToRelative(-0.018f, -0.002f)
            arcToRelative(3.694f, 3.694f, 0.0f, false, false, -1.701f, 0.0f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.072f, -0.018f)
            arcToRelative(5.842f, 5.842f, 0.0f, false, true, -2.037f, -4.792f)
            curveToRelative(0.003f, -0.045f, 0.039f, -0.07f, 0.087f, -0.077f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.076f, 0.087f)
            arcToRelative(5.677f, 5.677f, 0.0f, false, false, 1.95f, 4.631f)
            arcToRelative(3.907f, 3.907f, 0.0f, false, true, 1.736f, 0.009f)
            curveToRelative(0.043f, 0.01f, 0.071f, 0.054f, 0.06f, 0.098f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.08f, 0.064f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(7.53f, 68.324f)
            curveToRelative(-0.085f, -1.087f, 0.889f, -3.156f, 2.164f, -4.38f)
            lineToRelative(-3.648f, -1.777f)
            arcToRelative(52.534f, 52.534f, 0.0f, false, false, -1.588f, 2.037f)
            arcTo(56.91f, 56.91f, 0.0f, false, false, 0.0f, 71.194f)
            lineToRelative(5.14f, 3.885f)
            horizontalLineToRelative(9.412f)
            reflectiveCurveToRelative(-1.152f, -3.762f, -5.766f, -3.762f)
            curveToRelative(0.0f, 0.0f, -1.126f, -1.276f, -1.256f, -2.993f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(14.552f, 75.078f)
            horizontalLineTo(5.14f)
            lineTo(0.0f, 71.193f)
            curveToRelative(0.24f, -0.443f, 0.482f, -0.873f, 0.729f, -1.301f)
            arcToRelative(56.555f, 56.555f, 0.0f, false, true, 3.728f, -5.688f)
            curveToRelative(0.377f, 2.16f, 1.887f, 3.547f, 3.072f, 4.12f)
            curveToRelative(0.13f, 1.716f, 1.256f, 2.993f, 1.256f, 2.993f)
            curveToRelative(2.733f, 0.0f, 4.249f, 1.32f, 5.036f, 2.401f)
            curveToRelative(0.543f, 0.735f, 0.73f, 1.36f, 0.73f, 1.36f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(14.552f, 75.078f)
            horizontalLineTo(5.14f)
            lineTo(0.0f, 71.193f)
            curveToRelative(0.24f, -0.443f, 0.482f, -0.873f, 0.729f, -1.301f)
            lineToRelative(4.848f, 3.826f)
            horizontalLineToRelative(8.245f)
            curveToRelative(0.542f, 0.735f, 0.73f, 1.36f, 0.73f, 1.36f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(9.848f, 73.517f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, -0.075f)
            arcToRelative(1.774f, 1.774f, 0.0f, false, true, 0.76f, -1.59f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.114f, 0.021f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.02f, 0.113f)
            curveToRelative(-0.47f, 0.322f, -0.735f, 0.875f, -0.693f, 1.442f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.075f, 0.088f)
            lineToRelative(-0.006f, 0.001f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(9.968f, 72.758f)
            lineToRelative(-0.019f, -0.002f)
            arcToRelative(3.694f, 3.694f, 0.0f, false, false, -1.7f, 0.0f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.072f, -0.018f)
            arcToRelative(5.842f, 5.842f, 0.0f, false, true, -2.038f, -4.792f)
            curveToRelative(0.003f, -0.045f, 0.04f, -0.07f, 0.087f, -0.077f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.076f, 0.087f)
            arcToRelative(5.677f, 5.677f, 0.0f, false, false, 1.95f, 4.631f)
            arcToRelative(3.907f, 3.907f, 0.0f, false, true, 1.736f, 0.009f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.06f, 0.098f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.08f, 0.064f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(37.673f, 41.972f)
            lineToRelative(-2.623f, 2.714f)
            lineToRelative(-18.645f, 19.256f)
            horizontalLineTo(4.658f)
            curveTo(10.645f, 55.19f, 19.6f, 47.888f, 19.6f, 47.888f)
            lineToRelative(0.814f, -1.718f)
            horizontalLineToRelative(1.178f)
            curveToRelative(5.174f, -5.955f, 10.907f, -11.031f, 10.907f, -11.031f)
            lineToRelative(1.724f, 2.277f)
            lineToRelative(3.45f, 4.556f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(37.673f, 41.973f)
            lineToRelative(-2.623f, 2.714f)
            curveToRelative(-1.152f, -1.614f, -2.036f, -3.632f, -2.739f, -5.31f)
            lineToRelative(1.914f, -1.96f)
            lineToRelative(3.448f, 4.556f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(7.277f, 61.907f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.064f, -0.03f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.014f, -0.115f)
            curveToRelative(4.518f, -3.53f, 8.942f, -4.961f, 12.846f, -6.226f)
            curveToRelative(2.042f, -0.662f, 3.971f, -1.287f, 5.758f, -2.148f)
            arcToRelative(0.08f, 0.08f, 0.0f, true, true, 0.07f, 0.146f)
            curveToRelative(-1.796f, 0.866f, -3.73f, 1.493f, -5.778f, 2.157f)
            curveToRelative(-3.891f, 1.26f, -8.3f, 2.688f, -12.795f, 6.199f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.051f, 0.017f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(49.127f, 47.076f)
            curveToRelative(-1.57f, -6.203f, -4.23f, -9.781f, -4.23f, -9.781f)
            lineToRelative(-12.586f, -6.54f)
            curveToRelative(-2.689f, 3.54f, -2.349f, 8.207f, 7.39f, 17.198f)
            horizontalLineToRelative(-0.94f)
            verticalLineToRelative(2.042f)
            curveToRelative(-2.832f, 1.773f, -8.335f, 5.695f, -13.616f, 12.172f)
            curveToRelative(3.0f, 4.087f, 7.042f, 6.26f, 7.042f, 6.26f)
            curveToRelative(2.284f, -1.498f, 9.924f, -8.688f, 15.274f, -14.662f)
            arcToRelative(7.342f, 7.342f, 0.0f, false, false, 1.666f, -6.69f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(29.235f, 63.612f)
            curveToRelative(-0.1f, 0.0f, -0.197f, 0.0f, -0.294f, -0.002f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.08f, -0.083f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.082f, -0.08f)
            curveToRelative(3.181f, 0.066f, 7.626f, -0.92f, 9.495f, -2.106f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.111f, 0.025f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.024f, 0.112f)
            curveToRelative(-1.835f, 1.163f, -6.103f, 2.134f, -9.29f, 2.134f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.141f, 34.43f)
            curveToRelative(0.311f, 1.709f, 3.527f, 6.138f, 5.053f, 7.54f)
            curveToRelative(0.0f, 0.0f, -0.685f, -3.676f, -0.467f, -8.224f)
            lineToRelative(-2.18f, -6.637f)
            lineToRelative(-2.406f, 7.322f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(31.357f, 30.215f)
            reflectiveCurveToRelative(2.16f, -1.952f, 3.199f, -2.7f)
            lineToRelative(-0.374f, -0.997f)
            lineToRelative(1.37f, -0.374f)
            reflectiveCurveToRelative(4.901f, -10.758f, 15.992f, -10.758f)
            curveToRelative(4.195f, 0.0f, 3.655f, 6.106f, 2.202f, 8.432f)
            horizontalLineToRelative(-2.95f)
            reflectiveCurveToRelative(-5.025f, 5.898f, -6.189f, 17.612f)
            curveToRelative(0.0f, 0.0f, -6.936f, -2.367f, -13.25f, -11.215f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.464f, 32.487f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.072f, -0.118f)
            arcToRelative(13.627f, 13.627f, 0.0f, false, true, 12.64f, -7.522f)
            curveToRelative(0.044f, 0.002f, 0.08f, 0.04f, 0.078f, 0.084f)
            curveToRelative(0.0f, 0.045f, -0.042f, 0.073f, -0.083f, 0.078f)
            arcToRelative(13.464f, 13.464f, 0.0f, false, false, -12.49f, 7.432f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.073f, 0.046f)
            close()
            moveTo(39.747f, 38.655f)
            lineToRelative(-0.008f, -0.001f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.073f, -0.089f)
            arcToRelative(10.654f, 10.654f, 0.0f, false, true, 7.609f, -9.136f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.101f, 0.056f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.054f, 0.101f)
            arcToRelative(10.49f, 10.49f, 0.0f, false, false, -7.492f, 8.997f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.083f, 0.072f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(70.227f, 34.108f)
            curveToRelative(1.765f, 0.0f, 5.86f, 0.82f, 5.66f, 1.641f)
            curveToRelative(-0.198f, 0.82f, -0.938f, 0.246f, -1.683f, 0.0f)
            curveToRelative(-0.82f, -0.27f, -3.074f, -0.757f, -4.393f, -0.529f)
            curveToRelative(-1.318f, 0.228f, 0.416f, -1.112f, 0.416f, -1.112f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.79f, 23.817f)
            horizontalLineToRelative(-6.562f)
            reflectiveCurveToRelative(1.332f, 5.645f, 2.693f, 9.869f)
            curveToRelative(0.37f, 1.151f, 1.212f, 2.1f, 2.33f, 2.564f)
            curveToRelative(3.333f, 1.384f, 8.31f, 2.374f, 14.286f, 2.608f)
            curveToRelative(1.548f, 0.06f, 3.011f, -0.6f, 4.115f, -1.687f)
            curveToRelative(0.679f, -0.67f, 1.412f, -1.07f, 2.527f, -0.912f)
            curveToRelative(1.91f, 0.27f, 2.814f, 1.179f, 3.297f, 0.4f)
            curveToRelative(0.483f, -0.78f, -4.19f, -2.618f, -5.343f, -2.695f)
            curveToRelative(-1.153f, -0.078f, -3.786f, 1.433f, -3.786f, 1.433f)
            reflectiveCurveTo(58.013f, 34.37f, 54.493f, 32.5f)
            curveToRelative(0.0f, 0.0f, -1.702f, -3.907f, -1.702f, -8.683f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(42.22f, 17.128f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.037f, -0.153f)
            curveToRelative(2.175f, -1.12f, 4.796f, -2.125f, 9.22f, -1.9f)
            curveToRelative(0.045f, 0.003f, 0.08f, 0.041f, 0.078f, 0.087f)
            curveToRelative(-0.002f, 0.044f, -0.043f, 0.082f, -0.086f, 0.076f)
            curveToRelative(-4.382f, -0.225f, -6.982f, 0.77f, -9.139f, 1.88f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.037f, 0.01f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.202f, 24.668f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.052f, -0.145f)
            curveToRelative(1.143f, -0.93f, 2.213f, -1.57f, 3.367f, -2.015f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.105f, 0.047f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.046f, 0.104f)
            curveToRelative(-1.138f, 0.44f, -2.193f, 1.07f, -3.322f, 1.99f)
            arcToRelative(0.073f, 0.073f, 0.0f, false, true, -0.052f, 0.019f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.866f, 17.254f)
            curveToRelative(-2.752f, 1.741f, -4.802f, 3.95f, -6.837f, 6.92f)
            curveToRelative(-1.062f, 1.548f, -2.91f, 2.446f, -4.752f, 2.088f)
            curveToRelative(-1.818f, -0.354f, -2.547f, -1.712f, -2.409f, -3.567f)
            curveToRelative(0.207f, -2.782f, 6.064f, -9.429f, 13.998f, -5.441f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.866f, 17.254f)
            curveToRelative(-2.18f, -1.91f, -6.064f, -2.534f, -9.117f, -1.62f)
            lineToRelative(2.118f, 2.056f)
            reflectiveCurveToRelative(2.74f, -1.973f, 7.0f, -0.436f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.403f, 17.78f)
            curveToRelative(0.125f, 0.157f, -0.29f, 0.695f, -0.928f, 1.203f)
            curveToRelative(-0.638f, 0.507f, -1.256f, 0.79f, -1.38f, 0.633f)
            curveToRelative(-0.126f, -0.157f, 0.29f, -0.695f, 0.927f, -1.203f)
            curveToRelative(0.639f, -0.507f, 1.257f, -0.79f, 1.381f, -0.633f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(38.266f, 22.417f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.077f, -0.058f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.053f, -0.101f)
            curveToRelative(0.072f, -0.023f, 0.143f, -0.044f, 0.215f, -0.063f)
            curveToRelative(1.881f, -0.536f, 4.122f, -0.64f, 7.052f, -0.328f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.072f, 0.09f)
            curveToRelative(-0.005f, 0.044f, -0.045f, 0.082f, -0.09f, 0.072f)
            curveToRelative(-2.908f, -0.31f, -5.13f, -0.207f, -6.99f, 0.323f)
            curveToRelative(-0.072f, 0.02f, -0.142f, 0.041f, -0.212f, 0.062f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.023f, 0.003f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.866f, 17.771f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.057f, -0.023f)
            lineToRelative(-0.852f, -0.827f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.002f, -0.115f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.114f, -0.002f)
            lineToRelative(0.805f, 0.782f)
            curveToRelative(0.18f, -0.118f, 0.78f, -0.477f, 1.733f, -0.733f)
            curveToRelative(1.042f, -0.278f, 2.72f, -0.477f, 4.792f, 0.16f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.054f, 0.1f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.102f, 0.055f)
            curveToRelative(-3.894f, -1.195f, -6.413f, 0.57f, -6.437f, 0.588f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.048f, 0.015f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(53.24f, 23.899f)
            horizontalLineToRelative(-8.098f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.067f, -0.037f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.007f, -0.075f)
            lineToRelative(1.776f, -4.341f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.107f, -0.045f)
            curveToRelative(0.041f, 0.017f, 0.06f, 0.064f, 0.044f, 0.106f)
            lineToRelative(-1.73f, 4.23f)
            horizontalLineToRelative(7.977f)
            curveToRelative(0.044f, 0.0f, 0.081f, 0.036f, 0.081f, 0.081f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.083f, 0.08f)
            close()
            moveTo(34.586f, 43.11f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.064f, -0.03f)
            curveToRelative(-1.146f, -1.43f, -2.236f, -3.351f, -3.242f, -5.71f)
            arcToRelative(0.081f, 0.081f, 0.0f, true, true, 0.15f, -0.062f)
            curveToRelative(1.0f, 2.345f, 2.081f, 4.252f, 3.218f, 5.67f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.012f, 0.114f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.05f, 0.018f)
            close()
            moveTo(44.608f, 41.51f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.03f, -0.005f)
            curveToRelative(-3.599f, -1.371f, -9.062f, -5.223f, -12.097f, -9.66f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.02f, -0.113f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.114f, 0.021f)
            curveToRelative(2.989f, 4.37f, 8.347f, 8.169f, 11.923f, 9.563f)
            curveToRelative(0.055f, -0.457f, 0.278f, -2.288f, 0.392f, -2.935f)
            curveToRelative(0.008f, -0.043f, 0.045f, -0.071f, 0.094f, -0.066f)
            curveToRelative(0.044f, 0.007f, 0.073f, 0.05f, 0.066f, 0.094f)
            curveToRelative(-0.13f, 0.734f, -0.4f, 3.007f, -0.403f, 3.03f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.038f, 0.06f)
            arcToRelative(0.073f, 0.073f, 0.0f, false, true, -0.041f, 0.011f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(49.483f, 34.9f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.068f, -0.035f)
            arcToRelative(4.382f, 4.382f, 0.0f, false, true, -0.572f, -1.154f)
            curveToRelative(-1.164f, -3.61f, -2.298f, -8.224f, -2.618f, -9.553f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.06f, -0.098f)
            curveToRelative(0.045f, -0.013f, 0.088f, 0.016f, 0.099f, 0.06f)
            curveToRelative(0.319f, 1.327f, 1.452f, 5.935f, 2.614f, 9.54f)
            curveToRelative(0.128f, 0.398f, 0.313f, 0.772f, 0.55f, 1.112f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.019f, 0.112f)
            arcToRelative(0.072f, 0.072f, 0.0f, false, true, -0.047f, 0.016f)
            close()
            moveTo(53.208f, 28.145f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.08f, -0.066f)
            arcToRelative(23.134f, 23.134f, 0.0f, false, true, -0.413f, -3.801f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.08f, -0.084f)
            curveToRelative(0.04f, -0.01f, 0.082f, 0.034f, 0.082f, 0.08f)
            curveToRelative(0.028f, 1.22f, 0.165f, 2.49f, 0.41f, 3.774f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.065f, 0.095f)
            curveToRelative(-0.004f, 0.002f, -0.01f, 0.002f, -0.014f, 0.002f)
            close()
            moveTo(65.737f, 38.943f)
            arcToRelative(4.37f, 4.37f, 0.0f, false, true, -0.202f, -0.004f)
            curveToRelative(-0.529f, -0.02f, -1.057f, -0.047f, -1.57f, -0.08f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.076f, -0.086f)
            curveToRelative(0.003f, -0.045f, 0.045f, -0.074f, 0.086f, -0.077f)
            curveToRelative(0.512f, 0.032f, 1.038f, 0.06f, 1.565f, 0.08f)
            curveToRelative(1.473f, 0.055f, 2.908f, -0.535f, 4.054f, -1.663f)
            curveToRelative(0.81f, -0.799f, 1.586f, -1.077f, 2.595f, -0.935f)
            curveToRelative(0.751f, 0.106f, 1.346f, 0.309f, 1.823f, 0.471f)
            curveToRelative(0.786f, 0.268f, 1.144f, 0.37f, 1.394f, -0.034f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.113f, -0.027f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.025f, 0.112f)
            curveToRelative(-0.328f, 0.53f, -0.828f, 0.36f, -1.585f, 0.102f)
            curveToRelative(-0.47f, -0.161f, -1.056f, -0.36f, -1.793f, -0.464f)
            curveToRelative(-0.953f, -0.134f, -1.688f, 0.131f, -2.458f, 0.89f)
            curveToRelative(-1.125f, 1.112f, -2.528f, 1.715f, -3.971f, 1.715f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(78.793f, 47.137f)
            lineToRelative(-1.908f, 6.207f)
            lineToRelative(4.71f, 4.013f)
            lineToRelative(-2.802f, -10.22f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(78.793f, 47.137f)
            lineToRelative(-8.06f, 6.883f)
            lineToRelative(6.152f, -0.676f)
            lineToRelative(1.908f, -6.207f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(74.929f, 59.711f)
            lineToRelative(-1.296f, 4.218f)
            lineToRelative(7.666f, -5.594f)
            lineToRelative(-6.37f, 1.376f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(73.633f, 63.929f)
            lineToRelative(1.296f, -4.218f)
            lineToRelative(-4.496f, -4.714f)
            lineToRelative(3.2f, 8.932f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(75.229f, 58.735f)
            lineToRelative(6.367f, -1.378f)
            lineToRelative(-4.711f, -4.012f)
            lineToRelative(-1.656f, 5.39f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(70.734f, 54.02f)
            lineToRelative(4.495f, 4.715f)
            lineToRelative(1.656f, -5.39f)
            lineToRelative(-6.151f, 0.675f)
            close()
        }
    }
        .build()
}

