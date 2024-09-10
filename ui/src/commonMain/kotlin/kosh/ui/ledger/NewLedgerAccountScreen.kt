package kosh.ui.ledger

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.account.ledgerAddressIndex
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberCreateAccount
import kosh.presentation.ledger.LedgerAccountsState
import kosh.presentation.ledger.rememberLedger
import kosh.presentation.ledger.rememberLedgerAccounts
import kosh.presentation.ledger.rememberLedgerListener
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.component.signer.SignerItem
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun NewLedgerAccountScreen(
    onNavigateUp: () -> Unit,
    onResult: () -> Unit,
    modifier: Modifier = Modifier,
) {
    KoshScaffold(
        modifier = modifier,
        title = { Text("Ledger New Account") },
        onUp = onNavigateUp,
    ) {
        val ledgerState = rememberLedger()

        ledgerState.ledger?.let { ledger ->
            val ledgerListenerState = rememberLedgerListener()

            LedgerButtonRequest(
                request = ledgerListenerState.buttonRequest,
                onConfirm = ledgerListenerState.confirm,
                onDismiss = ledgerListenerState.dismiss
            )

            val createAccountState = rememberCreateAccount()

            AppFailureMessage(createAccountState.failure)

            LaunchedEffect(createAccountState.created) {
                if (createAccountState.created) {
                    onResult()
                }
            }

            val ledgerAccountsState = rememberLedgerAccounts(
                listener = ledgerListenerState.listener,
                ledger = ledger,
            )

            NewLedgerAccountContent(
                ledgerAccountsState = ledgerAccountsState,
                onSelect = { createAccountState.create(it) }
            )

            LoadingIndicator(
                createAccountState.loading || ledgerAccountsState.loading
            )

        } ?: AppFailureItem(
            LedgerFailure.NotConnected(),
        )
    }
}

@Composable
fun NewLedgerAccountContent(
    ledgerAccountsState: LedgerAccountsState,
    onSelect: (Signer) -> Unit,
) {
    LazyColumn {
        ledgerAccountsState.failure?.let {
            item {
                AppFailureItem(it) {
                    ledgerAccountsState.retry()
                }
            }
        } ?: run {
            items(
                items = ledgerAccountsState.accounts,
                key = { signer -> signer.derivationPath.ledgerAddressIndex.toInt() }
            ) { signer ->
                SignerItem(signer) {
                    onSelect(signer)
                }
            }
        }
    }
}
