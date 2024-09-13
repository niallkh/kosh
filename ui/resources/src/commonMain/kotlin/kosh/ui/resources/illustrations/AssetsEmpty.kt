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
        name = "AssetsEmpty", defaultWidth = 100.0.dp, defaultHeight =
        79.0.dp, viewportWidth = 100.0f, viewportHeight = 79.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(100.0f, 58.271f)
            curveToRelative(0.0f, -11.281f, -9.145f, -20.426f, -20.426f, -20.426f)
            curveToRelative(-11.281f, 0.0f, -20.426f, 9.145f, -20.426f, 20.426f)
            curveToRelative(0.0f, 11.281f, 9.145f, 20.426f, 20.426f, 20.426f)
            curveToRelative(11.28f, 0.0f, 20.426f, -9.145f, 20.426f, -20.426f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(79.57f, 75.827f)
            curveToRelative(-9.681f, 0.0f, -17.557f, -7.876f, -17.557f, -17.557f)
            curveToRelative(0.0f, -9.681f, 7.876f, -17.557f, 17.557f, -17.557f)
            curveToRelative(9.68f, 0.0f, 17.557f, 7.876f, 17.557f, 17.557f)
            curveToRelative(0.0f, 9.68f, -7.875f, 17.557f, -17.557f, 17.557f)
            close()
            moveTo(79.57f, 40.883f)
            curveToRelative(-9.587f, 0.0f, -17.387f, 7.8f, -17.387f, 17.387f)
            reflectiveCurveToRelative(7.8f, 17.387f, 17.387f, 17.387f)
            curveToRelative(9.586f, 0.0f, 17.387f, -7.8f, 17.387f, -17.386f)
            curveToRelative(0.0f, -9.587f, -7.8f, -17.388f, -17.387f, -17.388f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(61.322f, 6.678f)
            curveToRelative(2.567f, 0.764f, 3.046f, -2.676f, 0.76f, -4.266f)
            curveToRelative(-2.286f, -1.59f, -6.599f, -4.028f, -7.295f, -0.893f)
            curveToRelative(-2.766f, -0.936f, -4.138f, 2.536f, -2.2f, 4.752f)
            curveToRelative(-3.745f, 0.387f, -0.873f, 3.94f, 1.83f, 5.784f)
            curveToRelative(1.981f, -1.89f, 6.905f, -5.377f, 6.905f, -5.377f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.322f, 6.678f)
            curveToRelative(-0.811f, 3.528f, -0.484f, 8.993f, -4.421f, 7.124f)
            lineToRelative(-0.362f, 1.092f)
            lineToRelative(-0.929f, 2.804f)
            horizontalLineToRelative(-5.288f)
            lineToRelative(4.203f, -7.575f)
            reflectiveCurveToRelative(-1.747f, -1.003f, -0.983f, -2.443f)
            curveToRelative(0.764f, -1.432f, 1.938f, 0.13f, 1.938f, 0.13f)
            reflectiveCurveToRelative(0.73f, -1.064f, 0.56f, -2.6f)
            arcToRelative(0.373f, 0.373f, 0.0f, false, true, 0.559f, -0.368f)
            curveToRelative(0.662f, 0.39f, 1.289f, 0.472f, 1.74f, 0.294f)
            curveToRelative(0.286f, -0.116f, 0.614f, -0.068f, 0.778f, 0.19f)
            curveToRelative(0.656f, 1.039f, 1.87f, 1.468f, 2.206f, 1.352f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(55.092f, 8.465f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.029f, -0.004f)
            lineToRelative(-1.067f, -0.37f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.053f, -0.109f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.108f, -0.053f)
            lineToRelative(1.068f, 0.37f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.053f, 0.109f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.08f, 0.057f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.969f, 7.027f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.053f, -0.019f)
            arcToRelative(3.033f, 3.033f, 0.0f, false, false, -2.647f, -0.55f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.104f, -0.06f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.06f, -0.106f)
            arcToRelative(3.207f, 3.207f, 0.0f, false, true, 2.798f, 0.583f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.054f, 0.152f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFB3261E)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(57.246f, 10.796f)
            arcToRelative(6.016f, 6.016f, 0.0f, false, false, 2.047f, 0.85f)
            arcToRelative(1.164f, 1.164f, 0.0f, false, true, -2.047f, -0.85f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(60.519f, 9.186f)
            lineToRelative(0.908f, 1.469f)
            arcToRelative(0.265f, 0.265f, 0.0f, false, true, -0.302f, 0.393f)
            lineToRelative(-1.374f, -0.41f)
            lineToRelative(0.768f, -1.452f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.515f, 9.43f)
            horizontalLineTo(57.7f)
            arcToRelative(0.455f, 0.455f, 0.0f, false, true, -0.454f, -0.454f)
            verticalLineTo(8.12f)
            curveToRelative(0.0f, -0.251f, 0.204f, -0.454f, 0.454f, -0.454f)
            horizontalLineToRelative(1.814f)
            curveToRelative(0.25f, 0.0f, 0.454f, 0.204f, 0.454f, 0.454f)
            verticalLineToRelative(0.856f)
            curveToRelative(0.0f, 0.25f, -0.204f, 0.454f, -0.454f, 0.454f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(59.294f, 8.437f)
            curveToRelative(0.0f, 0.287f, -0.061f, 0.52f, -0.138f, 0.52f)
            curveToRelative(-0.077f, 0.0f, -0.139f, -0.233f, -0.139f, -0.52f)
            curveToRelative(0.0f, -0.287f, 0.062f, -0.52f, 0.139f, -0.52f)
            curveToRelative(0.076f, 0.0f, 0.139f, 0.233f, 0.139f, 0.52f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(50.858f, 10.34f)
            arcToRelative(0.219f, 0.219f, 0.0f, false, true, 0.016f, -0.292f)
            lineToRelative(0.237f, -0.237f)
            arcToRelative(0.221f, 0.221f, 0.0f, false, true, 0.165f, -0.064f)
            curveToRelative(0.062f, 0.002f, 0.118f, 0.03f, 0.158f, 0.078f)
            lineToRelative(0.994f, 1.18f)
            arcToRelative(0.219f, 0.219f, 0.0f, false, true, -0.022f, 0.303f)
            lineToRelative(-0.278f, 0.249f)
            arcToRelative(0.222f, 0.222f, 0.0f, false, true, -0.165f, 0.055f)
            arcToRelative(0.223f, 0.223f, 0.0f, false, true, -0.153f, -0.081f)
            lineToRelative(-0.952f, -1.191f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(51.151f, 11.77f)
            arcToRelative(10.91f, 10.91f, 0.0f, false, true, -6.678f, 5.258f)
            arcToRelative(0.26f, 0.26f, 0.0f, false, true, -0.315f, -0.317f)
            arcToRelative(8.35f, 8.35f, 0.0f, false, true, 6.251f, -5.86f)
            arcToRelative(0.26f, 0.26f, 0.0f, false, true, 0.252f, 0.086f)
            lineToRelative(0.461f, 0.542f)
            arcToRelative(0.26f, 0.26f, 0.0f, false, true, 0.03f, 0.29f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.203f, 11.144f)
            arcToRelative(0.326f, 0.326f, 0.0f, false, true, -0.101f, -0.015f)
            lineToRelative(-1.375f, -0.41f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.058f, -0.106f)
            curveToRelative(0.013f, -0.044f, 0.062f, -0.068f, 0.107f, -0.057f)
            lineToRelative(1.374f, 0.41f)
            arcToRelative(0.175f, 0.175f, 0.0f, false, false, 0.194f, -0.062f)
            arcToRelative(0.176f, 0.176f, 0.0f, false, false, 0.011f, -0.204f)
            lineToRelative(-0.908f, -1.47f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.028f, -0.116f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.119f, 0.028f)
            lineToRelative(0.907f, 1.47f)
            curveToRelative(0.078f, 0.124f, 0.07f, 0.28f, -0.02f, 0.396f)
            arcToRelative(0.354f, 0.354f, 0.0f, false, true, -0.279f, 0.136f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(74.636f, 19.428f)
            curveToRelative(1.16f, -1.175f, 4.411f, -3.345f, 4.842f, -2.658f)
            curveToRelative(0.431f, 0.687f, -0.448f, 0.791f, -1.106f, 1.12f)
            curveToRelative(-0.724f, 0.363f, -2.539f, 1.534f, -3.248f, 2.565f)
            curveToRelative(-0.709f, 1.033f, -0.488f, -1.027f, -0.488f, -1.027f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(57.007f, 21.172f)
            lineToRelative(-4.065f, 5.693f)
            reflectiveCurveToRelative(3.621f, 4.502f, 7.41f, 6.453f)
            curveToRelative(1.033f, 0.532f, 2.236f, 0.613f, 3.287f, 0.184f)
            curveToRelative(3.137f, -1.282f, 7.086f, -3.924f, 11.17f, -7.744f)
            curveToRelative(1.059f, -0.989f, 1.567f, -2.408f, 1.547f, -3.878f)
            curveToRelative(-0.013f, -0.905f, 0.194f, -1.664f, 1.033f, -2.3f)
            curveToRelative(1.44f, -1.088f, 2.656f, -1.075f, 2.44f, -1.923f)
            curveToRelative(-0.217f, -0.848f, -4.546f, 1.019f, -5.356f, 1.733f)
            curveToRelative(-0.811f, 0.714f, -1.504f, 3.488f, -1.504f, 3.488f)
            reflectiveCurveToRelative(-6.178f, 4.85f, -9.771f, 5.928f)
            curveToRelative(0.002f, 0.001f, -3.742f, -3.196f, -6.191f, -7.634f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.92f, 71.64f)
            curveToRelative(-0.088f, -1.14f, 0.247f, -2.471f, 1.584f, -3.754f)
            lineToRelative(-3.14f, -2.702f)
            curveToRelative(-0.558f, 0.682f, -1.118f, 1.398f, -1.664f, 2.135f)
            arcToRelative(59.65f, 59.65f, 0.0f, false, false, -4.675f, 7.329f)
            lineToRelative(5.39f, 4.074f)
            horizontalLineToRelative(9.867f)
            reflectiveCurveToRelative(-1.208f, -3.944f, -6.046f, -3.944f)
            curveToRelative(0.002f, 0.0f, -1.179f, -1.338f, -1.315f, -3.139f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(35.284f, 78.72f)
            horizontalLineToRelative(-9.867f)
            lineToRelative(-5.39f, -4.073f)
            curveToRelative(0.252f, -0.464f, 0.504f, -0.915f, 0.763f, -1.364f)
            arcToRelative(59.304f, 59.304f, 0.0f, false, true, 3.91f, -5.964f)
            curveToRelative(0.395f, 2.266f, 1.978f, 3.719f, 3.22f, 4.32f)
            curveToRelative(0.137f, 1.8f, 1.317f, 3.138f, 1.317f, 3.138f)
            curveToRelative(2.866f, 0.0f, 4.457f, 1.385f, 5.282f, 2.518f)
            curveToRelative(0.567f, 0.77f, 0.765f, 1.426f, 0.765f, 1.426f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(35.284f, 78.721f)
            horizontalLineToRelative(-9.867f)
            lineToRelative(-5.39f, -4.074f)
            curveToRelative(0.252f, -0.463f, 0.504f, -0.914f, 0.763f, -1.364f)
            lineToRelative(5.084f, 4.012f)
            horizontalLineToRelative(8.645f)
            curveToRelative(0.567f, 0.77f, 0.765f, 1.426f, 0.765f, 1.426f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.352f, 77.084f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.085f, -0.08f)
            arcToRelative(1.86f, 1.86f, 0.0f, false, true, 0.799f, -1.667f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.118f, 0.023f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.022f, 0.118f)
            arcToRelative(1.69f, 1.69f, 0.0f, false, false, -0.726f, 1.513f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.079f, 0.092f)
            horizontalLineToRelative(-0.005f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(30.477f, 76.288f)
            lineToRelative(-0.02f, -0.002f)
            arcToRelative(3.874f, 3.874f, 0.0f, false, false, -1.783f, 0.0f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.075f, -0.019f)
            arcToRelative(6.126f, 6.126f, 0.0f, false, true, -2.137f, -5.025f)
            curveToRelative(0.004f, -0.046f, 0.041f, -0.073f, 0.091f, -0.08f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.08f, 0.091f)
            arcToRelative(5.953f, 5.953f, 0.0f, false, false, 2.045f, 4.856f)
            arcToRelative(4.097f, 4.097f, 0.0f, false, true, 1.82f, 0.009f)
            curveToRelative(0.046f, 0.01f, 0.075f, 0.057f, 0.063f, 0.103f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.084f, 0.067f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(7.895f, 71.64f)
            curveToRelative(-0.088f, -1.14f, 0.932f, -3.31f, 2.27f, -4.594f)
            lineTo(6.34f, 65.184f)
            curveToRelative(-0.56f, 0.682f, -1.12f, 1.398f, -1.666f, 2.135f)
            arcTo(59.66f, 59.66f, 0.0f, false, false, 0.0f, 74.648f)
            lineToRelative(5.39f, 4.074f)
            horizontalLineToRelative(9.868f)
            reflectiveCurveToRelative(-1.208f, -3.944f, -6.046f, -3.944f)
            curveToRelative(0.0f, 0.0f, -1.18f, -1.338f, -1.317f, -3.139f)
            close()
        }
        path(
            fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.258f, 78.72f)
            horizontalLineTo(5.39f)
            lineTo(0.0f, 74.648f)
            curveToRelative(0.252f, -0.464f, 0.505f, -0.915f, 0.764f, -1.364f)
            arcToRelative(59.298f, 59.298f, 0.0f, false, true, 3.91f, -5.964f)
            curveToRelative(0.395f, 2.266f, 1.978f, 3.719f, 3.22f, 4.32f)
            curveToRelative(0.137f, 1.8f, 1.317f, 3.138f, 1.317f, 3.138f)
            curveToRelative(2.866f, 0.0f, 4.455f, 1.385f, 5.28f, 2.518f)
            curveToRelative(0.57f, 0.77f, 0.767f, 1.426f, 0.767f, 1.426f)
            close()
        }
        path(
            fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.258f, 78.721f)
            horizontalLineTo(5.39f)
            lineTo(0.0f, 74.647f)
            curveToRelative(0.252f, -0.463f, 0.505f, -0.914f, 0.764f, -1.364f)
            lineToRelative(5.084f, 4.012f)
            horizontalLineToRelative(8.645f)
            curveToRelative(0.568f, 0.77f, 0.765f, 1.426f, 0.765f, 1.426f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(10.326f, 77.084f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.085f, -0.08f)
            arcToRelative(1.86f, 1.86f, 0.0f, false, true, 0.799f, -1.667f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, 0.119f, 0.023f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.022f, 0.118f)
            arcToRelative(1.69f, 1.69f, 0.0f, false, false, -0.726f, 1.513f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.079f, 0.092f)
            horizontalLineToRelative(-0.006f)
            close()
        }
        path(
            fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(10.451f, 76.288f)
            lineToRelative(-0.02f, -0.002f)
            arcToRelative(3.874f, 3.874f, 0.0f, false, false, -1.783f, 0.0f)
            arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.074f, -0.019f)
            arcToRelative(6.126f, 6.126f, 0.0f, false, true, -2.137f, -5.025f)
            curveToRelative(0.003f, -0.046f, 0.042f, -0.073f, 0.091f, -0.08f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.08f, 0.091f)
            arcToRelative(5.953f, 5.953f, 0.0f, false, false, 2.045f, 4.856f)
            arcToRelative(4.097f, 4.097f, 0.0f, false, true, 1.82f, 0.009f)
            curveToRelative(0.045f, 0.01f, 0.075f, 0.057f, 0.063f, 0.103f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.085f, 0.067f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(39.5f, 44.009f)
            lineToRelative(-2.75f, 2.845f)
            lineToRelative(-19.549f, 20.191f)
            horizontalLineTo(4.884f)
            curveToRelative(6.278f, -9.177f, 15.668f, -16.833f, 15.668f, -16.833f)
            lineToRelative(0.852f, -1.802f)
            horizontalLineToRelative(1.236f)
            curveToRelative(5.425f, -6.243f, 11.436f, -11.566f, 11.436f, -11.566f)
            lineToRelative(1.808f, 2.388f)
            lineToRelative(3.617f, 4.777f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(39.501f, 44.01f)
            lineToRelative(-2.75f, 2.845f)
            curveToRelative(-1.208f, -1.693f, -2.135f, -3.808f, -2.872f, -5.568f)
            lineToRelative(2.007f, -2.055f)
            lineTo(39.5f, 44.01f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(7.63f, 64.911f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.067f, -0.032f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.015f, -0.12f)
            curveToRelative(4.737f, -3.7f, 9.375f, -5.203f, 13.469f, -6.529f)
            curveToRelative(2.14f, -0.693f, 4.164f, -1.349f, 6.037f, -2.251f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.114f, 0.039f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.04f, 0.113f)
            curveToRelative(-1.883f, 0.908f, -3.912f, 1.566f, -6.06f, 2.262f)
            curveToRelative(-4.078f, 1.32f, -8.702f, 2.819f, -13.415f, 6.5f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.053f, 0.018f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.51f, 49.36f)
            curveToRelative(-1.644f, -6.503f, -4.434f, -10.255f, -4.434f, -10.255f)
            lineToRelative(-13.197f, -6.858f)
            curveToRelative(-2.82f, 3.711f, -2.463f, 8.605f, 7.749f, 18.033f)
            horizontalLineToRelative(-0.986f)
            verticalLineToRelative(2.14f)
            curveToRelative(-2.97f, 1.86f, -8.74f, 5.972f, -14.276f, 12.763f)
            curveToRelative(3.145f, 4.285f, 7.383f, 6.564f, 7.383f, 6.564f)
            curveToRelative(2.394f, -1.57f, 10.405f, -9.109f, 16.015f, -15.374f)
            curveToRelative(1.705f, -1.902f, 2.374f, -4.536f, 1.746f, -7.013f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(30.653f, 66.699f)
            lineToRelative(-0.307f, -0.002f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.084f, -0.088f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.086f, -0.084f)
            curveToRelative(3.335f, 0.07f, 7.995f, -0.965f, 9.955f, -2.208f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.117f, 0.026f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.026f, 0.118f)
            curveToRelative(-1.924f, 1.22f, -6.4f, 2.238f, -9.74f, 2.238f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(47.331f, 36.101f)
            curveToRelative(0.327f, 1.791f, 3.698f, 6.435f, 5.299f, 7.906f)
            curveToRelative(0.0f, 0.0f, -0.719f, -3.854f, -0.49f, -8.624f)
            lineToRelative(-2.286f, -6.958f)
            lineTo(47.33f, 36.1f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(32.879f, 31.681f)
            reflectiveCurveToRelative(2.264f, -2.047f, 3.353f, -2.831f)
            lineToRelative(-0.392f, -1.046f)
            lineToRelative(1.437f, -0.392f)
            reflectiveCurveToRelative(5.139f, -11.28f, 16.768f, -11.28f)
            curveToRelative(4.399f, 0.0f, 3.832f, 6.402f, 2.309f, 8.841f)
            horizontalLineToRelative(-3.092f)
            reflectiveCurveToRelative(-5.27f, 6.185f, -6.49f, 18.467f)
            curveToRelative(0.0f, 0.0f, -7.273f, -2.482f, -13.893f, -11.759f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(73.634f, 35.764f)
            curveToRelative(1.85f, 0.0f, 6.144f, 0.86f, 5.935f, 1.72f)
            curveToRelative(-0.208f, 0.86f, -0.984f, 0.258f, -1.765f, 0.0f)
            curveToRelative(-0.86f, -0.282f, -3.223f, -0.794f, -4.606f, -0.555f)
            curveToRelative(-1.382f, 0.239f, 0.436f, -1.165f, 0.436f, -1.165f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFDBD1)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(55.352f, 24.974f)
            horizontalLineToRelative(-6.881f)
            reflectiveCurveToRelative(1.396f, 5.918f, 2.823f, 10.347f)
            curveToRelative(0.39f, 1.207f, 1.271f, 2.202f, 2.443f, 2.688f)
            curveToRelative(3.495f, 1.452f, 8.714f, 2.49f, 14.98f, 2.735f)
            curveToRelative(1.623f, 0.063f, 3.157f, -0.629f, 4.314f, -1.768f)
            curveToRelative(0.712f, -0.703f, 1.48f, -1.123f, 2.65f, -0.957f)
            curveToRelative(2.003f, 0.283f, 2.95f, 1.236f, 3.457f, 0.42f)
            curveToRelative(0.506f, -0.818f, -4.394f, -2.745f, -5.603f, -2.827f)
            curveToRelative(-1.208f, -0.08f, -3.969f, 1.503f, -3.969f, 1.503f)
            reflectiveCurveToRelative(-8.738f, -1.078f, -12.43f, -3.038f)
            curveToRelative(0.0f, 0.0f, -1.784f, -4.095f, -1.784f, -9.103f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.268f, 17.959f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.039f, -0.16f)
            curveToRelative(2.281f, -1.175f, 5.03f, -2.228f, 9.669f, -1.992f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.08f, 0.09f)
            curveToRelative(-0.001f, 0.046f, -0.045f, 0.086f, -0.089f, 0.08f)
            curveToRelative(-4.595f, -0.236f, -7.321f, 0.808f, -9.582f, 1.972f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.04f, 0.01f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(35.862f, 25.865f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.066f, -0.032f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, 0.012f, -0.12f)
            curveToRelative(1.198f, -0.975f, 2.32f, -1.646f, 3.53f, -2.113f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, 0.11f, 0.049f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.049f, 0.11f)
            curveToRelative(-1.193f, 0.46f, -2.299f, 1.122f, -3.483f, 2.085f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.054f, 0.02f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.898f, 18.091f)
            curveToRelative(-2.885f, 1.826f, -5.035f, 4.143f, -7.17f, 7.257f)
            curveToRelative(-1.112f, 1.623f, -3.05f, 2.564f, -4.982f, 2.188f)
            curveToRelative(-1.906f, -0.37f, -2.67f, -1.794f, -2.525f, -3.739f)
            curveToRelative(0.217f, -2.918f, 6.358f, -9.887f, 14.677f, -5.706f)
            close()
        }
        path(
            fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.898f, 18.091f)
            curveToRelative(-2.287f, -2.003f, -6.36f, -2.657f, -9.56f, -1.699f)
            lineToRelative(2.221f, 2.156f)
            reflectiveCurveToRelative(2.873f, -2.068f, 7.339f, -0.457f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.558f, 18.633f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.06f, -0.024f)
            lineToRelative(-0.893f, -0.868f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.003f, -0.12f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.12f, -0.002f)
            lineToRelative(0.845f, 0.82f)
            curveToRelative(0.188f, -0.123f, 0.817f, -0.5f, 1.817f, -0.768f)
            curveToRelative(1.092f, -0.292f, 2.85f, -0.5f, 5.024f, 0.167f)
            curveToRelative(0.045f, 0.013f, 0.07f, 0.061f, 0.056f, 0.106f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.106f, 0.056f)
            curveToRelative(-4.084f, -1.252f, -6.725f, 0.598f, -6.75f, 0.617f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.05f, 0.016f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.264f, 45.202f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.067f, -0.033f)
            curveToRelative(-1.201f, -1.498f, -2.345f, -3.512f, -3.399f, -5.985f)
            arcToRelative(0.085f, 0.085f, 0.0f, true, true, 0.157f, -0.066f)
            curveToRelative(1.048f, 2.458f, 2.183f, 4.458f, 3.374f, 5.945f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.013f, 0.12f)
            arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.052f, 0.019f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(35.088f, 34.063f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.076f, -0.123f)
            arcToRelative(14.288f, 14.288f, 0.0f, false, true, 13.253f, -7.887f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.082f, 0.087f)
            curveToRelative(0.0f, 0.048f, -0.044f, 0.077f, -0.087f, 0.083f)
            arcToRelative(14.117f, 14.117f, 0.0f, false, false, -13.095f, 7.793f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.077f, 0.047f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(41.676f, 40.53f)
            horizontalLineToRelative(-0.009f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.076f, -0.094f)
            arcToRelative(11.17f, 11.17f, 0.0f, false, true, 7.978f, -9.579f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, 0.106f, 0.059f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.057f, 0.106f)
            arcToRelative(10.999f, 10.999f, 0.0f, false, false, -7.856f, 9.433f)
            arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.086f, 0.076f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(51.883f, 36.593f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.07f, -0.037f)
            arcToRelative(4.59f, 4.59f, 0.0f, false, true, -0.6f, -1.21f)
            curveToRelative(-1.22f, -3.785f, -2.41f, -8.623f, -2.745f, -10.016f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.063f, -0.103f)
            curveToRelative(0.047f, -0.014f, 0.092f, 0.016f, 0.103f, 0.063f)
            curveToRelative(0.335f, 1.391f, 1.523f, 6.224f, 2.741f, 10.004f)
            curveToRelative(0.135f, 0.416f, 0.328f, 0.808f, 0.578f, 1.165f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.02f, 0.118f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.05f, 0.016f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(55.79f, 29.511f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.084f, -0.07f)
            arcToRelative(24.25f, 24.25f, 0.0f, false, true, -0.434f, -3.985f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.084f, -0.088f)
            curveToRelative(0.042f, -0.011f, 0.087f, 0.036f, 0.087f, 0.083f)
            curveToRelative(0.029f, 1.28f, 0.173f, 2.613f, 0.43f, 3.958f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.069f, 0.1f)
            curveToRelative(-0.004f, 0.002f, -0.01f, 0.002f, -0.015f, 0.002f)
            close()
        }
        path(
            fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(68.927f, 40.833f)
            curveToRelative(-0.07f, 0.0f, -0.141f, -0.001f, -0.212f, -0.004f)
            arcToRelative(70.913f, 70.913f, 0.0f, false, true, -1.645f, -0.084f)
            arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.08f, -0.09f)
            curveToRelative(0.003f, -0.047f, 0.046f, -0.078f, 0.09f, -0.081f)
            curveToRelative(0.536f, 0.034f, 1.088f, 0.062f, 1.64f, 0.084f)
            curveToRelative(1.545f, 0.057f, 3.05f, -0.56f, 4.251f, -1.744f)
            curveToRelative(0.85f, -0.837f, 1.662f, -1.129f, 2.721f, -0.98f)
            curveToRelative(0.788f, 0.111f, 1.41f, 0.324f, 1.911f, 0.494f)
            curveToRelative(0.825f, 0.28f, 1.2f, 0.386f, 1.462f, -0.036f)
            arcToRelative(0.085f, 0.085f, 0.0f, true, true, 0.145f, 0.089f)
            curveToRelative(-0.344f, 0.557f, -0.869f, 0.378f, -1.662f, 0.107f)
            curveToRelative(-0.493f, -0.169f, -1.107f, -0.378f, -1.88f, -0.486f)
            curveToRelative(-1.0f, -0.141f, -1.77f, 0.137f, -2.578f, 0.932f)
            curveToRelative(-1.18f, 1.166f, -2.65f, 1.799f, -4.163f, 1.799f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(40.123f, 23.505f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.081f, -0.061f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.056f, -0.106f)
            curveToRelative(0.075f, -0.024f, 0.15f, -0.046f, 0.226f, -0.066f)
            curveToRelative(1.972f, -0.562f, 4.322f, -0.671f, 7.393f, -0.344f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.076f, 0.095f)
            curveToRelative(-0.005f, 0.046f, -0.047f, 0.085f, -0.094f, 0.075f)
            curveToRelative(-3.05f, -0.326f, -5.38f, -0.217f, -7.33f, 0.338f)
            lineToRelative(-0.222f, 0.065f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.024f, 0.004f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(55.823f, 25.058f)
            horizontalLineToRelative(-8.49f)
            arcToRelative(0.084f, 0.084f, 0.0f, false, true, -0.07f, -0.037f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.008f, -0.08f)
            lineToRelative(1.862f, -4.551f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, 0.112f, -0.047f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.047f, 0.11f)
            lineToRelative(-1.814f, 4.435f)
            horizontalLineToRelative(8.363f)
            curveToRelative(0.047f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
            arcToRelative(0.089f, 0.089f, 0.0f, false, true, -0.088f, 0.084f)
            close()
        }
        path(
            fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(46.772f, 43.525f)
            arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.03f, -0.006f)
            curveToRelative(-3.774f, -1.438f, -9.503f, -5.476f, -12.685f, -10.13f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.022f, -0.118f)
            arcToRelative(0.085f, 0.085f, 0.0f, false, true, 0.118f, 0.023f)
            curveToRelative(3.134f, 4.582f, 8.752f, 8.565f, 12.502f, 10.027f)
            curveToRelative(0.058f, -0.48f, 0.292f, -2.4f, 0.41f, -3.077f)
            curveToRelative(0.01f, -0.046f, 0.049f, -0.075f, 0.1f, -0.07f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.069f, 0.099f)
            curveToRelative(-0.137f, 0.77f, -0.42f, 3.153f, -0.423f, 3.177f)
            arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.04f, 0.063f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.043f, 0.012f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(82.616f, 49.424f)
            lineToRelative(-2.0f, 6.508f)
            lineToRelative(4.939f, 4.208f)
            lineToRelative(-2.94f, -10.716f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(82.616f, 49.424f)
            lineToRelative(-8.45f, 7.217f)
            lineToRelative(6.45f, -0.709f)
            lineToRelative(2.0f, -6.508f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(78.565f, 62.608f)
            lineToRelative(-1.36f, 4.423f)
            lineToRelative(8.039f, -5.866f)
            lineToRelative(-6.68f, 1.443f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.7f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(77.206f, 67.03f)
            lineToRelative(1.359f, -4.422f)
            lineToRelative(-4.714f, -4.943f)
            lineToRelative(3.355f, 9.366f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.2f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(78.88f, 61.584f)
            lineToRelative(6.675f, -1.444f)
            lineToRelative(-4.94f, -4.206f)
            lineToRelative(-1.736f, 5.65f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, fillAlpha = 0.5f,
            strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveToRelative(74.166f, 56.64f)
            lineToRelative(4.713f, 4.944f)
            lineToRelative(1.736f, -5.65f)
            lineToRelative(-6.45f, 0.707f)
            close()
        }
    }
        .build()
}
