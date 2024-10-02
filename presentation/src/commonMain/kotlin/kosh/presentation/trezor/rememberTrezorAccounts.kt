package kosh.presentation.trezor

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
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.Trezor
import kosh.domain.models.web3.Signer
import kosh.domain.repositories.TrezorListener
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.presentation.di.di

@Composable
fun rememberTrezorAccounts(
    listener: TrezorListener,
    trezor: Trezor,
    trezorAccountService: TrezorAccountService = di { domain.trezorAccountService },
): TrezorAccountsState {
    val accounts = remember { mutableStateListOf<Signer>() }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TrezorFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }

    LaunchedEffect(retry, refresh, trezor) {
        loading = true
        accounts.clear()

        recover({
            trezorAccountService.getAccounts(
                listener, trezor, refresh, 0u, 10u
            ).collect { either ->
                accounts += either.bind()
                failure = null
            }

            loading = false
        }) {
            failure = it
            loading = false
        }
    }

    return TrezorAccountsState(
        accounts = accounts,
        loading = loading,
        failure = failure,
        retry = {
            retry++
            refresh = it
        }
    )
}

@Stable
data class TrezorAccountsState(
    val accounts: SnapshotStateList<Signer>,
    val loading: Boolean,
    val failure: TrezorFailure?,
    val retry: (Boolean) -> Unit,
)
