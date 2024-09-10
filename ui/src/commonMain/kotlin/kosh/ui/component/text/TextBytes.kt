package kosh.ui.component.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import kosh.domain.models.ByteString
import kosh.ui.component.modifier.optionalClickable

@Composable
fun TextBytes(
    bytes: ByteString,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
) {
    TextBytes(
        text = bytes.toString(),
        modifier = modifier,
        clickable = clickable,
        style = style
    )
}

@Composable
fun TextBytes(
    text: String,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
) {
    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .optionalClickable(clickable) { clipboardManager.setText(AnnotatedString(text)) },
        text = text,
        fontFamily = FontFamily.Monospace,
        style = style,
    )
}
