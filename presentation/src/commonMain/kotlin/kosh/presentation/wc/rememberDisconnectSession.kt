package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcSession
import kosh.domain.usecases.reown.WcSessionService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberDisconnectSession(
    id: WcSession.Id,
    onDisconnected: () -> Unit,
    sessionService: WcSessionService = di { domain.wcSessionService },
): DisconnectSessionState {
    val disconnect = rememberEitherEffect(id, onFinish = { onDisconnected() }) { _: Unit ->
        sessionService.disconnect(id).bind()
    }

    return remember {
        object : DisconnectSessionState {
            override val disconnecting: Boolean get() = disconnect.inProgress
            override val failure: WcFailure? get() = disconnect.failure
            override operator fun invoke() = disconnect(Unit)
        }
    }
}

@Stable
interface DisconnectSessionState {
    val disconnecting: Boolean
    val failure: WcFailure?
    operator fun invoke()
}
