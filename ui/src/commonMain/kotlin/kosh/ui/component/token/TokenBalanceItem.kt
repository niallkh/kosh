package kosh.ui.component.token

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.token.TokenBalance
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.icon.TokenIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.single.single
import kosh.ui.component.text.TextAmount
import kosh.ui.component.text.TextLine


@Composable
fun TokenBalanceItem(
    modifier: Modifier = Modifier,
    tokenBalance: TokenBalance,
    onClick: (() -> Unit)? = null,
) {
    val network = rememberNetwork(tokenBalance.token.networkId)

    TokenBalanceItem(
        modifier = modifier,
        token = tokenBalance.token,
        balance = tokenBalance.value.value,
        network = network.entity,
        onClick = onClick,
    )
}

@Composable
fun TokenBalanceItem(
    modifier: Modifier = Modifier,
    token: TokenEntity,
    balance: BigInteger,
    network: NetworkEntity?,
    onClick: (() -> Unit)? = null,
    overlineContent: @Composable (() -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(onClick?.single()),
        overlineContent = overlineContent,
        leadingContent = {
            Box {
                TokenIcon(
                    modifier = modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    token = token,
                )

                ChainBadge(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(2.dp, 2.dp),
                    network = network,
                )
            }
        },
        headlineContent = {
            TextLine(token.name)
        },
        trailingContent = {
            TextAmount(
                amount = balance,
                symbol = token.symbol,
                decimals = token.decimals,
            )
        },
    )
}
