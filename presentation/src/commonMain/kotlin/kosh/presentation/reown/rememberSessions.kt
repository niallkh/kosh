package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.models.reown.WcSession
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.core.di
import kosh.presentation.rememberCollect
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberSessions(
    sessionService: WcSessionService = di { domain.wcSessionService },
): SessionsState {
    val sessions = rememberCollect(persistentListOf()) {
        sessionService.sessions
    }

    return SessionsState(
        sessions = sessions.result,
        init = sessions.init,
    )
}

data class SessionsState(
    val sessions: ImmutableList<WcSession>,
    val init: Boolean,
)
