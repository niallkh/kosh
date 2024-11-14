package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad


@Composable
fun rememberSignTypedRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SignTypedRequestState {
    val request = rememberLoad(id) {
        val request = requestService.get(id).bind()
        val call = request.call as? WcRequest.Call.SignTyped
            ?: raise(WcFailure.Other("Expected Sign Typed call"))

        request to call
    }

    return remember {
        object : SignTypedRequestState {
            override val request: WcRequest?
                get() = request.result?.first
            override val call: WcRequest.Call.SignTyped?
                get() = request.result?.second
            override val loading: Boolean
                get() = request.loading
            override val failure: WcFailure?
                get() = request.failure

            override fun retry() {
                request.retry()
            }
        }
    }
}

@Stable
interface SignTypedRequestState {
    val request: WcRequest?
    val call: WcRequest.Call.SignTyped?
    val loading: Boolean
    val failure: WcFailure?
    fun retry()
}
