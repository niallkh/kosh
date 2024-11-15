package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.AppFailure
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberAddNetworkRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): AddNetworkRequestState {
    val request = rememberLoad(id) {
        val request = requestService.get(id).bind()
        val call = request.call as? WcRequest.Call.AddNetwork
            ?: raise(WcFailure.Other("Expected Add Network call"))

        request to call
    }

    return remember {
        object : AddNetworkRequestState {
            override val request: WcRequest? get() = request.result?.first
            override val call: WcRequest.Call.AddNetwork? get() = request.result?.second
            override val loading: Boolean get() = request.loading
            override val failure: AppFailure? get() = request.failure

            override fun retry() = request.retry()
        }
    }
}

@Stable
interface AddNetworkRequestState {
    val request: WcRequest?
    val call: WcRequest.Call.AddNetwork?
    val loading: Boolean
    val failure: AppFailure?
    fun retry()
}
