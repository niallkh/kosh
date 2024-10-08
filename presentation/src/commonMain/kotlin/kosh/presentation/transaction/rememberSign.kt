package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.failure.LedgerFailure
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.models.hw.Ledger
import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.TrezorListener
import kosh.presentation.ledger.rememberSignLedger
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.trezor.rememberSignTrezor

@Composable
fun rememberSign(
    trezorListener: TrezorListener,
    ledgerListener: LedgerListener,
): SignState {
    val signTrezor = rememberSignTrezor(trezorListener)
    val signLedger = rememberSignLedger(ledgerListener)

    return SignState(
        signedRequest = signTrezor.signedRequest ?: signLedger.signedRequest,
        loading = signTrezor.loading || signLedger.loading,
        ledgerFailure = signLedger.failure,
        trezorFailure = signTrezor.failure,

        sign = { hw, request ->
            when (hw) {
                is Trezor -> signTrezor.sign(hw, request)
                is Ledger -> signLedger.sign(hw, request)
            }
        },
    )
}

@Immutable
data class SignState(
    val signedRequest: SignedRequest?,
    val loading: Boolean,
    val ledgerFailure: LedgerFailure?,
    val trezorFailure: TrezorFailure?,

    val sign: (HardwareWallet, SignRequest) -> Unit,
)
