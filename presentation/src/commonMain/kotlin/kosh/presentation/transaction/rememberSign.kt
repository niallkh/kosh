package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.failure.LedgerFailure
import kosh.domain.failure.TrezorFailure
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.TrezorListener
import kosh.presentation.ledger.rememberLedger
import kosh.presentation.ledger.rememberSignLedger
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.trezor.rememberSignTrezor
import kosh.presentation.trezor.rememberTrezor

@Composable
fun rememberSign(
    trezorListener: TrezorListener,
    ledgerListener: LedgerListener,
): SignState {
    val ledger = rememberLedger()
    val trezor = rememberTrezor()

    val signTrezor = rememberSignTrezor(trezorListener)
    val signLedger = rememberSignLedger(ledgerListener)

    return SignState(
        signedRequest = signTrezor.signedRequest ?: signLedger.signedRequest,
        loading = signTrezor.loading || signLedger.loading,
        ledgerFailure = signLedger.failure,
        trezorFailure = signTrezor.failure,

        sign = { request ->
            when {
                trezor.trezor != null -> signTrezor.sign(trezor.trezor, request)
                ledger.ledger != null -> signLedger.sign(ledger.ledger, request)
                else -> signTrezor.sign(trezor.trezor, request)
            }
        },
        retry = {
            when {
                trezor.trezor != null -> signTrezor.retry(trezor.trezor)
                ledger.ledger != null -> signLedger.retry(ledger.ledger)
                else -> signTrezor.retry(trezor.trezor)
            }
        }
    )
}


@Immutable
data class SignState(
    val signedRequest: SignedRequest?,
    val loading: Boolean,
    val ledgerFailure: LedgerFailure?,
    val trezorFailure: TrezorFailure?,

    val sign: (SignRequest) -> Unit,
    val retry: () -> Unit,
)
