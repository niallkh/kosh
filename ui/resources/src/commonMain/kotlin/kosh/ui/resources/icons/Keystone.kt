package kosh.ui.resources.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path

public val Keystone: ImageVector
    get() {
        if (_keystone != null) {
            return _keystone!!
        }
        _keystone = materialIcon("Keystone") {
            materialPath {
                path(
                    fill = SolidColor(Color(0xFFF5F8FF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(4.5f, 3.75f)
                    horizontalLineTo(13.5f)
                    lineTo(7.5f, 16.5f)
                    horizontalLineTo(3.0f)
                    lineTo(4.5f, 3.75f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF3D71FF)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero
                ) {
                    moveTo(19.5f, 20.25f)
                    horizontalLineTo(10.5f)
                    lineTo(16.5f, 7.5f)
                    horizontalLineTo(21.0f)
                    lineTo(19.5f, 20.25f)
                    close()
                }
            }
        }
        return _keystone!!
    }

private var _keystone: ImageVector? = null
