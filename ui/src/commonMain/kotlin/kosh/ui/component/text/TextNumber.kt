package kosh.ui.component.text

import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import com.ionspin.kotlin.bignum.integer.BigInteger

@Composable
fun TextNumber(
    number: BigInteger,
    modifier: Modifier = Modifier,
) {
    val text = remember(number) {
        if (number.bitLength() < 64) {
            number.toString()
        } else {
            number.toString(16)
        }
    }

    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable { clipboardManager.setText(AnnotatedString(text)) },
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace,
    )
}

@Composable
fun TextNumber(
    number: ULong,
    modifier: Modifier = Modifier,
) {
    val text = remember(number) {
        number.toString()
    }

    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable { clipboardManager.setText(AnnotatedString(text)) },
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace,
    )
}
