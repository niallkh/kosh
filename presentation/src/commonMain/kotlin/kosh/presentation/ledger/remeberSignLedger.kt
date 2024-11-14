package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.hw.Ledger
import kosh.domain.repositories.LedgerListener
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.presentation.core.di
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.rememberEitherEffect


@Composable
fun rememberSignLedger(
    ledgerListener: LedgerListener,
    onSigned: (SignedRequest) -> Unit = {},
    ledgerAccountService: LedgerAccountService = di { domain.ledgerAccountService },
): SignLedgerState {
    val signLedger = rememberEitherEffect(
        ledgerListener,
        onFinish = onSigned
    ) { (ledger, request): Pair<Ledger, SignRequest> ->
            val signature = when (request) {
                is SignRequest.SignPersonal -> ledgerAccountService.sign(
                    listener = ledgerListener,
                    ledger = ledger,
                    address = request.account,
                    message = request.message,
                )

                is SignRequest.SignTyped -> ledgerAccountService.sign(
                    listener = ledgerListener,
                    ledger = ledger,
                    address = request.account,
                    jsonTypeData = request.json,
                )

                is SignRequest.SignTransaction -> ledgerAccountService.sign(
                    listener = ledgerListener,
                    ledger = ledger,
                    transaction = request.data,
                )
            }.bind()

            SignedRequest(request, signature)
    }


    return remember(ledgerListener) {
        object : SignLedgerState {
            override val signedRequest: SignedRequest?
                get() = signLedger.result
            override val loading: Boolean
                get() = signLedger.inProgress
            override val failure: LedgerFailure?
                get() = signLedger.failure

            override fun sign(ledger: Ledger, request: SignRequest) {
                signLedger(Pair(ledger, request))
            }
        }
    }
}

@Stable
interface SignLedgerState {
    val signedRequest: SignedRequest?
    val loading: Boolean
    val failure: LedgerFailure?

    fun sign(ledger: Ledger, request: SignRequest)
}
