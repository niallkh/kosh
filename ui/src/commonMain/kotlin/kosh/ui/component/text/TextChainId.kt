package kosh.ui.component.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import kosh.domain.models.ChainId

@Composable
fun TextChainId(
    chainId: ChainId,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    val text = remember(chainId) { chainId.value.toString() }

    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        fontFamily = FontFamily.Monospace,
        style = style,
    )
}
