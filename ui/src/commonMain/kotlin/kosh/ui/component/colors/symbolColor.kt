package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kosh.domain.utils.md5
import kotlinx.io.bytestring.encodeToByteString

@Composable
internal fun symbolColor(
    symbol: String,
    isDark: Boolean,
): () -> ColorScheme {
    return dynamicColor(
        bytes = remember(symbol) { symbol.encodeToByteString().md5() },
        isDark = isDark,
    )
}
