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
import kosh.domain.failure.TransactionFailure
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.di.rememberRetained
import kosh.presentation.models.SignRequest
import kosh.presentation.transaction.rememberSignTyped


@Composable
fun rememberSignTypedRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SignTypedRequestState {
    var request by rememberRetained { mutableStateOf<WcRequest?>(null) }
    var call by rememberRetained { mutableStateOf<WcRequest.Call.SignTyped?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var sent by remember { mutableStateOf(false) }

    val signTyped = rememberSignTyped()

    LaunchedEffect(signTyped.signature) {
        signTyped.signature?.let {
            requestService.onTypedSigned(id, it.data)
            sent = true
        }
    }

    LaunchedEffect(id, retry) {
        loading = true

        recover({
            request = requestService.get(id).bind().also {
                call = it.call as? WcRequest.Call.SignTyped
                    ?: error("Request is not a SignTyped")
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

    return SignTypedRequestState(
        request = request,
        call = call,
        sent = sent,
        loading = loading || signTyped.loading,
        failure = failure,
        txFailure = signTyped.failure,
        send = { req, signature ->
            sent = false
            signTyped.add(req, signature)
        },
        reject = { requestService.reject(id) },
        retry = {
            if (signTyped.failure != null) {
                signTyped.retry()
            } else {
                retry++
            }
        }
    )
}

@Immutable
data class SignTypedRequestState(
    val request: WcRequest?,
    val call: WcRequest.Call.SignTyped?,
    val sent: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val txFailure: TransactionFailure?,
    val send: (SignRequest.SignTyped, Signature) -> Unit,
    val retry: () -> Unit,
    val reject: () -> Unit,
)
