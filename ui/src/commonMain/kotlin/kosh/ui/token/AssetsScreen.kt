package kosh.ui.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.isNft
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.ImmutableList
import kosh.presentation.account.rememberWallets
import kosh.presentation.network.rememberNetworks
import kosh.presentation.token.rememberBalances
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.text.Header
import kosh.ui.component.token.NftBalanceItem
import kosh.ui.component.token.TokenBalanceItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.navigation.LocalRootNavigator
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.resources.illustrations.AssetsEmpty

@Composable
fun AssetsScreen(
    paddingValues: PaddingValues,
    open: (TokenEntity.Id, Boolean) -> Unit,
) {
    val balances = rememberBalances()

    AppFailureMessage(balances.failures) {
        balances.retry()
    }

    AssetsContent(
        paddingValues = paddingValues,
        balances = balances.balances,
        onSelect = { open(it.id, it.isNft) },
    )

    LoadingIndicator(
        balances.loading,
        Modifier.padding(paddingValues),
    )
}

@Composable
fun AssetsContent(
    paddingValues: PaddingValues,
    balances: ImmutableList<TokenBalance>,
    onSelect: (TokenEntity) -> Unit,
) {
    val wallets = rememberWallets()
    val networks = rememberNetworks()

    val rootNavigator = LocalRootNavigator.current

    LazyColumn(
        contentPadding = paddingValues,
    ) {

        when {
            wallets.enabled.isEmpty() -> item {
                Illustration(
                    AssetsEmpty(),
                    "AssetsEmpty",
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Get started by adding your first wallet",
                            textAlign = TextAlign.Center
                        )

                        TextButton(onClick = {
                            rootNavigator.open(RootRoute.Wallets())
                        }) {
                            Text("Add Wallet")
                        }
                    }
                }
            }

            networks.enabled.isEmpty() -> item {
                Illustration(
                    AssetsEmpty(),
                    "AssetsEmpty",
                    Modifier
                        .fillMaxWidth()
                        .padding(64.dp),
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "Get started by adding your first network",
                            textAlign = TextAlign.Center
                        )

                        TextButton(onClick = {
                            rootNavigator.open(RootRoute.Networks())
                        }) {
                            Text("Add Network")
                        }
                    }
                }
            }

            else -> balances(balances, onSelect)
        }

        item { Spacer(Modifier.height(64.dp)) }
    }
}

private fun LazyListScope.balances(
    balances: ImmutableList<TokenBalance>,
    onSelect: (TokenEntity) -> Unit,
) {
    val (tokenBalances, nftBalances) = balances.partition { !it.token.isNft }

    if (tokenBalances.isNotEmpty()) {
        stickyHeader {
            Header("Tokens")
        }
    }

    items(
        items = tokenBalances,
        key = { it.token.id.value.toString() }
    ) { tokenBalance ->
        TokenBalanceItem(
            modifier = Modifier.animateItem(),
            tokenBalance = tokenBalance,
            onClick = { onSelect(tokenBalance.token) }
        )
    }

    if (nftBalances.isNotEmpty()) {
        stickyHeader {
            Header("NFTs")
        }
    }

    items(
        items = nftBalances.chunked(2),
        key = {
            it[0].token.id.value.leastSignificantBits xor
                    (it.getOrNull(1)?.token?.id?.value?.leastSignificantBits
                        ?: Long.MIN_VALUE)

        },
    ) { nfts ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                val tokenBalance = nfts[0]
                NftBalanceItem(
                    tokenBalance = tokenBalance,
                    onClick = { onSelect(tokenBalance.token) }
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                nfts.getOrNull(1)?.let { tokenBalance ->
                    NftBalanceItem(
                        tokenBalance = tokenBalance,
                        onClick = { onSelect(tokenBalance.token) }
                    )
                }
            }
        }
    }

    item {
        Spacer(Modifier.height(64.dp))
    }
}
