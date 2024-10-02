package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import arrow.core.raise.recover
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.hw.Ledger
import kosh.domain.models.web3.Signer
import kosh.domain.repositories.LedgerListener
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.presentation.di.di

@Composable
fun rememberLedgerAccounts(
    listener: LedgerListener,
    ledger: Ledger,
    ledgerAccountService: LedgerAccountService = di { domain.ledgerAccountService },
): LedgerAccountsState {
    val accounts = remember { mutableStateListOf<Signer>() }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<LedgerFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, ledger) {
        loading = true
        accounts.clear()

        recover({
            ledgerAccountService.getAccounts(listener, ledger, 0u, 10u).collect { either ->
                accounts += either.bind()
                failure = null
            }

            loading = false
        }) {
            failure = it
            loading = false
        }
    }

    return LedgerAccountsState(
        accounts = accounts,
        loading = loading,
        failure = failure,
        retry = { retry++ }
    )
}

@Stable
data class LedgerAccountsState(
    val accounts: SnapshotStateList<Signer>,
    val loading: Boolean,
    val failure: LedgerFailure?,
    val retry: () -> Unit,
)
