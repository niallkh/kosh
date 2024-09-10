package kosh.ui.resources.icons

import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.materialPath
import androidx.compose.ui.graphics.vector.ImageVector

public val LedgerIcon: ImageVector
    get() {
        if (ledgerIcon != null) {
            return ledgerIcon!!
        }
        ledgerIcon = materialIcon("Ledger") {
            materialPath {
                moveTo(0.0f, 16.5641f)
                verticalLineTo(22.4981f)
                horizontalLineTo(9.0274f)
                verticalLineTo(21.1821f)
                horizontalLineTo(1.3153f)
                verticalLineTo(16.5641f)
                horizontalLineTo(0.0f)
                close()
                moveTo(22.6847f, 16.5641f)
                verticalLineTo(21.1821f)
                horizontalLineTo(14.9726f)
                verticalLineTo(22.4977f)
                horizontalLineTo(24.0f)
                verticalLineTo(16.5641f)
                horizontalLineTo(22.6847f)
                close()
                moveTo(9.0405f, 7.534f)
                verticalLineTo(16.5638f)
                horizontalLineTo(14.9726f)
                verticalLineTo(15.3771f)
                horizontalLineTo(10.3559f)
                verticalLineTo(7.534f)
                horizontalLineTo(9.0405f)
                close()
                moveTo(0.0f, 1.6001f)
                verticalLineTo(7.534f)
                horizontalLineTo(1.3153f)
                verticalLineTo(2.9158f)
                horizontalLineTo(9.0274f)
                verticalLineTo(1.6001f)
                horizontalLineTo(0.0f)
                close()
                moveTo(14.9726f, 1.6001f)
                verticalLineTo(2.9158f)
                horizontalLineTo(22.6847f)
                verticalLineTo(7.534f)
                horizontalLineTo(24.0f)
                verticalLineTo(1.6001f)
                horizontalLineTo(14.9726f)
                close()
            }
        }
        return ledgerIcon!!
    }

private var ledgerIcon: ImageVector? = null
