package kosh.ui.wallet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.router.slot.dismiss
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.presentation.account.rememberToggleAccount
import kosh.presentation.account.rememberWallets
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.items.AccountItem
import kosh.ui.component.placeholder.placeholder
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.TextLine
import kosh.ui.navigation.slot.Slot
import kosh.ui.navigation.slot.SlotHost
import kosh.ui.navigation.slot.activate
import kosh.ui.navigation.slot.rememberSlotRouter
import kosh.ui.resources.Res
import kosh.ui.resources.icons.LedgerIcon
import kosh.ui.resources.icons.TrezorIcon
import kosh.ui.resources.illustrations.WalletsEmpty
import kosh.ui.resources.wallets_title
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun WalletsScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onOpen: (AccountEntity.Id) -> Unit,
    onAdd: (HardwareWallet) -> Unit,
) {
    KoshScaffold(
        modifier = modifier,
        title = { Text(stringResource(Res.string.wallets_title)) },
        floatingActionButton = {
            val slotRouter = rememberSlotRouter<Slot>()

            SlotHost(slotRouter) {
                HardwareWalletSelector(
                    onDismiss = slotRouter::dismiss,
                    onSelected = onAdd
                )
            }

            FloatingActionButton(
                onClick = { slotRouter.activate() },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Wallet")
            }
        },
        onNavigateUp = { onNavigateUp() }
    ) { paddingValues ->
        val wallets = rememberWallets()
        val toggleAccount = rememberToggleAccount()

        WalletsContent(
            init = wallets.init,
            wallets = wallets.wallets,
            enabled = wallets.enabled,
            onSelect = { onOpen(it.id) },
            onToggle = { account, enabled -> toggleAccount.toggle(account.id, enabled) },
            modifier = modifier,
            contentPadding = paddingValues
        )
    }
}

@Composable
fun WalletsContent(
    init: Boolean,
    wallets: ImmutableList<Pair<WalletEntity, PersistentList<AccountEntity>>>,
    enabled: ImmutableSet<AccountEntity.Id>,
    onSelect: (AccountEntity) -> Unit,
    onToggle: (AccountEntity, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        when {
            !init -> wallets(
                wallets = List(1) {
                    null to List(3) { null }.toPersistentList()
                }.toPersistentList(),
                enabled = persistentSetOf(),
                onSelect = onSelect,
                onToggle = onToggle,
            )

            wallets.isEmpty() -> item {
                EmptyWalletContent(Modifier.animateItem())
            }

            else -> wallets(
                wallets = wallets,
                enabled = enabled,
                onSelect = onSelect,
                onToggle = onToggle
            )
        }

        item {
            Spacer(Modifier.height(64.dp))
        }
    }
}

private fun LazyListScope.wallets(
    wallets: ImmutableList<Pair<WalletEntity?, ImmutableList<AccountEntity?>>>,
    enabled: ImmutableSet<AccountEntity.Id>,
    onSelect: (AccountEntity) -> Unit,
    onToggle: (AccountEntity, Boolean) -> Unit,
) {
    items(
        count = wallets.size,
        key = { wallets[it].first?.id?.value?.leastSignificantBits ?: it }
    ) { index ->
        val (wallet, accounts) = wallets[index]

        WalletAccountsItem(
            modifier = Modifier.animateItem(),
            wallet = wallet,
            accounts = accounts,
            enabled = enabled,
            onSelect = { onSelect(it) },
            onToggle = onToggle
        )
    }
}

@Composable
private fun EmptyWalletContent(
    modifier: Modifier = Modifier,
) {
    Illustration(
        WalletsEmpty(),
        "AccountsEmpty",
        modifier
            .fillMaxWidth()
            .padding(64.dp),
    ) {
        Text(
            "Get started by adding your first wallet",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WalletAccountsItem(
    modifier: Modifier = Modifier,
    wallet: WalletEntity?,
    accounts: ImmutableList<AccountEntity?>,
    enabled: ImmutableSet<AccountEntity.Id>,
    onSelect: (AccountEntity) -> Unit,
    onToggle: (AccountEntity, Boolean) -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        WalletItem(wallet)

        for ((index, account) in accounts.withIndex()) {
            key(account?.id?.value?.leastSignificantBits ?: index) {
                HorizontalDivider()

                AccountItem(
                    account = account,
                    onClick = { if (account != null) onSelect(account) },
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        VerticalDivider(
                            modifier = Modifier
                                .height(32.dp)
                                .placeholder(account == null),
                        )

                        Switch(
                            checked = account?.id in enabled,
                            onCheckedChange = { checked ->
                                account?.let { onToggle(it, checked) }
                            },
                            modifier = Modifier.placeholder(account == null)
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun WalletItem(
    wallet: WalletEntity?,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .placeholder(wallet == null),
                contentAlignment = Alignment.Center
            ) {
                when (wallet?.location) {
                    is WalletEntity.Location.Trezor -> Icon(TrezorIcon, "Trezor")
                    is WalletEntity.Location.Ledger -> Icon(LedgerIcon, "Ledger")
                    null -> Icon(TrezorIcon, "Trezor")
                }
            }
        },
        supportingContent = {
            val text = when (val location = wallet?.location) {
                is WalletEntity.Location.Trezor -> location.name ?: location.product ?: "Trezor"
                is WalletEntity.Location.Ledger -> location.product ?: "Ledger"
                null -> null
            }

            TextLine(text)
        },
        headlineContent = {
            TextLine(wallet?.name)
        },
    )
}

