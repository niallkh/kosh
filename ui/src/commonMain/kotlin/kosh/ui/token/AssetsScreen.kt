package kosh.ui.token

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.isNft
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.ImmutableList
import kosh.domain.uuid.leastSignificantBits
import kosh.presentation.account.WalletState
import kosh.presentation.account.rememberWallets
import kosh.presentation.network.NetworksState
import kosh.presentation.network.rememberNetworks
import kosh.presentation.token.BalancesState
import kosh.presentation.token.rememberBalances
import kosh.presentation.token.rememberUpdateBalances
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.cards.NftBalanceCard
import kosh.ui.component.items.TokenBalanceItem
import kosh.ui.component.refresh.PullRefreshBox
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.single.single
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.icons.Networks
import kotlinx.collections.immutable.toPersistentList

@Composable
fun AssetsScreen(
    onOpenToken: (TokenEntity.Id, Boolean) -> Unit,
    onOpenWallets: () -> Unit,
    onOpenNetworks: () -> Unit,
    onAddToken: () -> Unit,
) {
    val updateBalances = rememberUpdateBalances()
    val balances = rememberBalances()
    val refreshState = rememberPullToRefreshState()

    KoshScaffold(
        modifier = Modifier
            .pullToRefresh(
                isRefreshing = updateBalances.refreshing,
                state = refreshState,
                onRefresh = { updateBalances.refresh() },
                enabled = balances.init,
            ),
        title = { Text("Assets") },
        actions = {
            IconButton(onClick = onOpenNetworks.single()) {
                Icon(Networks, "Networks")
            }

            IconButton(onClick = onOpenWallets.single()) {
                Icon(Icons.Outlined.AccountBalanceWallet, "Accounts")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddToken.single()) {
                Icon(Icons.Default.Add, contentDescription = "Add Token")
            }
        },
        onNavigateUp = null,
    ) { contentPadding ->
        AppFailureMessage(updateBalances.failures)

        PullRefreshBox(
            isRefreshing = updateBalances.refreshing,
            state = refreshState,
            modifier = Modifier.padding(contentPadding),
        ) {
            AssetsContent(
                isRefreshing = updateBalances.refreshing,
                balances = balances,
                onSelectToken = { onOpenToken(it.id, it.isNft) },
                onOpenWallets = onOpenWallets,
                onOpenNetworks = onOpenNetworks,
                contentPadding = contentPadding,
            )
        }

        LoadingIndicator(
            updateBalances.loading && !updateBalances.refreshing,
            Modifier.padding(contentPadding),
        )
    }
}

@Composable
fun AssetsContent(
    isRefreshing: Boolean,
    balances: BalancesState = rememberBalances(),
    wallets: WalletState = rememberWallets(),
    networks: NetworksState = rememberNetworks(),
    onSelectToken: (TokenEntity) -> Unit,
    onOpenWallets: () -> Unit,
    onOpenNetworks: () -> Unit,
    contentPadding: PaddingValues,
) {
    LazyColumn(
        userScrollEnabled = !isRefreshing,
        contentPadding = contentPadding,
        modifier = Modifier.fillMaxSize(),
    ) {
        when {
            !balances.init -> {
                balances(
                    tokenBalances = List(5) { null }.toPersistentList(),
                    nftBalances = List(1) { null }.toPersistentList(),
                    onSelect = onSelectToken
                )
            }

            wallets.enabled.isEmpty() -> item {
                EmptyAssetsContent(Modifier.animateItem()) {
                    OpenWallets(onOpenWallets)
                }
            }

            networks.enabled.isEmpty() -> item {
                EmptyAssetsContent(Modifier.animateItem()) {
                    OpenNetworks(onOpenNetworks)
                }
            }

            else -> balances(
                tokenBalances = balances.balances.toPersistentList().removeAll { it.token.isNft },
                nftBalances = balances.balances.toPersistentList().removeAll { !it.token.isNft },
                onSelect = onSelectToken
            )
        }

        item { Spacer(Modifier.height(64.dp)) }
    }
}

private fun LazyListScope.balances(
    tokenBalances: ImmutableList<TokenBalance?>,
    nftBalances: ImmutableList<TokenBalance?>,
    onSelect: (TokenEntity) -> Unit,
) {
    if (tokenBalances.isNotEmpty()) {
        stickyHeader {
            Header(
                "Tokens",
                Modifier.animateItem(),
            )
        }
    }

    items(
        count = tokenBalances.size,
        key = { tokenBalances[it]?.token?.id?.value?.leastSignificantBits ?: it }
    ) { i ->
        val tokenBalance = tokenBalances[i]
        TokenBalanceItem(
            modifier = Modifier.animateItem(),
            token = tokenBalance?.token,
            balance = tokenBalance?.value,
            onClick = { tokenBalance?.let { onSelect(it.token) } }
        )
    }

    if (nftBalances.isNotEmpty()) {
        stickyHeader {
            Header(
                "NFTs",
                Modifier.animateItem()
            )
        }
    }

    val nfts = nftBalances.chunked(2)

    items(
        count = nfts.size,
        key = {
            (nfts[it][0]?.token?.id?.value?.leastSignificantBits ?: it.toLong()) xor
                    (nfts[it].getOrNull(1)?.token?.id?.value?.leastSignificantBits ?: 0L)
        },
    ) { index ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .animateItem()
            ) {
                val tokenBalance = nfts[index][0]
                NftBalanceCard(
                    token = tokenBalance?.token,
                    balance = tokenBalance?.value,
                    onClick = { tokenBalance?.token?.let { onSelect(it) } }
                )
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .animateItem()
            ) {
                nfts[index].getOrNull(1)?.let { tokenBalance ->
                    NftBalanceCard(
                        token = tokenBalance.token,
                        balance = tokenBalance.value,
                        onClick = { onSelect(tokenBalance.token) }
                    )
                }
            }
        }
    }
}
