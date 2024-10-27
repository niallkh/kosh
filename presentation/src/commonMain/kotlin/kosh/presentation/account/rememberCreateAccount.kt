package kosh.presentation.account

import androidx.compose.runtime.Composable
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.AccountFailure
import kosh.domain.models.web3.Signer
import kosh.domain.usecases.account.AccountService
import kosh.presentation.Perform
import kosh.presentation.core.di

@Composable
fun rememberCreateAccount(
    accountService: AccountService = di { domain.accountService },
): CreateAccountState {
    val create = Perform { signer: Signer ->
        accountService.create(
            location = signer.location,
            address = signer.address,
            derivationPath = signer.derivationPath,
        ).bind()
    }

    return CreateAccountState(
        created = create.performed,
        createdAccount = create.result,
        loading = create.inProgress,
        failure = create.failure,
        create = { create(it) },
        retry = { create.retry() }
    )
}

data class CreateAccountState(
    val created: Boolean,
    val createdAccount: AccountEntity.Id?,
    val loading: Boolean,
    val failure: AccountFailure?,
    val create: (Signer) -> Unit,
    val retry: () -> Unit,
)
