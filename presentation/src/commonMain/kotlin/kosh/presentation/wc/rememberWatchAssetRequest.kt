package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.WcFailure
import kosh.domain.models.wc.WcRequest
import kosh.domain.usecases.wc.WcRequestService
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberWatchAssetRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): WatchAssetRequestState {
    var request by rememberSerializable { mutableStateOf<WcRequest?>(null) }
    var call by rememberSerializable { mutableStateOf<WcRequest.Call.WatchAsset?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }

    LaunchedEffect(id) {
        loading = true

        recover({
            request = requestService.get(id).bind().also {
                call = it.call as? WcRequest.Call.WatchAsset
                    ?: raise(WcFailure.RequestNotFound())
            }

            loading = false
            failure = null
        }) {
            failure = it
            request = null
            call = null
            loading = false
        }
    }

    return WatchAssetRequestState(
        request = request,
        call = call,
        loading = loading,
        failure = failure,
        onWatch = { requestService.onAssetWatched(id) },
        reject = { requestService.reject(id) },
    )
}

@Immutable
data class WatchAssetRequestState(
    val request: WcRequest?,
    val call: WcRequest.Call.WatchAsset?,
    val loading: Boolean,
    val failure: WcFailure?,
    val onWatch: () -> Unit,
    val reject: () -> Unit,
)