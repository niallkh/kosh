package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.TransactionFailure
import kosh.domain.models.Hash
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberSendTransaction(
    onSent: (Hash) -> Unit,
    transactionService: Eip1559TransactionService = di { domain.eip1559TransactionService },
): SendTransactionState {
    val send = rememberEitherEffect(
        onFinish = onSent
    ) { (request, signature): Pair<SignRequest.SignTransaction, Signature> ->
        transactionService.send(
            transaction = request.data,
            signature = signature,
            dapp = request.dapp,
        ).bind()
    }

    return remember {
        object : SendTransactionState {
            override val hash: Hash? get() = send.result
            override val sending: Boolean get() = send.inProgress
            override val failure: TransactionFailure? get() = send.failure

            override fun invoke(request: SignRequest.SignTransaction, signature: Signature) {
                send(request to signature)
            }
        }
    }
}

@Stable
interface SendTransactionState {
    val hash: Hash?
    val sending: Boolean
    val failure: TransactionFailure?
    operator fun invoke(request: SignRequest.SignTransaction, signature: Signature)
}
