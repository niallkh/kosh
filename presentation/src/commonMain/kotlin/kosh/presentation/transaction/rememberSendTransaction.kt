package kosh.presentation.transaction

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
import kosh.domain.models.Hash
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.transaction.Eip1559TransactionService
import kosh.presentation.di.di
import kosh.presentation.di.rememberSerializable
import kosh.presentation.models.SignRequest

@Composable
fun rememberSendTransaction(
    transactionService: Eip1559TransactionService = di { domain.eip1559TransactionService },
): SendTransactionState {
    var hash by rememberSerializable { mutableStateOf<Hash?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<TransactionFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var signRequest by rememberSerializable { mutableStateOf<SignRequest.SignTransaction?>(null) }
    var signature by rememberSerializable { mutableStateOf<Signature?>(null) }

    LaunchedEffect(retry, signRequest, signature) {
        loading = true

        recover({
            hash = transactionService.send(
                transaction = signRequest?.data ?: raise(null),
                signature = signature ?: raise(null),
                dapp = signRequest?.dapp ?: raise(null),
            ).bind()

            loading = false
            failure = null
        }) {
            hash = null
            loading = false
            failure = it
        }
    }

    return SendTransactionState(
        hash = hash,
        loading = loading,
        failure = failure,
        retry = { retry++ },
        send = { request, sig ->
            retry++
            signRequest = request
            signature = sig
        },
    )
}

@Immutable
data class SendTransactionState(
    val hash: Hash?,
    val loading: Boolean,
    val failure: TransactionFailure?,
    val retry: () -> Unit,
    val send: (SignRequest.SignTransaction, Signature) -> Unit,
)
