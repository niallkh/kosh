package kosh.ui.component.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kosh.domain.models.Uri
import kosh.domain.models.toLibUri
import kosh.domain.utils.md5
import kotlinx.io.bytestring.encodeToByteString

@Composable
internal fun uriColor(
    uri: Uri,
    isDark: Boolean,
): () -> ColorScheme {
    return dynamicColor(
        bytes = remember(uri) { uri.toLibUri().host.orEmpty().encodeToByteString().md5() },
        isDark = isDark,
    )
}

