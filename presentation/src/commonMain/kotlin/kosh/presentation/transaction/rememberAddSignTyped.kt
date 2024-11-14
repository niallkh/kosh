package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.transaction.TypedDataService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberAddSignTyped(
    typedDataService: TypedDataService = di { domain.typedDataService },
    onAdded: (Signature) -> Unit = {},
): AddSignTypedState {

    val addSignTyped = rememberEitherEffect(
        onFinish = { onAdded(it) }
    ) { (signRequest, signature): Pair<SignRequest.SignTyped, Signature> ->
        typedDataService.add(
            signature = signature,
            dapp = signRequest.dapp,
            jsonTypeData = signRequest.json,
            chainId = signRequest.chainId,
        ).toEither().bind()
    }

    return remember {
        object : AddSignTypedState {
            override val signature: Signature? get() = addSignTyped.result
            override val adding: Boolean get() = addSignTyped.inProgress
            override val failure: TransactionFailure? get() = addSignTyped.failure?.firstOrNull()
            override operator fun invoke(
                signRequest: SignRequest.SignTyped,
                signature: Signature,
            ) {
                addSignTyped(Pair(signRequest, signature))
            }
        }
    }
}

@Stable
interface AddSignTypedState {
    val signature: Signature?
    val adding: Boolean
    val failure: TransactionFailure?
    operator fun invoke(signRequest: SignRequest.SignTyped, signature: Signature)
}
