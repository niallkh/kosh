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
public fun DappsEmpty(
    upperClothes: Color = MaterialTheme.colorScheme.primary,
    upperClothesDetails: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    downClothes: Color = MaterialTheme.colorScheme.primaryContainer,
    downClothesDetails: Color = MaterialTheme.colorScheme.primary,
    shoes: Color = MaterialTheme.colorScheme.secondary,
    shoesDetails: Color = MaterialTheme.colorScheme.onSecondary,
    shoesBase: Color = MaterialTheme.colorScheme.outlineVariant,
    accessories: Color = MaterialTheme.colorScheme.tertiary,
    accessoriesDetails: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    objects: Color = MaterialTheme.colorScheme.tertiaryContainer,
    background: Color = MaterialTheme.colorScheme.background,
): ImageVector = remember(upperClothes, downClothes, shoes, accessories, objects, background) {
    Builder(
        name = "DappsEmpty", defaultWidth = 100.0.dp, defaultHeight = 90.0.dp,
        viewportWidth = 100.0f, viewportHeight = 90.0f
    ).apply {
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(39.562f, 89.703f)
            lineTo(17.0f, 89.703f)
            lineTo(17.0f, 39.685f)
            horizontalLineToRelative(22.562f)
            verticalLineToRelative(50.018f)
            close()
            moveTo(17.179f, 89.524f)
            horizontalLineToRelative(22.204f)
            verticalLineToRelative(-49.66f)
            lineTo(17.18f, 39.864f)
            verticalLineToRelative(49.66f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.823f, 42.433f)
            horizontalLineTo(19.74f)
            verticalLineToRelative(44.524f)
            horizontalLineToRelative(17.083f)
            verticalLineTo(42.433f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(46.605f, 22.474f)
            curveToRelative(-0.686f, 1.181f, -2.278f, 4.094f, -2.844f, 6.578f)
            horizontalLineToRelative(2.671f)
            lineToRelative(0.173f, -6.578f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.028f, 5.746f)
            curveToRelative(-1.586f, 0.18f, -2.66f, -1.76f, -0.764f, -3.383f)
            curveToRelative(1.896f, -1.621f, 10.689f, -3.38f, 11.03f, 4.888f)
            curveToRelative(0.0f, 0.0f, -1.667f, 3.54f, -4.864f, 5.78f)
            lineToRelative(-5.402f, -7.285f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(53.424f, 17.532f)
            verticalLineToRelative(3.285f)
            horizontalLineToRelative(-6.826f)
            curveToRelative(0.207f, -2.232f, 0.215f, -3.943f, 0.142f, -5.174f)
            curveToRelative(-0.085f, -1.739f, -0.321f, -2.527f, -0.321f, -2.527f)
            curveToRelative(-4.738f, 0.88f, -3.307f, -4.63f, -2.39f, -7.371f)
            curveToRelative(0.508f, -0.122f, 1.087f, -0.365f, 1.874f, -0.68f)
            curveToRelative(2.684f, -1.073f, 2.706f, 3.02f, 2.706f, 3.02f)
            reflectiveCurveToRelative(2.124f, -0.308f, 2.333f, 1.302f)
            curveToRelative(0.2f, 1.61f, -1.51f, 1.746f, -1.51f, 1.746f)
            curveToRelative(0.55f, 2.149f, 2.025f, 3.909f, 3.992f, 6.4f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.111f, 29.05f)
            curveToRelative(-2.5f, 8.84f, -0.846f, 18.672f, 2.923f, 26.346f)
            lineToRelative(-0.6f, -26.345f)
            horizontalLineTo(44.11f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(82.0f, 78.555f)
            curveToRelative(-0.637f, 2.047f, -3.45f, 7.922f, -7.85f, 10.863f)
            curveToRelative(-0.58f, 0.386f, -1.353f, -0.042f, -1.39f, -0.737f)
            curveToRelative(-0.057f, -1.18f, 0.037f, -2.863f, 0.78f, -4.186f)
            curveToRelative(1.181f, -2.118f, 1.582f, -3.686f, 1.596f, -4.838f)
            curveToRelative(0.015f, -1.238f, -0.422f, -1.997f, -0.844f, -2.448f)
            curveToRelative(-0.407f, -0.422f, -0.801f, -0.573f, -0.801f, -0.573f)
            lineToRelative(3.356f, -2.211f)
            curveToRelative(0.264f, 0.272f, 0.658f, 0.622f, 1.116f, 1.01f)
            lineToRelative(0.565f, 0.464f)
            curveTo(80.096f, 77.16f, 82.0f, 78.555f, 82.0f, 78.555f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(82.0f, 78.557f)
            curveToRelative(-0.64f, 2.045f, -3.45f, 7.921f, -7.848f, 10.859f)
            curveToRelative(-0.58f, 0.386f, -1.36f, -0.04f, -1.393f, -0.733f)
            curveToRelative(-0.006f, -0.133f, -0.013f, -0.267f, -0.013f, -0.413f)
            curveToRelative(-0.014f, -1.132f, 0.133f, -2.598f, 0.792f, -3.771f)
            curveToRelative(1.18f, -2.118f, 1.586f, -3.691f, 1.6f, -4.844f)
            curveToRelative(1.832f, -1.145f, 2.91f, -2.698f, 3.39f, -3.758f)
            arcToRelative(89.007f, 89.007f, 0.0f, false, false, 2.405f, 1.86f)
            curveToRelative(0.634f, 0.473f, 1.067f, 0.8f, 1.067f, 0.8f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(78.851f, 72.802f)
            lineTo(71.237f, 79.9f)
            curveToRelative(-3.35f, -3.027f, -6.835f, -6.92f, -10.02f, -11.143f)
            curveToRelative(-2.612f, -3.456f, -5.009f, -7.135f, -6.97f, -10.742f)
            curveToRelative(-2.513f, -4.623f, -4.28f, -9.124f, -4.817f, -12.896f)
            lineToRelative(3.935f, -3.112f)
            lineToRelative(0.486f, -0.387f)
            lineToRelative(5.167f, -4.086f)
            curveToRelative(0.128f, 0.91f, 0.315f, 1.868f, 0.558f, 2.87f)
            curveToRelative(0.172f, 0.737f, 0.38f, 1.489f, 0.616f, 2.269f)
            curveToRelative(3.015f, 10.011f, 10.536f, 22.786f, 18.66f, 30.128f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(82.0f, 78.556f)
            curveToRelative(-0.639f, 2.045f, -3.45f, 7.922f, -7.848f, 10.859f)
            curveToRelative(-0.58f, 0.386f, -1.36f, -0.04f, -1.392f, -0.733f)
            arcToRelative(7.861f, 7.861f, 0.0f, false, true, -0.014f, -0.413f)
            curveToRelative(2.065f, 0.38f, 7.361f, -7.095f, 8.187f, -10.512f)
            curveToRelative(0.634f, 0.472f, 1.067f, 0.799f, 1.067f, 0.799f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.217f, 68.758f)
            curveToRelative(-2.612f, -3.456f, -5.009f, -7.135f, -6.97f, -10.742f)
            lineToRelative(2.97f, -7.578f)
            curveToRelative(0.208f, 7.0f, 1.897f, 13.132f, 4.0f, 18.32f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(60.58f, 82.67f)
            horizontalLineTo(48.794f)
            reflectiveCurveToRelative(-2.97f, -18.135f, -3.198f, -39.289f)
            lineToRelative(11.78f, -2.505f)
            curveToRelative(0.0f, 22.2f, 3.204f, 41.794f, 3.204f, 41.794f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.337f, 35.2f)
            reflectiveCurveToRelative(1.325f, 1.544f, 2.43f, 4.311f)
            lineToRelative(1.016f, -0.217f)
            curveToRelative(-3.908f, -12.052f, -8.252f, -17.826f, -11.358f, -21.763f)
            curveToRelative(-4.072f, 2.92f, -6.827f, 3.285f, -6.827f, 3.285f)
            curveToRelative(-0.83f, 7.464f, -1.08f, 15.201f, -1.001f, 22.565f)
            lineToRelative(11.78f, -2.505f)
            lineToRelative(5.214f, -1.115f)
            curveToRelative(-0.334f, -2.044f, -1.254f, -4.56f, -1.254f, -4.56f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.858f, 89.611f)
            horizontalLineTo(47.575f)
            reflectiveCurveToRelative(0.028f, -2.01f, 3.98f, -3.177f)
            curveToRelative(1.974f, -0.586f, 3.163f, -1.36f, 3.842f, -2.068f)
            curveToRelative(0.68f, -0.716f, 0.844f, -1.36f, 0.752f, -1.696f)
            horizontalLineToRelative(4.402f)
            lineToRelative(0.272f, 1.453f)
            lineToRelative(1.036f, 5.488f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.858f, 89.61f)
            horizontalLineTo(47.575f)
            reflectiveCurveToRelative(0.007f, -0.55f, 0.58f, -1.237f)
            curveToRelative(0.521f, -0.637f, 1.51f, -1.381f, 3.4f, -1.94f)
            curveToRelative(1.974f, -0.586f, 3.163f, -1.359f, 3.842f, -2.068f)
            curveToRelative(1.281f, 0.48f, 3.707f, 0.623f, 5.425f, -0.243f)
            lineToRelative(0.801f, 4.25f)
            lineToRelative(0.236f, 1.239f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.858f, 89.611f)
            horizontalLineTo(47.575f)
            reflectiveCurveToRelative(0.007f, -0.552f, 0.58f, -1.238f)
            horizontalLineToRelative(13.468f)
            lineToRelative(0.236f, 1.238f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.332f, 88.255f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.089f, -0.084f)
            arcToRelative(1.303f, 1.303f, 0.0f, false, false, -0.737f, -1.107f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.041f, -0.119f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.119f, -0.041f)
            curveToRelative(0.49f, 0.234f, 0.81f, 0.715f, 0.84f, 1.257f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.085f, 0.094f)
            horizontalLineToRelative(-0.007f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.945f, 39.91f)
            horizontalLineToRelative(16.776f)
            arcToRelative(3.368f, 3.368f, 0.0f, false, false, 3.369f, -3.367f)
            verticalLineTo(29.05f)
            horizontalLineToRelative(-5.687f)
            verticalLineToRelative(5.33f)
            arcToRelative(0.956f, 0.956f, 0.0f, false, true, -0.817f, 0.947f)
            curveToRelative(-2.256f, 0.333f, -8.613f, 1.234f, -11.7f, 1.27f)
            curveToRelative(-3.266f, -0.844f, -5.675f, 0.96f, -6.383f, 2.377f)
            curveToRelative(-0.708f, 1.415f, -0.57f, 1.987f, -0.206f, 2.124f)
            curveToRelative(0.366f, 0.138f, 0.788f, -0.41f, 0.788f, -0.41f)
            reflectiveCurveToRelative(-0.182f, 0.49f, 0.25f, 0.581f)
            curveToRelative(0.434f, 0.092f, 0.64f, -0.194f, 0.755f, -0.422f)
            curveToRelative(0.022f, 0.548f, 0.25f, 0.708f, 0.628f, 0.595f)
            curveToRelative(0.468f, -0.143f, 0.674f, -1.532f, 2.227f, -1.532f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.234f, 87.815f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.085f, -0.06f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.057f, -0.114f)
            curveToRelative(0.488f, -0.166f, 0.985f, -0.309f, 1.465f, -0.447f)
            curveToRelative(1.659f, -0.478f, 3.226f, -0.93f, 4.033f, -2.195f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.151f, 0.097f)
            curveToRelative(-0.844f, 1.32f, -2.442f, 1.782f, -4.135f, 2.27f)
            curveToRelative(-0.478f, 0.137f, -0.972f, 0.28f, -1.457f, 0.445f)
            arcToRelative(0.096f, 0.096f, 0.0f, false, true, -0.03f, 0.004f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(50.3f, 9.304f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.037f, -0.008f)
            curveToRelative(-0.32f, -0.14f, -0.844f, -0.198f, -1.513f, -0.176f)
            horizontalLineToRelative(-0.004f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.089f, -0.085f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.086f, -0.093f)
            curveToRelative(0.485f, -0.017f, 1.157f, 0.0f, 1.592f, 0.19f)
            curveToRelative(0.045f, 0.02f, 0.067f, 0.074f, 0.046f, 0.118f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.082f, 0.054f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(53.954f, 7.11f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.078f, -0.044f)
            curveToRelative(-0.71f, -1.253f, -3.408f, -5.2f, -8.273f, -3.387f)
            curveToRelative(-1.35f, 0.503f, -2.353f, 0.52f, -2.687f, 0.045f)
            curveToRelative(-0.195f, -0.275f, -0.108f, -0.667f, 0.235f, -1.076f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.137f, 0.115f)
            curveToRelative(-0.286f, 0.342f, -0.37f, 0.655f, -0.226f, 0.859f)
            curveToRelative(0.21f, 0.296f, 0.984f, 0.447f, 2.48f, -0.11f)
            curveToRelative(1.265f, -0.47f, 5.612f, -1.613f, 8.49f, 3.467f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.033f, 0.122f)
            arcToRelative(0.113f, 0.113f, 0.0f, false, true, -0.045f, 0.01f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(51.514f, 9.375f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.07f, -0.143f)
            curveToRelative(0.494f, -0.653f, 1.209f, -1.65f, 1.587f, -2.3f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.155f, 0.09f)
            curveToRelative(-0.383f, 0.657f, -1.078f, 1.629f, -1.601f, 2.318f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.071f, 0.035f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(51.147f, 8.17f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.072f, -0.143f)
            curveToRelative(0.009f, -0.012f, 0.916f, -1.215f, 1.203f, -1.668f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, 0.123f, -0.028f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.028f, 0.122f)
            curveToRelative(-0.29f, 0.46f, -1.174f, 1.631f, -1.212f, 1.682f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.07f, 0.035f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(50.76f, 7.196f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.072f, -0.143f)
            curveToRelative(0.007f, -0.01f, 0.69f, -0.917f, 0.978f, -1.372f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.124f, -0.027f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.027f, 0.124f)
            curveToRelative(-0.292f, 0.459f, -0.98f, 1.374f, -0.987f, 1.383f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.07f, 0.035f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(47.033f, 6.907f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.071f, -0.036f)
            curveToRelative(-0.611f, -0.816f, -1.365f, -0.93f, -1.663f, -0.941f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.085f, -0.093f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.093f, -0.086f)
            curveToRelative(0.322f, 0.012f, 1.14f, 0.135f, 1.798f, 1.012f)
            curveToRelative(0.03f, 0.04f, 0.021f, 0.095f, -0.018f, 0.125f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.054f, 0.019f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF741A15)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.036f, 9.925f)
            curveToRelative(-0.045f, 0.0f, -0.09f, 0.0f, -0.134f, -0.004f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.084f, -0.095f)
            curveToRelative(0.003f, -0.05f, 0.051f, -0.08f, 0.095f, -0.083f)
            curveToRelative(0.447f, 0.03f, 0.885f, -0.099f, 1.244f, -0.36f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.125f, 0.02f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.02f, 0.125f)
            arcToRelative(2.086f, 2.086f, 0.0f, false, true, -1.226f, 0.397f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.932f, 7.134f)
            arcToRelative(3.04f, 3.04f, 0.0f, false, false, -1.355f, 0.654f)
            arcToRelative(0.336f, 0.336f, 0.0f, false, false, 0.032f, 0.538f)
            curveToRelative(0.336f, 0.219f, 0.713f, 0.37f, 1.109f, 0.443f)
            lineToRelative(0.214f, -1.635f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(47.283f, 7.858f)
            arcToRelative(1.988f, 1.988f, 0.0f, false, true, -1.252f, 0.529f)
            arcToRelative(1.997f, 1.997f, 0.0f, false, true, -1.547f, -0.58f)
            arcToRelative(1.808f, 1.808f, 0.0f, false, true, 1.41f, -0.644f)
            curveToRelative(0.051f, 0.0f, 0.1f, 0.0f, 0.143f, 0.007f)
            curveToRelative(0.488f, 0.05f, 0.939f, 0.294f, 1.246f, 0.688f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(46.167f, 7.771f)
            curveToRelative(0.0f, 0.222f, -0.05f, 0.43f, -0.136f, 0.616f)
            arcToRelative(1.997f, 1.997f, 0.0f, false, true, -1.547f, -0.58f)
            arcToRelative(1.808f, 1.808f, 0.0f, false, true, 1.41f, -0.644f)
            curveToRelative(0.051f, 0.0f, 0.1f, 0.0f, 0.143f, 0.007f)
            curveToRelative(0.087f, 0.179f, 0.13f, 0.386f, 0.13f, 0.601f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(54.748f, 29.14f)
            horizontalLineToRelative(-7.244f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.063f, -0.027f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.026f, -0.064f)
            lineToRelative(0.102f, -6.407f)
            curveToRelative(0.001f, -0.049f, 0.035f, -0.088f, 0.091f, -0.087f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.088f, 0.09f)
            lineToRelative(-0.101f, 6.317f)
            horizontalLineToRelative(7.154f)
            curveToRelative(0.05f, 0.0f, 0.089f, 0.039f, 0.089f, 0.09f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.09f, 0.089f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(31.137f, 40.583f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.086f, -0.116f)
            curveToRelative(0.344f, -1.106f, 1.363f, -1.723f, 2.163f, -1.826f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.1f, 0.076f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.076f, 0.1f)
            curveToRelative(-0.77f, 0.1f, -1.71f, 0.717f, -2.017f, 1.703f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.084f, 0.063f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(30.092f, 40.394f)
            lineToRelative(-0.012f, -0.001f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.077f, -0.1f)
            curveToRelative(0.067f, -0.526f, 0.685f, -1.742f, 1.626f, -2.194f)
            curveToRelative(0.042f, -0.022f, 0.097f, -0.003f, 0.118f, 0.042f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.041f, 0.12f)
            curveToRelative(-0.87f, 0.418f, -1.465f, 1.576f, -1.526f, 2.054f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.088f, 0.079f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(75.057f, 86.662f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.07f, -0.034f)
            arcToRelative(1.96f, 1.96f, 0.0f, false, false, -1.681f, -0.72f)
            horizontalLineToRelative(-0.008f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.007f, -0.179f)
            arcToRelative(2.13f, 2.13f, 0.0f, false, true, 1.837f, 0.787f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.013f, 0.126f)
            arcToRelative(0.097f, 0.097f, 0.0f, false, true, -0.058f, 0.02f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(74.178f, 85.544f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.084f, -0.118f)
            curveToRelative(0.122f, -0.352f, 0.381f, -0.837f, 0.68f, -1.4f)
            curveToRelative(0.722f, -1.358f, 1.711f, -3.218f, 1.362f, -4.393f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.061f, -0.111f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.111f, 0.06f)
            curveToRelative(0.37f, 1.244f, -0.64f, 3.143f, -1.376f, 4.528f)
            curveToRelative(-0.296f, 0.556f, -0.55f, 1.035f, -0.668f, 1.373f)
            arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.086f, 0.06f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(43.718f, 8.86f)
            lineToRelative(-0.016f, -0.001f)
            arcToRelative(3.104f, 3.104f, 0.0f, false, true, -1.14f, -0.457f)
            arcToRelative(0.424f, 0.424f, 0.0f, false, true, -0.043f, -0.681f)
            curveToRelative(0.4f, -0.337f, 0.883f, -0.57f, 1.395f, -0.673f)
            curveToRelative(0.048f, -0.014f, 0.096f, 0.021f, 0.105f, 0.07f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.07f, 0.104f)
            arcToRelative(2.955f, 2.955f, 0.0f, false, false, -1.315f, 0.635f)
            arcToRelative(0.244f, 0.244f, 0.0f, false, false, -0.088f, 0.204f)
            arcToRelative(0.244f, 0.244f, 0.0f, false, false, 0.112f, 0.191f)
            curveToRelative(0.327f, 0.214f, 0.689f, 0.359f, 1.076f, 0.43f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.072f, 0.105f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.088f, 0.073f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.369f, 61.242f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.088f, -0.072f)
            curveToRelative(-0.758f, -3.84f, -1.006f, -12.734f, -1.006f, -15.366f)
            curveToRelative(0.0f, -0.05f, 0.04f, -0.09f, 0.089f, -0.09f)
            curveToRelative(0.05f, 0.0f, 0.089f, 0.04f, 0.089f, 0.09f)
            curveToRelative(0.0f, 2.629f, 0.246f, 11.505f, 1.004f, 15.331f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.071f, 0.106f)
            lineToRelative(-0.017f, 0.001f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.908f, 29.14f)
            horizontalLineTo(44.11f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.089f, -0.089f)
            curveToRelative(0.0f, -0.05f, 0.04f, -0.09f, 0.089f, -0.09f)
            horizontalLineToRelative(1.798f)
            curveToRelative(0.05f, 0.0f, 0.09f, 0.04f, 0.09f, 0.09f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.09f, 0.089f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.595f, 43.471f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.056f, -0.02f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.033f, -0.07f)
            lineToRelative(-0.01f, -2.898f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.177f, 0.0f)
            lineToRelative(0.01f, 2.79f)
            lineToRelative(12.55f, -2.484f)
            curveToRelative(0.053f, -0.008f, 0.096f, 0.022f, 0.106f, 0.07f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.07f, 0.106f)
            lineTo(45.61f, 43.47f)
            lineToRelative(-0.016f, 0.002f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(60.194f, 42.673f)
            curveToRelative(-1.417f, -0.508f, -3.577f, -0.88f, -6.826f, -0.665f)
            curveToRelative(-0.787f, 0.05f, -1.639f, 0.136f, -2.562f, 0.264f)
            lineToRelative(3.049f, -0.651f)
            lineToRelative(5.725f, -1.217f)
            curveToRelative(0.17f, 0.737f, 0.378f, 1.49f, 0.614f, 2.27f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.328f, 36.007f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.09f, -0.078f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.078f, -0.1f)
            curveToRelative(1.83f, -0.234f, 3.452f, -0.468f, 4.257f, -0.587f)
            arcToRelative(0.872f, 0.872f, 0.0f, false, false, 0.74f, -0.859f)
            verticalLineTo(29.84f)
            curveToRelative(0.0f, -0.05f, 0.04f, -0.089f, 0.089f, -0.089f)
            curveToRelative(0.05f, 0.0f, 0.089f, 0.04f, 0.089f, 0.089f)
            verticalLineToRelative(4.543f)
            curveToRelative(0.0f, 0.516f, -0.385f, 0.961f, -0.894f, 1.037f)
            curveToRelative(-0.804f, 0.119f, -2.428f, 0.353f, -4.26f, 0.587f)
            horizontalLineToRelative(-0.01f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(50.722f, 40.002f)
            horizontalLineTo(43.34f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.089f, -0.09f)
            curveToRelative(0.0f, -0.05f, 0.04f, -0.089f, 0.089f, -0.089f)
            horizontalLineToRelative(7.382f)
            arcToRelative(3.282f, 3.282f, 0.0f, false, false, 3.279f, -3.28f)
            verticalLineToRelative(-4.67f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.179f, 0.0f)
            verticalLineToRelative(4.67f)
            arcToRelative(3.462f, 3.462f, 0.0f, false, true, -3.458f, 3.459f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(49.43f, 11.225f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.006f, -0.179f)
            curveToRelative(0.009f, 0.0f, 0.766f, -0.059f, 1.18f, -0.565f)
            curveToRelative(0.226f, -0.277f, 0.309f, -0.64f, 0.25f, -1.08f)
            curveToRelative(-0.054f, -0.385f, -0.221f, -0.686f, -0.503f, -0.895f)
            curveToRelative(-0.657f, -0.49f, -1.718f, -0.332f, -1.728f, -0.332f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.075f, -0.023f)
            arcToRelative(0.095f, 0.095f, 0.0f, false, true, -0.029f, -0.072f)
            curveToRelative(0.036f, -0.524f, -0.04f, -1.365f, -0.608f, -2.385f)
            curveToRelative(-0.165f, -0.296f, -0.38f, -0.506f, -0.636f, -0.622f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.044f, -0.118f)
            arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.119f, -0.045f)
            curveToRelative(0.291f, 0.133f, 0.533f, 0.368f, 0.718f, 0.698f)
            curveToRelative(0.552f, 0.991f, 0.656f, 1.824f, 0.636f, 2.376f)
            curveToRelative(0.302f, -0.03f, 1.159f, -0.065f, 1.754f, 0.377f)
            curveToRelative(0.32f, 0.239f, 0.513f, 0.581f, 0.573f, 1.015f)
            curveToRelative(0.066f, 0.492f, -0.03f, 0.902f, -0.29f, 1.218f)
            curveToRelative(-0.462f, 0.567f, -1.269f, 0.627f, -1.302f, 0.63f)
            lineToRelative(-0.008f, 0.002f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(44.65f, 5.65f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.084f, -0.059f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.052f, -0.115f)
            curveToRelative(0.19f, -0.07f, 0.414f, -0.166f, 0.632f, -0.26f)
            curveToRelative(0.36f, -0.155f, 0.733f, -0.314f, 0.971f, -0.37f)
            curveToRelative(0.048f, -0.009f, 0.097f, 0.018f, 0.109f, 0.066f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.067f, 0.108f)
            curveToRelative(-0.224f, 0.052f, -0.588f, 0.209f, -0.943f, 0.36f)
            curveToRelative(-0.22f, 0.093f, -0.446f, 0.191f, -0.64f, 0.263f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.03f, 0.007f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(49.078f, 20.114f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.033f, -0.172f)
            curveToRelative(1.322f, -0.523f, 3.038f, -1.567f, 3.665f, -2.012f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.125f, 0.022f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.022f, 0.125f)
            curveToRelative(-0.63f, 0.449f, -2.366f, 1.503f, -3.701f, 2.031f)
            arcToRelative(0.117f, 0.117f, 0.0f, false, true, -0.034f, 0.006f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.83f, 51.718f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.09f, -0.084f)
            curveToRelative(-0.109f, -1.531f, -0.195f, -5.83f, -0.224f, -7.243f)
            lineToRelative(-0.003f, -0.16f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.088f, -0.092f)
            curveToRelative(0.06f, -0.006f, 0.091f, 0.039f, 0.091f, 0.088f)
            lineToRelative(0.004f, 0.162f)
            curveToRelative(0.076f, 3.787f, 0.151f, 6.221f, 0.223f, 7.234f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.083f, 0.096f)
            lineToRelative(-0.007f, -0.001f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(46.903f, 49.091f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.067f, -0.148f)
            lineToRelative(3.334f, -3.837f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.127f, -0.009f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.009f, 0.127f)
            lineToRelative(-3.335f, 3.838f)
            arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.068f, 0.03f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(73.26f, 76.134f)
            lineToRelative(-0.014f, -0.001f)
            curveToRelative(-2.437f, -0.414f, -6.664f, -2.485f, -8.216f, -3.625f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.02f, -0.125f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.126f, -0.02f)
            curveToRelative(1.537f, 1.13f, 5.726f, 3.184f, 8.138f, 3.594f)
            curveToRelative(0.05f, 0.008f, 0.082f, 0.056f, 0.073f, 0.103f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.087f, 0.074f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.522f, 81.66f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.067f, -0.03f)
            curveToRelative(-1.547f, -1.765f, -3.729f, -6.491f, -4.406f, -9.54f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.067f, -0.107f)
            curveToRelative(0.054f, -0.01f, 0.097f, 0.02f, 0.107f, 0.068f)
            curveToRelative(0.672f, 3.026f, 2.835f, 7.712f, 4.366f, 9.462f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.008f, 0.126f)
            arcToRelative(0.096f, 0.096f, 0.0f, false, true, -0.059f, 0.022f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(57.198f, 40.429f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.086f, -0.065f)
            arcToRelative(9.198f, 9.198f, 0.0f, false, true, 3.719f, -10.255f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.124f, 0.024f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.025f, 0.124f)
            arcToRelative(9.022f, 9.022f, 0.0f, false, false, -3.646f, 10.058f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.061f, 0.111f)
            arcToRelative(0.16f, 0.16f, 0.0f, false, true, -0.025f, 0.003f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(55.66f, 25.854f)
            arcToRelative(8.097f, 8.097f, 0.0f, false, true, -7.516f, -5.094f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.05f, -0.117f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.117f, 0.05f)
            arcToRelative(7.916f, 7.916f, 0.0f, false, false, 10.027f, 4.516f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.115f, 0.055f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.054f, 0.114f)
            arcToRelative(8.162f, 8.162f, 0.0f, false, true, -2.738f, 0.476f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, fillAlpha = 0.6f, strokeAlpha = 0.6f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(29.15f, 62.562f)
            arcToRelative(1.065f, 1.065f, 0.0f, false, false, -1.792f, 0.0f)
            lineToRelative(-2.31f, 3.593f)
            arcToRelative(1.065f, 1.065f, 0.0f, false, false, 0.897f, 1.64f)
            horizontalLineToRelative(4.619f)
            curveToRelative(0.842f, 0.0f, 1.351f, -0.932f, 0.895f, -1.64f)
            lineToRelative(-2.309f, -3.593f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, fillAlpha = 0.6f, strokeAlpha
            = 0.6f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(28.254f, 79.275f)
            arcToRelative(3.195f, 3.195f, 0.0f, true, false, 0.0f, -6.39f)
            arcToRelative(3.195f, 3.195f, 0.0f, false, false, 0.0f, 6.39f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, fillAlpha = 0.6f, strokeAlpha
            = 0.6f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(25.059f, 51.583f)
            curveToRelative(0.0f, -0.589f, 0.477f, -1.065f, 1.065f, -1.065f)
            horizontalLineToRelative(4.26f)
            curveToRelative(0.588f, 0.0f, 1.065f, 0.476f, 1.065f, 1.065f)
            verticalLineToRelative(4.26f)
            curveToRelative(0.0f, 0.588f, -0.477f, 1.065f, -1.065f, 1.065f)
            horizontalLineToRelative(-4.26f)
            arcToRelative(1.065f, 1.065f, 0.0f, false, true, -1.065f, -1.065f)
            verticalLineToRelative(-4.26f)
            close()
        }
    }
        .build()
}
