package kosh.ui.component.text

import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import kotlinx.datetime.Instant

@Composable
fun TextDate(
    date: Instant,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    color: Color = Color.Unspecified,
) {
    TextLine(formatDateTime(date), modifier, style, color)
}

@Composable
expect fun formatDateTime(date: Instant): String
