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
import kosh.presentation.transaction.rememberAddSignPersonal

@Composable
fun rememberSendPersonalMessage(
    id: WcRequest.Id,
    onSent: () -> Unit = {},
    requestService: WcRequestService = di { domain.wcRequestService },
): SendPersonalMessageState {
    val sendSignPersonal = rememberEitherEffect(
        id,
        onFinish = { onSent() }
    ) { signature: Signature ->
        requestService.onPersonalSigned2(id, signature).bind()
    }

    val addSignPersonal = rememberAddSignPersonal(
        onAdded = { sendSignPersonal(it) }
    )

    return remember {
        object : SendPersonalMessageState {
            override val sending: Boolean by derivedStateOf {
                addSignPersonal.adding || sendSignPersonal.inProgress
            }

            override val failure: AppFailure? by derivedStateOf {
                addSignPersonal.failure ?: sendSignPersonal.failure
            }

            override fun send(signRequest: SignRequest.SignPersonal, signature: Signature) {
                addSignPersonal(signRequest, signature)
            }
        }
    }
}

interface SendPersonalMessageState {
    val sending: Boolean
    val failure: AppFailure?
    fun send(signRequest: SignRequest.SignPersonal, signature: Signature)
}
