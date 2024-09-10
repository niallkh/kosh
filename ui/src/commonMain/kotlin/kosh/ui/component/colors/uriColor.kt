package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.eygraber.uri.Uri
import okio.ByteString.Companion.encodeUtf8

@Composable
internal fun uriColor(
    uri: Uri,
    isDark: Boolean,
): ColorScheme = remember(uri, isDark) {
    dynamicColor(
        bytes = uri.host.orEmpty().encodeUtf8().md5(),
        isDark = isDark,
    )
}

