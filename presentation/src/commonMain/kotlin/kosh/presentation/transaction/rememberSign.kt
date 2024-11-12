package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import kosh.domain.failure.KeystoneFailure
import kosh.domain.failure.LedgerFailure
import kosh.domain.failure.TrezorFailure
import kosh.domain.models.hw.HardwareWallet
import kosh.domain.models.hw.Keystone
import kosh.domain.models.hw.Ledger
import kosh.domain.models.hw.Trezor
import kosh.domain.repositories.KeystoneListener
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.TrezorListener
import kosh.presentation.keystone.rememberSignKeystone
import kosh.presentation.ledger.rememberSignLedger
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.trezor.rememberSignTrezor

@Composable
fun rememberSign(
    trezorListener: TrezorListener,
    ledgerListener: LedgerListener,
    keystoneListener: KeystoneListener,
): SignState {
    val signTrezor = rememberSignTrezor(trezorListener)
    val signLedger = rememberSignLedger(ledgerListener)
    val signKeystone = rememberSignKeystone(keystoneListener)

    return SignState(
        signedRequest = signTrezor.signedRequest
            ?: signLedger.signedRequest
            ?: signKeystone.signedRequest,
        loading = signTrezor.loading ||
                signLedger.loading ||
                signKeystone.loading,

        ledgerFailure = signLedger.failure,
        trezorFailure = signTrezor.failure,
        keystoneFailure = signKeystone.failure,

        sign = { hw, request ->
            when (hw) {
                is Trezor -> signTrezor.sign(hw, request)
                is Ledger -> signLedger.sign(hw, request)
                is Keystone -> signKeystone.sign(hw, request)
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
    val keystoneFailure: KeystoneFailure?,

    val sign: (HardwareWallet, SignRequest) -> Unit,
)
