package kosh.ui.component.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
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


@Composable
fun NftBalanceItem(
    modifier: Modifier = Modifier,
    tokenBalance: TokenBalance,
    onClick: () -> Unit = {},
) {
    val network = rememberNetwork(tokenBalance.token.networkId)

    NftBalanceItem(
        modifier = modifier,
        network = network.entity,
        token = tokenBalance.token,
        balance = tokenBalance.value,
        onClick = onClick,
    )
}

@Composable
fun NftBalanceItem(
    modifier: Modifier = Modifier,
    token: TokenEntity,
    balance: Balance,
    network: NetworkEntity?,
    onClick: () -> Unit = {},
) {
    OutlinedCard(
        modifier = modifier,
        onClick = onClick.single()
    ) {

        Box {
            NftImage(
                image = token.image!!,
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
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = token.tokenName ?: token.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (balance.value != BigInteger.ZERO) {
                CompositionLocalProvider(
                    LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant,
                    LocalTextStyle provides MaterialTheme.typography.bodyLarge
                ) {
                    TextAmount(
                        amount = balance.value,
                        decimals = token.decimals,
                        symbol = "",
                    )
                }
            }
        }
    }
}
