package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.reown.WcSession
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.Perform
import kosh.presentation.di.di

@Composable
fun rememberUpdateSession(
    id: WcSession.Id,
    sessionService: WcSessionService = di { domain.wcSessionService },
): UpdateSessionState {
    val approve = Perform(id) { (accounts, chains): Pair<List<Address>, List<ChainId>> ->
        sessionService.update(id, accounts, chains).bind()
    }

    return UpdateSessionState(
        updated = approve.performed,
        loading = approve.inProgress,
        failure = approve.failure,
        retry = { approve.retry() },
        update = { accounts, chains -> approve(accounts to chains) },
    )
}

data class UpdateSessionState(
    val updated: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val retry: () -> Unit,
    val update: (List<Address>, List<ChainId>) -> Unit,
)
