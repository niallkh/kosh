package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.failure.AppFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberEitherEffect
import kosh.presentation.transaction.rememberAddSignTyped

@Composable
fun rememberSendTypedData(
    id: WcRequest.Id,
    onSent: () -> Unit = {},
    requestService: WcRequestService = di { domain.wcRequestService },
): SendTypedDataState {
    val sendSignTyped = rememberEitherEffect(id, onFinish = { onSent() }) { signature: Signature ->
        requestService.onTypedSigned2(id, signature).bind()
    }

    val addSignTyped = rememberAddSignTyped(
        onAdded = { sendSignTyped(it) }
    )

    return remember {
        object : SendTypedDataState {
            override val sending: Boolean by derivedStateOf {
                addSignTyped.adding || sendSignTyped.inProgress
            }

            override val failure: AppFailure? by derivedStateOf {
                addSignTyped.failure ?: sendSignTyped.failure
            }

            override fun send(signRequest: SignRequest.SignTyped, signature: Signature) {
                addSignTyped(signRequest, signature)
            }
        }
    }
}

interface SendTypedDataState {
    val sending: Boolean
    val failure: AppFailure?
    fun send(signRequest: SignRequest.SignTyped, signature: Signature)
}
