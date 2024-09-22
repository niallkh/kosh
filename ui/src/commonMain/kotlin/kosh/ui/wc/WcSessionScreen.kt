package kosh.ui.wc

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.raise.nullable
import arrow.optics.Getter
import kosh.domain.models.wc.WcSession
import kosh.domain.state.AppState
import kosh.domain.state.accounts
import kosh.domain.state.networks
import kosh.presentation.account.AccountMultiSelectorState
import kosh.presentation.account.rememberAccountMultiSelector
import kosh.presentation.network.NetworkMultiSelectorState
import kosh.presentation.network.rememberNetworkMultiSelector
import kosh.presentation.wc.SessionState
import kosh.presentation.wc.UpdateSessionState
import kosh.presentation.wc.rememberDisconnectSession
import kosh.presentation.wc.rememberSession
import kosh.presentation.wc.rememberUpdateSession
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.button.LoadingButton
import kosh.ui.component.button.PrimaryButtons
import kosh.ui.component.dapp.DappIcon
import kosh.ui.component.dapp.DappTitle
import kosh.ui.component.network.NetworkItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.text.Header
import kosh.ui.component.wallet.AccountItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.resources.Res
import kosh.ui.resources.wc_session_disconnect_btn
import kosh.ui.resources.wc_session_update_btn
import kotlinx.collections.immutable.toPersistentList
import org.jetbrains.compose.resources.stringResource

@Composable
fun WcSessionScreen(
    id: WcSession.Id,
    onCancel: () -> Unit,
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
) {
    val session = rememberSession(id)
    val disconnect = rememberDisconnectSession(id)
    val update = rememberUpdateSession(id)

    val accountSelector = session.session?.let { sessionAggregated ->
        rememberAccountMultiSelector(
            optic = AppState.accounts compose Getter { it.values.toPersistentList() },
            initialSelected = sessionAggregated.approvedAccounts
        )
    }

    val networkSelector = session.session?.let { sessionAggregated ->
        rememberNetworkMultiSelector(
            initial = AppState.networks compose Getter { map ->
                map.values.filter { it.id in sessionAggregated.availableNetworks }
                    .toPersistentList()
            },
            initialRequired = sessionAggregated.requiredNetworks,
            initialSelected = sessionAggregated.approvedNetworks,
        )
    }

    LaunchedEffect(update.updated) {
        if (update.updated) {
            onFinish()
        }
    }

    LaunchedEffect(disconnect.disconnected) {
        if (disconnect.disconnected) {
            onCancel()
        }
    }

    WcSessionContent(
        session = session,
        networkSelector = networkSelector,
        accountSelector = accountSelector,
        update = update,
        onNavigateUp = onNavigateUp,
        onDisconnect = { disconnect.disconnect() }
    )
}

@Composable
fun WcSessionContent(
    session: SessionState,
    networkSelector: NetworkMultiSelectorState?,
    accountSelector: AccountMultiSelectorState?,
    update: UpdateSessionState,
    onNavigateUp: () -> Unit,
    onDisconnect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoshScaffold(
        modifier = modifier,
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

        AppFailureMessage(update.failure) {
            update.retry()
        }

        LazyColumn(
            contentPadding = paddingValues,
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
                items = networkSelector?.available.orEmpty(),
                key = { it.id.value.leastSignificantBits }
            ) { network ->
                NetworkItem(
                    network = network,
                    onClick = { networkSelector?.select?.invoke(network.id) },
                ) {
                    Checkbox(
                        checked = network.id in networkSelector?.selected.orEmpty(),
                        onCheckedChange = { networkSelector?.select?.invoke(network.id) },
                        enabled = network.id !in networkSelector?.required.orEmpty()
                    )
                }
            }

            stickyHeader {
                Header("Accounts")
            }

            items(
                items = accountSelector?.available.orEmpty(),
                key = { it.id.value.leastSignificantBits }
            ) { account ->
                AccountItem(
                    account = account,
                    onClick = { accountSelector?.select?.invoke(account.id) },
                ) {
                    Checkbox(
                        checked = account.id in accountSelector?.selected.orEmpty(),
                        onCheckedChange = { accountSelector?.select?.invoke(account.id) }
                    )
                }
            }

            item {
                PrimaryButtons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    cancel = {
                        TextButton(onDisconnect) {
                            Text(stringResource(Res.string.wc_session_disconnect_btn))
                        }
                    },
                    confirm = {
                        LoadingButton(update.loading, onClick = {
                            nullable {
                                update.update(
                                    ensureNotNull(accountSelector).available
                                        .filter { it.id in accountSelector.selected }
                                        .map { it.address },
                                    ensureNotNull(networkSelector).available
                                        .filter { it.id in networkSelector.selected }
                                        .map { it.chainId },
                                )
                            }
                        }) {
                            Text(stringResource(Res.string.wc_session_update_btn))
                        }
                    }
                )
            }

            item {
                Spacer(Modifier.height(64.dp))
            }
        }

        LoadingIndicator(
            session.loading,
            Modifier.padding(paddingValues),
        )
    }
}
