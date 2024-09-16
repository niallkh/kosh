package kosh.ui.component.text

import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.unit.Dp
import kosh.domain.serializers.Uri
import kosh.ui.component.modifier.optionalClickable

@Composable
fun TextUri(
    uri: Uri,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
    maxWidth: Dp = Dp.Unspecified,
) {
    val text = remember(uri) {
        uri.toString()
    }

    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .optionalClickable(clickable) { clipboardManager.setText(AnnotatedString(text)) }
            .widthIn(max = maxWidth),
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.SansSerif,
        style = style,
    )
}
