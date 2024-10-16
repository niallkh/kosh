package kosh.presentation.reown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.di.rememberRetained

@Composable
fun rememberWatchAssetRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): WatchAssetRequestState {
    var request by rememberRetained { mutableStateOf<WcRequest?>(null) }
    var call by rememberRetained { mutableStateOf<WcRequest.Call.WatchAsset?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }

    LaunchedEffect(id) {
        loading = true

        recover({
            request = requestService.get(id).bind().also {
                call = it.call as? WcRequest.Call.WatchAsset
                    ?: error("Request is not a WatchAsset")
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
