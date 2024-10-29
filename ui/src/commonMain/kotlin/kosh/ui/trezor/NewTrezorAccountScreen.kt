package kosh.ui.trezor

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.domain.models.account.ethereumAddressIndex
import kosh.domain.models.hw.Trezor
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberCreateAccount
import kosh.presentation.keystore.rememberKeyStoreListener
import kosh.presentation.trezor.TrezorAccountsState
import kosh.presentation.trezor.rememberTrezorAccounts
import kosh.presentation.trezor.rememberTrezorListener
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.items.SignerItem
import kosh.ui.component.refresh.PullRefreshBox
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.keystore.KeyStoreListenerContent

@Composable
fun NewTrezorAccountScreen(
    trezor: Trezor,
    onNavigateUp: () -> Unit,
    onFinish: (AccountEntity.Id) -> Unit,
) {
    val keyStoreListener = rememberKeyStoreListener()
    val trezorListener = rememberTrezorListener(null, keyStoreListener.listener)
    val trezorAccounts = rememberTrezorAccounts(
        listener = trezorListener.listener,
        trezor = trezor,
    )
    val refreshState = rememberPullToRefreshState()

    KoshScaffold(
        modifier = Modifier
            .pullToRefresh(
                isRefreshing = trezorAccounts.refreshing,
                state = refreshState,
                onRefresh = { trezorAccounts.refresh() },
                enabled = !trezorAccounts.loading,
            ),
        title = { Text("Trezor New Account") },
        onNavigateUp = onNavigateUp
    ) { contentPadding ->

        TrezorListenerContent(
            trezorListener = trezorListener,
        )

        KeyStoreListenerContent(
            keyStoreListener = keyStoreListener
        )

        val createAccountState = rememberCreateAccount()

        AppFailureMessage(createAccountState.failure)

        LaunchedEffect(createAccountState.createdAccount) {
            createAccountState.createdAccount?.let(onFinish)
        }

        PullRefreshBox(
            modifier = Modifier.padding(contentPadding),
            isRefreshing = trezorAccounts.refreshing,
            state = refreshState,
        ) {
            NewTrezorAccountContent(
                trezorAccounts = trezorAccounts,
                paddingValues = contentPadding,
                onSelect = { createAccountState.create(it) },
            )
        }

        LoadingIndicator(
            createAccountState.loading ||
                    trezorAccounts.loading && !trezorAccounts.refreshing,
            Modifier.padding(contentPadding),
        )
    }
}

@Composable
fun NewTrezorAccountContent(
    trezorAccounts: TrezorAccountsState,
    paddingValues: PaddingValues = PaddingValues(),
    onSelect: (Signer) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues
    ) {
        trezorAccounts.failure?.let {
            item {
                AppFailureItem(it) {
                    trezorAccounts.retry()
                }
            }
        } ?: run {
            items(
                items = trezorAccounts.accounts,
                key = { signer -> signer.derivationPath.ethereumAddressIndex.toInt() }
            ) { signer ->
                SignerItem(signer) {
                    onSelect(signer)
                }
            }
        }
    }
}
