package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.toTransaction
import kosh.domain.models.web3.Transaction
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberLoad

@Composable
fun rememberSendTransactionRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
): SendTransactionRequestState {

    val request = rememberLoad(id) {
        val request = requestService.get(id).bind()
        val call = request.call as? WcRequest.Call.SendTransaction
            ?: raise(WcFailure.Other("Expected Send Transaction call"))

        request to call.toTransaction()
    }

    return remember {
        object : SendTransactionRequestState {
            override val request: WcRequest? get() = request.result?.first
            override val transaction: Transaction? get() = request.result?.second
            override val loading: Boolean get() = request.loading
            override val failure: WcFailure? get() = request.failure

            override fun retry() {
                request.retry()
            }
        }
    }
}

@Stable
interface SendTransactionRequestState {
    val request: WcRequest?
    val transaction: Transaction?
    val loading: Boolean
    val failure: WcFailure?
    fun retry()
}
