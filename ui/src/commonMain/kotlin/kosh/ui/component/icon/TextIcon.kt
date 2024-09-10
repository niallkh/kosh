package kosh.ui.component.icon

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TextIcon(
    modifier: Modifier,
    text: String,
    contentDescription: String,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    contentColor: Color = contentColorFor(color),
) {
    Box(
        modifier = modifier
            .toolingGraphicsLayer()
            .semantics {
                this.contentDescription = contentDescription
                this.role = Role.Image
            }
            .sizeIn(minWidth = 16.dp, minHeight = 16.dp, maxWidth = 48.dp, maxHeight = 48.dp)
            .background(color),
        contentAlignment = Alignment.Center,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides MaterialTheme.typography.labelMedium
        ) {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                textAlign = TextAlign.Center,
            )
        }
    }
}
