package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.transaction.PersonalMessageService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberIorEffect

@Composable
fun rememberAddSignPersonal(
    personalMessageService: PersonalMessageService = di { domain.personalMessageService },
    onAdded: (Signature) -> Unit = {},
): AddSignPersonalState {

    val addSignPersonal = rememberIorEffect(
        onFinish = onAdded
    ) { (signRequest, signature): Pair<SignRequest.SignPersonal, Signature> ->
        personalMessageService.add(
            signature = signature,
            dapp = signRequest.dapp,
            message = signRequest.message,
        )
    }

    return remember {
        object : AddSignPersonalState {
            override val signature: Signature? get() = addSignPersonal.result
            override val adding: Boolean get() = addSignPersonal.inProgress
            override val failure: TransactionFailure? get() = addSignPersonal.failure?.firstOrNull()
            override operator fun invoke(
                signRequest: SignRequest.SignPersonal,
                signature: Signature,
            ) {
                addSignPersonal(Pair(signRequest, signature))
            }
        }
    }
}

@Stable
interface AddSignPersonalState {
    val signature: Signature?
    val adding: Boolean
    val failure: TransactionFailure?
    operator fun invoke(signRequest: SignRequest.SignPersonal, signature: Signature)
}
