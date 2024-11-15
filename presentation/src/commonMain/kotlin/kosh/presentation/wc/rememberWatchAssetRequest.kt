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
fun rememberWatchAssetRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): WatchAssetRequestState {

    val request = rememberLoad(id) {
        val request = requestService.get(id).bind()
        val call = request.call as? WcRequest.Call.WatchAsset
            ?: raise(WcFailure.Other("Expected Watch Asset call"))

        request to call
    }

    return remember {
        object : WatchAssetRequestState {
            override val request: WcRequest? get() = request.result?.first
            override val call: WcRequest.Call.WatchAsset? get() = request.result?.second
            override val loading: Boolean get() = request.loading
            override val failure: WcFailure? get() = request.failure
            override fun retry() = request.retry()
            override fun onWatch() {
                requestService.onAssetWatched(id)
            }
        }
    }
}

@Stable
interface WatchAssetRequestState {
    val request: WcRequest?
    val call: WcRequest.Call.WatchAsset?
    val loading: Boolean
    val failure: WcFailure?
    fun onWatch()
    fun retry()
}
