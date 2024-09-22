package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.wc.WcSession
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.Collect
import kosh.presentation.di.di
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.map

@Composable
fun rememberSessions(
    sessionService: WcSessionService = di { domain.wcSessionService },
): SessionsState {
    val sessions = Collect<AppFailure, _> {
        sessionService.sessions.map { it.toPersistentList() }
    }

    return SessionsState(
        sessions = sessions.content ?: persistentListOf(),
        init = sessions.init,
        loading = sessions.loading,
    )
}

data class SessionsState(
    val sessions: ImmutableList<WcSession>,
    val init: Boolean,
    val loading: Boolean,
)
