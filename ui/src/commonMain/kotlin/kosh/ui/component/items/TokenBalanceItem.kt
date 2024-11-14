package kosh.ui.component.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.models.Uri
import kosh.domain.models.ethereum
import kosh.domain.models.token.Balance
import kosh.eth.abi.Value
import kosh.presentation.network.rememberNetwork
import kosh.ui.component.text.TextAmount
import kosh.ui.resources.Icons
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TokenBalanceItem(
    token: TokenEntity?,
    balance: Balance?,
    onClick: () -> Unit,
    network: NetworkEntity? = token?.networkId?.let { rememberNetwork(it) }?.entity,
    modifier: Modifier = Modifier,
) {
    TokenItem(
        token = token,
        network = network,
        modifier = modifier,
        onClick = onClick,
    ) {
        TextAmount(
            amount = balance?.value,
            token = token,
        )
    }
}

@Preview
@Composable
private fun TokenBalanceItemPreview() {
    val network = NetworkEntity(
        chainId = ethereum,
        name = "Ethereum",
        readRpcProvider = Uri(),
        icon = Icons.icon("eth")
    )

    val token = TokenEntity(
        networkId = NetworkEntity.Id(ethereum),
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
        type = TokenEntity.Type.Native,
        icon = Icons.icon("eth"),
    )

    Column(Modifier.verticalScroll(rememberScrollState())) {
        TokenBalanceItem(
            token = token,
            balance = Balance(BigInteger.ZERO),
            network = network,
            onClick = {}
        )

        for (i in 21..30) {
            PreviewToken(token.copy(name = token.name + " #$i"), network, i)
        }

        TokenBalanceItem(
            token = token,
            balance = Balance(Value.BigNumber.UINT256_MAX),
            network = network,
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun PreviewToken(
    token: TokenEntity,
    network: NetworkEntity,
    decimals: Int,
) {
    TokenBalanceItem(
        token = token,
        balance = Balance(BigInteger.TEN.pow(decimals)),
        network = network,
        onClick = {}
    )
}
