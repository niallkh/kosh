package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.TransactionFailure
import kosh.domain.failure.WcFailure
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.wc.WcRequestService
import kosh.presentation.di.di
import kosh.presentation.di.rememberRetainable
import kosh.presentation.models.SignRequest
import kosh.presentation.transaction.rememberSignPersonal

@Composable
fun rememberSignPersonalRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SignPersonalRequestState {
    var request by rememberRetainable { mutableStateOf<WcRequest?>(null) }
    var call by rememberRetainable { mutableStateOf<WcRequest.Call.SignPersonal?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var sent by remember { mutableStateOf(false) }

    val signPersonal = rememberSignPersonal()

    LaunchedEffect(signPersonal.signature) {
        signPersonal.signature?.let {
            requestService.onPersonalSigned(id, it.data)
            sent = true
        }
    }

    LaunchedEffect(id, retry) {
        loading = true

        recover({
            request = requestService.get(id).bind().also {
                call = it.call as? WcRequest.Call.SignPersonal
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

    return SignPersonalRequestState(
        request = request,
        call = call,
        sent = sent,
        loading = loading || signPersonal.loading,
        failure = failure,
        txFailure = signPersonal.failure,
        send = { req, signature ->
            sent = false
            signPersonal.add(req, signature)
        },
        reject = { requestService.reject(id) },
        retry = {
            if (signPersonal.failure != null) {
                signPersonal.retry()
            } else {
                retry++
            }
        }
    )
}

@Immutable
data class SignPersonalRequestState(
    val request: WcRequest?,
    val call: WcRequest.Call.SignPersonal?,
    val sent: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val txFailure: TransactionFailure?,
    val send: (SignRequest.SignPersonal, Signature) -> Unit,
    val retry: () -> Unit,
    val reject: () -> Unit,
)
