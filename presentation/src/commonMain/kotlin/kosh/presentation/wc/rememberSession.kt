package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.WcFailure
import kosh.domain.models.wc.WcSession
import kosh.domain.models.wc.WcSessionAggregated
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.LoadContent
import kosh.presentation.di.di

@Composable
fun rememberSession(
    id: WcSession.Id,
    proposalService: WcSessionService = di { domain.wcSessionService },
): SessionState {

    val content = LoadContent(id) {
        proposalService.get(id).bind()
    }

    return SessionState(
        loading = content.loading,
        session = content.content,
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
