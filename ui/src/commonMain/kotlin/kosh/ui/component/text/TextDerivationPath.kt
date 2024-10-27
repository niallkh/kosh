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
import androidx.compose.ui.text.style.TextOverflow
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.ethereumDerivationPath
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.placeholder.placeholder

@Composable
fun TextDerivationPath(
    derivationPath: DerivationPath?,
    modifier: Modifier = Modifier,
    clickable: Boolean = false,
    style: TextStyle = LocalTextStyle.current,
) {
    val clipboardManager = LocalClipboardManager.current

    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .optionalClickable(clickable) {
                clipboardManager.setText(AnnotatedString(derivationPath.toString()))
            }
            .placeholder(derivationPath == null),
        text = (derivationPath ?: ethereumDerivationPath()).toString(),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace,
        style = style,
    )
}
