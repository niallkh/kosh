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
public fun WcEmpty(
    upperClothes: Color = MaterialTheme.colorScheme.primary,
    downClothes: Color = MaterialTheme.colorScheme.secondary,
    details: Color = MaterialTheme.colorScheme.tertiary,
    secondaryDetails: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    tertiaryDetails: Color = MaterialTheme.colorScheme.background,
): ImageVector = remember(upperClothes, downClothes, details, secondaryDetails, tertiaryDetails) {
    Builder(
        name = "WcEmpty", defaultWidth = 60.0.dp, defaultHeight = 82.0.dp,
        viewportWidth = 60.0f, viewportHeight = 82.0f
    ).apply {
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(20.827f, 81.879f)
            horizontalLineTo(0.0f)
            verticalLineToRelative(-46.17f)
            horizontalLineToRelative(20.827f)
            verticalLineToRelative(46.17f)
            close()
            moveTo(0.165f, 81.714f)
            horizontalLineToRelative(20.496f)
            verticalLineToRelative(-45.84f)
            horizontalLineTo(0.165f)
            verticalLineToRelative(45.84f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.298f, 38.245f)
            horizontalLineTo(2.528f)
            verticalLineToRelative(41.1f)
            horizontalLineToRelative(15.77f)
            verticalLineToRelative(-41.1f)
            close()
            moveTo(27.327f, 19.822f)
            curveToRelative(-0.632f, 1.091f, -2.102f, 3.78f, -2.625f, 6.072f)
            horizontalLineToRelative(2.466f)
            lineToRelative(0.16f, -6.072f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.949f, 4.381f)
            curveToRelative(-1.464f, 0.167f, -2.455f, -1.625f, -0.705f, -3.122f)
            curveToRelative(1.75f, -1.497f, 9.867f, -3.121f, 10.182f, 4.511f)
            curveToRelative(0.0f, 0.0f, -1.54f, 3.268f, -4.49f, 5.335f)
            lineTo(24.948f, 4.38f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.622f, 15.26f)
            verticalLineToRelative(3.033f)
            horizontalLineToRelative(-6.301f)
            curveToRelative(0.19f, -2.06f, 0.198f, -3.64f, 0.132f, -4.776f)
            curveToRelative(-0.08f, -1.605f, -0.297f, -2.333f, -0.297f, -2.333f)
            curveToRelative(-4.374f, 0.813f, -3.053f, -4.273f, -2.207f, -6.804f)
            curveToRelative(0.47f, -0.112f, 1.005f, -0.337f, 1.73f, -0.627f)
            curveToRelative(2.478f, -0.991f, 2.498f, 2.787f, 2.498f, 2.787f)
            reflectiveCurveToRelative(1.961f, -0.284f, 2.154f, 1.202f)
            curveToRelative(0.184f, 1.486f, -1.395f, 1.612f, -1.395f, 1.612f)
            curveToRelative(0.51f, 1.983f, 1.87f, 3.608f, 3.686f, 5.907f)
            close()
            moveTo(25.026f, 25.894f)
            curveToRelative(-2.309f, 8.159f, -0.782f, 17.235f, 2.698f, 24.319f)
            lineToRelative(-0.554f, -24.32f)
            horizontalLineToRelative(-2.144f)
            close()
            moveTo(60.0f, 71.59f)
            curveToRelative(-0.588f, 1.89f, -3.184f, 7.312f, -7.247f, 10.028f)
            curveToRelative(-0.535f, 0.356f, -1.248f, -0.04f, -1.282f, -0.68f)
            curveToRelative(-0.053f, -1.09f, 0.034f, -2.644f, 0.72f, -3.865f)
            curveToRelative(1.09f, -1.955f, 1.46f, -3.402f, 1.473f, -4.466f)
            curveToRelative(0.014f, -1.143f, -0.39f, -1.843f, -0.779f, -2.26f)
            curveToRelative(-0.376f, -0.39f, -0.74f, -0.528f, -0.74f, -0.528f)
            lineToRelative(3.098f, -2.042f)
            curveToRelative(0.244f, 0.252f, 0.608f, 0.575f, 1.03f, 0.932f)
            lineToRelative(0.522f, 0.43f)
            arcTo(106.637f, 106.637f, 0.0f, false, false, 60.0f, 71.588f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(60.0f, 71.591f)
            curveToRelative(-0.59f, 1.888f, -3.185f, 7.313f, -7.245f, 10.024f)
            curveToRelative(-0.535f, 0.357f, -1.255f, -0.036f, -1.285f, -0.676f)
            arcToRelative(7.231f, 7.231f, 0.0f, false, true, -0.012f, -0.381f)
            curveToRelative(-0.013f, -1.045f, 0.122f, -2.399f, 0.731f, -3.482f)
            curveToRelative(1.089f, -1.955f, 1.464f, -3.407f, 1.476f, -4.47f)
            curveToRelative(1.691f, -1.058f, 2.688f, -2.492f, 3.13f, -3.47f)
            arcToRelative(82.45f, 82.45f, 0.0f, false, false, 2.22f, 1.717f)
            curveToRelative(0.585f, 0.437f, 0.985f, 0.739f, 0.985f, 0.739f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(56.273f, 68.71f)
            curveToRelative(-1.168f, 0.31f, -2.649f, 1.102f, -3.388f, 1.637f)
            curveToRelative(-0.376f, -0.39f, -0.74f, -0.528f, -0.74f, -0.528f)
            lineToRelative(3.098f, -2.042f)
            curveToRelative(0.245f, 0.252f, 0.607f, 0.576f, 1.03f, 0.932f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(57.093f, 66.279f)
            lineToRelative(-7.028f, 6.553f)
            curveToRelative(-3.092f, -2.795f, -6.31f, -6.388f, -9.25f, -10.286f)
            curveToRelative(-2.41f, -3.19f, -4.623f, -6.586f, -6.434f, -9.916f)
            curveToRelative(-2.318f, -4.267f, -3.95f, -8.422f, -4.446f, -11.904f)
            lineToRelative(3.633f, -2.873f)
            lineToRelative(0.449f, -0.356f)
            lineToRelative(4.769f, -3.772f)
            curveToRelative(0.118f, 0.839f, 0.29f, 1.724f, 0.515f, 2.649f)
            curveToRelative(0.16f, 0.68f, 0.35f, 1.374f, 0.568f, 2.094f)
            curveToRelative(2.784f, 9.241f, 9.726f, 21.033f, 17.224f, 27.81f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(60.0f, 71.59f)
            curveToRelative(-0.59f, 1.888f, -3.185f, 7.313f, -7.245f, 10.024f)
            curveToRelative(-0.535f, 0.356f, -1.254f, -0.037f, -1.285f, -0.676f)
            arcToRelative(7.231f, 7.231f, 0.0f, false, true, -0.012f, -0.382f)
            curveToRelative(1.906f, 0.35f, 6.795f, -6.55f, 7.558f, -9.703f)
            curveToRelative(0.584f, 0.436f, 0.984f, 0.737f, 0.984f, 0.737f)
            close()
            moveTo(40.816f, 62.546f)
            curveToRelative(-2.411f, -3.19f, -4.624f, -6.586f, -6.435f, -9.916f)
            lineToRelative(2.742f, -6.995f)
            curveToRelative(0.192f, 6.461f, 1.751f, 12.122f, 3.693f, 16.911f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(40.228f, 75.388f)
            horizontalLineToRelative(-10.88f)
            reflectiveCurveToRelative(-2.741f, -16.74f, -2.952f, -36.267f)
            lineToRelative(10.873f, -2.312f)
            curveToRelative(0.0f, 20.492f, 2.96f, 38.579f, 2.96f, 38.579f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(40.926f, 31.57f)
            reflectiveCurveToRelative(1.224f, 1.425f, 2.243f, 3.98f)
            lineToRelative(0.938f, -0.201f)
            curveTo(40.5f, 24.224f, 36.49f, 18.894f, 33.623f, 15.259f)
            curveToRelative(-3.758f, 2.695f, -6.301f, 3.033f, -6.301f, 3.033f)
            curveToRelative(-0.767f, 6.89f, -0.998f, 14.032f, -0.925f, 20.829f)
            lineToRelative(10.873f, -2.313f)
            lineToRelative(4.814f, -1.028f)
            curveToRelative(-0.308f, -1.888f, -1.158f, -4.21f, -1.158f, -4.21f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.408f, 81.795f)
            horizontalLineTo(28.222f)
            reflectiveCurveToRelative(0.026f, -1.856f, 3.673f, -2.932f)
            curveToRelative(1.824f, -0.542f, 2.92f, -1.255f, 3.548f, -1.91f)
            curveToRelative(0.628f, -0.66f, 0.78f, -1.255f, 0.694f, -1.565f)
            horizontalLineTo(40.2f)
            lineToRelative(0.251f, 1.34f)
            lineToRelative(0.957f, 5.067f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.408f, 81.795f)
            horizontalLineTo(28.222f)
            reflectiveCurveToRelative(0.006f, -0.51f, 0.535f, -1.143f)
            curveToRelative(0.482f, -0.588f, 1.394f, -1.275f, 3.138f, -1.79f)
            curveToRelative(1.824f, -0.54f, 2.92f, -1.255f, 3.548f, -1.91f)
            curveToRelative(1.182f, 0.444f, 3.422f, 0.575f, 5.007f, -0.224f)
            lineToRelative(0.74f, 3.924f)
            lineToRelative(0.218f, 1.143f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.408f, 81.795f)
            horizontalLineTo(28.222f)
            reflectiveCurveToRelative(0.006f, -0.509f, 0.535f, -1.143f)
            horizontalLineTo(41.19f)
            lineToRelative(0.218f, 1.143f)
            close()
            moveTo(31.692f, 80.543f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.082f, -0.078f)
            arcToRelative(1.202f, 1.202f, 0.0f, false, false, -0.681f, -1.021f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.038f, -0.11f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.11f, -0.039f)
            curveToRelative(0.452f, 0.216f, 0.748f, 0.661f, 0.775f, 1.16f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.078f, 0.088f)
            horizontalLineToRelative(-0.006f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.642f, 35.918f)
            horizontalLineToRelative(15.486f)
            arcToRelative(3.11f, 3.11f, 0.0f, false, false, 3.109f, -3.109f)
            verticalLineToRelative(-6.915f)
            horizontalLineToRelative(-5.25f)
            verticalLineToRelative(4.92f)
            curveToRelative(0.0f, 0.438f, -0.32f, 0.81f, -0.754f, 0.874f)
            curveToRelative(-2.082f, 0.308f, -7.95f, 1.139f, -10.8f, 1.173f)
            curveToRelative(-3.014f, -0.78f, -5.238f, 0.886f, -5.892f, 2.193f)
            curveToRelative(-0.653f, 1.307f, -0.526f, 1.835f, -0.19f, 1.961f)
            curveToRelative(0.338f, 0.127f, 0.727f, -0.379f, 0.727f, -0.379f)
            reflectiveCurveToRelative(-0.168f, 0.453f, 0.232f, 0.537f)
            curveToRelative(0.4f, 0.084f, 0.59f, -0.18f, 0.696f, -0.39f)
            curveToRelative(0.02f, 0.507f, 0.232f, 0.654f, 0.58f, 0.55f)
            curveToRelative(0.432f, -0.133f, 0.622f, -1.415f, 2.056f, -1.415f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(31.6f, 80.137f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.026f, -0.16f)
            curveToRelative(0.451f, -0.153f, 0.91f, -0.285f, 1.353f, -0.413f)
            curveToRelative(1.531f, -0.442f, 2.978f, -0.86f, 3.723f, -2.026f)
            arcToRelative(0.083f, 0.083f, 0.0f, true, true, 0.14f, 0.09f)
            curveToRelative(-0.78f, 1.218f, -2.255f, 1.644f, -3.817f, 2.095f)
            curveToRelative(-0.442f, 0.127f, -0.898f, 0.258f, -1.346f, 0.41f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.027f, 0.004f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.738f, 7.665f)
            arcToRelative(0.074f, 0.074f, 0.0f, false, true, -0.033f, -0.007f)
            curveToRelative(-0.295f, -0.129f, -0.78f, -0.183f, -1.398f, -0.162f)
            horizontalLineToRelative(-0.003f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.082f, -0.079f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.08f, -0.086f)
            curveToRelative(0.447f, -0.016f, 1.067f, 0.0f, 1.47f, 0.176f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.042f, 0.108f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.076f, 0.05f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(34.111f, 5.64f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.071f, -0.04f)
            curveToRelative(-0.657f, -1.157f, -3.147f, -4.8f, -7.637f, -3.126f)
            curveToRelative(-1.246f, 0.464f, -2.172f, 0.478f, -2.48f, 0.041f)
            curveToRelative(-0.18f, -0.254f, -0.1f, -0.616f, 0.217f, -0.993f)
            arcToRelative(0.082f, 0.082f, 0.0f, true, true, 0.127f, 0.106f)
            curveToRelative(-0.266f, 0.316f, -0.343f, 0.604f, -0.21f, 0.793f)
            curveToRelative(0.195f, 0.273f, 0.908f, 0.412f, 2.289f, -0.101f)
            curveToRelative(1.168f, -0.434f, 5.18f, -1.49f, 7.838f, 3.2f)
            curveToRelative(0.023f, 0.039f, 0.01f, 0.089f, -0.031f, 0.112f)
            arcToRelative(0.104f, 0.104f, 0.0f, false, true, -0.042f, 0.009f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(31.859f, 7.73f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.065f, -0.132f)
            curveToRelative(0.457f, -0.603f, 1.116f, -1.522f, 1.465f, -2.122f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.144f, 0.083f)
            curveToRelative(-0.354f, 0.606f, -0.996f, 1.503f, -1.479f, 2.14f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.065f, 0.032f)
            close()
            moveTo(31.52f, 6.618f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.066f, -0.132f)
            curveToRelative(0.008f, -0.01f, 0.845f, -1.121f, 1.11f, -1.54f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.114f, -0.026f)
            curveToRelative(0.038f, 0.025f, 0.05f, 0.076f, 0.026f, 0.114f)
            curveToRelative(-0.269f, 0.425f, -1.084f, 1.505f, -1.12f, 1.552f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.064f, 0.032f)
            close()
            moveTo(31.163f, 5.72f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.066f, -0.132f)
            curveToRelative(0.006f, -0.009f, 0.637f, -0.847f, 0.903f, -1.267f)
            arcToRelative(0.083f, 0.083f, 0.0f, true, true, 0.139f, 0.09f)
            arcToRelative(27.436f, 27.436f, 0.0f, false, true, -0.911f, 1.276f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.065f, 0.033f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.722f, 5.453f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.065f, -0.034f)
            curveToRelative(-0.564f, -0.753f, -1.26f, -0.858f, -1.535f, -0.868f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.078f, -0.086f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.086f, -0.08f)
            curveToRelative(0.297f, 0.012f, 1.052f, 0.125f, 1.659f, 0.935f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.017f, 0.115f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.05f, 0.018f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF741A15)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(25.88f, 8.239f)
            curveToRelative(-0.042f, 0.0f, -0.083f, -0.001f, -0.124f, -0.004f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.078f, -0.088f)
            curveToRelative(0.003f, -0.045f, 0.048f, -0.073f, 0.088f, -0.077f)
            curveToRelative(0.413f, 0.029f, 0.817f, -0.09f, 1.148f, -0.332f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.115f, 0.019f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.018f, 0.115f)
            arcToRelative(1.925f, 1.925f, 0.0f, false, true, -1.132f, 0.367f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF985557)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.86f, 5.662f)
            arcToRelative(2.806f, 2.806f, 0.0f, false, false, -1.25f, 0.604f)
            arcToRelative(0.31f, 0.31f, 0.0f, false, false, 0.03f, 0.496f)
            curveToRelative(0.31f, 0.203f, 0.657f, 0.342f, 1.023f, 0.41f)
            lineToRelative(0.197f, -1.51f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.954f, 6.33f)
            curveToRelative(-0.33f, 0.304f, -0.74f, 0.47f, -1.157f, 0.489f)
            arcToRelative(1.844f, 1.844f, 0.0f, false, true, -1.427f, -0.535f)
            arcToRelative(1.67f, 1.67f, 0.0f, false, true, 1.302f, -0.594f)
            arcToRelative(1.675f, 1.675f, 0.0f, false, true, 1.282f, 0.641f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.923f, 6.25f)
            curveToRelative(0.0f, 0.205f, -0.047f, 0.397f, -0.125f, 0.569f)
            arcToRelative(1.844f, 1.844f, 0.0f, false, true, -1.428f, -0.535f)
            arcToRelative(1.67f, 1.67f, 0.0f, false, true, 1.302f, -0.594f)
            curveToRelative(0.046f, 0.0f, 0.092f, 0.0f, 0.132f, 0.006f)
            curveToRelative(0.08f, 0.165f, 0.12f, 0.356f, 0.12f, 0.555f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(34.844f, 25.976f)
            horizontalLineToRelative(-6.687f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.058f, -0.025f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.024f, -0.06f)
            lineToRelative(0.095f, -5.913f)
            curveToRelative(0.0f, -0.046f, 0.032f, -0.083f, 0.084f, -0.082f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.081f, 0.085f)
            lineToRelative(-0.094f, 5.83f)
            horizontalLineToRelative(6.604f)
            curveToRelative(0.046f, 0.0f, 0.082f, 0.036f, 0.082f, 0.083f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.083f, 0.082f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(13.049f, 36.538f)
            arcToRelative(0.07f, 0.07f, 0.0f, false, true, -0.025f, -0.004f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.054f, -0.103f)
            curveToRelative(0.318f, -1.021f, 1.258f, -1.59f, 1.997f, -1.686f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.092f, 0.07f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.07f, 0.093f)
            curveToRelative(-0.711f, 0.093f, -1.579f, 0.662f, -1.862f, 1.572f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.058f)
            close()
            moveTo(12.085f, 36.364f)
            horizontalLineToRelative(-0.01f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.072f, -0.093f)
            curveToRelative(0.062f, -0.485f, 0.632f, -1.608f, 1.5f, -2.025f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.11f, 0.038f)
            curveToRelative(0.02f, 0.041f, 0.003f, 0.09f, -0.038f, 0.11f)
            curveToRelative(-0.802f, 0.387f, -1.352f, 1.456f, -1.41f, 1.898f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.072f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(53.591f, 79.073f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.064f, -0.031f)
            arcToRelative(1.81f, 1.81f, 0.0f, false, false, -1.552f, -0.665f)
            horizontalLineToRelative(-0.008f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.006f, -0.165f)
            arcToRelative(1.967f, 1.967f, 0.0f, false, true, 1.696f, 0.726f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.013f, 0.116f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.053f, 0.019f)
            close()
            moveTo(52.78f, 78.04f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.078f, -0.109f)
            curveToRelative(0.114f, -0.324f, 0.352f, -0.772f, 0.628f, -1.291f)
            curveToRelative(0.667f, -1.254f, 1.58f, -2.971f, 1.258f, -4.056f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.056f, -0.103f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.103f, 0.056f)
            curveToRelative(0.342f, 1.148f, -0.591f, 2.9f, -1.27f, 4.18f)
            curveToRelative(-0.274f, 0.513f, -0.509f, 0.955f, -0.618f, 1.267f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.079f, 0.056f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF300A08)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.663f, 7.255f)
            horizontalLineToRelative(-0.015f)
            arcToRelative(2.866f, 2.866f, 0.0f, false, true, -1.053f, -0.423f)
            arcToRelative(0.392f, 0.392f, 0.0f, false, true, -0.038f, -0.628f)
            curveToRelative(0.368f, -0.31f, 0.814f, -0.526f, 1.287f, -0.621f)
            curveToRelative(0.044f, -0.013f, 0.088f, 0.02f, 0.096f, 0.064f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.064f, 0.097f)
            curveToRelative(-0.447f, 0.09f, -0.865f, 0.293f, -1.213f, 0.586f)
            arcToRelative(0.225f, 0.225f, 0.0f, false, false, -0.082f, 0.188f)
            arcToRelative(0.225f, 0.225f, 0.0f, false, false, 0.103f, 0.176f)
            curveToRelative(0.303f, 0.198f, 0.636f, 0.332f, 0.993f, 0.397f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.067f, 0.097f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.081f, 0.067f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(38.187f, 55.608f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.081f, -0.066f)
            curveToRelative(-0.7f, -3.545f, -0.93f, -11.755f, -0.93f, -14.184f)
            curveToRelative(0.0f, -0.046f, 0.037f, -0.083f, 0.083f, -0.083f)
            curveToRelative(0.045f, 0.0f, 0.082f, 0.037f, 0.082f, 0.083f)
            curveToRelative(0.0f, 2.426f, 0.227f, 10.62f, 0.927f, 14.152f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.066f, 0.097f)
            lineToRelative(-0.015f, 0.001f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(26.684f, 25.976f)
            horizontalLineToRelative(-1.66f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.082f, -0.082f)
            curveToRelative(0.0f, -0.046f, 0.037f, -0.083f, 0.082f, -0.083f)
            horizontalLineToRelative(1.66f)
            curveToRelative(0.046f, 0.0f, 0.083f, 0.036f, 0.083f, 0.083f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.082f, 0.082f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.396f, 39.205f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.052f, -0.02f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.03f, -0.064f)
            lineToRelative(-0.01f, -2.676f)
            curveToRelative(0.0f, -0.044f, 0.036f, -0.082f, 0.081f, -0.082f)
            curveToRelative(0.045f, 0.0f, 0.082f, 0.037f, 0.082f, 0.082f)
            lineToRelative(0.01f, 2.576f)
            lineToRelative(11.585f, -2.293f)
            curveToRelative(0.049f, -0.007f, 0.088f, 0.02f, 0.097f, 0.065f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.065f, 0.098f)
            lineTo(26.41f, 39.204f)
            horizontalLineToRelative(-0.014f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(39.871f, 38.467f)
            curveToRelative(-1.307f, -0.468f, -3.302f, -0.812f, -6.301f, -0.614f)
            curveToRelative(-0.726f, 0.046f, -1.513f, 0.126f, -2.364f, 0.244f)
            lineToRelative(2.814f, -0.601f)
            lineToRelative(5.284f, -1.123f)
            curveToRelative(0.158f, 0.68f, 0.35f, 1.374f, 0.567f, 2.094f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(24.303f, 32.314f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.082f, -0.072f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.071f, -0.092f)
            arcToRelative(208.4f, 208.4f, 0.0f, false, false, 3.93f, -0.542f)
            arcToRelative(0.805f, 0.805f, 0.0f, false, false, 0.683f, -0.793f)
            verticalLineTo(26.62f)
            curveToRelative(0.0f, -0.046f, 0.036f, -0.082f, 0.082f, -0.082f)
            curveToRelative(0.045f, 0.0f, 0.082f, 0.036f, 0.082f, 0.082f)
            verticalLineToRelative(4.194f)
            arcToRelative(0.973f, 0.973f, 0.0f, false, true, -0.825f, 0.957f)
            curveToRelative(-0.743f, 0.11f, -2.242f, 0.326f, -3.932f, 0.542f)
            horizontalLineToRelative(-0.01f)
            close()
            moveTo(31.129f, 36.002f)
            horizontalLineToRelative(-6.815f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.082f, -0.084f)
            curveToRelative(0.0f, -0.045f, 0.036f, -0.081f, 0.082f, -0.081f)
            horizontalLineToRelative(6.815f)
            arcToRelative(3.03f, 3.03f, 0.0f, false, false, 3.026f, -3.028f)
            verticalLineToRelative(-4.312f)
            curveToRelative(0.0f, -0.046f, 0.036f, -0.082f, 0.082f, -0.082f)
            curveToRelative(0.046f, 0.0f, 0.083f, 0.036f, 0.083f, 0.082f)
            verticalLineToRelative(4.312f)
            arcToRelative(3.196f, 3.196f, 0.0f, false, true, -3.191f, 3.193f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(29.936f, 9.438f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.005f, -0.165f)
            curveToRelative(0.007f, 0.0f, 0.706f, -0.054f, 1.088f, -0.521f)
            curveToRelative(0.208f, -0.256f, 0.285f, -0.591f, 0.23f, -0.998f)
            curveToRelative(-0.049f, -0.354f, -0.203f, -0.632f, -0.463f, -0.825f)
            curveToRelative(-0.607f, -0.452f, -1.586f, -0.307f, -1.596f, -0.307f)
            arcToRelative(0.074f, 0.074f, 0.0f, false, true, -0.068f, -0.02f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.027f, -0.067f)
            curveToRelative(0.033f, -0.484f, -0.037f, -1.26f, -0.561f, -2.201f)
            curveToRelative(-0.153f, -0.274f, -0.351f, -0.468f, -0.587f, -0.575f)
            curveToRelative(-0.042f, -0.02f, -0.06f, -0.068f, -0.042f, -0.109f)
            curveToRelative(0.02f, -0.042f, 0.069f, -0.057f, 0.11f, -0.042f)
            curveToRelative(0.27f, 0.123f, 0.493f, 0.34f, 0.663f, 0.645f)
            curveToRelative(0.51f, 0.915f, 0.606f, 1.683f, 0.587f, 2.194f)
            curveToRelative(0.28f, -0.028f, 1.07f, -0.06f, 1.62f, 0.348f)
            curveToRelative(0.296f, 0.22f, 0.473f, 0.536f, 0.528f, 0.937f)
            curveToRelative(0.062f, 0.454f, -0.028f, 0.832f, -0.267f, 1.124f)
            curveToRelative(-0.427f, 0.523f, -1.171f, 0.578f, -1.203f, 0.58f)
            curveToRelative(-0.003f, 0.002f, -0.005f, 0.002f, -0.007f, 0.002f)
            close()
            moveTo(25.522f, 4.292f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.077f, -0.054f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.048f, -0.106f)
            curveToRelative(0.176f, -0.066f, 0.383f, -0.154f, 0.584f, -0.241f)
            curveToRelative(0.332f, -0.142f, 0.676f, -0.289f, 0.896f, -0.342f)
            curveToRelative(0.044f, -0.007f, 0.09f, 0.018f, 0.1f, 0.061f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.061f, 0.1f)
            curveToRelative(-0.207f, 0.049f, -0.543f, 0.193f, -0.87f, 0.333f)
            curveToRelative(-0.203f, 0.086f, -0.412f, 0.176f, -0.59f, 0.243f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.03f, 0.006f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(29.61f, 17.644f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.03f, -0.159f)
            curveToRelative(1.22f, -0.482f, 2.805f, -1.446f, 3.383f, -1.857f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.116f, 0.02f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.02f, 0.115f)
            curveToRelative(-0.583f, 0.415f, -2.185f, 1.388f, -3.417f, 1.876f)
            arcToRelative(0.11f, 0.11f, 0.0f, false, true, -0.031f, 0.005f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(26.612f, 46.816f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.082f, -0.076f)
            curveToRelative(-0.101f, -1.414f, -0.181f, -5.382f, -0.207f, -6.686f)
            lineToRelative(-0.003f, -0.149f)
            curveToRelative(0.0f, -0.046f, 0.035f, -0.084f, 0.08f, -0.084f)
            curveToRelative(0.056f, -0.006f, 0.085f, 0.035f, 0.085f, 0.081f)
            lineToRelative(0.003f, 0.15f)
            curveToRelative(0.07f, 3.495f, 0.14f, 5.742f, 0.207f, 6.677f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.077f, 0.089f)
            curveToRelative(-0.002f, -0.002f, -0.004f, -0.002f, -0.006f, -0.002f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.603f, 44.392f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.062f, -0.136f)
            lineToRelative(3.078f, -3.543f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.116f, -0.008f)
            curveToRelative(0.034f, 0.03f, 0.039f, 0.083f, 0.009f, 0.117f)
            lineToRelative(-3.079f, 3.543f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.062f, 0.027f)
            close()
            moveTo(51.932f, 69.354f)
            horizontalLineToRelative(-0.013f)
            curveToRelative(-2.25f, -0.383f, -6.151f, -2.294f, -7.584f, -3.347f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.017f, -0.115f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.115f, -0.018f)
            curveToRelative(1.419f, 1.043f, 5.286f, 2.938f, 7.512f, 3.317f)
            curveToRelative(0.045f, 0.008f, 0.076f, 0.051f, 0.067f, 0.095f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.08f, 0.068f)
            close()
            moveTo(32.79f, 74.456f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.062f, -0.028f)
            curveToRelative(-1.428f, -1.63f, -3.442f, -5.992f, -4.068f, -8.806f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.063f, -0.099f)
            curveToRelative(0.05f, -0.01f, 0.09f, 0.019f, 0.099f, 0.063f)
            curveToRelative(0.62f, 2.793f, 2.616f, 7.119f, 4.03f, 8.734f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.008f, 0.117f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.054f, 0.02f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(37.106f, 36.395f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.079f, -0.059f)
            arcToRelative(8.491f, 8.491f, 0.0f, false, true, 3.432f, -9.467f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.115f, 0.023f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.023f, 0.114f)
            arcToRelative(8.328f, 8.328f, 0.0f, false, false, -3.366f, 9.285f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.056f, 0.102f)
            lineToRelative(-0.023f, 0.002f)
            close()
            moveTo(35.687f, 22.942f)
            arcToRelative(7.474f, 7.474f, 0.0f, false, true, -6.938f, -4.703f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.046f, -0.108f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.108f, 0.046f)
            arcToRelative(7.307f, 7.307f, 0.0f, false, false, 9.256f, 4.17f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, 0.106f, 0.05f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.05f, 0.106f)
            curveToRelative(-0.836f, 0.297f, -1.69f, 0.44f, -2.528f, 0.44f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(11.216f, 56.827f)
            arcToRelative(0.983f, 0.983f, 0.0f, false, false, -1.655f, 0.0f)
            lineTo(7.43f, 60.143f)
            arcToRelative(0.983f, 0.983f, 0.0f, false, false, 0.827f, 1.515f)
            horizontalLineToRelative(4.263f)
            arcToRelative(0.983f, 0.983f, 0.0f, false, false, 0.827f, -1.515f)
            lineToRelative(-2.131f, -3.316f)
            close()
            moveTo(10.388f, 72.254f)
            arcToRelative(2.95f, 2.95f, 0.0f, true, false, 0.0f, -5.899f)
            arcToRelative(2.95f, 2.95f, 0.0f, false, false, 0.0f, 5.899f)
            close()
            moveTo(7.439f, 46.692f)
            curveToRelative(0.0f, -0.543f, 0.44f, -0.983f, 0.983f, -0.983f)
            horizontalLineToRelative(3.933f)
            curveToRelative(0.543f, 0.0f, 0.983f, 0.44f, 0.983f, 0.983f)
            verticalLineToRelative(3.933f)
            curveToRelative(0.0f, 0.543f, -0.44f, 0.983f, -0.983f, 0.983f)
            horizontalLineTo(8.422f)
            arcToRelative(0.983f, 0.983f, 0.0f, false, true, -0.983f, -0.983f)
            verticalLineToRelative(-3.933f)
            close()
        }
    }
        .build()
}
