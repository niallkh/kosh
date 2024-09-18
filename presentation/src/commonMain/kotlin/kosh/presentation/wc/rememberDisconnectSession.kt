package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.models.wc.WcSession
import kosh.domain.usecases.wc.WcSessionService
import kosh.presentation.Perform
import kosh.presentation.di.di
import kosh.presentation.invoke

@Composable
fun rememberDisconnectSession(
    id: WcSession.Id,
    sessionService: WcSessionService = di { domain.wcSessionService },
): DisconnectSessionState {
    val reject = Perform(id) {
        sessionService.disconnect(id)
    }

    return DisconnectSessionState(
        disconnected = reject.performed,
        disconnect = { reject() }
    )
}

data class DisconnectSessionState(
    val disconnected: Boolean,
    val disconnect: () -> Unit,
)
