package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.failure.AppFailure
import kosh.domain.models.wc.WcSession
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.PerformAction
import kosh.presentation.di.di

@Composable
fun rememberDisconnectSession(
    id: WcSession.Id,
    sessionService: WcSessionService = di { domain.wcSessionService },
): DisconnectSessionState {
    val reject = PerformAction<Unit, AppFailure>(id) {
        sessionService.disconnect(id)
    }

    return DisconnectSessionState(
        disconnected = reject.performed,
        disconnect = { reject(Unit) }
    )
}

data class DisconnectSessionState(
    val disconnected: Boolean,
    val disconnect: () -> Unit,
)
