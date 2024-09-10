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
public fun ActivityEmpty(
    upperClothes: Color = MaterialTheme.colorScheme.primary,
    downClothes: Color = MaterialTheme.colorScheme.secondary,
    details: Color = MaterialTheme.colorScheme.tertiary,
    secondaryDetails: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    tertiaryDetails: Color = MaterialTheme.colorScheme.background,
): ImageVector = remember(upperClothes, downClothes, details, secondaryDetails, tertiaryDetails) {
    Builder(
        name = "ActivityEmpty", defaultWidth = 66.0.dp, defaultHeight =
        73.0.dp, viewportWidth = 66.0f, viewportHeight = 73.0f
    ).apply {
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(64.112f, 67.615f)
            horizontalLineToRelative(-30.39f)
            verticalLineToRelative(3.83f)
            horizontalLineToRelative(30.39f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(35.61f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.08f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.158f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(37.652f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(39.695f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(41.738f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(43.78f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(45.823f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(47.865f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(49.908f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(51.951f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(53.993f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(56.036f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(58.08f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(60.121f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(62.164f, 70.594f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            reflectiveCurveToRelative(0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(61.635f, 59.953f)
            horizontalLineTo(31.244f)
            verticalLineToRelative(3.83f)
            horizontalLineToRelative(30.39f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(33.131f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(35.174f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(37.217f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(39.259f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(41.302f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(43.344f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(45.387f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(47.43f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(49.473f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.156f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(51.516f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(53.557f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.043f, 0.035f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.044f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(55.6f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(57.643f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.156f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(59.686f, 62.934f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.079f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(60.116f, 48.462f)
            horizontalLineToRelative(-30.39f)
            verticalLineToRelative(3.83f)
            horizontalLineToRelative(30.39f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(31.613f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(33.656f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(35.699f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(37.74f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(39.784f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(41.826f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(43.869f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(45.912f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.156f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(47.955f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.156f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(49.996f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(52.04f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.08f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.158f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(54.082f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(56.125f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(58.168f, 51.441f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(58.517f, 44.63f)
            horizontalLineTo(28.126f)
            verticalLineToRelative(3.831f)
            horizontalLineToRelative(30.391f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.014f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            reflectiveCurveToRelative(0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(32.057f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(34.1f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(36.142f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(38.185f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(40.228f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            reflectiveCurveToRelative(0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(42.27f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(44.314f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(46.355f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(48.398f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(50.441f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(52.484f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(54.527f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(56.57f, 47.611f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            reflectiveCurveToRelative(0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(64.552f, 52.292f)
            horizontalLineTo(34.16f)
            verticalLineToRelative(3.83f)
            horizontalLineToRelative(30.39f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.049f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(38.092f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(40.134f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(42.177f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(44.22f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(46.263f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(48.305f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(50.347f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(52.39f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(54.433f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(56.476f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.156f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(58.519f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(60.56f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(62.603f, 55.272f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.975f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(62.633f, 56.122f)
            horizontalLineToRelative(-30.39f)
            verticalLineToRelative(3.83f)
            horizontalLineToRelative(30.39f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.13f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(36.173f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(38.216f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.036f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(40.259f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.036f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(42.302f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(44.344f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(46.387f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(48.43f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.08f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.036f, -0.078f, 0.08f, -0.078f)
            curveToRelative(0.042f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(50.472f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(52.514f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(54.557f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(56.6f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.035f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.044f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(58.643f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.034f, -0.078f, 0.078f, -0.078f)
            reflectiveCurveToRelative(0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(60.686f, 59.103f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineTo(57.05f)
            curveToRelative(0.0f, -0.043f, 0.035f, -0.078f, 0.079f, -0.078f)
            reflectiveCurveToRelative(0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.078f, 0.078f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFE98331)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(66.0f, 63.783f)
            horizontalLineTo(35.607f)
            verticalLineToRelative(3.83f)
            horizontalLineTo(66.0f)
            verticalLineToRelative(-3.83f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(37.497f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(39.54f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(41.582f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(43.625f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(45.668f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(47.71f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            reflectiveCurveToRelative(0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(49.753f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(51.796f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(53.84f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.08f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.08f, -0.078f)
            curveToRelative(0.042f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(55.88f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            reflectiveCurveToRelative(0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(57.923f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(59.966f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.034f, -0.078f, 0.078f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.079f, 0.034f, 0.079f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(62.009f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(64.052f, 66.763f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            verticalLineToRelative(-1.974f)
            curveToRelative(0.0f, -0.044f, 0.035f, -0.078f, 0.079f, -0.078f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            verticalLineToRelative(1.974f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.078f)
            close()
            moveTo(56.57f, 48.539f)
            horizontalLineTo(31.0f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(25.57f)
            curveToRelative(0.044f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.079f, 0.079f)
            close()
            moveTo(61.884f, 56.2f)
            horizontalLineTo(35.609f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.078f)
            curveToRelative(0.0f, -0.042f, 0.036f, -0.078f, 0.079f, -0.078f)
            horizontalLineToRelative(26.275f)
            curveToRelative(0.044f, 0.0f, 0.078f, 0.035f, 0.078f, 0.078f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(60.206f, 60.031f)
            horizontalLineTo(33.931f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(26.276f)
            curveToRelative(0.044f, 0.0f, 0.078f, 0.035f, 0.078f, 0.078f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.079f)
            close()
            moveTo(61.095f, 63.862f)
            horizontalLineTo(36.48f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(24.616f)
            curveToRelative(0.044f, 0.0f, 0.079f, 0.035f, 0.079f, 0.079f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(63.132f, 67.692f)
            horizontalLineTo(36.518f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(26.614f)
            curveToRelative(0.044f, 0.0f, 0.079f, 0.035f, 0.079f, 0.079f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, 0.078f)
            close()
            moveTo(59.217f, 52.37f)
            horizontalLineToRelative(-24.24f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.0f, -0.157f)
            horizontalLineToRelative(24.24f)
            curveToRelative(0.043f, 0.0f, 0.078f, 0.034f, 0.078f, 0.078f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.079f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(16.967f, 64.103f)
            curveToRelative(-0.163f, 0.188f, -0.319f, 0.394f, -0.476f, 0.607f)
            curveToRelative(-0.457f, 0.62f, -0.89f, 1.34f, -1.29f, 2.097f)
            curveToRelative(-0.857f, 1.61f, -1.578f, 3.375f, -2.072f, 4.64f)
            curveToRelative(0.0f, 0.0f, -9.323f, 1.145f, -13.129f, -1.917f)
            curveToRelative(1.478f, -3.043f, 6.85f, -0.883f, 10.719f, -3.982f)
            arcToRelative(7.978f, 7.978f, 0.0f, false, false, 1.922f, -2.273f)
            curveToRelative(0.256f, -0.426f, 0.489f, -0.889f, 0.701f, -1.409f)
            lineToRelative(3.625f, 2.237f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(15.202f, 66.808f)
            curveToRelative(-0.607f, 1.14f, -1.146f, 2.36f, -1.584f, 3.424f)
            curveToRelative(-0.18f, 0.439f, -0.344f, 0.846f, -0.488f, 1.215f)
            curveToRelative(0.0f, 0.0f, -9.323f, 1.146f, -13.129f, -1.916f)
            curveToRelative(0.132f, -0.263f, 0.288f, -0.495f, 0.47f, -0.683f)
            curveToRelative(1.935f, -2.021f, 6.718f, -0.47f, 10.248f, -3.3f)
            curveToRelative(1.008f, 0.978f, 3.218f, 1.385f, 4.483f, 1.26f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(13.617f, 70.231f)
            curveToRelative(-0.18f, 0.439f, -0.344f, 0.846f, -0.488f, 1.215f)
            curveToRelative(0.0f, 0.0f, -9.323f, 1.146f, -13.129f, -1.916f)
            curveToRelative(0.132f, -0.263f, 0.288f, -0.495f, 0.47f, -0.682f)
            curveToRelative(2.116f, 1.252f, 7.156f, 2.648f, 13.147f, 1.383f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(4.304f, 70.064f)
            horizontalLineToRelative(-0.008f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.07f, -0.086f)
            arcToRelative(2.047f, 2.047f, 0.0f, false, false, -0.977f, -1.964f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.026f, -0.108f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.107f, -0.027f)
            arcToRelative(2.206f, 2.206f, 0.0f, false, true, 1.052f, 2.114f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.071f)
            close()
            moveTo(4.316f, 68.68f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.079f, -0.074f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.075f, -0.082f)
            arcToRelative(15.563f, 15.563f, 0.0f, false, false, 7.15f, -2.161f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.08f, 0.134f)
            arcTo(15.714f, 15.714f, 0.0f, false, true, 4.32f, 68.68f)
            horizontalLineToRelative(-0.004f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(16.967f, 64.103f)
            curveToRelative(-0.163f, 0.188f, -0.319f, 0.394f, -0.476f, 0.607f)
            lineToRelative(-3.85f, -1.434f)
            curveToRelative(0.257f, -0.426f, 0.489f, -0.889f, 0.701f, -1.409f)
            lineToRelative(3.625f, 2.236f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.885f, 23.206f)
            reflectiveCurveToRelative(-2.139f, -1.664f, -3.996f, -0.81f)
            curveToRelative(-1.859f, 0.855f, -3.042f, 5.11f, -3.042f, 5.11f)
            reflectiveCurveToRelative(0.629f, 0.36f, 1.259f, -0.928f)
            curveToRelative(0.629f, -1.289f, 0.764f, -1.463f, 0.764f, -1.463f)
            reflectiveCurveToRelative(0.614f, 1.387f, 1.723f, 0.639f)
            curveToRelative(-0.014f, 1.138f, 0.465f, 1.753f, 1.094f, 1.678f)
            curveToRelative(-0.225f, -0.808f, -0.42f, -3.18f, 1.11f, -2.578f)
            lineToRelative(1.088f, -1.648f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(29.825f, 24.932f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.076f, -0.057f)
            curveToRelative(-0.1f, -0.372f, -0.076f, -1.082f, 0.114f, -1.505f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.104f, -0.04f)
            curveToRelative(0.04f, 0.019f, 0.056f, 0.065f, 0.039f, 0.105f)
            curveToRelative(-0.172f, 0.381f, -0.198f, 1.053f, -0.106f, 1.399f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.055f, 0.096f)
            arcToRelative(0.122f, 0.122f, 0.0f, false, true, -0.02f, 0.002f)
            close()
            moveTo(31.763f, 25.673f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.063f, -0.032f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.017f, -0.109f)
            curveToRelative(0.37f, -0.275f, 0.493f, -0.551f, 0.42f, -0.67f)
            curveToRelative(-0.044f, -0.073f, -0.098f, -0.114f, -0.16f, -0.126f)
            curveToRelative(-0.096f, -0.015f, -0.221f, 0.034f, -0.365f, 0.146f)
            arcToRelative(0.576f, 0.576f, 0.0f, false, true, -0.615f, 0.082f)
            curveToRelative(-0.137f, -0.068f, -0.367f, -0.258f, -0.36f, -0.774f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.078f, -0.078f)
            horizontalLineToRelative(0.001f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.078f, 0.08f)
            curveToRelative(-0.004f, 0.318f, 0.093f, 0.542f, 0.273f, 0.632f)
            curveToRelative(0.14f, 0.069f, 0.312f, 0.044f, 0.45f, -0.066f)
            curveToRelative(0.182f, -0.143f, 0.346f, -0.205f, 0.489f, -0.177f)
            arcToRelative(0.393f, 0.393f, 0.0f, false, true, 0.266f, 0.198f)
            curveToRelative(0.135f, 0.222f, -0.05f, 0.575f, -0.46f, 0.879f)
            arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.05f, 0.015f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(43.462f, 20.774f)
            lineToRelative(-6.953f, -5.672f)
            arcToRelative(3.577f, 3.577f, 0.0f, false, true, -0.16f, -5.404f)
            lineToRelative(5.365f, -4.935f)
            lineToRelative(3.157f, 4.845f)
            lineToRelative(-1.925f, 1.813f)
            arcToRelative(1.645f, 1.645f, 0.0f, false, false, 0.148f, 2.519f)
            lineToRelative(2.537f, 1.88f)
            lineToRelative(-2.17f, 4.954f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(47.732f, 0.908f)
            curveToRelative(0.465f, -0.705f, 1.054f, -1.049f, 1.479f, -0.854f)
            curveToRelative(0.425f, 0.194f, 0.0f, 0.74f, 0.0f, 0.74f)
            reflectiveCurveToRelative(0.514f, -0.779f, 1.144f, -0.34f)
            curveToRelative(0.629f, 0.438f, -0.09f, 1.128f, -0.09f, 1.128f)
            reflectiveCurveToRelative(0.673f, -0.72f, 1.109f, -0.33f)
            curveToRelative(0.434f, 0.39f, 0.0f, 0.95f, 0.0f, 0.95f)
            reflectiveCurveToRelative(0.645f, -0.17f, 0.854f, 0.383f)
            curveToRelative(0.21f, 0.555f, -0.677f, 1.28f, -0.677f, 1.28f)
            lineToRelative(-3.055f, -0.667f)
            lineToRelative(-0.764f, -2.29f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(41.993f, 18.945f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.065f, -0.122f)
            curveToRelative(0.27f, -0.395f, 0.522f, -1.378f, 0.231f, -2.329f)
            curveToRelative(-0.254f, -0.83f, -0.852f, -1.438f, -1.777f, -1.802f)
            curveToRelative(-1.01f, -0.398f, -1.57f, -0.85f, -1.713f, -1.384f)
            curveToRelative(-0.182f, -0.68f, 0.335f, -1.403f, 0.928f, -2.145f)
            curveToRelative(0.382f, -0.478f, 0.98f, -1.383f, 0.694f, -2.067f)
            curveToRelative(-0.199f, -0.472f, -0.788f, -0.76f, -1.752f, -0.858f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.07f, -0.086f)
            curveToRelative(0.004f, -0.043f, 0.043f, -0.078f, 0.086f, -0.07f)
            curveToRelative(1.026f, 0.104f, 1.658f, 0.425f, 1.88f, 0.953f)
            curveToRelative(0.237f, 0.562f, -0.018f, 1.352f, -0.715f, 2.226f)
            curveToRelative(-0.567f, 0.71f, -1.063f, 1.397f, -0.9f, 2.006f)
            curveToRelative(0.13f, 0.482f, 0.659f, 0.9f, 1.62f, 1.279f)
            curveToRelative(0.958f, 0.377f, 1.605f, 1.035f, 1.87f, 1.902f)
            curveToRelative(0.306f, 1.0f, 0.037f, 2.042f, -0.251f, 2.463f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.066f, 0.034f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(42.31f, 6.53f)
            curveToRelative(-1.317f, -0.341f, -1.756f, -1.6f, -1.236f, -2.5f)
            curveToRelative(0.519f, -0.898f, 1.329f, -0.999f, 1.953f, -0.599f)
            curveToRelative(-0.335f, -2.237f, 2.183f, -3.277f, 3.642f, -1.878f)
            curveToRelative(0.32f, -1.24f, 2.837f, -1.1f, 2.896f, 1.478f)
            curveToRelative(2.008f, -0.966f, 4.516f, 2.558f, 1.273f, 6.005f)
            arcToRelative(1.953f, 1.953f, 0.0f, false, false, -0.535f, 1.51f)
            curveToRelative(0.078f, 0.855f, 0.202f, 1.947f, 0.202f, 1.947f)
            lineTo(46.93f, 8.578f)
            lineTo(42.31f, 6.53f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(46.968f, 12.998f)
            lineToRelative(0.332f, -0.895f)
            lineToRelative(-0.038f, 0.025f)
            curveToRelative(-0.18f, 0.1f, -0.964f, 0.533f, -1.935f, 0.726f)
            curveToRelative(-0.47f, 0.101f, -0.945f, -0.125f, -1.17f, -0.55f)
            curveToRelative(-0.983f, -1.872f, -1.716f, -4.125f, -1.848f, -5.773f)
            curveToRelative(0.595f, 0.176f, 2.079f, -0.17f, 2.517f, -1.127f)
            curveToRelative(0.088f, 0.527f, 1.033f, 1.14f, 1.753f, 0.225f)
            curveToRelative(-0.194f, 1.247f, 1.12f, 2.192f, 1.12f, 2.192f)
            reflectiveCurveToRelative(1.096f, -1.61f, 1.829f, -0.483f)
            curveToRelative(0.739f, 1.121f, -0.219f, 1.86f, -0.219f, 1.86f)
            lineToRelative(2.242f, 4.614f)
            horizontalLineToRelative(-4.883f)
            lineToRelative(0.3f, -0.814f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(43.462f, 7.865f)
            curveToRelative(-0.006f, 0.0f, -0.013f, 0.0f, -0.02f, -0.002f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.056f, -0.095f)
            curveToRelative(0.133f, -0.524f, 0.563f, -0.93f, 1.094f, -1.032f)
            curveToRelative(0.041f, -0.006f, 0.084f, 0.02f, 0.092f, 0.063f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.062f, 0.092f)
            arcToRelative(1.25f, 1.25f, 0.0f, false, false, -0.973f, 0.917f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.075f, 0.057f)
            close()
            moveTo(48.093f, 8.764f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.027f, -0.004f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.048f, -0.1f)
            curveToRelative(0.16f, -0.445f, 0.45f, -0.822f, 0.84f, -1.088f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.109f, 0.021f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.021f, 0.109f)
            arcToRelative(2.055f, 2.055f, 0.0f, false, false, -0.78f, 1.01f)
            arcToRelative(0.083f, 0.083f, 0.0f, false, true, -0.073f, 0.052f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(42.998f, 8.31f)
            curveToRelative(-0.491f, 0.453f, -0.68f, 1.2f, -0.752f, 1.662f)
            arcToRelative(0.389f, 0.389f, 0.0f, false, false, 0.377f, 0.446f)
            curveToRelative(0.325f, 0.01f, 0.804f, -0.02f, 1.31f, -0.204f)
            lineToRelative(-0.935f, -1.903f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.87f, 11.084f)
            curveToRelative(0.425f, -0.251f, 0.733f, -0.66f, 0.855f, -1.139f)
            arcToRelative(0.712f, 0.712f, 0.0f, false, true, -0.854f, 1.139f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.084f, 8.708f)
            arcToRelative(0.955f, 0.955f, 0.0f, false, true, -0.138f, 0.138f)
            arcToRelative(1.59f, 1.59f, 0.0f, false, true, -1.203f, 0.408f)
            arcToRelative(1.856f, 1.856f, 0.0f, false, true, 0.495f, -1.359f)
            curveToRelative(0.3f, -0.325f, 0.72f, -0.55f, 1.19f, -0.594f)
            arcToRelative(1.601f, 1.601f, 0.0f, false, true, -0.344f, 1.407f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(44.946f, 8.846f)
            arcToRelative(1.59f, 1.59f, 0.0f, false, true, -1.203f, 0.407f)
            arcToRelative(1.856f, 1.856f, 0.0f, false, true, 0.495f, -1.358f)
            curveToRelative(0.395f, 0.157f, 0.676f, 0.519f, 0.708f, 0.95f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(42.666f, 4.561f)
            curveToRelative(-0.27f, 0.0f, -0.517f, -0.08f, -0.748f, -0.154f)
            curveToRelative(-0.237f, -0.076f, -0.462f, -0.147f, -0.681f, -0.124f)
            curveToRelative(-0.041f, 0.012f, -0.082f, -0.025f, -0.086f, -0.07f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.069f, -0.085f)
            curveToRelative(0.25f, -0.03f, 0.49f, 0.05f, 0.746f, 0.13f)
            curveToRelative(0.439f, 0.141f, 0.89f, 0.287f, 1.45f, -0.117f)
            curveToRelative(0.426f, -0.307f, 0.498f, -0.814f, 0.574f, -1.348f)
            curveToRelative(0.11f, -0.769f, 0.223f, -1.563f, 1.429f, -1.738f)
            curveToRelative(0.04f, -0.01f, 0.081f, 0.024f, 0.089f, 0.067f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.067f, 0.09f)
            curveToRelative(-1.09f, 0.156f, -1.19f, 0.858f, -1.295f, 1.603f)
            curveToRelative(-0.077f, 0.544f, -0.157f, 1.108f, -0.637f, 1.454f)
            curveToRelative(-0.306f, 0.219f, -0.584f, 0.292f, -0.843f, 0.292f)
            close()
            moveTo(50.37f, 9.337f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.069f, -0.042f)
            curveToRelative(-0.01f, -0.02f, -0.25f, -0.518f, 0.418f, -2.026f)
            curveToRelative(0.283f, -0.641f, 0.279f, -1.181f, -0.013f, -1.52f)
            curveToRelative(-0.268f, -0.311f, -0.76f, -0.414f, -1.29f, -0.27f)
            curveToRelative(-1.18f, 0.323f, -1.968f, 0.028f, -2.553f, -0.956f)
            curveToRelative(-0.197f, -0.33f, -0.447f, -0.52f, -0.743f, -0.566f)
            curveToRelative(-0.466f, -0.072f, -1.058f, 0.206f, -1.724f, 0.802f)
            curveToRelative(-1.089f, 0.972f, -2.01f, 1.315f, -2.903f, 1.082f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.056f, -0.096f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, 0.095f, -0.056f)
            curveToRelative(0.84f, 0.22f, 1.713f, -0.113f, 2.759f, -1.047f)
            curveToRelative(0.713f, -0.636f, 1.336f, -0.915f, 1.852f, -0.84f)
            curveToRelative(0.344f, 0.053f, 0.632f, 0.269f, 0.853f, 0.642f)
            curveToRelative(0.55f, 0.924f, 1.261f, 1.187f, 2.378f, 0.885f)
            curveToRelative(0.591f, -0.161f, 1.143f, -0.038f, 1.45f, 0.318f)
            curveToRelative(0.332f, 0.387f, 0.346f, 0.986f, 0.036f, 1.686f)
            curveToRelative(-0.628f, 1.418f, -0.423f, 1.888f, -0.42f, 1.893f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.036f, 0.104f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.034f, 0.007f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(49.1f, 4.838f)
            curveToRelative(-0.305f, 0.0f, -0.622f, -0.114f, -0.943f, -0.428f)
            curveToRelative(-0.546f, -0.535f, -0.332f, -1.188f, -0.124f, -1.82f)
            curveToRelative(0.185f, -0.56f, 0.36f, -1.091f, 0.0f, -1.512f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.008f, -0.11f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.11f, 0.009f)
            curveToRelative(0.417f, 0.487f, 0.22f, 1.084f, 0.03f, 1.662f)
            curveToRelative(-0.202f, 0.613f, -0.393f, 1.192f, 0.085f, 1.66f)
            curveToRelative(0.712f, 0.698f, 1.377f, 0.306f, 2.02f, -0.074f)
            curveToRelative(0.404f, -0.238f, 0.79f, -0.47f, 1.142f, -0.358f)
            curveToRelative(0.22f, 0.067f, 0.401f, 0.258f, 0.555f, 0.581f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.038f, 0.105f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.105f, -0.038f)
            curveToRelative(-0.132f, -0.281f, -0.282f, -0.445f, -0.458f, -0.5f)
            curveToRelative(-0.288f, -0.087f, -0.641f, 0.123f, -1.017f, 0.345f)
            curveToRelative(-0.375f, 0.223f, -0.807f, 0.478f, -1.264f, 0.478f)
            close()
            moveTo(46.6f, 2.73f)
            horizontalLineToRelative(-0.021f)
            arcToRelative(0.3f, 0.3f, 0.0f, false, true, -0.282f, -0.204f)
            curveToRelative(-0.072f, -0.194f, 0.002f, -0.526f, 0.33f, -0.832f)
            lineToRelative(0.069f, -0.063f)
            lineToRelative(0.051f, 0.077f)
            curveToRelative(0.233f, 0.354f, 0.3f, 0.654f, 0.194f, 0.844f)
            curveToRelative(-0.066f, 0.114f, -0.188f, 0.179f, -0.34f, 0.179f)
            close()
            moveTo(46.667f, 1.877f)
            curveToRelative(-0.218f, 0.233f, -0.271f, 0.467f, -0.223f, 0.595f)
            arcToRelative(0.145f, 0.145f, 0.0f, false, false, 0.141f, 0.102f)
            curveToRelative(0.065f, -0.002f, 0.167f, -0.009f, 0.219f, -0.098f)
            curveToRelative(0.033f, -0.06f, 0.082f, -0.232f, -0.137f, -0.6f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.4f, strokeAlpha
            = 0.4f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(49.2f, 4.097f)
            arcToRelative(0.446f, 0.446f, 0.0f, false, true, -0.204f, -0.053f)
            arcToRelative(0.304f, 0.304f, 0.0f, false, true, -0.171f, -0.304f)
            curveToRelative(0.016f, -0.205f, 0.223f, -0.476f, 0.648f, -0.616f)
            lineToRelative(0.088f, -0.028f)
            lineToRelative(0.015f, 0.092f)
            curveToRelative(0.062f, 0.418f, -0.001f, 0.72f, -0.179f, 0.847f)
            arcToRelative(0.34f, 0.34f, 0.0f, false, true, -0.197f, 0.062f)
            close()
            moveTo(49.433f, 3.306f)
            curveToRelative(-0.295f, 0.121f, -0.442f, 0.31f, -0.452f, 0.446f)
            curveToRelative(-0.006f, 0.069f, 0.023f, 0.12f, 0.085f, 0.152f)
            curveToRelative(0.053f, 0.027f, 0.155f, 0.063f, 0.239f, 0.003f)
            curveToRelative(0.056f, -0.039f, 0.173f, -0.176f, 0.128f, -0.601f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFF1A2E35)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(42.726f, 10.499f)
            lineToRelative(-0.105f, -0.001f)
            arcToRelative(0.468f, 0.468f, 0.0f, false, true, -0.453f, -0.536f)
            curveToRelative(0.067f, -0.433f, 0.257f, -1.23f, 0.775f, -1.708f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.111f, 0.004f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.004f, 0.111f)
            curveToRelative(-0.483f, 0.446f, -0.663f, 1.205f, -0.727f, 1.617f)
            arcToRelative(0.303f, 0.303f, 0.0f, false, false, 0.068f, 0.242f)
            curveToRelative(0.058f, 0.069f, 0.143f, 0.11f, 0.234f, 0.113f)
            arcToRelative(3.42f, 3.42f, 0.0f, false, false, 1.281f, -0.2f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.1f, 0.046f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.045f, 0.1f)
            arcToRelative(3.64f, 3.64f, 0.0f, false, true, -1.235f, 0.212f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.303f, 16.506f)
            verticalLineToRelative(-1.88f)
            curveToRelative(0.0f, -0.555f, -0.45f, -1.005f, -1.004f, -1.005f)
            horizontalLineToRelative(-5.436f)
            curveToRelative(-0.555f, 0.0f, -1.005f, 0.45f, -1.005f, 1.005f)
            verticalLineToRelative(2.268f)
            arcToRelative(12.467f, 12.467f, 0.0f, false, false, -2.917f, 7.528f)
            arcToRelative(7.611f, 7.611f, 0.0f, false, false, -2.413f, -0.81f)
            arcToRelative(3.636f, 3.636f, 0.0f, false, false, -2.02f, 0.27f)
            lineToRelative(-2.01f, -1.161f)
            arcToRelative(0.56f, 0.56f, 0.0f, false, false, -0.764f, 0.204f)
            lineToRelative(-1.35f, 2.338f)
            arcToRelative(0.56f, 0.56f, 0.0f, false, false, 0.205f, 0.764f)
            lineToRelative(1.705f, 0.984f)
            curveToRelative(-0.076f, 0.937f, 0.193f, 1.89f, 0.836f, 2.632f)
            curveToRelative(1.628f, 1.879f, 4.534f, 3.499f, 7.192f, 4.16f)
            curveToRelative(0.04f, 0.01f, 0.084f, 0.016f, 0.126f, 0.025f)
            lineToRelative(-0.126f, 0.694f)
            lineToRelative(9.521f, 2.638f)
            curveToRelative(1.216f, -4.758f, 1.684f, -10.747f, 1.162f, -14.481f)
            lineToRelative(0.257f, -0.68f)
            curveToRelative(0.819f, -2.166f, -0.143f, -4.438f, -1.959f, -5.493f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, fillAlpha = 0.2f, strokeAlpha
            = 0.2f, strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
            strokeLineMiter = 4.0f, pathFillType = NonZero
        ) {
            moveTo(52.885f, 33.71f)
            curveToRelative(-1.412f, 0.0f, -1.69f, -0.794f, -2.034f, -1.784f)
            curveToRelative(-0.305f, -0.87f, -0.65f, -1.857f, -1.857f, -2.643f)
            curveToRelative(-1.25f, -0.812f, -2.771f, -0.048f, -4.244f, 0.692f)
            curveToRelative(-1.613f, 0.808f, -3.28f, 1.648f, -4.465f, 0.22f)
            curveToRelative(-1.189f, -1.434f, -0.606f, -2.758f, -0.137f, -3.823f)
            curveToRelative(0.424f, -0.962f, 0.758f, -1.723f, -0.147f, -2.334f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.02f, -0.11f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.108f, -0.02f)
            curveToRelative(1.012f, 0.684f, 0.619f, 1.58f, 0.201f, 2.527f)
            curveToRelative(-0.475f, 1.08f, -1.013f, 2.3f, 0.115f, 3.66f)
            curveToRelative(1.104f, 1.332f, 2.645f, 0.558f, 4.274f, -0.262f)
            curveToRelative(1.511f, -0.76f, 3.076f, -1.545f, 4.4f, -0.683f)
            curveToRelative(1.252f, 0.814f, 1.622f, 1.872f, 1.92f, 2.723f)
            curveToRelative(0.357f, 1.024f, 0.619f, 1.766f, 2.138f, 1.67f)
            curveToRelative(0.044f, 0.001f, 0.08f, 0.03f, 0.084f, 0.072f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.073f, 0.083f)
            arcToRelative(2.872f, 2.872f, 0.0f, false, true, -0.263f, 0.012f)
            close()
            moveTo(51.255f, 28.382f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.065f, -0.035f)
            curveToRelative(-0.462f, -0.675f, -0.642f, -2.192f, 0.052f, -3.927f)
            curveToRelative(0.37f, -0.926f, 0.292f, -1.834f, -0.193f, -2.205f)
            curveToRelative(-0.51f, -0.395f, -1.379f, -0.175f, -2.445f, 0.62f)
            curveToRelative(-1.621f, 1.206f, -2.466f, 0.949f, -2.824f, 0.688f)
            curveToRelative(-0.514f, -0.375f, -0.653f, -1.185f, -0.354f, -2.066f)
            curveToRelative(0.369f, -1.086f, 0.36f, -1.92f, -0.023f, -2.286f)
            curveToRelative(-0.32f, -0.31f, -0.905f, -0.303f, -1.734f, 0.016f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.1f, -0.045f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, 0.045f, -0.1f)
            curveToRelative(0.89f, -0.346f, 1.529f, -0.34f, 1.898f, 0.016f)
            curveToRelative(0.587f, 0.562f, 0.291f, 1.776f, 0.063f, 2.45f)
            curveToRelative(-0.276f, 0.814f, -0.16f, 1.556f, 0.298f, 1.889f)
            curveToRelative(0.55f, 0.401f, 1.513f, 0.15f, 2.639f, -0.69f)
            curveToRelative(1.52f, -1.13f, 2.302f, -0.873f, 2.634f, -0.617f)
            curveToRelative(0.547f, 0.422f, 0.644f, 1.38f, 0.242f, 2.389f)
            curveToRelative(-0.718f, 1.794f, -0.434f, 3.244f, -0.068f, 3.78f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.02f, 0.11f)
            arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.045f, 0.013f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.23f, 28.006f)
            horizontalLineToRelative(-0.005f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.07f, -0.058f)
            curveToRelative(-0.227f, -0.82f, -2.077f, -2.579f, -2.968f, -3.224f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.018f, -0.11f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.11f, -0.017f)
            curveToRelative(0.737f, 0.535f, 2.517f, 2.156f, 2.954f, 3.117f)
            lineToRelative(3.46f, -8.328f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.104f, -0.042f)
            curveToRelative(0.04f, 0.017f, 0.058f, 0.063f, 0.041f, 0.102f)
            lineToRelative(-3.537f, 8.511f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.071f, 0.049f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.688f, 24.53f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.039f, -0.011f)
            lineToRelative(-1.678f, -0.959f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.03f, -0.106f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, 0.107f, -0.03f)
            lineToRelative(1.679f, 0.959f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.04f, 0.146f)
            close()
            moveTo(36.21f, 25.376f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.039f, -0.01f)
            lineToRelative(-1.678f, -0.96f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.03f, -0.107f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.107f, -0.028f)
            lineToRelative(1.679f, 0.96f)
            curveToRelative(0.037f, 0.02f, 0.05f, 0.069f, 0.029f, 0.106f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.068f, 0.04f)
            close()
            moveTo(35.724f, 26.153f)
            arcToRelative(0.074f, 0.074f, 0.0f, false, true, -0.039f, -0.01f)
            lineToRelative(-1.678f, -0.96f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.03f, -0.107f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, 0.107f, -0.028f)
            lineToRelative(1.679f, 0.96f)
            curveToRelative(0.037f, 0.021f, 0.05f, 0.069f, 0.029f, 0.106f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.068f, 0.039f)
            close()
            moveTo(45.982f, 16.317f)
            lineToRelative(-0.01f, -0.001f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.067f, -0.087f)
            lineToRelative(0.213f, -1.778f)
            curveToRelative(0.005f, -0.043f, 0.046f, -0.077f, 0.087f, -0.07f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.068f, 0.088f)
            lineToRelative(-0.214f, 1.779f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.077f, 0.069f)
            close()
            moveTo(47.595f, 16.317f)
            lineToRelative(-0.01f, -0.001f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.069f, -0.087f)
            lineToRelative(0.213f, -1.778f)
            curveToRelative(0.005f, -0.043f, 0.045f, -0.077f, 0.087f, -0.07f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.068f, 0.088f)
            lineToRelative(-0.213f, 1.779f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, -0.076f, 0.069f)
            close()
            moveTo(49.206f, 16.317f)
            lineToRelative(-0.009f, -0.001f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.068f, -0.087f)
            lineToRelative(0.213f, -1.778f)
            curveToRelative(0.005f, -0.043f, 0.044f, -0.077f, 0.086f, -0.07f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.068f, 0.088f)
            lineToRelative(-0.212f, 1.779f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, 0.069f)
            close()
            moveTo(50.819f, 16.317f)
            lineToRelative(-0.01f, -0.001f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.068f, -0.087f)
            lineToRelative(0.214f, -1.778f)
            curveToRelative(0.005f, -0.043f, 0.044f, -0.077f, 0.086f, -0.07f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.068f, 0.088f)
            lineToRelative(-0.213f, 1.779f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.069f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(52.783f, 35.17f)
            lineToRelative(-0.02f, -0.003f)
            lineToRelative(-4.66f, -1.2f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.057f, -0.094f)
            curveToRelative(0.01f, -0.042f, 0.05f, -0.07f, 0.095f, -0.057f)
            lineToRelative(4.66f, 1.2f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, 0.057f, 0.095f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.075f, 0.058f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(49.585f, 32.636f)
            arcToRelative(5.623f, 5.623f, 0.0f, false, false, 1.471f, -2.166f)
            lineToRelative(2.46f, -6.5f)
            curveToRelative(-0.136f, 4.117f, -0.795f, 7.02f, -3.931f, 8.666f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(45.839f, 34.157f)
            horizontalLineToRelative(-0.084f)
            curveToRelative(-0.668f, -0.01f, -1.285f, -0.061f, -1.835f, -0.152f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.063f, -0.09f)
            curveToRelative(0.006f, -0.044f, 0.047f, -0.07f, 0.09f, -0.064f)
            curveToRelative(0.54f, 0.09f, 1.15f, 0.14f, 1.81f, 0.15f)
            horizontalLineToRelative(0.082f)
            arcToRelative(5.514f, 5.514f, 0.0f, false, false, 5.142f, -3.556f)
            lineToRelative(2.674f, -7.068f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.147f, 0.054f)
            lineTo(51.128f, 30.5f)
            arcToRelative(5.671f, 5.671f, 0.0f, false, true, -5.29f, 3.658f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(34.8f, 29.2f)
            horizontalLineToRelative(-9.604f)
            verticalLineToRelative(1.09f)
            horizontalLineTo(34.8f)
            arcToRelative(0.545f, 0.545f, 0.0f, true, false, 0.0f, -1.09f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.475f, 19.434f)
            horizontalLineTo(9.897f)
            lineToRelative(7.985f, 10.857f)
            horizontalLineToRelative(8.578f)
            lineToRelative(-7.985f, -10.857f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(18.702f, 24.376f)
            curveToRelative(0.793f, 1.138f, 1.057f, 2.06f, 0.591f, 2.06f)
            curveToRelative(-0.466f, 0.0f, -1.487f, -0.921f, -2.279f, -2.06f)
            curveToRelative(-0.793f, -1.138f, -1.057f, -2.06f, -0.591f, -2.06f)
            curveToRelative(0.467f, 0.0f, 1.487f, 0.922f, 2.28f, 2.06f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(52.841f, 37.157f)
            reflectiveCurveToRelative(-0.52f, 3.73f, -3.256f, 7.52f)
            lineToRelative(-19.859f, -0.045f)
            reflectiveCurveToRelative(-3.818f, 6.699f, -7.794f, 16.177f)
            curveToRelative(-0.388f, 0.927f, -0.783f, 1.885f, -1.171f, 2.861f)
            curveToRelative(-0.263f, 0.664f, -0.526f, 1.34f, -0.79f, 2.029f)
            curveToRelative(0.0f, 0.0f, -6.874f, -2.404f, -10.03f, -6.042f)
            curveToRelative(0.0f, 0.0f, 3.494f, -13.598f, 10.23f, -23.096f)
            arcToRelative(6.846f, 6.846f, 0.0f, false, true, 4.972f, -2.855f)
            curveToRelative(3.825f, -0.363f, 10.787f, -0.65f, 18.18f, 0.815f)
            lineToRelative(9.518f, 2.636f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(21.932f, 60.81f)
            curveToRelative(-0.388f, 0.926f, -0.783f, 1.884f, -1.171f, 2.86f)
            curveToRelative(-0.877f, -2.253f, -2.611f, -8.633f, -2.823f, -13.291f)
            lineToRelative(3.994f, 10.43f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveToRelative(13.838f, 60.882f)
            lineToRelative(-0.014f, -0.001f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.062f, -0.092f)
            curveToRelative(0.36f, -1.882f, 1.63f, -7.114f, 5.623f, -15.036f)
            curveToRelative(4.893f, -9.705f, 13.956f, -6.819f, 18.313f, -5.431f)
            lineToRelative(0.243f, 0.077f)
            curveToRelative(4.397f, 1.398f, 9.61f, 0.767f, 11.342f, -3.788f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.101f, -0.046f)
            curveToRelative(0.041f, 0.016f, 0.061f, 0.06f, 0.045f, 0.1f)
            curveToRelative(-1.77f, 4.653f, -7.069f, 5.301f, -11.537f, 3.883f)
            lineToRelative(-0.242f, -0.076f)
            curveToRelative(-4.315f, -1.376f, -13.293f, -4.237f, -18.126f, 5.353f)
            curveToRelative(-3.983f, 7.902f, -5.25f, 13.118f, -5.61f, 14.993f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.076f, 0.064f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(36.21f, 34.08f)
            curveToRelative(-2.772f, 0.489f, -4.613f, 1.56f, -6.196f, 2.918f)
            curveToRelative(0.378f, 2.426f, 0.77f, 5.19f, 0.518f, 7.632f)
            horizontalLineToRelative(-1.277f)
            lineToRelative(-1.829f, -9.091f)
            lineToRelative(8.784f, -1.46f)
            close()
        }
        path(
            fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(28.744f, 36.637f)
            arcToRelative(107.04f, 107.04f, 0.0f, false, true, 3.5f, 23.897f)
            arcToRelative(14.5f, 14.5f, 0.0f, false, true, -10.35f, 1.997f)
            arcToRelative(44.456f, 44.456f, 0.0f, false, true, -3.812f, -28.01f)
            curveToRelative(0.237f, -1.228f, 0.844f, -2.373f, 1.828f, -3.15f)
            curveToRelative(0.013f, -0.006f, 0.019f, -0.018f, 0.032f, -0.024f)
            curveToRelative(0.795f, -0.62f, 1.784f, -0.958f, 2.792f, -1.008f)
            arcToRelative(35.35f, 35.35f, 0.0f, false, true, 17.46f, 3.656f)
            curveToRelative(-4.0f, -0.364f, -8.014f, 0.563f, -11.45f, 2.642f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(32.243f, 60.534f)
            arcToRelative(14.5f, 14.5f, 0.0f, false, true, -10.349f, 1.996f)
            arcToRelative(12.647f, 12.647f, 0.0f, false, true, 10.35f, -1.996f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(27.132f, 58.77f)
            horizontalLineToRelative(-0.002f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.076f, -0.08f)
            arcToRelative(63.976f, 63.976f, 0.0f, false, false, -3.09f, -21.314f)
            arcToRelative(3.449f, 3.449f, 0.0f, false, true, 0.345f, -2.862f)
            arcToRelative(3.459f, 3.459f, 0.0f, false, true, 2.398f, -1.613f)
            arcToRelative(28.835f, 28.835f, 0.0f, false, true, 11.436f, 0.437f)
            curveToRelative(0.042f, 0.01f, 0.068f, 0.052f, 0.057f, 0.094f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.094f, 0.058f)
            arcToRelative(28.722f, 28.722f, 0.0f, false, false, -11.373f, -0.436f)
            arcToRelative(3.3f, 3.3f, 0.0f, false, false, -2.29f, 1.542f)
            arcToRelative(3.294f, 3.294f, 0.0f, false, false, -0.329f, 2.731f)
            arcToRelative(64.147f, 64.147f, 0.0f, false, true, 3.096f, 21.366f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.078f, 0.077f)
            close()
        }
        path(
            fill = SolidColor(Color(0xFFFDB470)), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.346f, 66.288f)
            curveToRelative(-1.052f, 1.959f, -5.522f, 6.974f, -8.94f, 6.266f)
            arcToRelative(1.21f, 1.21f, 0.0f, false, true, -0.965f, -1.102f)
            curveToRelative(-0.237f, -3.174f, 4.145f, -4.144f, 4.808f, -6.742f)
            curveToRelative(0.038f, -0.145f, 0.07f, -0.282f, 0.094f, -0.414f)
            curveToRelative(0.407f, -1.934f, 0.05f, -3.262f, 0.05f, -3.262f)
            reflectiveCurveToRelative(1.41f, -0.682f, 3.75f, -0.67f)
            curveToRelative(0.044f, 0.477f, 0.107f, 1.077f, 0.194f, 1.716f)
            curveToRelative(0.2f, 1.521f, 0.527f, 3.28f, 1.009f, 4.208f)
            close()
        }
        path(
            fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.346f, 66.288f)
            curveToRelative(-1.052f, 1.96f, -5.522f, 6.974f, -8.94f, 6.267f)
            arcToRelative(1.21f, 1.21f, 0.0f, false, true, -0.965f, -1.102f)
            arcToRelative(2.366f, 2.366f, 0.0f, false, true, 0.02f, -0.601f)
            curveToRelative(0.312f, -2.704f, 4.168f, -3.72f, 4.788f, -6.142f)
            curveToRelative(0.038f, -0.144f, 0.069f, -0.281f, 0.094f, -0.413f)
            curveToRelative(1.033f, -0.038f, 3.281f, -0.626f, 3.995f, -2.217f)
            curveToRelative(0.144f, 1.096f, 0.35f, 2.31f, 0.638f, 3.256f)
            curveToRelative(0.113f, 0.37f, 0.239f, 0.688f, 0.37f, 0.952f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(30.346f, 66.288f)
            curveToRelative(-1.052f, 1.96f, -5.522f, 6.974f, -8.94f, 6.267f)
            arcToRelative(1.21f, 1.21f, 0.0f, false, true, -0.965f, -1.102f)
            arcToRelative(2.365f, 2.365f, 0.0f, false, true, 0.02f, -0.6f)
            curveToRelative(0.199f, 1.151f, 1.871f, 1.12f, 3.63f, 0.23f)
            curveToRelative(1.728f, -0.876f, 4.753f, -3.631f, 5.885f, -5.747f)
            curveToRelative(0.113f, 0.37f, 0.239f, 0.689f, 0.37f, 0.952f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(24.03f, 70.903f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.078f, -0.066f)
            arcToRelative(2.118f, 2.118f, 0.0f, false, false, -2.235f, -1.78f)
            curveToRelative(-0.048f, 0.0f, -0.08f, -0.03f, -0.082f, -0.074f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.073f, -0.082f)
            arcToRelative(2.272f, 2.272f, 0.0f, false, true, 2.4f, 1.912f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.064f, 0.089f)
            horizontalLineToRelative(-0.014f)
            close()
            moveTo(23.091f, 69.051f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.051f, -0.019f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.008f, -0.11f)
            curveToRelative(0.672f, -0.78f, 1.336f, -1.38f, 1.92f, -1.908f)
            curveToRelative(1.071f, -0.968f, 1.845f, -1.667f, 1.877f, -2.745f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.079f, -0.076f)
            horizontalLineToRelative(0.002f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, 0.075f, 0.08f)
            curveToRelative(-0.034f, 1.146f, -0.829f, 1.864f, -1.927f, 2.857f)
            curveToRelative(-0.582f, 0.526f, -1.242f, 1.122f, -1.908f, 1.895f)
            arcToRelative(0.075f, 0.075f, 0.0f, false, true, -0.059f, 0.026f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.495f, 58.117f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.075f, -0.057f)
            curveToRelative(-1.123f, -4.1f, -2.241f, -10.604f, -2.201f, -15.107f)
            curveToRelative(0.0f, -0.043f, 0.035f, -0.077f, 0.078f, -0.077f)
            horizontalLineToRelative(0.001f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.078f, 0.079f)
            curveToRelative(-0.039f, 4.488f, 1.075f, 10.976f, 2.195f, 15.064f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.054f, 0.096f)
            curveToRelative(-0.008f, 0.002f, -0.015f, 0.002f, -0.022f, 0.002f)
            close()
            moveTo(49.467f, 36.302f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.02f, -0.004f)
            lineTo(43.3f, 34.596f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.054f, -0.097f)
            arcToRelative(0.076f, 0.076f, 0.0f, false, true, 0.096f, -0.054f)
            lineToRelative(6.145f, 1.703f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.054f, 0.096f)
            arcToRelative(0.074f, 0.074f, 0.0f, false, true, -0.074f, 0.058f)
            close()
        }
        path(
            fill = SolidColor(tertiaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(42.992f, 19.963f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.072f, -0.109f)
            curveToRelative(0.083f, -0.2f, 0.832f, -1.97f, 1.86f, -2.994f)
            verticalLineToRelative(-1.072f)
            arcToRelative(0.078f, 0.078f, 0.0f, true, true, 0.157f, 0.0f)
            verticalLineToRelative(1.105f)
            curveToRelative(0.0f, 0.02f, -0.008f, 0.04f, -0.023f, 0.055f)
            curveToRelative(-1.019f, 1.0f, -1.767f, 2.768f, -1.85f, 2.966f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.072f, 0.05f)
            close()
        }
        path(
            fill = SolidColor(secondaryDetails), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(19.815f, 64.491f)
            arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.023f, -0.003f)
            arcToRelative(22.833f, 22.833f, 0.0f, false, true, -9.063f, -5.333f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, -0.002f, -0.111f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.111f, -0.002f)
            arcToRelative(22.686f, 22.686f, 0.0f, false, false, 9.0f, 5.296f)
            curveToRelative(0.041f, 0.013f, 0.065f, 0.057f, 0.053f, 0.098f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.076f, 0.055f)
            close()
            moveTo(22.132f, 61.333f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.065f, -0.034f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.02f, -0.11f)
            arcToRelative(11.716f, 11.716f, 0.0f, false, true, 9.696f, -1.648f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, 0.055f, 0.096f)
            arcToRelative(0.077f, 0.077f, 0.0f, false, true, -0.096f, 0.055f)
            arcToRelative(11.547f, 11.547f, 0.0f, false, false, -9.566f, 1.628f)
            arcToRelative(0.073f, 0.073f, 0.0f, false, true, -0.044f, 0.013f)
            close()
        }
        path(
            fill = SolidColor(details), stroke = null, strokeLineWidth = 0.0f,
            strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
            pathFillType = NonZero
        ) {
            moveTo(31.219f, 35.693f)
            arcToRelative(0.079f, 0.079f, 0.0f, false, true, -0.036f, -0.149f)
            curveToRelative(0.832f, -0.417f, 3.697f, -1.715f, 7.06f, -1.715f)
            curveToRelative(0.19f, 0.0f, 0.382f, 0.004f, 0.576f, 0.013f)
            arcToRelative(0.078f, 0.078f, 0.0f, false, true, 0.074f, 0.081f)
            arcToRelative(0.08f, 0.08f, 0.0f, false, true, -0.082f, 0.075f)
            arcToRelative(12.94f, 12.94f, 0.0f, false, false, -0.569f, -0.013f)
            curveToRelative(-3.329f, 0.0f, -6.166f, 1.285f, -6.989f, 1.697f)
            arcToRelative(0.054f, 0.054f, 0.0f, false, true, -0.034f, 0.011f)
            close()
        }
    }
        .build()
}

