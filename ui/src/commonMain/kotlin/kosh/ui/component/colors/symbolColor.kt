package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import okio.ByteString.Companion.encodeUtf8

@Composable
internal fun symbolColor(
    symbol: String,
    isDark: Boolean,
): ColorScheme = remember(symbol, isDark) {
    dynamicColor(
        bytes = symbol.encodeUtf8().md5(),
        isDark = isDark,
    )
}
