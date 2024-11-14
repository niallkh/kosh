package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionAggregated
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberSession(
    id: WcSession.Id,
    proposalService: WcSessionService = di { domain.wcSessionService },
): SessionState {
    val session = rememberLoad(id) {
        proposalService.get(id).bind()
    }

    return remember {
        object : SessionState {
            override val session: WcSessionAggregated? get() = session.result
            override val loading: Boolean get() = session.loading
            override val failure: WcFailure? get() = session.failure
            override fun retry() = session.retry()
        }
    }
}

@Stable
interface SessionState {
    val session: WcSessionAggregated?
    val loading: Boolean
    val failure: WcFailure?
    fun retry()
}
