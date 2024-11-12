package kosh.presentation.keystone

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.KeystoneFailure
import kosh.domain.models.hw.Keystone
import kosh.domain.models.web3.Signer
import kosh.domain.repositories.KeystoneListener
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.keystone.KeystoneAccountService
import kosh.presentation.core.di
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus

@Composable
fun rememberKeystoneAccounts(
    listener: KeystoneListener,
    keystone: Keystone,
    keystoneAccountService: KeystoneAccountService = di { domain.keystoneAccountService },
): KeystoneAccountsState {
    var accounts by remember { mutableStateOf(persistentListOf<Signer>()) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<KeystoneFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var refresh by remember { mutableStateOf(false) }

    LaunchedEffect(retry, keystone) {
        loading = true
        accounts = persistentListOf()

        recover({
            keystoneAccountService.getAccounts(
                listener, keystone, refresh, 0u, 10u
            ).collect { either ->
                accounts += either.bind()
                failure = null
            }

            refresh = false
            loading = false
        }) {
            failure = it
            loading = false
        }
    }

    return KeystoneAccountsState(
        accounts = accounts,
        loading = loading,
        failure = failure,
        retry = {
            retry++
        },
    )
}

@Stable
data class KeystoneAccountsState(
    val accounts: ImmutableList<Signer>,
    val loading: Boolean,
    val failure: KeystoneFailure?,
    val retry: () -> Unit,
)
