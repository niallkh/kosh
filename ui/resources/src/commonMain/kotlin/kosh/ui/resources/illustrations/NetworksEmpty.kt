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
        name = "NetworksEmpty", defaultWidth = 101.0.dp, defaultHeight =
        94.0.dp, viewportWidth = 101.0f, viewportHeight = 94.0f
    ).apply {
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.734f, 28.16f)
            reflectiveCurveToRelative(-0.734f, 4.495f, -0.519f, 8.176f)
            curveToRelative(0.037f, 0.72f, 0.116f, 1.412f, 0.245f, 2.031f)
            horizontalLineToRelative(-8.458f)
            reflectiveCurveToRelative(-0.504f, -0.857f, -1.109f, -2.031f)
            arcToRelative(27.637f, 27.637f, 0.0f, false, true, -1.931f, -4.913f)
            horizontalLineToRelative(-6.484f)
            reflectiveCurveToRelative(0.649f, -1.96f, 1.7f, -4.164f)
            curveToRelative(1.175f, -2.45f, 3.502f, -4.15f, 6.19f, -4.582f)
            lineToRelative(6.102f, -0.987f)
            arcToRelative(8.602f, 8.602f, 0.0f, false, true, 6.786f, 1.816f)
            curveToRelative(1.42f, 1.153f, 2.953f, 2.716f, 3.963f, 4.653f)
            horizontalLineToRelative(-6.485f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFCC80)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(87.69f, 10.606f)
            curveToRelative(-0.684f, 0.0f, -1.253f, 0.472f, -1.412f, 1.107f)
            horizontalLineToRelative(-0.876f)
            arcToRelative(2.955f, 2.955f, 0.0f, false, false, -3.217f, -4.807f)
            arcToRelative(5.194f, 5.194f, 0.0f, false, false, -9.957f, 2.079f)
            arcToRelative(5.196f, 5.196f, 0.0f, false, false, 5.195f, 5.196f)
            curveToRelative(0.441f, 0.0f, 0.458f, 0.042f, 0.866f, -0.062f)
            curveToRelative(-0.027f, 0.183f, 0.352f, 0.262f, 0.352f, 0.452f)
            arcToRelative(3.702f, 3.702f, 0.0f, false, false, 7.404f, 0.0f)
            curveToRelative(0.0f, -0.805f, -0.264f, -1.546f, -0.7f, -2.153f)
            horizontalLineToRelative(0.933f)
            curveToRelative(0.159f, 0.634f, 0.728f, 1.107f, 1.412f, 1.107f)
            arcToRelative(1.46f, 1.46f, 0.0f, true, false, 0.0f, -2.919f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(81.494f, 25.725f)
            curveToRelative(-3.523f, -0.403f, -2.508f, -3.495f, -2.508f, -3.495f)
            reflectiveCurveToRelative(0.202f, -0.835f, -0.065f, -2.32f)
            arcToRelative(10.991f, 10.991f, 0.0f, false, false, -0.46f, -1.772f)
            curveToRelative(-4.093f, 0.757f, -4.533f, -3.912f, -4.144f, -6.736f)
            curveToRelative(0.829f, 0.253f, 2.118f, -0.44f, 2.486f, -1.382f)
            curveToRelative(0.043f, 0.67f, 0.734f, 1.447f, 1.405f, 1.426f)
            curveToRelative(-0.39f, 1.448f, 0.505f, 2.83f, 0.505f, 2.83f)
            curveToRelative(0.828f, -2.737f, 3.976f, -1.245f, 1.973f, 1.082f)
            curveToRelative(1.635f, 1.974f, 3.89f, 6.296f, 3.89f, 6.296f)
            curveToRelative(0.7f, 0.864f, 0.181f, 4.445f, -3.082f, 4.07f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(79.552f, 14.334f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.08f, -0.13f)
            curveToRelative(0.197f, -0.393f, 0.681f, -0.704f, 1.128f, -0.724f)
            curveToRelative(0.052f, 0.005f, 0.092f, 0.037f, 0.094f, 0.086f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.086f, 0.095f)
            curveToRelative(-0.38f, 0.016f, -0.809f, 0.29f, -0.976f, 0.622f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.08f, 0.051f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(75.24f, 12.988f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.045f, -0.013f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.031f, -0.124f)
            curveToRelative(0.34f, -0.566f, 1.347f, -0.82f, 2.042f, -0.698f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.072f, 0.105f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.105f, 0.073f)
            curveToRelative(-0.634f, -0.114f, -1.562f, 0.124f, -1.856f, 0.613f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.076f, 0.044f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFB3261E)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(76.515f, 16.249f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.048f, -0.166f)
            curveToRelative(0.272f, -0.176f, 0.439f, -0.408f, 0.424f, -0.59f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.083f, -0.097f)
            curveToRelative(0.048f, -0.007f, 0.093f, 0.033f, 0.097f, 0.083f)
            curveToRelative(0.02f, 0.251f, -0.175f, 0.54f, -0.507f, 0.756f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.049f, 0.014f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(74.74f, 13.745f)
            arcToRelative(7.653f, 7.653f, 0.0f, false, false, -1.069f, 1.503f)
            arcToRelative(0.238f, 0.238f, 0.0f, false, false, 0.178f, 0.347f)
            curveToRelative(0.434f, 0.06f, 0.96f, 0.081f, 0.96f, 0.081f)
            lineToRelative(-0.069f, -1.93f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(82.457f, 15.434f)
            arcToRelative(0.553f, 0.553f, 0.0f, true, true, -1.105f, -0.002f)
            arcToRelative(0.553f, 0.553f, 0.0f, false, true, 1.105f, 0.002f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(77.538f, 13.636f)
            arcToRelative(1.372f, 1.372f, 0.0f, false, true, -2.275f, 0.676f)
            curveToRelative(0.115f, -0.497f, 0.497f, -0.878f, 0.994f, -1.0f)
            curveToRelative(0.108f, -0.037f, 0.224f, -0.051f, 0.345f, -0.051f)
            curveToRelative(0.36f, 0.0f, 0.684f, 0.145f, 0.936f, 0.375f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(76.551f, 14.19f)
            curveToRelative(0.0f, 0.166f, -0.03f, 0.324f, -0.079f, 0.468f)
            arcToRelative(1.364f, 1.364f, 0.0f, false, true, -1.21f, -0.347f)
            curveToRelative(0.116f, -0.497f, 0.498f, -0.878f, 0.995f, -1.0f)
            curveToRelative(0.186f, 0.245f, 0.294f, 0.548f, 0.294f, 0.88f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(85.501f, 16.058f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.05f, -0.015f)
            arcToRelative(4.265f, 4.265f, 0.0f, false, true, -1.864f, -3.701f)
            arcToRelative(4.267f, 4.267f, 0.0f, false, true, 2.18f, -3.523f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.123f, 0.036f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.035f, 0.122f)
            arcToRelative(4.084f, 4.084f, 0.0f, false, false, -2.09f, 3.373f)
            arcToRelative(4.086f, 4.086f, 0.0f, false, false, 1.786f, 3.544f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.05f, 0.164f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(78.483f, 13.04f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.086f, -0.12f)
            arcToRelative(8.245f, 8.245f, 0.0f, false, true, 6.4f, -5.544f)
            curveToRelative(0.046f, -0.01f, 0.095f, 0.025f, 0.103f, 0.073f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.073f, 0.105f)
            arcToRelative(8.065f, 8.065f, 0.0f, false, false, -6.26f, 5.423f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.084f, 0.063f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(75.028f, 8.633f)
            arcToRelative(6.54f, 6.54f, 0.0f, false, true, -2.46f, -0.478f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.05f, -0.117f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.118f, -0.05f)
            curveToRelative(2.946f, 1.194f, 6.375f, 0.024f, 7.976f, -2.72f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.124f, -0.032f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.031f, 0.122f)
            curveToRelative(-1.21f, 2.075f, -3.44f, 3.275f, -5.739f, 3.275f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(75.632f, 5.711f)
            arcToRelative(6.91f, 6.91f, 0.0f, false, true, -1.619f, -0.19f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.042f, -0.175f)
            arcToRelative(6.784f, 6.784f, 0.0f, false, false, 5.344f, -0.953f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.124f, 0.025f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.024f, 0.125f)
            arcToRelative(6.982f, 6.982f, 0.0f, false, true, -3.867f, 1.168f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(71.826f, 28.802f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.03f, -0.176f)
            arcToRelative(11.124f, 11.124f, 0.0f, false, false, 6.433f, -5.678f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.12f, -0.042f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.041f, 0.12f)
            arcToRelative(11.303f, 11.303f, 0.0f, false, true, -6.535f, 5.772f)
            arcToRelative(0.127f, 0.127f, 0.0f, false, true, -0.03f, 0.005f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.276f, 29.18f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.061f, -0.024f)
            arcToRelative(7.21f, 7.21f, 0.0f, false, true, -2.026f, -7.23f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.173f, 0.048f)
            arcToRelative(7.03f, 7.03f, 0.0f, false, false, 1.976f, 7.05f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.004f, 0.127f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.066f, 0.03f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.138f, 31.215f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.078f, -0.044f)
            arcToRelative(8.161f, 8.161f, 0.0f, false, false, -6.921f, -3.946f)
            horizontalLineToRelative(-0.04f)
            arcToRelative(8.164f, 8.164f, 0.0f, false, false, -6.921f, 3.877f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, -0.153f, -0.094f)
            arcToRelative(8.34f, 8.34f, 0.0f, false, true, 7.075f, -3.963f)
            horizontalLineToRelative(0.04f)
            arcToRelative(8.34f, 8.34f, 0.0f, false, true, 7.075f, 4.033f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.03f, 0.124f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.047f, 0.013f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(83.69f, 38.115f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.09f, -0.09f)
            arcToRelative(4.613f, 4.613f, 0.0f, false, true, 4.29f, -4.642f)
            curveToRelative(0.053f, -0.008f, 0.094f, 0.034f, 0.097f, 0.084f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.083f, 0.095f)
            arcToRelative(4.434f, 4.434f, 0.0f, false, false, -4.123f, 4.463f)
            curveToRelative(0.0f, 0.05f, -0.04f, 0.09f, -0.091f, 0.09f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.46f, 38.368f)
            horizontalLineToRelative(-8.458f)
            reflectiveCurveToRelative(-0.505f, -0.857f, -1.11f, -2.031f)
            horizontalLineToRelative(9.323f)
            curveToRelative(0.035f, 0.72f, 0.115f, 1.411f, 0.245f, 2.031f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.588f, 93.999f)
            horizontalLineTo(69.374f)
            reflectiveCurveToRelative(2.73f, -2.896f, 8.104f, -4.899f)
            curveToRelative(1.332f, -0.497f, 2.226f, -1.116f, 2.802f, -1.758f)
            curveToRelative(0.72f, -0.8f, 0.966f, -1.635f, 1.001f, -2.355f)
            curveToRelative(0.058f, -1.038f, -0.317f, -1.816f, -0.317f, -1.816f)
            lineToRelative(4.936f, 0.41f)
            lineToRelative(0.482f, 1.867f)
            lineToRelative(0.785f, 3.04f)
            lineToRelative(1.42f, 5.51f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.588f, 93.998f)
            horizontalLineTo(69.374f)
            reflectiveCurveToRelative(0.828f, -0.878f, 2.47f, -2.003f)
            arcTo(26.3f, 26.3f, 0.0f, false, true, 77.48f, 89.1f)
            curveToRelative(1.332f, -0.498f, 2.226f, -1.117f, 2.803f, -1.758f)
            curveToRelative(1.39f, 1.369f, 4.668f, 2.031f, 6.887f, 1.146f)
            lineToRelative(0.901f, 3.508f)
            lineToRelative(0.518f, 2.002f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(88.588f, 94.0f)
            horizontalLineTo(69.374f)
            reflectiveCurveToRelative(0.828f, -0.88f, 2.47f, -2.004f)
            horizontalLineTo(88.07f)
            lineTo(88.588f, 94.0f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(75.455f, 91.784f)
            lineToRelative(-0.037f, -0.13f)
            curveToRelative(-0.002f, -0.005f, -0.172f, -0.591f, -0.718f, -0.906f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.033f, -0.123f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.123f, -0.033f)
            curveToRelative(0.456f, 0.264f, 0.674f, 0.685f, 0.758f, 0.893f)
            curveToRelative(0.721f, -0.533f, 1.707f, -0.906f, 2.66f, -1.269f)
            curveToRelative(1.253f, -0.475f, 2.436f, -0.923f, 3.018f, -1.686f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.143f, 0.11f)
            curveToRelative(-0.614f, 0.804f, -1.82f, 1.26f, -3.097f, 1.745f)
            curveToRelative(-0.986f, 0.373f, -2.005f, 0.76f, -2.714f, 1.317f)
            lineToRelative(-0.103f, 0.082f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(86.383f, 85.448f)
            curveToRelative(-1.404f, 0.296f, -3.797f, 0.043f, -5.1f, -0.461f)
            curveToRelative(0.057f, -1.038f, -0.318f, -1.816f, -0.318f, -1.816f)
            lineToRelative(4.936f, 0.41f)
            lineToRelative(0.482f, 1.867f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(97.797f, 93.999f)
            horizontalLineTo(78.583f)
            reflectiveCurveToRelative(2.73f, -2.896f, 8.104f, -4.899f)
            curveToRelative(1.333f, -0.497f, 2.226f, -1.116f, 2.803f, -1.758f)
            curveToRelative(0.72f, -0.8f, 0.966f, -1.635f, 1.0f, -2.355f)
            curveToRelative(0.059f, -1.038f, -0.316f, -1.816f, -0.316f, -1.816f)
            lineToRelative(4.935f, 0.41f)
            lineToRelative(0.482f, 1.867f)
            lineToRelative(0.785f, 3.04f)
            lineToRelative(1.421f, 5.51f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(97.797f, 93.998f)
            horizontalLineTo(78.583f)
            reflectiveCurveToRelative(0.829f, -0.878f, 2.471f, -2.003f)
            arcToRelative(26.286f, 26.286f, 0.0f, false, true, 5.633f, -2.895f)
            curveToRelative(1.333f, -0.498f, 2.226f, -1.117f, 2.803f, -1.758f)
            curveToRelative(1.39f, 1.369f, 4.669f, 2.031f, 6.888f, 1.146f)
            lineToRelative(0.9f, 3.508f)
            lineToRelative(0.52f, 2.002f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(97.797f, 94.0f)
            horizontalLineTo(78.583f)
            reflectiveCurveToRelative(0.829f, -0.88f, 2.471f, -2.004f)
            horizontalLineToRelative(16.224f)
            lineToRelative(0.52f, 2.003f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(84.664f, 91.784f)
            lineToRelative(-0.037f, -0.13f)
            curveToRelative(-0.002f, -0.005f, -0.17f, -0.59f, -0.716f, -0.906f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.033f, -0.123f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.123f, -0.033f)
            curveToRelative(0.457f, 0.264f, 0.674f, 0.685f, 0.757f, 0.893f)
            curveToRelative(0.723f, -0.533f, 1.708f, -0.906f, 2.663f, -1.269f)
            curveToRelative(1.25f, -0.474f, 2.434f, -0.923f, 3.016f, -1.686f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.143f, 0.11f)
            curveToRelative(-0.613f, 0.804f, -1.82f, 1.26f, -3.097f, 1.745f)
            curveToRelative(-0.986f, 0.373f, -2.005f, 0.76f, -2.714f, 1.317f)
            lineToRelative(-0.105f, 0.082f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(95.592f, 85.448f)
            curveToRelative(-1.405f, 0.296f, -3.796f, 0.043f, -5.1f, -0.461f)
            curveToRelative(0.058f, -1.038f, -0.317f, -1.816f, -0.317f, -1.816f)
            lineToRelative(4.935f, 0.41f)
            lineToRelative(0.482f, 1.867f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(78.98f, 93.707f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.061f, -0.157f)
            curveToRelative(0.41f, -0.373f, 1.105f, -0.959f, 2.083f, -1.628f)
            arcToRelative(26.415f, 26.415f, 0.0f, false, true, 5.653f, -2.906f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.116f, 0.053f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.053f, 0.115f)
            arcToRelative(26.135f, 26.135f, 0.0f, false, false, -5.615f, 2.886f)
            arcToRelative(19.298f, 19.298f, 0.0f, false, false, -2.063f, 1.613f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.06f, 0.024f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(80.002f, 38.368f)
            curveToRelative(-1.774f, 1.63f, -7.018f, 14.98f, -9.176f, 20.604f)
            arcToRelative(8.106f, 8.106f, 0.0f, false, false, -0.262f, 5.011f)
            lineToRelative(5.665f, 21.095f)
            lineToRelative(10.505f, -1.092f)
            reflectiveCurveToRelative(-4.02f, -16.555f, -5.17f, -19.82f)
            verticalLineToRelative(-2.022f)
            horizontalLineToRelative(-0.873f)
            reflectiveCurveToRelative(8.6f, -16.51f, 3.678f, -23.776f)
            horizontalLineToRelative(-4.367f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(85.304f, 84.14f)
            lineToRelative(-6.787f, -22.343f)
            lineToRelative(2.845f, -9.132f)
            lineToRelative(5.372f, 31.321f)
            lineToRelative(-1.43f, 0.153f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(82.103f, 38.368f)
            curveToRelative(-0.92f, 0.846f, -3.023f, 4.737f, 0.288f, 9.566f)
            curveToRelative(-0.852f, 3.634f, -1.784f, 8.465f, -2.308f, 11.259f)
            arcToRelative(9.41f, 9.41f, 0.0f, false, false, 0.24f, 4.455f)
            lineToRelative(6.482f, 21.339f)
            lineToRelative(10.093f, -1.093f)
            lineToRelative(-7.012f, -24.291f)
            curveToRelative(1.241f, -5.84f, 5.425f, -18.302f, -1.427f, -21.235f)
            horizontalLineToRelative(-6.356f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(81.2f, 83.993f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.06f, -0.022f)
            curveToRelative(-2.509f, -2.167f, -5.72f, -7.224f, -7.376f, -10.861f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.045f, -0.12f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.12f, 0.046f)
            curveToRelative(1.647f, 3.62f, 4.839f, 8.648f, 7.33f, 10.798f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.06f, 0.16f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(90.938f, 83.993f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.06f, -0.022f)
            curveToRelative(-2.507f, -2.168f, -5.719f, -7.224f, -7.374f, -10.861f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.045f, -0.12f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.119f, 0.046f)
            curveToRelative(1.648f, 3.62f, 4.84f, 8.648f, 7.33f, 10.798f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.06f, 0.16f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(74.524f, 56.56f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.024f, -0.003f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.062f, -0.111f)
            curveToRelative(0.92f, -3.345f, 3.63f, -10.695f, 3.657f, -10.768f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.116f, -0.053f)
            curveToRelative(0.047f, 0.018f, 0.07f, 0.068f, 0.053f, 0.116f)
            curveToRelative(-0.027f, 0.073f, -2.734f, 7.414f, -3.654f, 10.753f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.086f, 0.066f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(94.142f, 49.714f)
            curveToRelative(0.0f, 0.648f, -0.623f, 1.248f, -1.203f, 1.248f)
            curveToRelative(-0.58f, 0.0f, -0.896f, -0.599f, -0.896f, -1.248f)
            reflectiveCurveToRelative(0.492f, -1.245f, 1.072f, -1.245f)
            curveToRelative(0.58f, 0.0f, 1.027f, 0.596f, 1.027f, 1.245f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(83.071f, 56.791f)
            lineToRelative(-0.01f, -0.001f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.08f, -0.1f)
            curveToRelative(0.345f, -3.451f, 1.775f, -11.152f, 1.789f, -11.23f)
            curveToRelative(0.01f, -0.05f, 0.062f, -0.083f, 0.105f, -0.072f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.072f, 0.105f)
            curveToRelative(-0.016f, 0.078f, -1.445f, 7.77f, -1.788f, 11.215f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.088f, 0.083f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(95.217f, 28.158f)
            lineToRelative(5.473f, 10.487f)
            arcToRelative(2.705f, 2.705f, 0.0f, false, true, -0.229f, 2.877f)
            curveToRelative(-2.975f, 3.957f, -7.24f, 5.618f, -6.723f, 9.069f)
            curveToRelative(0.276f, -0.29f, 1.356f, -0.393f, 1.574f, 1.125f)
            curveToRelative(0.14f, 0.965f, -0.38f, 1.523f, -1.056f, 1.695f)
            curveToRelative(-1.224f, 0.314f, -2.517f, -0.218f, -3.25f, -1.247f)
            curveToRelative(-0.484f, -0.683f, -0.902f, -1.68f, -0.902f, -3.081f)
            curveToRelative(0.0f, -3.346f, 1.62f, -3.058f, 5.112f, -9.025f)
            lineToRelative(-6.484f, -11.9f)
            horizontalLineToRelative(6.485f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(93.916f, 51.829f)
            curveToRelative(-0.163f, 0.0f, -0.333f, -0.087f, -0.466f, -0.237f)
            arcToRelative(0.953f, 0.953f, 0.0f, false, true, -0.226f, -0.738f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.07f, -0.077f)
            curveToRelative(0.199f, -0.046f, 0.565f, -0.369f, 0.706f, -0.832f)
            curveToRelative(0.085f, -0.28f, 0.107f, -0.7f, -0.27f, -1.1f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.004f, -0.126f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.128f, 0.003f)
            curveToRelative(0.435f, 0.462f, 0.409f, 0.95f, 0.31f, 1.275f)
            curveToRelative(-0.138f, 0.453f, -0.495f, 0.83f, -0.774f, 0.936f)
            arcToRelative(0.768f, 0.768f, 0.0f, false, false, 0.188f, 0.539f)
            curveToRelative(0.099f, 0.112f, 0.22f, 0.175f, 0.33f, 0.175f)
            curveToRelative(0.15f, 0.0f, 0.265f, -0.063f, 0.322f, -0.18f)
            curveToRelative(0.082f, -0.17f, 0.03f, -0.414f, -0.133f, -0.623f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.015f, -0.126f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.126f, 0.015f)
            curveToRelative(0.209f, 0.267f, 0.268f, 0.58f, 0.154f, 0.813f)
            curveToRelative(-0.087f, 0.18f, -0.264f, 0.283f, -0.484f, 0.283f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(71.032f, 31.423f)
            lineToRelative(-2.448f, 8.188f)
            reflectiveCurveToRelative(-7.478f, 4.78f, -11.41f, 8.538f)
            curveToRelative(-1.207f, 1.104f, -0.121f, 1.601f, 0.293f, 1.233f)
            curveToRelative(-0.53f, 0.622f, -0.138f, 0.99f, 0.62f, 0.598f)
            curveToRelative(-0.368f, 0.76f, 0.023f, 0.806f, 0.53f, 0.576f)
            curveToRelative(-0.369f, 0.781f, 0.321f, 0.864f, 1.149f, 0.121f)
            curveToRelative(0.827f, -0.743f, 1.173f, -1.203f, 3.472f, -2.536f)
            curveToRelative(1.633f, -0.947f, 5.828f, -3.215f, 8.122f, -4.452f)
            arcToRelative(5.103f, 5.103f, 0.0f, false, false, 2.283f, -2.504f)
            curveToRelative(1.29f, -3.047f, 3.068f, -7.642f, 3.318f, -9.762f)
            horizontalLineToRelative(-5.929f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.99f, 56.292f)
            curveToRelative(0.227f, 0.294f, -1.152f, 1.743f, -3.08f, 3.237f)
            curveToRelative(-1.93f, 1.494f, -3.677f, 2.466f, -3.904f, 2.173f)
            curveToRelative(-0.228f, -0.294f, 1.151f, -1.743f, 3.08f, -3.237f)
            curveToRelative(1.928f, -1.493f, 3.676f, -2.466f, 3.904f, -2.173f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(53.994f, 48.563f)
            lineToRelative(-6.983f, 5.409f)
            lineToRelative(5.981f, 7.723f)
            lineToRelative(6.984f, -5.408f)
            lineToRelative(-5.982f, -7.724f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.673f, 58.59f)
            curveToRelative(0.056f, -0.237f, 0.063f, -0.439f, 0.026f, -0.514f)
            lineToRelative(0.003f, -0.008f)
            lineToRelative(-1.324f, -2.697f)
            lineToRelative(-1.296f, 5.528f)
            lineToRelative(2.384f, -1.827f)
            lineToRelative(0.002f, -0.01f)
            curveToRelative(0.066f, -0.051f, 0.15f, -0.235f, 0.205f, -0.472f)
            close()
        }
        path(
            fill = SolidColor(accessoriesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(42.106f, 58.223f)
            curveToRelative(-0.365f, 1.558f, -0.87f, 2.772f, -1.13f, 2.71f)
            curveToRelative(-0.26f, -0.06f, -0.175f, -1.373f, 0.19f, -2.93f)
            curveToRelative(0.366f, -1.558f, 0.872f, -2.772f, 1.132f, -2.71f)
            curveToRelative(0.259f, 0.06f, 0.173f, 1.373f, -0.192f, 2.93f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(52.908f, 60.254f)
            lineToRelative(-9.403f, -2.203f)
            lineToRelative(-0.213f, 0.911f)
            lineToRelative(9.402f, 2.203f)
            lineToRelative(0.214f, -0.911f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.99f, 55.001f)
            curveToRelative(0.76f, -1.393f, 1.81f, -3.505f, 1.24f, -5.435f)
            curveToRelative(-0.654f, -2.212f, -4.55f, -3.253f, -5.712f, -0.34f)
            lineToRelative(0.691f, 0.92f)
            curveToRelative(0.032f, -0.196f, 0.084f, -0.39f, 0.16f, -0.58f)
            curveToRelative(0.187f, -0.467f, 0.448f, -0.756f, 0.723f, -0.934f)
            curveToRelative(0.31f, -0.2f, 0.69f, -0.292f, 1.107f, -0.273f)
            curveToRelative(0.53f, 0.025f, 1.075f, 0.23f, 1.494f, 0.557f)
            curveToRelative(0.338f, 0.266f, 0.562f, 0.584f, 0.659f, 0.91f)
            curveToRelative(0.125f, 0.422f, 0.152f, 0.872f, 0.101f, 1.345f)
            curveToRelative(-0.126f, 1.192f, -0.726f, 2.399f, -1.267f, 3.392f)
            lineToRelative(0.804f, 0.438f)
            close()
        }
        path(
            fill = SolidColor(accessoriesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(54.007f, 48.57f)
            curveToRelative(0.228f, 0.293f, -1.151f, 1.742f, -3.08f, 3.236f)
            curveToRelative(-1.93f, 1.494f, -3.676f, 2.467f, -3.904f, 2.173f)
            curveToRelative(-0.228f, -0.294f, 1.152f, -1.742f, 3.08f, -3.236f)
            curveToRelative(1.93f, -1.494f, 3.677f, -2.467f, 3.904f, -2.173f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(74.81f, 15.766f)
            horizontalLineToRelative(-0.004f)
            curveToRelative(-0.006f, 0.0f, -0.537f, -0.022f, -0.97f, -0.082f)
            arcToRelative(0.327f, 0.327f, 0.0f, false, true, -0.244f, -0.48f)
            arcToRelative(7.703f, 7.703f, 0.0f, false, true, 1.082f, -1.521f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.127f, -0.005f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.005f, 0.127f)
            curveToRelative(-0.4f, 0.436f, -0.795f, 0.99f, -1.056f, 1.485f)
            arcToRelative(0.151f, 0.151f, 0.0f, false, false, -0.002f, 0.137f)
            arcToRelative(0.151f, 0.151f, 0.0f, false, false, 0.111f, 0.079f)
            curveToRelative(0.425f, 0.059f, 0.947f, 0.08f, 0.952f, 0.08f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.086f, 0.095f)
            curveToRelative(0.0f, 0.047f, -0.04f, 0.085f, -0.088f, 0.085f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(76.961f, 31.511f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.087f, -0.066f)
            lineToRelative(-1.216f, -4.515f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.064f, -0.111f)
            curveToRelative(0.046f, -0.014f, 0.097f, 0.015f, 0.11f, 0.064f)
            lineToRelative(1.216f, 4.515f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.063f, 0.111f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.024f, 0.002f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(72.246f, 31.512f)
            horizontalLineTo(70.48f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.087f, -0.12f)
            curveToRelative(0.333f, -0.928f, 1.083f, -2.924f, 1.548f, -3.848f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.122f, -0.04f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.039f, 0.122f)
            curveToRelative(-0.442f, 0.877f, -1.147f, 2.74f, -1.495f, 3.706f)
            horizontalLineToRelative(1.636f)
            curveToRelative(0.05f, 0.0f, 0.09f, 0.04f, 0.09f, 0.09f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.088f, 0.09f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(91.312f, 28.25f)
            horizontalLineToRelative(-3.897f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.083f, -0.057f)
            curveToRelative(-1.1f, -2.703f, -0.767f, -4.553f, 0.96f, -5.35f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.12f, 0.043f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.044f, 0.12f)
            curveToRelative(-2.031f, 0.938f, -1.614f, 3.26f, -0.891f, 5.063f)
            horizontalLineToRelative(3.836f)
            curveToRelative(0.05f, 0.0f, 0.09f, 0.04f, 0.09f, 0.09f)
            arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.091f, 0.09f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(79.448f, 61.556f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.088f, -0.11f)
            lineToRelative(3.855f, -17.724f)
            curveToRelative(0.01f, -0.048f, 0.06f, -0.08f, 0.107f, -0.07f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.068f, 0.108f)
            lineToRelative(-3.855f, 17.724f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.087f, 0.072f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.1f, 46.565f)
            curveToRelative(-0.983f, 0.26f, -1.828f, 0.173f, -1.828f, 0.173f)
            reflectiveCurveToRelative(-1.069f, 0.327f, -1.879f, 0.31f)
            curveToRelative(0.0f, 0.0f, -0.156f, 0.947f, 1.811f, 0.655f)
            curveToRelative(1.965f, -0.293f, 2.277f, -0.19f, 2.277f, -0.19f)
            lineToRelative(-0.381f, -0.948f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.278f, 47.996f)
            curveToRelative(0.144f, 0.166f, 0.019f, 0.512f, -0.28f, 0.771f)
            curveToRelative(-0.3f, 0.26f, -0.66f, 0.335f, -0.804f, 0.169f)
            curveToRelative(-0.144f, -0.166f, -0.02f, -0.512f, 0.28f, -0.771f)
            curveToRelative(0.3f, -0.26f, 0.66f, -0.336f, 0.804f, -0.169f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.938f, 48.505f)
            curveToRelative(0.145f, 0.166f, 0.02f, 0.512f, -0.28f, 0.771f)
            curveToRelative(-0.3f, 0.26f, -0.66f, 0.335f, -0.804f, 0.169f)
            curveToRelative(-0.145f, -0.166f, -0.02f, -0.512f, 0.28f, -0.771f)
            curveToRelative(0.3f, -0.26f, 0.66f, -0.335f, 0.804f, -0.169f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.496f, 49.01f)
            curveToRelative(0.155f, 0.156f, 0.055f, 0.51f, -0.226f, 0.79f)
            curveToRelative(-0.28f, 0.281f, -0.634f, 0.382f, -0.79f, 0.226f)
            curveToRelative(-0.155f, -0.156f, -0.055f, -0.51f, 0.226f, -0.79f)
            curveToRelative(0.28f, -0.28f, 0.634f, -0.38f, 0.79f, -0.225f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFB7A3)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.931f, 49.747f)
            curveToRelative(0.145f, 0.166f, 0.019f, 0.511f, -0.28f, 0.77f)
            curveToRelative(-0.3f, 0.26f, -0.66f, 0.336f, -0.805f, 0.17f)
            curveToRelative(-0.143f, -0.167f, -0.019f, -0.512f, 0.28f, -0.772f)
            curveToRelative(0.3f, -0.26f, 0.66f, -0.334f, 0.805f, -0.168f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.462f, 50.748f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.074f, -0.037f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.02f, -0.126f)
            curveToRelative(0.16f, -0.116f, 0.558f, -0.472f, 0.483f, -0.718f)
            curveToRelative(-0.015f, -0.051f, -0.043f, -0.082f, -0.083f, -0.098f)
            curveToRelative(-0.133f, -0.056f, -0.385f, 0.055f, -0.463f, 0.098f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.118f, -0.027f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.015f, -0.12f)
            curveToRelative(0.004f, -0.002f, 0.321f, -0.281f, 0.224f, -0.578f)
            curveToRelative(-0.015f, -0.047f, -0.04f, -0.075f, -0.079f, -0.09f)
            curveToRelative(-0.14f, -0.054f, -0.416f, 0.07f, -0.5f, 0.119f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.12f, -0.028f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.018f, -0.12f)
            curveToRelative(0.05f, -0.04f, 0.177f, -0.17f, 0.178f, -0.292f)
            curveToRelative(0.001f, -0.056f, -0.027f, -0.108f, -0.084f, -0.157f)
            curveToRelative(-0.247f, -0.21f, -0.664f, 0.125f, -0.668f, 0.129f)
            arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.12f, -0.005f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.009f, -0.12f)
            curveToRelative(0.151f, -0.191f, 0.176f, -0.4f, 0.146f, -0.487f)
            curveToRelative(-0.013f, -0.038f, -0.047f, -0.066f, -0.099f, -0.08f)
            curveToRelative(-0.055f, -0.016f, -0.266f, -0.047f, -0.598f, 0.223f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.127f, -0.013f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.013f, -0.126f)
            curveToRelative(0.275f, -0.223f, 0.552f, -0.315f, 0.763f, -0.256f)
            arcToRelative(0.318f, 0.318f, 0.0f, false, true, 0.22f, 0.195f)
            curveToRelative(0.034f, 0.1f, 0.025f, 0.23f, -0.018f, 0.361f)
            curveToRelative(0.184f, -0.078f, 0.422f, -0.12f, 0.614f, 0.044f)
            curveToRelative(0.123f, 0.106f, 0.147f, 0.22f, 0.147f, 0.297f)
            arcToRelative(0.407f, 0.407f, 0.0f, false, true, -0.033f, 0.152f)
            arcToRelative(0.564f, 0.564f, 0.0f, false, true, 0.342f, -0.002f)
            curveToRelative(0.09f, 0.034f, 0.155f, 0.105f, 0.187f, 0.201f)
            arcToRelative(0.633f, 0.633f, 0.0f, false, true, -0.067f, 0.506f)
            curveToRelative(0.1f, -0.021f, 0.21f, -0.026f, 0.303f, 0.01f)
            curveToRelative(0.093f, 0.037f, 0.157f, 0.11f, 0.188f, 0.213f)
            curveToRelative(0.128f, 0.414f, -0.479f, 0.868f, -0.548f, 0.917f)
            arcToRelative(0.11f, 0.11f, 0.0f, false, true, -0.053f, 0.016f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(91.101f, 52.378f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.07f, -0.032f)
            curveToRelative(-0.038f, -0.046f, -0.937f, -1.15f, -1.017f, -3.26f)
            curveToRelative(-0.074f, -1.918f, 0.813f, -3.025f, 1.594f, -4.004f)
            curveToRelative(0.093f, -0.115f, 0.184f, -0.23f, 0.274f, -0.344f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.142f, 0.11f)
            curveToRelative(-0.09f, 0.116f, -0.184f, 0.232f, -0.276f, 0.347f)
            curveToRelative(-0.799f, 1.0f, -1.625f, 2.034f, -1.555f, 3.885f)
            curveToRelative(0.078f, 2.044f, 0.967f, 3.141f, 0.975f, 3.152f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.011f, 0.126f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.056f, 0.02f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(41.137f, 51.034f)
            lineToRelative(-1.413f, -0.798f)
            lineToRelative(-14.798f, 26.208f)
            lineToRelative(1.413f, 0.798f)
            lineToRelative(14.798f, -26.208f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.153f, 75.894f)
            arcToRelative(43.268f, 43.268f, 0.0f, false, true, 2.68f, -24.334f)
            arcToRelative(35.126f, 35.126f, 0.0f, false, true, -1.679f, 24.334f)
            horizontalLineToRelative(-1.0f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(13.441f, 45.312f)
            lineToRelative(-1.506f, 0.604f)
            lineTo(24.58f, 77.448f)
            lineToRelative(1.506f, -0.603f)
            lineTo(13.44f, 45.312f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.59f, 28.023f)
            horizontalLineToRelative(-1.622f)
            verticalLineToRelative(47.87f)
            horizontalLineToRelative(1.622f)
            verticalLineToRelative(-47.87f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(40.088f, 32.342f)
            lineToRelative(-0.413f, -0.866f)
            lineTo(25.73f, 38.12f)
            lineToRelative(0.412f, 0.866f)
            lineToRelative(13.945f, -6.645f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(25.635f, 35.046f)
            lineToRelative(0.281f, -0.917f)
            lineTo(7.845f, 28.58f)
            lineToRelative(-0.281f, 0.917f)
            lineToRelative(18.072f, 5.548f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.982f, 94.0f)
            arcToRelative(34.995f, 34.995f, 0.0f, false, false, 4.92f, -18.106f)
            horizontalLineToRelative(-20.14f)
            arcTo(35.006f, 35.006f, 0.0f, false, false, 20.682f, 94.0f)
            horizontalLineToRelative(10.3f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(39.458f, 14.255f)
            curveToRelative(0.0f, 7.873f, -6.382f, 14.255f, -14.255f, 14.255f)
            curveToRelative(-7.873f, 0.0f, -14.255f, -6.382f, -14.255f, -14.255f)
            curveTo(10.948f, 6.382f, 17.33f, 0.0f, 25.203f, 0.0f)
            curveToRelative(7.873f, 0.0f, 14.255f, 6.382f, 14.255f, 14.255f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(40.612f, 14.255f)
            curveToRelative(0.0f, 7.873f, -6.382f, 14.255f, -14.255f, 14.255f)
            curveToRelative(-7.873f, 0.0f, -14.255f, -6.382f, -14.255f, -14.255f)
            curveTo(12.102f, 6.382f, 18.483f, 0.0f, 26.357f, 0.0f)
            curveToRelative(7.872f, 0.0f, 14.255f, 6.382f, 14.255f, 14.255f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.6f, strokeAlpha
            = 0.6f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.357f, 26.883f)
            curveToRelative(-6.963f, 0.0f, -12.628f, -5.665f, -12.628f, -12.628f)
            reflectiveCurveTo(19.394f, 1.627f, 26.357f, 1.627f)
            reflectiveCurveToRelative(12.628f, 5.665f, 12.628f, 12.628f)
            reflectiveCurveToRelative(-5.665f, 12.628f, -12.628f, 12.628f)
            close()
            moveTo(26.357f, 1.807f)
            curveToRelative(-6.864f, 0.0f, -12.448f, 5.584f, -12.448f, 12.45f)
            curveToRelative(0.0f, 6.863f, 5.584f, 12.447f, 12.448f, 12.447f)
            curveToRelative(6.864f, 0.0f, 12.45f, -5.585f, 12.45f, -12.448f)
            curveToRelative(-0.002f, -6.865f, -5.586f, -12.449f, -12.45f, -12.449f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(50.474f, 29.427f)
            arcToRelative(6.125f, 6.125f, 0.0f, true, true, -12.25f, 0.0f)
            arcToRelative(6.125f, 6.125f, 0.0f, false, true, 12.25f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(50.969f, 29.427f)
            arcToRelative(6.125f, 6.125f, 0.0f, true, true, -12.25f, 0.0f)
            arcToRelative(6.125f, 6.125f, 0.0f, false, true, 12.25f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(48.044f, 45.93f)
            arcToRelative(5.629f, 5.629f, 0.0f, true, true, -11.258f, -0.001f)
            arcToRelative(5.629f, 5.629f, 0.0f, false, true, 11.258f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(48.499f, 45.93f)
            arcToRelative(5.628f, 5.628f, 0.0f, true, true, -11.257f, 0.0f)
            arcToRelative(5.629f, 5.629f, 0.0f, false, true, 11.257f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(8.17f, 27.722f)
            arcToRelative(3.701f, 3.701f, 0.0f, true, true, -7.402f, 0.0f)
            arcToRelative(3.701f, 3.701f, 0.0f, false, true, 7.402f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(8.47f, 27.722f)
            arcToRelative(3.701f, 3.701f, 0.0f, true, true, -7.403f, 0.0f)
            arcToRelative(3.701f, 3.701f, 0.0f, false, true, 7.403f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.85f, 41.447f)
            arcToRelative(5.109f, 5.109f, 0.0f, true, true, -10.218f, 0.0f)
            arcToRelative(5.109f, 5.109f, 0.0f, false, true, 10.218f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(16.263f, 41.447f)
            arcToRelative(5.109f, 5.109f, 0.0f, true, true, -10.218f, 0.0f)
            arcToRelative(5.109f, 5.109f, 0.0f, false, true, 10.218f, 0.0f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(25.094f, 75.893f)
            arcTo(54.64f, 54.64f, 0.0f, false, false, 4.218f, 60.305f)
            arcToRelative(69.393f, 69.393f, 0.0f, false, false, 19.819f, 15.588f)
            horizontalLineToRelative(1.057f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(23.509f, 75.894f)
            arcToRelative(23.983f, 23.983f, 0.0f, false, false, -10.393f, -3.127f)
            arcToRelative(22.03f, 22.03f, 0.0f, false, true, -8.392f, 2.253f)
            arcToRelative(16.775f, 16.775f, 0.0f, false, true, 6.506f, -6.117f)
            curveToRelative(5.14f, 0.59f, 9.875f, 3.083f, 13.267f, 6.99f)
            horizontalLineToRelative(-0.988f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.154f, 75.893f)
            arcToRelative(35.085f, 35.085f, 0.0f, false, true, 12.357f, -11.175f)
            curveToRelative(1.996f, -0.154f, 4.003f, 0.04f, 5.932f, 0.576f)
            curveToRelative(-1.904f, 0.306f, -3.73f, 0.977f, -5.38f, 1.977f)
            arcToRelative(33.895f, 33.895f, 0.0f, false, true, -12.197f, 8.622f)
            horizontalLineToRelative(-0.712f)
            close()
        }
        path(
            fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.866f, 75.892f)
            arcToRelative(27.418f, 27.418f, 0.0f, false, true, 14.244f, -5.287f)
            arcToRelative(41.039f, 41.039f, 0.0f, false, true, -14.244f, 5.287f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.922f, 75.892f)
            arcToRelative(65.092f, 65.092f, 0.0f, false, true, -4.92f, -25.979f)
            arcToRelative(41.56f, 41.56f, 0.0f, false, true, 5.978f, 25.98f)
            horizontalLineToRelative(-1.058f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.532f, 75.893f)
            arcToRelative(38.792f, 38.792f, 0.0f, false, false, 6.316f, -28.452f)
            arcToRelative(73.429f, 73.429f, 0.0f, false, false, -7.09f, 28.452f)
            horizontalLineToRelative(0.774f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(25.475f, 75.893f)
            arcTo(46.35f, 46.35f, 0.0f, false, false, 8.632f, 50.325f)
            arcToRelative(51.751f, 51.751f, 0.0f, false, false, 15.865f, 25.568f)
            horizontalLineToRelative(0.978f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.686f, 86.846f)
            horizontalLineToRelative(-15.71f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, -0.179f)
            horizontalLineToRelative(15.71f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, 0.18f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(32.965f, 88.663f)
            horizontalLineTo(18.697f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, -0.18f)
            horizontalLineToRelative(14.268f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, 0.18f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(32.245f, 90.479f)
            horizontalLineTo(19.418f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, -0.18f)
            horizontalLineToRelative(12.827f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.0f, 0.18f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(27.441f, 74.363f)
            lineToRelative(-0.015f, -0.002f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.075f, -0.103f)
            curveToRelative(0.368f, -2.22f, 1.352f, -5.273f, 2.394f, -8.506f)
            curveToRelative(1.735f, -5.384f, 3.703f, -11.486f, 3.675f, -16.127f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.18f, -0.001f)
            curveToRelative(0.028f, 4.669f, -1.944f, 10.786f, -3.683f, 16.184f)
            curveToRelative(-1.041f, 3.226f, -2.023f, 6.272f, -2.388f, 8.48f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.088f, 0.075f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.91f, 75.282f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.084f, -0.06f)
            curveToRelative(-1.155f, -3.268f, -5.2f, -8.78f, -8.77f, -13.642f)
            curveToRelative(-3.038f, -4.14f, -5.66f, -7.713f, -6.267f, -9.49f)
            arcToRelative(0.09f, 0.09f, 0.0f, true, true, 0.171f, -0.058f)
            curveToRelative(0.598f, 1.751f, 3.339f, 5.486f, 6.242f, 9.442f)
            curveToRelative(3.576f, 4.871f, 7.629f, 10.394f, 8.795f, 13.687f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.086f, 0.121f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(25.336f, 75.19f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.09f, -0.09f)
            curveToRelative(0.0f, -5.711f, -3.021f, -18.556f, -4.776f, -22.774f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.049f, -0.118f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, 0.118f, 0.048f)
            curveToRelative(1.758f, 4.23f, 4.79f, 17.114f, 4.79f, 22.843f)
            curveToRelative(0.0f, 0.05f, -0.04f, 0.09f, -0.091f, 0.09f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.116f, 7.074f)
            verticalLineToRelative(5.23f)
            lineToRelative(4.125f, 1.975f)
            lineToRelative(-4.125f, -7.205f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(26.116f, 7.074f)
            lineToRelative(-4.125f, 7.205f)
            lineToRelative(4.125f, -1.975f)
            verticalLineToRelative(-5.23f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.116f, 17.669f)
            verticalLineToRelative(3.553f)
            lineToRelative(4.128f, -6.12f)
            lineToRelative(-4.128f, 2.567f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.116f, 21.222f)
            verticalLineTo(17.67f)
            lineToRelative(-4.125f, -2.567f)
            lineToRelative(4.125f, 6.12f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.2f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(26.116f, 16.846f)
            lineToRelative(4.125f, -2.567f)
            lineToRelative(-4.125f, -1.974f)
            verticalLineToRelative(4.541f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(21.99f, 14.28f)
            lineToRelative(4.126f, 2.566f)
            verticalLineToRelative(-4.54f)
            lineToRelative(-4.125f, 1.973f)
            close()
        }
    }
        .build()
}
