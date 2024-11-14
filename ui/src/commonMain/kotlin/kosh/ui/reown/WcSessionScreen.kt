package kosh.ui.reown

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import kosh.domain.models.reown.WcSession
import kosh.domain.uuid.leastSignificantBits
import kosh.presentation.account.AccountMultiSelectorState
import kosh.presentation.account.rememberAccountMultiSelector
import kosh.presentation.network.NetworkMultiSelectorState
import kosh.presentation.network.rememberNetworkMultiSelector
import kosh.presentation.wc.DisconnectSessionState
import kosh.presentation.wc.SessionState
import kosh.presentation.wc.UpdateSessionState
import kosh.presentation.wc.rememberDisconnectSession
import kosh.presentation.wc.rememberSession
import kosh.presentation.wc.rememberUpdateSession
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.LoadingTextButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.items.AccountItem
import kosh.ui.component.items.NetworkItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.Header
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_session_disconnect_btn
import kosh.ui.resources.wc_session_update_btn
import kotlinx.collections.immutable.persistentSetOf
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcSessionScreen(
    id: WcSession.Id,
    onCancel: () -> Unit,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
    session: SessionState = rememberSession(id),
    disconnectSession: DisconnectSessionState = rememberDisconnectSession(id, onCancel),
    updateSession: UpdateSessionState = rememberUpdateSession(id, onFinish),
    accountSelector: AccountMultiSelectorState = rememberAccountMultiSelector(
        selectedIds = session.session?.approvedAccounts ?: persistentSetOf(),
    ),
    networkSelector: NetworkMultiSelectorState = rememberNetworkMultiSelector(
        networkIds = session.session?.availableNetworks ?: persistentSetOf(),
        requiredIds = session.session?.requiredNetworks ?: persistentSetOf(),
        selectedIds = session.session?.approvedNetworks ?: persistentSetOf(),
    ),
) {

    KoshScaffold(
        title = {
            if (session.failure == null) {
                DappTitle(session.session?.session?.dapp)
            }
        },

        onNavigateUp = { onNavigateUp() },
        actions = {
            if (session.failure == null) {
                DappIcon(session.session?.session?.dapp)
                Spacer(Modifier.width(8.dp))
            }
        }
    ) { paddingValues ->

        AppFailureMessage(updateSession.failure)

        AppFailureMessage(disconnectSession.failure)

        WcSessionContent(
            session = session,
            networkSelector = networkSelector,
            accountSelector = accountSelector,
            disconnecting = disconnectSession.disconnecting,
            updating = updateSession.updating,
            onDisconnect = { disconnectSession() },
            onUpdate = {
                nullable {
                    updateSession(
                        ensureNotNull(accountSelector).accounts
                            .filter { it.id in accountSelector.selected },
                        ensureNotNull(networkSelector).networks
                            .filter { it.id in networkSelector.selected },
                    )
                }
            },
            contentPadding = paddingValues,
        )

        LoadingIndicator(
            session.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun WcSessionContent(
    session: SessionState,
    networkSelector: NetworkMultiSelectorState,
    accountSelector: AccountMultiSelectorState,
    updating: Boolean,
    disconnecting: Boolean,
    onDisconnect: () -> Unit,
    onUpdate: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(),
) {
    session.failure?.let {
        AppFailureItem(it) { session.retry() }
    } ?: LazyColumn(
        contentPadding = contentPadding,
    ) {

        stickyHeader {
            session.session?.session?.dapp?.description?.let {
                Header("Description")
            }
        }

        item {
            session.session?.session?.dapp?.description?.let { description ->
                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        stickyHeader {
            Header("Networks")
        }

        items(
            items = networkSelector.networks,
            key = { it.id.value.leastSignificantBits }
        ) { network ->
            NetworkItem(
                network = network,
                onClick = { networkSelector(network.id) },
            ) {
                Checkbox(
                    checked = network.id in networkSelector.selected,
                    onCheckedChange = { networkSelector(network.id) },
                    enabled = network.id !in networkSelector.required
                )
            }
        }

        stickyHeader {
            Header("Accounts")
        }

        items(
            items = accountSelector.accounts,
            key = { it.id.value.leastSignificantBits }
        ) { account ->
            AccountItem(
                account = account,
                onClick = { accountSelector.select(account.id) },
            ) {
                Checkbox(
                    checked = account.id in accountSelector.selected,
                    onCheckedChange = { accountSelector.select(account.id) }
                )
            }
        }

        item {
            PrimaryButtons(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                cancel = {
                    LoadingTextButton(disconnecting, onDisconnect) {
                        Text(stringResource(Res.string.wc_session_disconnect_btn))
                    }
                },
                confirm = {
                    LoadingButton(updating, onUpdate) {
                        Text(stringResource(Res.string.wc_session_update_btn))
                    }
                }
            )
        }

        item {
            Spacer(Modifier.height(128.dp))
        }
    }
}
