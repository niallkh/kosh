package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.failure.AppFailure
import kosh.domain.models.Hash
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberEitherEffect
import kosh.presentation.transaction.rememberSendTransaction

@Composable
fun rememberWcSendTransaction(
    id: WcRequest.Id,
    onSend: () -> Unit,
    requestService: WcRequestService = di { domain.wcRequestService },
): WcSendTransactionState {
    val wcSend = rememberEitherEffect(
        onFinish = { onSend() }
    ) { hash: Hash ->
        requestService.onTransactionSend(id, hash).bind()
    }

    val send = rememberSendTransaction(onSent = wcSend::invoke)

    return remember {
        object : WcSendTransactionState {
            override val sending: Boolean by derivedStateOf {
                send.sending || wcSend.inProgress
            }
            override val failure: AppFailure? by derivedStateOf {
                send.failure ?: wcSend.failure
            }

            override fun invoke(request: SignRequest.SignTransaction, signature: Signature) {
                send(request, signature)
            }
        }
    }
}

@Stable
interface WcSendTransactionState {
    val sending: Boolean
    val failure: AppFailure?
    operator fun invoke(request: SignRequest.SignTransaction, signature: Signature)
}
