package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.LedgerFailure
import kosh.domain.models.hw.Ledger
import kosh.domain.repositories.LedgerListener
import kosh.domain.usecases.ledger.LedgerAccountService
import kosh.presentation.di.di
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest


@Composable
fun rememberSignLedger(
    trezorListener: LedgerListener,
    trezorAccountService: LedgerAccountService = di { domain.ledgerAccountService },
): SignLedgerState {
    var signRequest by remember { mutableStateOf<SignRequest?>(null) }
    var signedRequest by remember { mutableStateOf<SignedRequest?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<LedgerFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var ledger by remember { mutableStateOf<Ledger?>(null) }

    LaunchedEffect(retry, signRequest, ledger) {
        loading = true

        recover({
            val request = signRequest ?: raise(null)
            val signature = when (request) {
                is SignRequest.SignPersonal -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger ?: raise(LedgerFailure.NotConnected()),
                    address = request.account,
                    message = request.message,
                )

                is SignRequest.SignTyped -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger ?: raise(LedgerFailure.NotConnected()),
                    address = request.account,
                    jsonTypeData = request.json,
                )

                is SignRequest.SignTransaction -> trezorAccountService.sign(
                    listener = trezorListener,
                    ledger = ledger ?: raise(LedgerFailure.NotConnected()),
                    transaction = request.data,
                )
            }.bind()

            signedRequest = SignedRequest(request, signature)

            loading = false
            failure = null
        }) {
            loading = false
            failure = it
        }
    }

    return SignLedgerState(
        signedRequest = signedRequest,
        loading = loading,
        failure = failure,
        sign = { ledger1, request ->
            retry++
            signRequest = request
            ledger = ledger1
        },
        retry = {
            retry++
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
