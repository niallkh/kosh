package kosh.ui.ledger

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kosh.domain.entities.AccountEntity
import kosh.domain.models.account.ledgerAddressIndex
import kosh.domain.models.hw.Ledger
import kosh.domain.models.web3.Signer
import kosh.presentation.account.rememberCreateAccount
import kosh.presentation.ledger.LedgerAccountsState
import kosh.presentation.ledger.rememberLedgerAccounts
import kosh.presentation.ledger.rememberLedgerListener
import kosh.ui.component.LoadingIndicator
import kosh.ui.component.items.SignerItem
import kosh.ui.component.scaffold.KoshScaffold
import kosh.ui.failure.AppFailureItem
import kosh.ui.failure.AppFailureMessage

@Composable
fun NewLedgerAccountScreen(
    ledger: Ledger,
    onNavigateUp: () -> Unit,
    onFinish: (AccountEntity.Id) -> Unit,
) {
    KoshScaffold(
        title = { Text("Ledger New Account") },
        onNavigateUp = onNavigateUp,
    ) { paddingValues ->

        val ledgerListenerState = rememberLedgerListener()

        LedgerButtonRequest(
            request = ledgerListenerState.buttonRequest,
            onConfirm = ledgerListenerState.confirm,
            onDismiss = ledgerListenerState.dismiss
        )

        val createAccountState = rememberCreateAccount()

        AppFailureMessage(createAccountState.failure)

        LaunchedEffect(createAccountState.createdAccount) {
            createAccountState.createdAccount?.let(onFinish)
        }

        val ledgerAccountsState = rememberLedgerAccounts(
            listener = ledgerListenerState.listener,
            ledger = ledger,
        )

        NewLedgerAccountContent(
            ledgerAccountsState = ledgerAccountsState,
            paddingValues = paddingValues,
            onSelect = { createAccountState.create(it) },
        )

        LoadingIndicator(
            createAccountState.loading || ledgerAccountsState.loading,
            Modifier.padding(paddingValues),
        )
    }
}

@Composable
fun NewLedgerAccountContent(
    ledgerAccountsState: LedgerAccountsState,
    paddingValues: PaddingValues = PaddingValues(),
    onSelect: (Signer) -> Unit,
) {
    LazyColumn(
        contentPadding = paddingValues,
    ) {
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
