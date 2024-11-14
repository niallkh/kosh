package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberSignPersonalRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SignPersonalRequestState {
    val request = rememberLoad(id) {
        val request = requestService.get(id).bind()
        val call = request.call as? WcRequest.Call.SignPersonal
            ?: raise(WcFailure.Other("Expected Sign Personal call"))

        request to call
    }

    return remember {
        object : SignPersonalRequestState {
            override val request: WcRequest? get() = request.result?.first
            override val call: WcRequest.Call.SignPersonal? get() = request.result?.second
            override val loading: Boolean get() = request.loading
            override val failure: WcFailure? get() = request.failure

            override fun retry() = request.retry()
        }
    }
}

interface SignPersonalRequestState {
    val request: WcRequest?
    val call: WcRequest.Call.SignPersonal?
    val loading: Boolean
    val failure: WcFailure?
    fun retry()
}
