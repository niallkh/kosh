package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.usecases.reown.WcAuthenticationService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberRejectAuthentication(
    id: WcAuthentication.Id,
    onRejected: () -> Unit,
    wcRequestService: WcAuthenticationService = di { domain.wcAuthenticationService },
): RejectAuthenticationState {
    val reject = rememberEitherEffect(id, onFinish = { onRejected() }) { _: Unit ->
        wcRequestService.reject(id).bind()
    }

    return remember {
        object : RejectAuthenticationState {
            override val rejecting: Boolean get() = reject.inProgress
            override val failure: WcFailure? get() = reject.failure
            override fun reject() = reject(Unit)
        }
    }
}

@Stable
interface RejectAuthenticationState {
    val rejecting: Boolean
    val failure: WcFailure?
    fun reject()
}
