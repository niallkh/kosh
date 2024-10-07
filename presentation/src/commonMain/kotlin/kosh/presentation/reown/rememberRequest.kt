package kosh.presentation.reown

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberRequest(
    id: WcRequest.Id?,
    requestService: WcRequestService = di { domain.wcRequestService },
): RequestState {
    var request by rememberSerializable { mutableStateOf<WcRequest?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(id, retry) {
        loading = true

        recover({
            request = requestService.get(id).bind()

            loading = false
            failure = null
        }) {
            loading = false
            failure = it
        }
    }

    return RequestState(
        request = request,
        loading = loading,
        failure = failure,
        retry = { retry++ },
        reject = { (id ?: request?.id)?.let { requestService.reject(it) } }
    )
}

@Immutable
data class RequestState(
    val request: WcRequest?,
    val loading: Boolean,
    val failure: WcFailure?,
    val retry: () -> Unit,
    val reject: () -> Unit,
)
