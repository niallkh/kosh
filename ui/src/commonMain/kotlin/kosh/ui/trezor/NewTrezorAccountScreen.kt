package kosh.ui.trezor

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.account.ethereumAddressIndex
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberCreateAccount
import kosh.presentation.keystore.rememberKeyStoreListener
import kosh.presentation.trezor.TrezorAccountsState
import kosh.presentation.trezor.rememberTrezor
import kosh.presentation.trezor.rememberTrezorAccounts
import kosh.presentation.trezor.rememberTrezorListener
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.signer.SignerItem
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage
import kosh.ui.keystore.KeyStoreListenerContent

@Composable
fun NewTrezorAccountScreen(
    onFinish: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoshScaffold(
        modifier = modifier,
        title = { Text("Trezor New Account") },
        onUp = onNavigateUp
    ) {
        val trezor = rememberTrezor()

        trezor.trezor?.let { ledger ->
            val keyStoreListener = rememberKeyStoreListener()

            val trezorListener = rememberTrezorListener(null, keyStoreListener.listener)

            TrezorListenerContent(
                trezorListener = trezorListener,
            )

            KeyStoreListenerContent(
                keyStoreListener = keyStoreListener
            )

            val createAccountState = rememberCreateAccount()

            AppFailureMessage(createAccountState.failure)

            LaunchedEffect(createAccountState.created) {
                if (createAccountState.created) {
                    onFinish()
                }
            }

            val trezorAccounts = rememberTrezorAccounts(
                listener = trezorListener.listener,
                trezor = ledger,
            )

            NewTrezorAccountContent(
                trezorAccounts = trezorAccounts,
                onSelect = { createAccountState.create(it) }
            )

            LoadingIndicator(
                createAccountState.loading || trezorAccounts.loading
            )
        } ?: AppFailureItem(
            TrezorFailure.NotConnected(),
        )
    }
}

@Composable
fun NewTrezorAccountContent(
    trezorAccounts: TrezorAccountsState,
    onSelect: (Signer) -> Unit,
) {
    LazyColumn {
        trezorAccounts.failure?.let {
            item {
                AppFailureItem(it) {
                    trezorAccounts.retry(true)
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