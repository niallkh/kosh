package kosh.ui.component.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.BigInteger
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.image.NftImage
import kosh.ui.component.single.single
import kosh.ui.component.text.TextAmount
import kosh.ui.component.text.TextLine


@Composable
fun NftBalanceItem(
    modifier: Modifier = Modifier,
    tokenBalance: TokenBalance?,
    onClick: () -> Unit,
) {
    val network = tokenBalance?.token?.networkId?.let { rememberNetwork(it) }

    NftBalanceItem(
        modifier = modifier,
        network = network?.entity,
        token = tokenBalance?.token,
        balance = tokenBalance?.value,
        onClick = onClick,
    )
}

@Composable
fun NftBalanceItem(
    token: TokenEntity?,
    balance: Balance?,
    network: NetworkEntity?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick.single(),
        enabled = token != null,
    ) {

        Box {
            NftImage(
                image = token?.image,
                maxSize = 512
            )

            ChainBadge(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp),
                network = network
            )
        }

        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.bodyLarge
            ) {
                TextLine(
                    modifier = Modifier.weight(1f),
                    text = token?.tokenName ?: token?.name,
                )

                if (balance?.value != BigInteger.ZERO) {
                    CompositionLocalProvider(
                        LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant,
                    ) {
                        TextAmount(
                            amount = balance?.value,
                            decimals = token?.decimals,
                            symbol = "",
                        )
                    }
                }
            }
        }
    }
}
