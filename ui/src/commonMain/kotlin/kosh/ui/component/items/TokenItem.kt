package kosh.ui.component.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Address
import kosh.domain.models.Uri
import kosh.domain.models.ethereum
import kosh.domain.models.token.TokenMetadata
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.icon.ChainBadge
import kosh.ui.component.icon.TokenIcon
import kosh.ui.component.modifier.optionalClickable
import kosh.ui.component.single.single
import kosh.ui.component.text.TextLine
import kosh.ui.resources.Icons
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TokenItem(
    token: TokenMetadata?,
    network: NetworkEntity? = token?.chainId?.let { rememberNetwork(it) }?.entity,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(token != null, onClick?.single()),
        leadingContent = {
            Box {
                TokenIcon(
                    token = token,
                    modifier = modifier
                        .size(40.dp),
                )

                ChainBadge(
                    network = network,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(2.dp, 2.dp),
                )
            }
        },
        headlineContent = { TextLine(token?.name) },
        trailingContent = trailingContent,
    )
}

@Composable
fun TokenItem(
    token: TokenEntity?,
    network: NetworkEntity? = token?.networkId?.let { rememberNetwork(it) }?.entity,
    onClick: (() -> Unit)?,
    modifier: Modifier = Modifier,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    ListItem(
        modifier = modifier.optionalClickable(token != null, onClick?.single()),
        leadingContent = {
            Box {
                TokenIcon(
                    token = token,
                    modifier = modifier
                        .size(40.dp),
                )

                ChainBadge(
                    network = network,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(2.dp, 2.dp),
                )
            }
        },
        headlineContent = { TextLine(token?.name) },
        trailingContent = trailingContent,
    )
}

@Preview
@Composable
private fun TokenItemPreview() {
    Column {
        val network = NetworkEntity(
            chainId = ethereum,
            name = "Ethereum",
            readRpcProvider = Uri(),
            icon = Icons.icon("eth")
        )

        TokenItem(
            token = null as TokenEntity?,
            network = null,
            onClick = {}
        )

        TokenItem(
            token = TokenEntity(
                networkId = NetworkEntity.Id(ethereum),
                name = "Ether",
                symbol = "ETH",
                decimals = 18u,
                type = TokenEntity.Type.Native,
                icon = Icons.icon("eth"),
            ),
            network = network,
            onClick = {}
        )

        TokenItem(
            token = TokenEntity(
                networkId = NetworkEntity.Id(ethereum),
                address = Address("0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2").getOrNull()!!,
                name = "Wrapped Ether",
                symbol = "WETH",
                decimals = 18u,
                type = TokenEntity.Type.Erc20,
                icon = Icons.icon("weth"),
            ),
            network = network,
            onClick = {}
        )
    }
}
