package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionAggregated
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.Load
import kosh.presentation.di.di

@Composable
fun rememberSession(
    id: WcSession.Id,
    proposalService: WcSessionService = di { domain.wcSessionService },
): SessionState {
    val content = Load(id) {
        proposalService.get(id).bind()
    }

    return SessionState(
        session = content.content,
        loading = content.loading,
        failure = content.failure,
        retry = { content.retry() },
    )
}

data class SessionState(
    val session: WcSessionAggregated?,
    val loading: Boolean,
    val failure: WcFailure?,
    val retry: () -> Unit,
)
