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
import kosh.domain.models.web3.Signature
import kosh.domain.usecases.transaction.PersonalMessageService
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable
import kosh.presentation.models.SignRequest

@Composable
fun rememberSignPersonal(
    transactionService: PersonalMessageService = di { domain.personalMessageService },
): SignPersonalState {
    var data by rememberSerializable { mutableStateOf<Signature?>(null) }
    var signRequest by rememberSerializable { mutableStateOf<SignRequest.SignPersonal?>(null) }
    var signature by rememberSerializable { mutableStateOf<Signature?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failures by remember { mutableStateOf<TransactionFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, signRequest, signature) {
        loading = true

        recover({
            data = transactionService.add(
                signature = signature ?: raise(null),
                dapp = signRequest?.dapp ?: raise(null),
                message = signRequest?.message ?: raise(null),
            ).toEither().bind()

            loading = false
            failures = null
        }) {
            data = null
            loading = false
            failures = it?.first()
        }
    }

    return SignPersonalState(
        signature = data,
        loading = loading,
        failure = failures,
        retry = { retry++ },
        add = { request, sig ->
            retry++
            signRequest = request
            signature = sig
        },
    )
}

@Immutable
data class SignPersonalState(
    val signature: Signature?,
    val loading: Boolean,
    val failure: TransactionFailure?,
    val retry: () -> Unit,
    val add: (SignRequest.SignPersonal, Signature) -> Unit,
)
