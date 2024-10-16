package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import arrow.core.raise.either
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.hw.Ledger
import kosh.domain.repositories.LedgerListener
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.presentation.core.di
import kosh.presentation.di.rememberRetained
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.rememberEffect


@Composable
fun rememberSignLedger(
    trezorListener: LedgerListener,
    trezorAccountService: LedgerAccountService = di { domain.ledgerAccountService },
): SignLedgerState {
    var ledger by rememberRetained { mutableStateOf<Ledger?>(null) }
    var signRequest by rememberRetained { mutableStateOf<SignRequest?>(null) }

    val sign = rememberEffect(ledger, signRequest) {
        either {
            val request = signRequest ?: raise(null)
            val ledger1 = ledger ?: raise(null)

            val signature = when (request) {
                is SignRequest.SignPersonal -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger1,
                    address = request.account,
                    message = request.message,
                )

                is SignRequest.SignTyped -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger1,
                    address = request.account,
                    jsonTypeData = request.json,
                )

                is SignRequest.SignTransaction -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger1,
                    transaction = request.data,
                )
            }.bind()

            SignedRequest(request, signature)
        }
    }


    return SignLedgerState(
        signedRequest = sign.result?.getOrNull(),
        loading = sign.inProgress,
        failure = sign.result?.leftOrNull(),
        sign = { ledger1, request ->
            signRequest = request
            ledger = ledger1
            sign()
        },
        retry = {
            sign()
        }
    )
}

@Immutable
data class SignLedgerState(
    val signedRequest: SignedRequest?,
    val loading: Boolean,
    val failure: LedgerFailure?,

    val sign: (Ledger, SignRequest) -> Unit,
    val retry: () -> Unit,
)
