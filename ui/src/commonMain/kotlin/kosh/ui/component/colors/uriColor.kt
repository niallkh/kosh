package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.eygraber.uri.Uri
import kosh.domain.utils.md5
import kotlinx.io.bytestring.encodeToByteString

@Composable
internal fun uriColor(
    uri: Uri,
    isDark: Boolean,
): ColorScheme = remember(uri, isDark) {
    dynamicColor(
        bytes = uri.host.orEmpty().encodeToByteString().md5(),
        isDark = isDark,
    )
}

