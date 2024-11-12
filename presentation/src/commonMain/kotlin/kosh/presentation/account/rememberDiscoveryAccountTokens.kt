package kosh.presentation.account

import androidx.compose.runtime.Composable
import arrow.core.Nel
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.usecases.account.AccountTokensDiscoveryService
import kosh.presentation.core.di
import kosh.presentation.rememberEffect

@Composable
fun rememberDiscoveryAccountTokens(
    id: AccountEntity.Id,
    accountTokensDiscoveryService: AccountTokensDiscoveryService = di { domain.accountTokensDiscoveryService },
): DiscoveryAccountTokensState {
    val discovery = rememberEffect(id) {
        accountTokensDiscoveryService.discoverTokens(id).toEither()
    }

    return DiscoveryAccountTokensState(
        finished = discovery.done,
        loading = discovery.inProgress,
        errors = discovery.result?.leftOrNull(),
        discover = { discovery() },
        retry = { discovery() }
    )
}

data class DiscoveryAccountTokensState(
    val finished: Boolean,
    val loading: Boolean,
    val errors: Nel<Web3Failure>?,
    val discover: () -> Unit,
    val retry: () -> Unit,
)
