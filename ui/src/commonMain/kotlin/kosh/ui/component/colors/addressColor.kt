package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kosh.domain.models.Address
import kosh.domain.utils.md5

@Composable
internal fun addressColor(
    address: Address,
    isDark: Boolean,
): ColorScheme = remember(address, isDark) {
    dynamicColor(
        bytes = address.bytes().md5(),
        isDark = isDark,
    )
}
