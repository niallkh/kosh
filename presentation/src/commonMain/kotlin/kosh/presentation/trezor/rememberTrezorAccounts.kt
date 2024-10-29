package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.Trezor
import kosh.domain.models.web3.Signer
import kosh.domain.repositories.TrezorListener
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.trezor.TrezorAccountService
import kosh.presentation.core.di
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus

@Composable
fun rememberTrezorAccounts(
    listener: TrezorListener,
    trezor: Trezor,
    trezorAccountService: TrezorAccountService = di { domain.trezorAccountService },
): TrezorAccountsState {
    var accounts by remember { mutableStateOf(persistentListOf<Signer>()) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TrezorFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }
    var refreshing by remember { mutableStateOf(false) }

    LaunchedEffect(retry, trezor) {
        loading = true
        accounts = persistentListOf()

        recover({
            trezorAccountService.getAccounts(
                listener, trezor, refresh, 0u, 10u
            ).collect { either ->
                accounts += either.bind()
                failure = null
                refreshing = false
            }

            refresh = false
            refreshing = false
            loading = false
        }) {
            failure = it
            loading = false
            refreshing = false
        }
    }

    return TrezorAccountsState(
        accounts = accounts,
        loading = loading,
        refreshing = refreshing,
        failure = failure,
        retry = {
            println("[T]Retry")
            retry++
        },
        refresh = {
            println("[T]Refresh")
            retry++
            refresh = true
            refreshing = true
        }
    )
}

@Stable
data class TrezorAccountsState(
    val accounts: ImmutableList<Signer>,
    val loading: Boolean,
    val refreshing: Boolean,
    val failure: TrezorFailure?,
    val retry: () -> Unit,
    val refresh: () -> Unit,
)
