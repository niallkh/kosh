package kosh.presentation.reown

import androidx.compose.runtime.Composable
import kosh.domain.models.reown.WcSession
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.Perform
import kosh.presentation.core.di
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
