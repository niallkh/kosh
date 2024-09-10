package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.wc.WcSession
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberUpdateSession(
    id: WcSession.Id,
    sessionService: WcSessionService = di { domain.wcSessionService },
): UpdateSessionState {
    val approve = PerformAction<Pair<List<Address>, List<ChainId>>, _>(id) {
        sessionService.update(id, it.first, it.second).bind()
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
