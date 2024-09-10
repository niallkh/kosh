package kosh.presentation.account

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.AccountFailure
import kosh.domain.models.web3.Signer
import kosh.domain.usecases.account.AccountService
import kosh.presentation.di.di

@Composable
fun rememberCreateAccount(
    accountService: AccountService = di { domain.accountService },
): CreateAccountState {
    var signer by remember { mutableStateOf<Signer?>(null) }
    var created by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<AccountFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, signer) {
        loading = true

        recover({
            accountService.create(
                raise = this,
                location = signer?.location ?: raise(null),
                address = signer?.address ?: raise(null),
                derivationPath = signer?.derivationPath ?: raise(null),
            )
            created = true

            failure = null
            loading = false
        }) {
            created = false

            failure = it
            loading = false
        }
    }

    return CreateAccountState(
        created = created,
        loading = loading,
        failure = failure,
        create = {
            retry++
            signer = it
        },
        retry = { retry++ }
    )
}

data class CreateAccountState(
    val created: Boolean,
    val loading: Boolean,
    val failure: AccountFailure?,
    val create: (Signer) -> Unit,
    val retry: () -> Unit,
)
