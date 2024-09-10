package kosh.ui.component.text

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextOverflow
import kosh.domain.models.account.DerivationPath

@Composable
fun TextDerivationPath(
    derivationPath: DerivationPath,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable { clipboardManager.setText(AnnotatedString(derivationPath.toString())) },
        text = derivationPath.toString(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace,
        style = style,
    )
}
