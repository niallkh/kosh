package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberRejectRequest(
    id: WcRequest.Id,
    onRejected: () -> Unit,
    wcRequestService: WcRequestService = di { domain.wcRequestService },
): RejectRequestState {
    val reject = rememberEitherEffect(id, onFinish = { onRejected() }) { _: Unit ->
        wcRequestService.reject2(id).bind()
    }

    return remember {
        object : RejectRequestState {
            override val rejecting: Boolean get() = reject.inProgress
            override val failure: WcFailure? get() = reject.failure
            override operator fun invoke() = reject(Unit)
        }
    }
}

@Stable
interface RejectRequestState {
    val rejecting: Boolean
    val failure: WcFailure?
    operator fun invoke()
}
