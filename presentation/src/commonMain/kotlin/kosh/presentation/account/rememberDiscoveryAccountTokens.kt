package kosh.presentation.account

import androidx.compose.runtime.Composable
import arrow.core.Nel
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.usecases.account.AccountTokensDiscoveryService
import kosh.presentation.Perform
import kosh.presentation.core.di

@Composable
fun rememberDiscoveryAccountTokens(
    id: AccountEntity.Id,
    accountTokensDiscoveryService: AccountTokensDiscoveryService = di { domain.accountTokensDiscoveryService },
): DiscoveryAccountTokensState {
    val discovery = Perform(id) { _: Unit ->
        accountTokensDiscoveryService.discoverTokens(id).toEither().bind()
    }

    return DiscoveryAccountTokensState(
        finished = discovery.performed,
        loading = discovery.inProgress,
        errors = discovery.failure,
        discover = { discovery(Unit) }
    )
}

data class DiscoveryAccountTokensState(
    val finished: Boolean,
    val loading: Boolean,
    val errors: Nel<Web3Failure>?,
    val discover: () -> Unit,
)
