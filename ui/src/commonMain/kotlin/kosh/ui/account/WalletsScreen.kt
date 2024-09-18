package kosh.ui.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arrow.core.partially1
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.entities.WalletEntity.Type
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.ImmutableSet
import kosh.presentation.account.rememberToggleAccount
import kosh.presentation.account.rememberWallets
import kosh.ui.component.icon.AccountIcon
import kosh.ui.component.illustration.Illustration
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.TextAddressShort
import kosh.ui.component.text.TextLine
import kosh.ui.resources.Res
import kosh.ui.resources.icons.LedgerIcon
import kosh.ui.resources.icons.TrezorIcon
import kosh.ui.resources.illustrations.WalletsEmpty
import kosh.ui.resources.wallets_title
import kotlinx.collections.immutable.PersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun WalletsScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onOpen: (AccountEntity.Id) -> Unit,
    onAdd: (Type) -> Unit,
) {
    val wallets = rememberWallets()

    val toggleAccount = rememberToggleAccount()

    WalletsContent(
        wallets = wallets.wallets,
        enabled = wallets.enabled,
        onAdd = onAdd,
        onNavigateUp = onNavigateUp,
        onSelect = { onOpen(it.id) },
        onToggle = { account, enabled -> toggleAccount.toggle(account.id, enabled) },
        modifier = modifier,
    )
}

@Composable
fun WalletsContent(
    wallets: ImmutableList<Pair<WalletEntity, PersistentList<AccountEntity>>>,
    enabled: ImmutableSet<AccountEntity.Id>,
    onAdd: (Type) -> Unit,
    onSelect: (AccountEntity) -> Unit,
    onToggle: (AccountEntity, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoshScaffold(
        modifier = modifier,
        title = { Text(stringResource(Res.string.wallets_title)) },
        floatingActionButton = {
            var selectorVisible by remember { mutableStateOf(false) }

            WalletTypeSelector(
                visible = selectorVisible,
                onSelected = { onAdd(it) },
                onDismiss = { selectorVisible = false }
            )

            FloatingActionButton(
                onClick = { selectorVisible = true },
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Wallet")
            }
        },
        onNavigateUp = { onNavigateUp() }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (wallets.isEmpty()) {
                item {
                    Illustration(
                        WalletsEmpty(),
                        "AccountsEmpty",
                        Modifier
                            .fillMaxWidth()
                            .padding(64.dp),
                    ) {
                        Text(
                            "Get started by adding your first wallet",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            items(
                items = wallets,
                key = { it.first.id.value.leastSignificantBits }
            ) { (wallet, accounts) ->
                WalletAccountsItem(
                    wallet = wallet,
                    accounts = accounts,
                    enabled = enabled,
                    onSelect = { onSelect(it) },
                    onToggle = onToggle
                )
            }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }
}

@Composable
private fun WalletAccountsItem(
    modifier: Modifier = Modifier,
    wallet: WalletEntity,
    accounts: ImmutableList<AccountEntity>,
    enabled: ImmutableSet<AccountEntity.Id>,
    onSelect: (AccountEntity) -> Unit,
    onToggle: (AccountEntity, Boolean) -> Unit,
) {
    OutlinedCard(
        modifier = modifier,
    ) {
        WalletItem(wallet)

        for (account in accounts) {
            key(account.id.value.leastSignificantBits) {
                HorizontalDivider()

                AccountItem(
                    account = account,
                    onClick = onSelect.partially1(account),
                    trailingContent = {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            VerticalDivider(
                                modifier = Modifier.height(32.dp),
                            )

                            Switch(
                                checked = account.id in enabled,
                                onCheckedChange = { onToggle(account, it) },
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AccountItem(
    modifier: Modifier = Modifier,
    account: AccountEntity,
    onClick: () -> Unit,
    trailingContent: @Composable () -> Unit,
) {
    ListItem(
        modifier = modifier.clickable(onClick = onClick),
        leadingContent = {
            AccountIcon(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp),
                address = account.address,
            )
        },
        headlineContent = {
            TextLine(account.name)
        },
        supportingContent = {
            TextAddressShort(account.address)
        },
        trailingContent = trailingContent
    )

}

@Composable
private fun WalletItem(
    wallet: WalletEntity,
    modifier: Modifier = Modifier,
) {
    ListItem(
        modifier = modifier,
        leadingContent = {
            Box(
                modifier = Modifier.size(40.dp),
                contentAlignment = Alignment.Center
            ) {
                when (wallet.location) {
                    is WalletEntity.Location.Trezor -> Icon(TrezorIcon, "Trezor")
                    is WalletEntity.Location.Ledger -> Icon(LedgerIcon, "Ledger")
                }
            }
        },
        supportingContent = {
            when (val location = wallet.location) {
                is WalletEntity.Location.Trezor -> TextLine(
                    location.name ?: location.product ?: "Trezor"
                )

                is WalletEntity.Location.Ledger -> TextLine(location.product ?: "Ledger")
            }
        },
        headlineContent = {
            TextLine(wallet.name)
        },
    )
}

