package kosh.ui.component.cards

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.Balance
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.text.TextAmount

@Composable
fun NftBalanceCard(
    token: TokenEntity?,
    balance: Balance?,
    network: NetworkEntity? = token?.networkId?.let { rememberNetwork(it) }?.entity,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    NftCard(
        token = token,
        network = network,
        onClick = onClick,
        modifier = modifier,
        trailingContent = {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant,
            ) {
                TextAmount(
                    token = token,
                    amount = balance?.value
                )
            }
        }
    )
}
