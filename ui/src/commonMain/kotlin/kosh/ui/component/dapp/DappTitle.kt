package kosh.ui.component.dapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.models.Uri
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri

@Composable
fun DappTitle(
    name: String?,
    url: Uri?,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        TextLine(
            name ?: "Unknown Dapp",
            Modifier.placeholder(name == null)
        )
        url?.let {
            TextUri(
                uri = it,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
