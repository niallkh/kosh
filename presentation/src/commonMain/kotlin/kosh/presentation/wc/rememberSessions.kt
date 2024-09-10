package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import kosh.domain.models.wc.WcSession
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.di.di
import kosh.presentation.di.serializableSaver

@Composable
fun rememberSessions(
    sessionService: WcSessionService = di { domain.wcSessionService },
): SessionsState {
    val sessions by sessionService.sessions.collectAsState(null)

    return SessionsState(
        sessions = rememberSaveable(sessions, saver = serializableSaver()) {
            sessions ?: emptyList()
        },
        loading = sessions == null,
    )
}

data class SessionsState(
    val sessions: List<WcSession>,
    val loading: Boolean,
)
