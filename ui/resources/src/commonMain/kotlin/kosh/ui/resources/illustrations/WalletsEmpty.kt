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
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

@Composable
public fun WalletsEmpty(
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
        name = "WalletsEmpty", defaultWidth = 100.0.dp, defaultHeight =
        100.0.dp, viewportWidth = 100.0f, viewportHeight = 100.0f
    ).apply {
        group {
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(28.113f, 95.309f)
                horizontalLineTo(3.6f)
                verticalLineToRelative(3.09f)
                horizontalLineToRelative(24.514f)
                verticalLineToRelative(-3.09f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.122f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.085f, 0.085f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(6.77f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.085f, 0.085f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.418f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.085f, 0.085f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.066f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                curveToRelative(0.0f, 0.047f, -0.039f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.713f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(13.36f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.037f, 0.085f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.01f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.087f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.657f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                curveToRelative(0.0f, 0.047f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.303f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.951f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.085f, -0.085f)
                curveToRelative(0.049f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(21.6f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(23.247f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.085f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                curveToRelative(0.0f, 0.047f, -0.039f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(24.896f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(26.543f, 97.737f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.593f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.085f, 0.086f, -0.085f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.037f, 0.086f, 0.085f)
                verticalLineToRelative(1.593f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(26.888f, 86.04f)
                horizontalLineTo(2.374f)
                verticalLineToRelative(3.09f)
                horizontalLineToRelative(24.514f)
                verticalLineToRelative(-3.09f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(3.897f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.546f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.047f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.192f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.84f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.488f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.136f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.047f, -0.039f, 0.086f, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(13.783f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.431f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.078f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.726f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.047f, -0.039f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.375f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.047f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(22.021f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(23.67f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(25.318f, 88.468f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(86.79f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(30.466f, 89.13f)
                horizontalLineTo(5.952f)
                verticalLineToRelative(3.09f)
                horizontalLineToRelative(24.514f)
                verticalLineToRelative(-3.09f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.475f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.124f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.77f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.419f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.066f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.714f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.362f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.01f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.657f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(22.305f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.047f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(23.953f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(25.6f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.039f, 0.086f, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(27.247f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(28.895f, 91.557f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineTo(89.88f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(28.919f, 92.218f)
                horizontalLineTo(4.405f)
                verticalLineToRelative(3.09f)
                horizontalLineToRelative(24.514f)
                verticalLineToRelative(-3.09f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(5.928f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(7.576f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(9.223f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.047f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(10.87f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(12.519f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.166f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.039f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(15.815f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.087f, 0.087f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(17.462f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(19.11f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.757f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(22.406f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.085f, 0.038f, 0.085f, 0.086f)
                verticalLineToRelative(1.592f)
                curveToRelative(0.0f, 0.048f, -0.039f, 0.086f, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(24.052f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(25.7f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(27.349f, 94.646f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                verticalLineToRelative(-1.592f)
                curveToRelative(0.0f, -0.048f, 0.037f, -0.086f, 0.085f, -0.086f)
                curveToRelative(0.048f, 0.0f, 0.086f, 0.038f, 0.086f, 0.086f)
                verticalLineToRelative(1.592f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.085f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(25.967f, 89.215f)
                horizontalLineTo(7.157f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                horizontalLineToRelative(18.81f)
                arcToRelative(0.086f, 0.086f, 0.0f, true, true, 0.0f, 0.172f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(27.754f, 92.305f)
                horizontalLineTo(6.927f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.085f)
                curveToRelative(0.0f, -0.048f, 0.038f, -0.086f, 0.086f, -0.086f)
                horizontalLineToRelative(20.827f)
                arcToRelative(0.086f, 0.086f, 0.0f, false, true, 0.0f, 0.172f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(27.29f, 95.395f)
                horizontalLineTo(5.22f)
                arcToRelative(0.085f, 0.085f, 0.0f, false, true, -0.086f, -0.086f)
                curveToRelative(0.0f, -0.047f, 0.038f, -0.085f, 0.086f, -0.085f)
                horizontalLineToRelative(22.07f)
                curveToRelative(0.049f, 0.0f, 0.087f, 0.037f, 0.087f, 0.085f)
                curveToRelative(0.0f, 0.048f, -0.04f, 0.086f, -0.086f, 0.086f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.512f, 67.83f)
                horizontalLineToRelative(-1.117f)
                verticalLineToRelative(18.617f)
                horizontalLineToRelative(1.117f)
                verticalLineTo(67.83f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(14.944f, 73.661f)
                lineToRelative(-0.125f, -0.024f)
                arcToRelative(18.26f, 18.26f, 0.0f, false, true, -10.402f, -5.989f)
                arcTo(18.275f, 18.275f, 0.0f, false, true, 0.005f, 56.485f)
                lineTo(0.0f, 56.358f)
                lineToRelative(0.119f, 0.042f)
                curveToRelative(7.583f, 2.677f, 13.254f, 9.243f, 14.8f, 17.136f)
                lineToRelative(0.025f, 0.125f)
                close()
                moveTo(0.183f, 56.605f)
                arcToRelative(18.09f, 18.09f, 0.0f, false, false, 4.365f, 10.932f)
                arcToRelative(18.1f, 18.1f, 0.0f, false, false, 10.18f, 5.908f)
                curveTo(13.168f, 65.708f, 7.61f, 59.273f, 0.184f, 56.605f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.217f, 74.03f)
                arcToRelative(8.713f, 8.713f, 0.0f, false, true, -3.178f, -0.601f)
                lineToRelative(-0.107f, -0.042f)
                lineToRelative(0.07f, -0.09f)
                arcToRelative(9.348f, 9.348f, 0.0f, false, true, 10.318f, -3.15f)
                lineToRelative(0.11f, 0.036f)
                lineToRelative(-0.066f, 0.095f)
                arcToRelative(8.693f, 8.693f, 0.0f, false, true, -7.147f, 3.751f)
                close()
                moveTo(17.209f, 73.31f)
                arcToRelative(8.512f, 8.512f, 0.0f, false, false, 9.945f, -3.035f)
                arcToRelative(9.178f, 9.178f, 0.0f, false, false, -9.945f, 3.035f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(15.952f, 67.557f)
                lineToRelative(-0.065f, -0.073f)
                arcToRelative(15.069f, 15.069f, 0.0f, false, true, -3.601f, -11.553f)
                arcToRelative(15.078f, 15.078f, 0.0f, false, true, 6.076f, -10.466f)
                lineToRelative(0.078f, -0.058f)
                lineToRelative(0.047f, 0.085f)
                curveToRelative(3.91f, 7.099f, 2.918f, 15.937f, -2.47f, 21.99f)
                lineToRelative(-0.065f, 0.075f)
                close()
                moveTo(18.383f, 45.661f)
                arcToRelative(14.907f, 14.907f, 0.0f, false, false, -5.928f, 10.288f)
                arcToRelative(14.906f, 14.906f, 0.0f, false, false, 3.498f, 11.347f)
                curveToRelative(5.263f, -5.974f, 6.236f, -14.642f, 2.43f, -21.635f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.199f, 67.723f)
                curveToRelative(-1.041f, 0.0f, -2.095f, -0.108f, -3.149f, -0.334f)
                lineToRelative(-0.12f, -0.025f)
                lineToRelative(0.066f, -0.104f)
                arcToRelative(19.221f, 19.221f, 0.0f, false, true, 16.957f, -8.976f)
                lineToRelative(0.123f, 0.005f)
                lineToRelative(-0.046f, 0.114f)
                arcToRelative(14.933f, 14.933f, 0.0f, false, true, -13.831f, 9.32f)
                close()
                moveTo(17.209f, 67.246f)
                arcToRelative(14.762f, 14.762f, 0.0f, false, false, 16.616f, -8.795f)
                arcToRelative(19.045f, 19.045f, 0.0f, false, false, -16.616f, 8.795f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(11.194f, 58.222f)
                arcToRelative(2.057f, 2.057f, 0.0f, true, true, -4.114f, 0.0f)
                arcToRelative(2.057f, 2.057f, 0.0f, true, true, 4.114f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(11.36f, 58.222f)
                arcToRelative(2.057f, 2.057f, 0.0f, true, true, -4.114f, 0.0f)
                arcToRelative(2.057f, 2.057f, 0.0f, true, true, 4.114f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.243f, 75.936f)
                arcToRelative(1.778f, 1.778f, 0.0f, true, true, -3.556f, 0.0f)
                arcToRelative(1.778f, 1.778f, 0.0f, false, true, 3.556f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(14.385f, 75.936f)
                arcToRelative(1.778f, 1.778f, 0.0f, true, true, -3.556f, 0.0f)
                arcToRelative(1.778f, 1.778f, 0.0f, false, true, 3.556f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(20.328f, 78.189f)
                arcToRelative(1.778f, 1.778f, 0.0f, true, true, -2.064f, -2.896f)
                arcToRelative(1.778f, 1.778f, 0.0f, false, true, 2.064f, 2.896f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(20.411f, 78.306f)
                arcToRelative(1.778f, 1.778f, 0.0f, true, true, -2.064f, -2.896f)
                arcToRelative(1.778f, 1.778f, 0.0f, false, true, 2.064f, 2.896f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(27.067f, 55.898f)
                arcToRelative(2.777f, 2.777f, 0.0f, true, true, -5.555f, 0.0f)
                arcToRelative(2.777f, 2.777f, 0.0f, false, true, 5.555f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(27.292f, 55.898f)
                arcToRelative(2.777f, 2.777f, 0.0f, true, true, -5.555f, 0.0f)
                arcToRelative(2.777f, 2.777f, 0.0f, false, true, 5.555f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.694f, 63.329f)
                arcToRelative(0.833f, 0.833f, 0.0f, true, true, -1.666f, 0.0f)
                arcToRelative(0.833f, 0.833f, 0.0f, false, true, 1.665f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(19.823f, 69.044f)
                arcToRelative(0.806f, 0.806f, 0.0f, true, true, -1.612f, 0.0f)
                arcToRelative(0.806f, 0.806f, 0.0f, false, true, 1.612f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE98331)), stroke = null, fillAlpha = 0.8f,
                strokeLineWidth = 0.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(10.395f, 73.554f)
                arcToRelative(0.85f, 0.85f, 0.0f, true, true, -1.701f, -0.001f)
                arcToRelative(0.85f, 0.85f, 0.0f, false, true, 1.701f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(14.381f, 61.614f)
                arcToRelative(13.453f, 13.453f, 0.0f, false, true, 3.524f, -13.71f)
                arcToRelative(17.55f, 17.55f, 0.0f, false, true, 1.271f, 8.8f)
                arcTo(17.563f, 17.563f, 0.0f, false, true, 15.985f, 65.0f)
                arcToRelative(13.398f, 13.398f, 0.0f, false, true, -1.603f, -3.386f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(24.948f, 61.779f)
                arcToRelative(17.702f, 17.702f, 0.0f, false, true, 6.768f, -1.993f)
                arcToRelative(13.47f, 13.47f, 0.0f, false, true, -5.22f, 4.916f)
                arcToRelative(13.476f, 13.476f, 0.0f, false, true, -7.001f, 1.552f)
                arcToRelative(17.704f, 17.704f, 0.0f, false, true, 5.453f, -4.475f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(8.601f, 64.034f)
                arcToRelative(21.229f, 21.229f, 0.0f, false, true, 4.16f, 7.427f)
                arcToRelative(16.591f, 16.591f, 0.0f, false, true, -7.148f, -4.846f)
                arcToRelative(16.603f, 16.603f, 0.0f, false, true, -3.754f, -7.778f)
                arcTo(21.239f, 21.239f, 0.0f, false, true, 8.6f, 64.034f)
                close()
            }
            path(
                fill = SolidColor(objects), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(22.02f, 71.267f)
                arcToRelative(8.08f, 8.08f, 0.0f, false, true, 3.088f, -0.32f)
                arcToRelative(7.48f, 7.48f, 0.0f, false, true, -5.827f, 1.778f)
                arcToRelative(8.079f, 8.079f, 0.0f, false, true, 2.74f, -1.458f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(64.044f, 32.385f)
                lineToRelative(-4.082f, 7.026f)
                arcToRelative(2.762f, 2.762f, 0.0f, false, true, -1.394f, 1.184f)
                curveToRelative(-8.337f, 3.142f, -16.502f, 4.113f, -18.605f, 4.39f)
                curveToRelative(-2.18f, 0.286f, -6.007f, 1.31f, -6.07f, 0.865f)
                curveToRelative(-0.053f, -0.39f, 1.794f, -0.86f, 2.22f, -0.971f)
                curveToRelative(-0.554f, 0.087f, -3.522f, 0.527f, -3.585f, 0.195f)
                curveToRelative(-0.066f, -0.34f, 2.602f, -0.894f, 2.963f, -0.968f)
                curveToRelative(-0.268f, 0.033f, -1.88f, 0.31f, -2.776f, 0.29f)
                curveToRelative(-0.649f, -0.013f, -0.875f, -0.309f, -0.121f, -0.552f)
                curveToRelative(0.699f, -0.226f, 1.97f, -0.4f, 2.578f, -0.501f)
                curveToRelative(-0.77f, 0.1f, -2.57f, 0.299f, -2.598f, -0.047f)
                curveToRelative(-0.037f, -0.39f, 0.928f, -0.496f, 0.928f, -0.496f)
                lineToRelative(4.308f, -0.713f)
                curveToRelative(-0.405f, -0.716f, -2.915f, -0.613f, -2.828f, -1.323f)
                curveToRelative(0.07f, -0.566f, 1.151f, -0.195f, 2.388f, 0.02f)
                curveToRelative(0.705f, 0.12f, 1.922f, 0.178f, 2.821f, 0.6f)
                curveToRelative(0.768f, 0.359f, 1.637f, 0.86f, 2.436f, 0.576f)
                lineToRelative(12.47f, -4.44f)
                arcToRelative(1.885f, 1.885f, 0.0f, false, false, 1.139f, -1.124f)
                lineToRelative(2.336f, -6.253f)
                lineToRelative(5.472f, 2.242f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(25.15f, 44.75f)
                arcTo(59.715f, 59.715f, 0.0f, false, true, 40.45f, 16.4f)
                arcToRelative(32.032f, 32.032f, 0.0f, false, false, 8.273f, 6.272f)
                arcToRelative(59.237f, 59.237f, 0.0f, false, false, -12.677f, 26.204f)
                arcToRelative(48.77f, 48.77f, 0.0f, false, false, -10.896f, -4.128f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3E2723)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(80.586f, 31.337f)
                curveToRelative(0.209f, 1.659f, -3.212f, 2.558f, -5.673f, 1.674f)
                arcToRelative(6.047f, 6.047f, 0.0f, false, true, -3.562f, -3.444f)
                lineToRelative(-3.026f, -5.83f)
                lineToRelative(-2.142f, -4.141f)
                arcToRelative(6.592f, 6.592f, 0.0f, false, true, -6.96f, 0.951f)
                arcToRelative(6.003f, 6.003f, 0.0f, false, true, -0.915f, -0.513f)
                curveToRelative(-3.495f, -2.306f, -3.8f, -7.421f, -2.468f, -9.2f)
                arcToRelative(3.962f, 3.962f, 0.0f, false, true, 3.57f, -1.561f)
                arcToRelative(6.429f, 6.429f, 0.0f, false, true, 7.138f, -5.22f)
                arcToRelative(6.435f, 6.435f, 0.0f, false, true, 5.622f, 6.818f)
                arcToRelative(8.145f, 8.145f, 0.0f, false, true, 6.291f, 5.385f)
                curveToRelative(2.402f, 6.997f, -5.852f, 9.481f, -0.246f, 14.664f)
                curveToRelative(0.253f, -0.491f, 0.795f, -0.759f, 1.34f, -0.669f)
                curveToRelative(0.54f, 0.097f, 0.958f, 0.536f, 1.031f, 1.086f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(69.558f, 24.029f)
                lineToRelative(-1.234f, -0.29f)
                lineToRelative(-2.141f, -4.142f)
                arcToRelative(6.592f, 6.592f, 0.0f, false, true, -6.96f, 0.952f)
                arcToRelative(6.0f, 6.0f, 0.0f, false, true, -0.916f, -0.514f)
                arcToRelative(7.81f, 7.81f, 0.0f, false, true, 0.618f, -8.232f)
                lineToRelative(1.74f, 3.911f)
                lineToRelative(5.079f, -0.959f)
                lineToRelative(0.691f, -1.398f)
                curveToRelative(3.718f, 2.157f, 5.087f, 6.85f, 3.123f, 10.672f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(63.535f, 21.611f)
                curveToRelative(0.58f, -1.39f, 0.648f, -2.49f, 0.648f, -2.49f)
                curveToRelative(-2.967f, 2.899f, -6.366f, -2.44f, -5.257f, -7.318f)
                curveToRelative(2.476f, 3.004f, 5.673f, 2.267f, 5.673f, 2.267f)
                curveToRelative(0.649f, -1.153f, 1.78f, -0.947f, 2.052f, -0.506f)
                curveToRelative(0.372f, 0.604f, 0.413f, 1.938f, -1.093f, 2.64f)
                curveToRelative(1.093f, 2.825f, 4.722f, 6.634f, 4.722f, 6.634f)
                lineToRelative(-8.59f, 1.532f)
                curveToRelative(0.9f, -0.891f, 1.48f, -1.88f, 1.846f, -2.759f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3E2723)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(65.01f, 14.848f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.086f, -0.126f)
                curveToRelative(0.272f, -0.694f, 0.915f, -0.967f, 0.941f, -0.978f)
                arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.121f, 0.05f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.05f, 0.122f)
                curveToRelative(-0.006f, 0.002f, -0.597f, 0.255f, -0.84f, 0.874f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.085f, 0.058f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(67.538f, 16.518f)
                arcToRelative(0.552f, 0.552f, 0.0f, true, true, -1.104f, 0.0f)
                arcToRelative(0.552f, 0.552f, 0.0f, false, true, 1.104f, 0.0f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFFFFFFE)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(61.72f, 14.777f)
                curveToRelative(-0.081f, 0.46f, -0.319f, 0.87f, -0.668f, 1.152f)
                arcToRelative(1.912f, 1.912f, 0.0f, false, true, -1.272f, 0.468f)
                curveToRelative(-0.06f, 0.0f, -0.111f, -0.007f, -0.172f, -0.015f)
                arcToRelative(1.971f, 1.971f, 0.0f, false, true, 1.934f, -1.63f)
                curveToRelative(0.059f, 0.002f, 0.12f, 0.017f, 0.179f, 0.025f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000001)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(61.051f, 15.93f)
                arcToRelative(1.912f, 1.912f, 0.0f, false, true, -1.272f, 0.467f)
                curveToRelative(-0.06f, 0.0f, -0.111f, -0.007f, -0.172f, -0.015f)
                arcToRelative(1.987f, 1.987f, 0.0f, false, true, 0.938f, -1.354f)
                curveToRelative(0.261f, 0.24f, 0.44f, 0.544f, 0.506f, 0.901f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFB3261E)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(60.916f, 18.227f)
                curveToRelative(0.478f, -0.252f, 0.856f, -0.66f, 1.068f, -1.158f)
                arcToRelative(0.787f, 0.787f, 0.0f, false, true, -1.068f, 1.158f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(59.469f, 16.617f)
                curveToRelative(-0.304f, 0.338f, -0.491f, 0.774f, -0.605f, 1.135f)
                arcToRelative(0.379f, 0.379f, 0.0f, false, false, 0.49f, 0.47f)
                curveToRelative(0.36f, -0.131f, 0.773f, -0.305f, 0.975f, -0.47f)
                lineToRelative(-0.86f, -1.135f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3E2723)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(59.409f, 15.407f)
                horizontalLineToRelative(-0.018f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, -0.074f, -0.11f)
                arcToRelative(1.657f, 1.657f, 0.0f, false, true, 1.331f, -1.308f)
                curveToRelative(0.048f, -0.006f, 0.099f, 0.025f, 0.107f, 0.076f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.075f, 0.107f)
                curveToRelative(-0.59f, 0.105f, -1.067f, 0.57f, -1.18f, 1.161f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.091f, 0.074f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(71.353f, 12.038f)
                arcToRelative(0.503f, 0.503f, 0.0f, false, true, -0.338f, -0.119f)
                curveToRelative(-0.111f, -0.1f, -0.14f, -0.26f, -0.076f, -0.426f)
                curveToRelative(0.111f, -0.29f, 0.496f, -0.58f, 1.103f, -0.58f)
                curveToRelative(0.052f, 0.0f, 0.093f, 0.04f, 0.093f, 0.092f)
                curveToRelative(0.0f, 0.676f, -0.354f, 0.906f, -0.507f, 0.975f)
                arcToRelative(0.699f, 0.699f, 0.0f, false, true, -0.275f, 0.058f)
                close()
                moveTo(71.946f, 11.1f)
                curveToRelative(-0.46f, 0.028f, -0.753f, 0.25f, -0.833f, 0.46f)
                curveToRelative(-0.016f, 0.044f, -0.048f, 0.153f, 0.027f, 0.22f)
                curveToRelative(0.077f, 0.07f, 0.245f, 0.103f, 0.411f, 0.027f)
                curveToRelative(0.232f, -0.101f, 0.373f, -0.356f, 0.395f, -0.707f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(56.514f, 17.672f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, -0.088f, -0.063f)
                curveToRelative(-0.796f, -2.363f, -0.75f, -4.468f, 0.125f, -5.773f)
                curveToRelative(0.505f, -0.753f, 1.27f, -1.198f, 2.153f, -1.257f)
                curveToRelative(0.242f, -0.015f, 0.483f, -0.026f, 0.72f, -0.036f)
                curveToRelative(1.897f, -0.087f, 3.533f, -0.162f, 3.836f, -2.515f)
                curveToRelative(0.18f, -1.393f, 0.725f, -2.437f, 1.576f, -3.015f)
                curveToRelative(0.797f, -0.54f, 1.848f, -0.665f, 3.04f, -0.363f)
                curveToRelative(0.05f, 0.013f, 0.08f, 0.063f, 0.067f, 0.114f)
                arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.114f, 0.066f)
                curveToRelative(-1.138f, -0.289f, -2.137f, -0.171f, -2.888f, 0.337f)
                curveToRelative(-0.806f, 0.548f, -1.324f, 1.545f, -1.497f, 2.884f)
                curveToRelative(-0.323f, 2.508f, -2.114f, 2.59f, -4.011f, 2.677f)
                curveToRelative(-0.236f, 0.012f, -0.476f, 0.022f, -0.717f, 0.038f)
                curveToRelative(-0.836f, 0.054f, -1.532f, 0.46f, -2.011f, 1.174f)
                curveToRelative(-0.843f, 1.257f, -0.88f, 3.302f, -0.104f, 5.61f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.087f, 0.122f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(61.509f, 12.67f)
                curveToRelative(-0.587f, 0.0f, -1.18f, -0.122f, -1.76f, -0.393f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.045f, -0.124f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, 0.124f, -0.045f)
                curveToRelative(2.406f, 1.13f, 5.072f, -0.448f, 6.361f, -2.505f)
                curveToRelative(0.714f, -1.14f, 1.89f, -1.773f, 3.063f, -1.644f)
                curveToRelative(1.21f, 0.129f, 2.204f, 1.028f, 2.727f, 2.47f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.056f, 0.118f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.119f, -0.056f)
                curveToRelative(-0.498f, -1.371f, -1.436f, -2.227f, -2.573f, -2.348f)
                curveToRelative(-1.1f, -0.115f, -2.208f, 0.48f, -2.885f, 1.56f)
                curveToRelative(-1.024f, 1.631f, -2.893f, 2.968f, -4.837f, 2.968f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(78.218f, 33.04f)
                arcToRelative(0.088f, 0.088f, 0.0f, false, true, -0.043f, -0.011f)
                curveToRelative(-4.887f, -2.598f, -4.473f, -6.879f, -4.072f, -11.018f)
                curveToRelative(0.159f, -1.634f, 0.308f, -3.178f, 0.119f, -4.566f)
                curveToRelative(-0.293f, -2.157f, -1.49f, -3.867f, -3.284f, -4.692f)
                curveToRelative(-1.768f, -0.811f, -3.897f, -0.65f, -5.553f, 0.42f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, -0.129f, -0.027f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.028f, -0.129f)
                curveToRelative(1.71f, -1.104f, 3.908f, -1.272f, 5.731f, -0.432f)
                curveToRelative(1.853f, 0.852f, 3.09f, 2.615f, 3.39f, 4.836f)
                curveToRelative(0.191f, 1.408f, 0.041f, 2.964f, -0.118f, 4.61f)
                curveToRelative(-0.395f, 4.08f, -0.804f, 8.296f, 3.974f, 10.835f)
                curveToRelative(0.045f, 0.024f, 0.062f, 0.08f, 0.038f, 0.126f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.081f, 0.048f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF3E2723)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(59.225f, 18.339f)
                arcToRelative(0.473f, 0.473f, 0.0f, false, true, -0.449f, -0.614f)
                curveToRelative(0.152f, -0.483f, 0.361f, -0.877f, 0.623f, -1.17f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.132f, -0.006f)
                curveToRelative(0.038f, 0.034f, 0.04f, 0.094f, 0.006f, 0.131f)
                curveToRelative(-0.245f, 0.273f, -0.442f, 0.643f, -0.584f, 1.1f)
                arcToRelative(0.287f, 0.287f, 0.0f, false, false, 0.37f, 0.353f)
                curveToRelative(0.464f, -0.17f, 0.792f, -0.326f, 0.948f, -0.454f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, 0.132f, 0.012f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.013f, 0.132f)
                curveToRelative(-0.174f, 0.143f, -0.511f, 0.306f, -1.0f, 0.485f)
                arcToRelative(0.438f, 0.438f, 0.0f, false, true, -0.165f, 0.03f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(97.192f, 98.367f)
                curveToRelative(-2.454f, 0.0f, -4.596f, -1.54f, -10.536f, -4.365f)
                curveToRelative(0.11f, -1.153f, -0.06f, -2.528f, -0.38f, -3.83f)
                arcToRelative(15.442f, 15.442f, 0.0f, false, false, -0.67f, -2.148f)
                curveToRelative(-0.385f, -1.034f, -0.832f, -1.874f, -1.226f, -2.306f)
                lineToRelative(3.792f, -4.179f)
                lineToRelative(0.506f, 1.026f)
                curveToRelative(0.789f, 1.621f, 1.54f, 3.115f, 2.774f, 4.886f)
                curveToRelative(1.843f, 2.885f, 4.893f, 4.58f, 6.826f, 6.023f)
                curveToRelative(3.539f, 2.64f, 0.825f, 4.893f, -1.086f, 4.893f)
                close()
            }
            path(
                fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(97.192f, 98.368f)
                curveToRelative(-2.454f, 0.0f, -4.596f, -1.539f, -10.537f, -4.364f)
                arcToRelative(8.45f, 8.45f, 0.0f, false, false, 0.023f, -1.22f)
                arcToRelative(13.49f, 13.49f, 0.0f, false, false, -0.401f, -2.61f)
                curveToRelative(2.544f, -0.514f, 4.239f, -1.442f, 5.176f, -2.722f)
                curveToRelative(1.844f, 2.885f, 4.894f, 4.58f, 6.827f, 6.023f)
                curveToRelative(0.498f, 0.372f, 0.877f, 0.737f, 1.145f, 1.093f)
                curveToRelative(1.641f, 2.142f, -0.59f, 3.8f, -2.233f, 3.8f)
                close()
            }
            path(
                fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(97.192f, 98.368f)
                curveToRelative(-2.454f, 0.0f, -4.595f, -1.54f, -10.536f, -4.365f)
                curveToRelative(0.036f, -0.386f, 0.044f, -0.796f, 0.022f, -1.22f)
                curveToRelative(1.8f, 0.93f, 9.08f, 4.632f, 10.352f, 4.632f)
                curveToRelative(1.256f, 0.0f, 3.1f, -0.683f, 2.395f, -2.848f)
                curveToRelative(1.641f, 2.143f, -0.59f, 3.801f, -2.233f, 3.801f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(94.558f, 96.276f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.09f, -0.12f)
                arcToRelative(3.52f, 3.52f, 0.0f, false, true, 3.608f, -2.534f)
                curveToRelative(0.05f, 0.003f, 0.09f, 0.047f, 0.087f, 0.099f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.094f, 0.087f)
                horizontalLineToRelative(-0.006f)
                arcToRelative(3.33f, 3.33f, 0.0f, false, false, -3.417f, 2.401f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.088f, 0.067f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(95.51f, 94.697f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.036f, -0.008f)
                curveToRelative(-2.474f, -1.066f, -4.7f, -3.038f, -5.958f, -5.273f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.036f, -0.128f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, 0.127f, 0.036f)
                curveToRelative(1.238f, 2.2f, 3.431f, 4.142f, 5.869f, 5.192f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.038f, 0.18f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(97.121f, 93.86f)
                arcToRelative(0.081f, 0.081f, 0.0f, false, true, -0.041f, -0.01f)
                curveToRelative(-2.39f, -1.21f, -4.766f, -3.242f, -6.205f, -5.303f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.151f, -0.106f)
                curveToRelative(1.421f, 2.037f, 3.773f, 4.047f, 6.137f, 5.245f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.042f, 0.175f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(43.709f, 45.997f)
                curveToRelative(-0.384f, 0.0f, -0.72f, -0.51f, -0.924f, -1.397f)
                curveToRelative(-0.011f, -0.05f, 0.02f, -0.1f, 0.07f, -0.112f)
                curveToRelative(0.055f, -0.011f, 0.1f, 0.02f, 0.112f, 0.07f)
                curveToRelative(0.177f, 0.772f, 0.46f, 1.253f, 0.742f, 1.253f)
                horizontalLineToRelative(0.001f)
                curveToRelative(0.218f, -0.002f, 0.448f, -0.308f, 0.616f, -0.824f)
                curveToRelative(0.184f, -0.562f, 0.283f, -1.311f, 0.28f, -2.109f)
                curveToRelative(-0.002f, -0.797f, -0.108f, -1.546f, -0.296f, -2.106f)
                curveToRelative(-0.172f, -0.512f, -0.406f, -0.819f, -0.623f, -0.819f)
                curveToRelative(-0.37f, 0.003f, -0.725f, 0.832f, -0.849f, 1.972f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.092f, 0.084f)
                curveToRelative(-0.002f, 0.0f, -0.006f, 0.0f, -0.01f, -0.002f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.082f, -0.102f)
                curveToRelative(0.14f, -1.296f, 0.544f, -2.136f, 1.031f, -2.137f)
                horizontalLineToRelative(0.002f)
                curveToRelative(0.31f, 0.0f, 0.594f, 0.335f, 0.798f, 0.945f)
                curveToRelative(0.194f, 0.579f, 0.303f, 1.347f, 0.307f, 2.163f)
                curveToRelative(0.003f, 0.816f, -0.099f, 1.586f, -0.29f, 2.166f)
                curveToRelative(-0.2f, 0.613f, -0.481f, 0.95f, -0.792f, 0.953f)
                curveToRelative(0.002f, 0.002f, -0.001f, 0.002f, -0.001f, 0.002f)
                close()
            }
            path(
                fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(68.517f, 91.153f)
                horizontalLineTo(56.7f)
                curveToRelative(-4.604f, -15.029f, -6.968f, -33.314f, -3.39f, -46.38f)
                arcTo(40.485f, 40.485f, 0.0f, false, false, 66.048f, 46.0f)
                curveToRelative(-0.781f, 4.93f, -1.041f, 10.536f, -0.9f, 16.382f)
                curveToRelative(0.216f, 8.864f, 1.346f, 18.3f, 2.982f, 26.808f)
                verticalLineToRelative(0.023f)
                curveToRelative(0.126f, 0.654f, 0.252f, 1.302f, 0.386f, 1.94f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(73.396f, 44.886f)
                arcToRelative(40.9f, 40.9f, 0.0f, false, true, -20.085f, -0.11f)
                curveToRelative(1.138f, -4.188f, 2.892f, -7.846f, 5.384f, -10.679f)
                arcToRelative(13.036f, 13.036f, 0.0f, false, true, -1.277f, -3.075f)
                arcToRelative(3.712f, 3.712f, 0.0f, false, true, 0.26f, -2.583f)
                curveToRelative(0.408f, -0.839f, 0.889f, -1.636f, 1.44f, -2.388f)
                arcToRelative(5.782f, 5.782f, 0.0f, false, true, 2.767f, -2.052f)
                arcToRelative(44.922f, 44.922f, 0.0f, false, true, 5.347f, -1.502f)
                curveToRelative(3.406f, -0.743f, 6.388f, 2.283f, 5.644f, 5.681f)
                arcToRelative(33.701f, 33.701f, 0.0f, false, true, -2.997f, 8.292f)
                arcToRelative(57.456f, 57.456f, 0.0f, false, true, 3.517f, 8.416f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(37.408f, 51.764f)
                lineToRelative(-0.118f, -0.069f)
                arcToRelative(46.783f, 46.783f, 0.0f, false, false, -14.245f, -5.52f)
                lineToRelative(-0.09f, -0.018f)
                lineToRelative(0.018f, -0.09f)
                arcToRelative(61.303f, 61.303f, 0.0f, false, true, 17.48f, -32.302f)
                lineToRelative(0.072f, -0.07f)
                lineToRelative(0.064f, 0.077f)
                arcToRelative(30.347f, 30.347f, 0.0f, false, false, 11.025f, 8.234f)
                lineToRelative(0.118f, 0.052f)
                lineToRelative(-0.087f, 0.096f)
                arcTo(57.228f, 57.228f, 0.0f, false, false, 37.43f, 51.63f)
                lineToRelative(-0.021f, 0.133f)
                close()
                moveTo(23.174f, 46.013f)
                arcToRelative(47.0f, 47.0f, 0.0f, false, true, 14.095f, 5.454f)
                arcToRelative(57.39f, 57.39f, 0.0f, false, true, 14.153f, -29.342f)
                arcToRelative(30.553f, 30.553f, 0.0f, false, true, -10.91f, -8.156f)
                curveToRelative(-8.93f, 8.718f, -14.923f, 19.795f, -17.338f, 32.044f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(68.132f, 89.191f)
                verticalLineToRelative(0.023f)
                lineToRelative(-7.385f, -34.75f)
                lineToRelative(4.403f, 7.92f)
                curveToRelative(0.214f, 8.864f, 1.345f, 18.3f, 2.981f, 26.807f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(55.755f, 69.193f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.09f, -0.072f)
                arcToRelative(53.336f, 53.336f, 0.0f, false, true, -0.642f, -21.56f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, 0.107f, -0.075f)
                curveToRelative(0.051f, 0.009f, 0.085f, 0.057f, 0.076f, 0.107f)
                arcToRelative(53.13f, 53.13f, 0.0f, false, false, 0.64f, 21.485f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.09f, 0.115f)
                close()
            }
            path(
                fill = SolidColor(downClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(88.796f, 81.16f)
                arcToRelative(30.72f, 30.72f, 0.0f, false, true, -8.552f, 9.517f)
                arcTo(80.512f, 80.512f, 0.0f, false, true, 59.17f, 45.845f)
                arcToRelative(40.96f, 40.96f, 0.0f, false, false, 14.225f, -0.96f)
                arcToRelative(58.047f, 58.047f, 0.0f, false, true, 2.959f, 18.546f)
                horizontalLineToRelative(1.041f)
                lineToRelative(0.952f, 2.797f)
                arcToRelative(59.205f, 59.205f, 0.0f, false, true, 10.449f, 14.931f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(69.875f, 65.962f)
                arcToRelative(0.091f, 0.091f, 0.0f, false, true, -0.08f, -0.045f)
                arcToRelative(54.17f, 54.17f, 0.0f, false, true, -6.68f, -18.322f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.076f, -0.108f)
                curveToRelative(0.057f, -0.007f, 0.1f, 0.026f, 0.107f, 0.076f)
                arcToRelative(53.99f, 53.99f, 0.0f, false, false, 6.658f, 18.26f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.033f, 0.128f)
                arcToRelative(0.11f, 0.11f, 0.0f, false, true, -0.048f, 0.011f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(66.541f, 90.489f)
                arcToRelative(0.098f, 0.098f, 0.0f, false, true, -0.051f, -0.015f)
                arcToRelative(33.103f, 33.103f, 0.0f, false, true, -12.258f, -14.807f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.05f, -0.123f)
                curveToRelative(0.048f, -0.02f, 0.1f, 0.003f, 0.122f, 0.05f)
                arcToRelative(32.915f, 32.915f, 0.0f, false, false, 12.19f, 14.724f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.026f, 0.13f)
                arcToRelative(0.096f, 0.096f, 0.0f, false, true, -0.079f, 0.04f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(84.943f, 83.189f)
                curveToRelative(-5.021f, 0.0f, -9.921f, -2.096f, -13.373f, -5.787f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.003f, -0.131f)
                arcToRelative(0.091f, 0.091f, 0.0f, false, true, 0.132f, 0.004f)
                curveToRelative(3.716f, 3.97f, 9.117f, 6.077f, 14.54f, 5.678f)
                arcToRelative(33.302f, 33.302f, 0.0f, false, true, -11.762f, -7.865f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.004f, -0.124f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.123f, -0.018f)
                arcToRelative(24.245f, 24.245f, 0.0f, false, false, 12.057f, 4.022f)
                curveToRelative(0.052f, 0.002f, 0.09f, 0.047f, 0.088f, 0.098f)
                curveToRelative(-0.002f, 0.05f, -0.045f, 0.087f, -0.098f, 0.089f)
                arcToRelative(24.431f, 24.431f, 0.0f, false, true, -11.447f, -3.6f)
                arcToRelative(33.12f, 33.12f, 0.0f, false, false, 11.485f, 7.373f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.024f, 0.179f)
                arcToRelative(18.23f, 18.23f, 0.0f, false, true, -1.72f, 0.082f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(61.204f, 27.413f)
                curveToRelative(-0.762f, 0.0f, -1.53f, -0.07f, -2.297f, -0.212f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.074f, -0.109f)
                curveToRelative(0.01f, -0.05f, 0.06f, -0.083f, 0.108f, -0.075f)
                curveToRelative(4.188f, 0.778f, 8.4f, -0.637f, 11.271f, -3.779f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.132f, -0.006f)
                curveToRelative(0.037f, 0.034f, 0.04f, 0.093f, 0.006f, 0.131f)
                arcToRelative(12.355f, 12.355f, 0.0f, false, true, -9.146f, 4.05f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(67.09f, 43.412f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.094f, -0.09f)
                arcToRelative(16.905f, 16.905f, 0.0f, false, false, -9.078f, -14.457f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, -0.04f, -0.126f)
                arcToRelative(0.097f, 0.097f, 0.0f, false, true, 0.124f, -0.04f)
                arcToRelative(17.092f, 17.092f, 0.0f, false, true, 9.178f, 14.615f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.09f, 0.096f)
                verticalLineToRelative(0.002f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(61.08f, 43.729f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, -0.084f, -0.053f)
                arcToRelative(11.06f, 11.06f, 0.0f, false, false, -5.0f, -5.092f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.04f, -0.125f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.123f, -0.042f)
                arcToRelative(11.232f, 11.232f, 0.0f, false, true, 5.083f, 5.178f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.043f, 0.123f)
                arcToRelative(0.082f, 0.082f, 0.0f, false, true, -0.04f, 0.01f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(62.738f, 44.233f)
                curveToRelative(-2.88f, 0.0f, -5.762f, -0.326f, -8.57f, -0.979f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.07f, -0.112f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.112f, -0.07f)
                arcToRelative(37.804f, 37.804f, 0.0f, false, false, 17.934f, -0.213f)
                curveToRelative(0.052f, -0.015f, 0.1f, 0.017f, 0.114f, 0.067f)
                curveToRelative(0.012f, 0.05f, -0.018f, 0.1f, -0.067f, 0.114f)
                arcToRelative(37.946f, 37.946f, 0.0f, false, true, -9.453f, 1.194f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(69.245f, 31.739f)
                lineToRelative(-6.202f, 5.25f)
                arcToRelative(2.756f, 2.756f, 0.0f, false, true, -1.71f, 0.648f)
                curveToRelative(-8.908f, 0.164f, -16.925f, -1.658f, -19.0f, -2.105f)
                curveToRelative(-2.148f, -0.46f, -6.097f, -0.781f, -6.008f, -1.219f)
                curveToRelative(0.082f, -0.386f, 1.978f, -0.208f, 2.416f, -0.17f)
                curveToRelative(-0.55f, -0.105f, -3.495f, -0.684f, -3.442f, -1.019f)
                curveToRelative(0.051f, -0.342f, 2.75f, 0.03f, 3.115f, 0.082f)
                curveToRelative(-0.262f, -0.059f, -1.875f, -0.34f, -2.712f, -0.657f)
                curveToRelative(-0.606f, -0.231f, -0.72f, -0.584f, 0.071f, -0.56f)
                curveToRelative(0.734f, 0.021f, 1.991f, 0.284f, 2.596f, 0.392f)
                curveToRelative(-0.759f, -0.164f, -2.521f, -0.58f, -2.432f, -0.915f)
                curveToRelative(0.097f, -0.378f, 1.041f, -0.156f, 1.041f, -0.156f)
                lineToRelative(4.298f, 0.773f)
                curveToRelative(-0.141f, -0.81f, -2.54f, -1.554f, -2.22f, -2.195f)
                curveToRelative(0.255f, -0.51f, 1.15f, 0.203f, 2.243f, 0.82f)
                curveToRelative(0.624f, 0.35f, 1.75f, 0.813f, 2.457f, 1.512f)
                curveToRelative(0.602f, 0.594f, 1.254f, 1.359f, 2.1f, 1.359f)
                horizontalLineToRelative(13.238f)
                curveToRelative(0.557f, 0.0f, 1.092f, -0.245f, 1.45f, -0.677f)
                lineToRelative(4.297f, -5.109f)
                lineToRelative(4.404f, 3.946f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(39.974f, 32.811f)
                lineToRelative(-0.018f, -0.001f)
                lineToRelative(-1.468f, -0.288f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.074f, -0.11f)
                curveToRelative(0.01f, -0.05f, 0.055f, -0.08f, 0.11f, -0.073f)
                lineToRelative(1.469f, 0.288f)
                curveToRelative(0.05f, 0.01f, 0.083f, 0.06f, 0.073f, 0.11f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.092f, 0.074f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(39.89f, 33.535f)
                curveToRelative(-0.004f, 0.0f, -0.01f, 0.0f, -0.014f, -0.002f)
                lineToRelative(-1.504f, -0.226f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.078f, -0.106f)
                arcToRelative(0.095f, 0.095f, 0.0f, false, true, 0.106f, -0.078f)
                lineToRelative(1.504f, 0.226f)
                curveToRelative(0.05f, 0.008f, 0.085f, 0.056f, 0.078f, 0.106f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.092f, 0.08f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(39.695f, 34.386f)
                horizontalLineToRelative(-0.015f)
                lineToRelative(-1.222f, -0.21f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.076f, -0.106f)
                curveToRelative(0.007f, -0.05f, 0.055f, -0.082f, 0.107f, -0.076f)
                lineToRelative(1.223f, 0.208f)
                curveToRelative(0.05f, 0.009f, 0.084f, 0.057f, 0.075f, 0.106f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.092f, 0.078f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(65.17f, 27.493f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.07f, -0.153f)
                lineToRelative(1.785f, -2.13f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.132f, -0.011f)
                curveToRelative(0.039f, 0.033f, 0.045f, 0.09f, 0.01f, 0.131f)
                lineToRelative(-1.785f, 2.131f)
                arcToRelative(0.1f, 0.1f, 0.0f, false, true, -0.072f, 0.032f)
                close()
            }
            path(
                fill = SolidColor(accessories), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(45.674f, 37.767f)
                curveToRelative(-0.05f, 0.0f, -0.1f, -0.008f, -0.148f, -0.026f)
                curveToRelative(-0.364f, -0.127f, -0.51f, -0.719f, -0.404f, -1.626f)
                curveToRelative(0.006f, -0.05f, 0.054f, -0.092f, 0.104f, -0.08f)
                curveToRelative(0.05f, 0.005f, 0.087f, 0.05f, 0.08f, 0.103f)
                curveToRelative(-0.092f, 0.787f, 0.016f, 1.335f, 0.28f, 1.428f)
                curveToRelative(0.402f, 0.146f, 1.254f, -0.833f, 1.826f, -2.46f)
                curveToRelative(0.264f, -0.753f, 0.416f, -1.493f, 0.427f, -2.084f)
                curveToRelative(0.01f, -0.55f, -0.104f, -0.907f, -0.312f, -0.98f)
                curveToRelative(-0.34f, -0.127f, -0.96f, 0.538f, -1.46f, 1.574f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.123f, 0.043f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.043f, -0.123f)
                curveToRelative(0.567f, -1.175f, 1.234f, -1.828f, 1.688f, -1.669f)
                curveToRelative(0.293f, 0.104f, 0.448f, 0.515f, 0.437f, 1.16f)
                curveToRelative(-0.012f, 0.61f, -0.167f, 1.372f, -0.437f, 2.141f)
                curveToRelative(-0.47f, 1.336f, -1.285f, 2.599f, -1.915f, 2.599f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(72.166f, 42.509f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.081f, -0.047f)
                arcToRelative(14.935f, 14.935f, 0.0f, false, false, -5.023f, -5.308f)
                lineToRelative(-0.12f, -0.074f)
                lineToRelative(0.117f, -0.08f)
                arcToRelative(12.543f, 12.543f, 0.0f, false, false, 4.31f, -5.195f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.123f, -0.046f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.047f, 0.122f)
                arcToRelative(12.715f, 12.715f, 0.0f, false, true, -4.26f, 5.191f)
                arcToRelative(15.132f, 15.132f, 0.0f, false, true, 4.969f, 5.298f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.036f, 0.127f)
                arcToRelative(0.105f, 0.105f, 0.0f, false, true, -0.046f, 0.012f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFEF9A9A)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(69.878f, 98.368f)
                horizontalLineTo(55.036f)
                reflectiveCurveToRelative(0.03f, -2.09f, 4.135f, -3.302f)
                curveToRelative(2.052f, -0.61f, 3.287f, -1.413f, 3.994f, -2.149f)
                curveToRelative(0.706f, -0.743f, 0.877f, -1.413f, 0.78f, -1.763f)
                horizontalLineToRelative(4.574f)
                lineToRelative(0.282f, 1.51f)
                lineToRelative(1.077f, 5.704f)
                close()
            }
            path(
                fill = SolidColor(shoes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(69.878f, 98.369f)
                horizontalLineTo(55.036f)
                reflectiveCurveToRelative(0.007f, -0.573f, 0.602f, -1.287f)
                curveToRelative(0.542f, -0.662f, 1.568f, -1.435f, 3.532f, -2.016f)
                curveToRelative(2.051f, -0.61f, 3.287f, -1.413f, 3.993f, -2.149f)
                curveToRelative(1.332f, 0.499f, 3.853f, 0.648f, 5.637f, -0.252f)
                lineToRelative(0.833f, 4.418f)
                lineToRelative(0.245f, 1.286f)
                close()
            }
            path(
                fill = SolidColor(shoesBase), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(69.878f, 98.368f)
                horizontalLineTo(55.036f)
                reflectiveCurveToRelative(0.007f, -0.573f, 0.602f, -1.287f)
                horizontalLineToRelative(13.995f)
                lineToRelative(0.245f, 1.287f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(58.941f, 96.957f)
                curveToRelative(-0.05f, 0.0f, -0.09f, -0.039f, -0.093f, -0.088f)
                arcToRelative(1.357f, 1.357f, 0.0f, false, false, -0.766f, -1.15f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.044f, -0.123f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.123f, -0.044f)
                curveToRelative(0.508f, 0.243f, 0.842f, 0.743f, 0.872f, 1.306f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.088f, 0.098f)
                lineToRelative(-0.004f, 0.001f)
                close()
            }
            path(
                fill = SolidColor(shoesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(58.838f, 96.5f)
                arcToRelative(0.096f, 0.096f, 0.0f, false, true, -0.089f, -0.063f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.058f, -0.117f)
                curveToRelative(0.508f, -0.172f, 1.024f, -0.32f, 1.522f, -0.464f)
                curveToRelative(1.725f, -0.497f, 3.353f, -0.967f, 4.192f, -2.28f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.129f, -0.028f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.028f, 0.128f)
                curveToRelative(-0.877f, 1.372f, -2.54f, 1.852f, -4.298f, 2.359f)
                arcToRelative(33.62f, 33.62f, 0.0f, false, false, -1.514f, 0.462f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.028f, 0.004f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(63.585f, 46.142f)
                arcToRelative(44.19f, 44.19f, 0.0f, false, true, -6.349f, -0.46f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.08f, -0.104f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.105f, -0.08f)
                curveToRelative(5.233f, 0.763f, 10.585f, 0.57f, 15.475f, -0.55f)
                curveToRelative(0.054f, -0.01f, 0.1f, 0.02f, 0.112f, 0.069f)
                curveToRelative(0.011f, 0.05f, -0.02f, 0.1f, -0.07f, 0.112f)
                curveToRelative(-2.94f, 0.674f, -6.046f, 1.013f, -9.193f, 1.013f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(59.095f, 33.669f)
                horizontalLineToRelative(-1.43f)
                arcToRelative(0.093f, 0.093f, 0.0f, true, true, 0.0f, -0.187f)
                horizontalLineToRelative(1.43f)
                arcToRelative(1.78f, 1.78f, 0.0f, false, false, 1.378f, -0.643f)
                lineToRelative(4.056f, -4.82f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.13f, -0.012f)
                curveToRelative(0.039f, 0.033f, 0.044f, 0.09f, 0.011f, 0.131f)
                lineToRelative(-4.056f, 4.82f)
                curveToRelative(-0.37f, 0.452f, -0.924f, 0.71f, -1.519f, 0.71f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(60.2f, 37.738f)
                curveToRelative(-1.311f, 0.0f, -2.663f, -0.04f, -4.033f, -0.12f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.006f, -0.185f)
                lineToRelative(0.006f, -0.001f)
                curveToRelative(1.764f, 0.103f, 3.498f, 0.144f, 5.155f, 0.11f)
                arcToRelative(2.668f, 2.668f, 0.0f, false, false, 1.652f, -0.624f)
                lineToRelative(5.875f, -4.972f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.131f, 0.012f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.011f, 0.131f)
                lineToRelative(-5.875f, 4.972f)
                arcToRelative(2.855f, 2.855f, 0.0f, false, true, -1.768f, 0.669f)
                arcToRelative(85.37f, 85.37f, 0.0f, false, true, -1.138f, 0.008f)
                close()
            }
            path(
                fill = SolidColor(upperClothesDetails), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(54.3f, 41.782f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.032f, -0.007f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.053f, -0.12f)
                curveToRelative(0.39f, -1.003f, 0.914f, -2.29f, 1.497f, -3.35f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.127f, -0.037f)
                curveToRelative(0.045f, 0.026f, 0.06f, 0.081f, 0.036f, 0.127f)
                curveToRelative(-0.578f, 1.05f, -1.098f, 2.33f, -1.488f, 3.327f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.087f, 0.06f)
                close()
            }
            path(
                fill = SolidColor(upperClothes), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(63.623f, 65.256f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.088f, -0.063f)
                curveToRelative(-2.059f, -6.042f, -3.567f, -13.882f, -4.12f, -16.972f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, 0.074f, -0.109f)
                curveToRelative(0.054f, -0.007f, 0.1f, 0.026f, 0.108f, 0.075f)
                curveToRelative(0.553f, 3.086f, 2.06f, 10.916f, 4.113f, 16.945f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.058f, 0.118f)
                arcToRelative(0.058f, 0.058f, 0.0f, false, true, -0.03f, 0.006f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(41.037f, 35.437f)
                curveToRelative(-0.005f, 0.0f, -0.009f, 0.0f, -0.014f, -0.002f)
                curveToRelative(-3.797f, -0.571f, -4.64f, -0.817f, -4.769f, -1.028f)
                arcToRelative(0.143f, 0.143f, 0.0f, false, true, -0.016f, -0.125f)
                arcToRelative(0.09f, 0.09f, 0.0f, false, true, 0.117f, -0.058f)
                curveToRelative(0.04f, 0.014f, 0.066f, 0.053f, 0.064f, 0.095f)
                curveToRelative(0.238f, 0.272f, 3.539f, 0.77f, 4.632f, 0.932f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.014f, 0.185f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(38.474f, 34.18f)
                lineToRelative(-0.018f, -0.002f)
                curveToRelative(-1.113f, -0.21f, -3.01f, -0.614f, -3.223f, -0.951f)
                arcToRelative(0.161f, 0.161f, 0.0f, false, true, -0.024f, -0.128f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, 0.114f, -0.065f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.068f, 0.098f)
                curveToRelative(0.073f, 0.112f, 0.804f, 0.43f, 3.1f, 0.863f)
                curveToRelative(0.05f, 0.009f, 0.082f, 0.058f, 0.074f, 0.109f)
                arcToRelative(0.094f, 0.094f, 0.0f, false, true, -0.091f, 0.075f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveToRelative(38.331f, 33.29f)
                lineToRelative(-0.019f, -0.003f)
                curveToRelative(-2.197f, -0.453f, -3.235f, -0.746f, -3.195f, -1.18f)
                curveToRelative(0.004f, -0.05f, 0.053f, -0.093f, 0.101f, -0.083f)
                curveToRelative(0.05f, 0.005f, 0.089f, 0.05f, 0.084f, 0.1f)
                curveToRelative(-0.032f, 0.345f, 2.048f, 0.774f, 3.047f, 0.981f)
                curveToRelative(0.05f, 0.01f, 0.083f, 0.06f, 0.072f, 0.11f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.09f, 0.075f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(38.45f, 32.483f)
                curveToRelative(-0.004f, 0.0f, -0.009f, 0.0f, -0.015f, -0.002f)
                curveToRelative(-1.244f, -0.193f, -2.614f, -0.671f, -2.588f, -1.023f)
                curveToRelative(0.004f, -0.05f, 0.052f, -0.086f, 0.1f, -0.086f)
                curveToRelative(0.049f, 0.004f, 0.087f, 0.047f, 0.086f, 0.096f)
                curveToRelative(0.046f, 0.148f, 1.005f, 0.61f, 2.431f, 0.83f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, -0.014f, 0.184f)
                close()
            }
            path(
                fill = SolidColor(background), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(41.19f, 31.948f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.08f, -0.048f)
                curveToRelative(-0.109f, -0.195f, -0.535f, -0.473f, -0.948f, -0.742f)
                curveToRelative(-0.723f, -0.47f, -1.404f, -0.916f, -1.185f, -1.317f)
                arcToRelative(0.093f, 0.093f, 0.0f, false, true, 0.127f, -0.037f)
                curveToRelative(0.045f, 0.026f, 0.06f, 0.082f, 0.036f, 0.126f)
                curveToRelative(-0.137f, 0.249f, 0.592f, 0.725f, 1.123f, 1.072f)
                curveToRelative(0.452f, 0.294f, 0.879f, 0.573f, 1.008f, 0.809f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.036f, 0.126f)
                arcToRelative(0.092f, 0.092f, 0.0f, false, true, -0.044f, 0.011f)
                close()
            }
        }
    }
        .build()
}
