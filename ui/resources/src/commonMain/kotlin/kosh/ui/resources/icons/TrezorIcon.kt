package kosh.ui.resources.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val TrezorIcon: ImageVector
    get() {
        if (trezorIcon != null) {
            return trezorIcon!!
        }
        trezorIcon = materialIcon("Trezor") {
            materialPath {
                moveTo(18.3078f, 5.5693f)
                curveTo(18.3078f, 2.5253f, 15.6324f, 0.0f, 12.3754f, 0.0f)
                curveTo(9.1178f, 0.0f, 6.4417f, 2.5265f, 6.4417f, 5.5687f)
                verticalLineTo(7.3493f)
                horizontalLineTo(4.0f)
                verticalLineTo(20.1526f)
                lineTo(12.3736f, 24.0f)
                lineTo(20.7519f, 20.1537f)
                verticalLineTo(7.4058f)
                horizontalLineTo(18.3102f)
                lineTo(18.3084f, 5.5687f)
                lineTo(18.3078f, 5.5693f)
                close()
                moveTo(9.4662f, 5.5693f)
                curveTo(9.4662f, 4.1341f, 10.7466f, 2.9857f, 12.3754f, 2.9857f)
                curveTo(14.0042f, 2.9857f, 15.2833f, 4.1341f, 15.2833f, 5.5693f)
                verticalLineTo(7.3493f)
                horizontalLineTo(9.4668f)
                lineTo(9.4662f, 5.5693f)
                close()
                moveTo(17.3777f, 18.0864f)
                lineTo(12.373f, 20.3845f)
                lineTo(7.37f, 18.0882f)
                verticalLineTo(10.3927f)
                horizontalLineTo(17.3772f)
                lineTo(17.3777f, 18.0864f)
                close()
            }
        }
        return trezorIcon!!
    }

private var trezorIcon: ImageVector? = null
