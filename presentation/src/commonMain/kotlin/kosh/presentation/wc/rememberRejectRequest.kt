package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.models.wc.WcRequest
import kosh.domain.usecases.wc.WcRequestService
import kosh.presentation.Perform
import kosh.presentation.di.di
import kosh.presentation.invoke

@Composable
fun rememberRejectRequest(
    id: WcRequest.Id,
    wcRequestService: WcRequestService = di { domain.wcRequestService },
): RejectRequestState {

    val reject = Perform(id) {
        wcRequestService.reject(id).join()
    }

    return RejectRequestState(
        loading = reject.inProgress,
        rejected = reject.performed,
        reject = { reject() },
        retry = { reject.retry() }
    )
}

data class RejectRequestState(
    val loading: Boolean,
    val rejected: Boolean,
    val reject: () -> Unit,
    val retry: () -> Unit,
)
