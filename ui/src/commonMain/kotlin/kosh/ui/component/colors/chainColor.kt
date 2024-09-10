package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kosh.domain.models.ChainId
import okio.Buffer

@Composable
internal fun chainColor(
    chainId: ChainId,
    isDark: Boolean,
): ColorScheme = remember(chainId, isDark) {
    dynamicColor(
        bytes = Buffer().apply { writeLong(chainId.value.toLong()) }.md5(),
        isDark = isDark,
    )
}
