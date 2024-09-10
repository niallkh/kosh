package kosh.ui.component.text

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
import androidx.compose.ui.text.style.TextOverflow
import kosh.domain.models.Address
import kosh.domain.models.eip55
import kosh.ui.component.modifier.optionalClickable

@Composable
fun TextAddressShort(
    address: Address,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
) {
    TextAddressShort(
        address = remember(address) { address.eip55() },
        modifier = modifier,
        clickable = clickable,
        style = style,
    )
}

@Composable
fun TextAddressShort(
    address: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
) {
    val text = remember(address) {
        "${address.subSequence(0, 6)}...${address.subSequence(36, 42)}"
    }

    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .optionalClickable(clickable) { clipboardManager.setText(AnnotatedString(address)) },
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = style.copy(fontFamily = FontFamily.Monospace)
    )
}

