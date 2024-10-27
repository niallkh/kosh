package kosh.ui.component.dapp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kosh.domain.entities.TransactionEntity
import kosh.domain.models.Uri
import kosh.domain.models.reown.DappMetadata
import kosh.ui.component.text.TextLine
import kosh.ui.component.text.TextUri

@Composable
fun DappTitle(
    dapp: TransactionEntity.Dapp?,
    modifier: Modifier = Modifier,
) {
    DappTitle(
        name = dapp?.name,
        url = dapp?.url,
        modifier = modifier,
    )
}

@Composable
fun DappTitle(
    dapp: DappMetadata?,
    modifier: Modifier = Modifier,
) {
    DappTitle(
        name = dapp?.name,
        url = dapp?.url,
        modifier = modifier,
    )
}


@Composable
private fun DappTitle(
    name: String?,
    url: Uri?,
    placeholder: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        TextLine(name)

        if (placeholder || url != null) {
            TextUri(
                uri = url,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}
