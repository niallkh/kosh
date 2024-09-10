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
import kosh.presentation.di.rememberSerializable
import kosh.presentation.models.SignRequest

@Composable
fun rememberSendTransactionRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SendTransactionRequestState {
    var request by rememberSerializable { mutableStateOf<WcRequest?>(null) }
    var call by rememberSerializable { mutableStateOf<WcRequest.Call.SendTransaction?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var sent by rememberSaveable { mutableStateOf(false) }

    val sendTransaction = kosh.presentation.transaction.rememberSendTransaction()

    LaunchedEffect(sendTransaction.hash) {
        sendTransaction.hash?.let {
            requestService.onTransactionSend(id, it)
            sent = true
        }
    }

    LaunchedEffect(id, retry) {
        loading = true

        recover({
            request = requestService.get(id).bind().also {
                call = it.call as? WcRequest.Call.SendTransaction
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

    return SendTransactionRequestState(
        request = request,
        call = call,
        sent = sent,
        loading = loading || sendTransaction.loading,
        failure = failure,
        txFailure = sendTransaction.failure,
        reject = { requestService.reject(id) },
        retry = {
            if (sendTransaction.failure != null) {
                sendTransaction.retry()
            } else {
                retry++
            }
        },
        send = { req, signature ->
            sent = false
            sendTransaction.send(req, signature)
        }
    )
}

@Immutable
data class SendTransactionRequestState(
    val request: WcRequest?,
    val call: WcRequest.Call.SendTransaction?,
    val sent: Boolean,
    val loading: Boolean,
    val failure: WcFailure?,
    val txFailure: TransactionFailure?,
    val reject: () -> Unit,
    val retry: () -> Unit,
    val send: (SignRequest.SignTransaction, Signature) -> Unit,
)
