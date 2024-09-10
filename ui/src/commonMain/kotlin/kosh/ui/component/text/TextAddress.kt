package kosh.ui.component.text

import androidx.compose.foundation.clickable
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import kosh.domain.models.Address
import kosh.domain.models.eip55

@Composable
fun TextAddress(
    address: Address,
    style: TextStyle = LocalTextStyle.current,
) {
    TextAddress(
        address = remember(address) { address.eip55() },
        style = style,
    )
}

@Composable
fun TextAddress(
    address: String,
    style: TextStyle = LocalTextStyle.current,
) {
    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = Modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable { clipboardManager.setText(AnnotatedString(address)) },
        text = address,
        fontFamily = FontFamily.Monospace,
        style = style,
    )
}
