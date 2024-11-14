package kosh.presentation.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import kosh.domain.failure.AppFailure
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
    onSigned: (SignedRequest) -> Unit,
): SignState {
    val signTrezor = rememberSignTrezor(trezorListener, onSigned = onSigned)
    val signLedger = rememberSignLedger(ledgerListener, onSigned = onSigned)
    val signKeystone = rememberSignKeystone(keystoneListener, onSigned = onSigned)

    return remember {
        object : SignState {
            override val signedRequest: SignedRequest? by derivedStateOf {
                signTrezor.signedRequest ?: signLedger.signedRequest ?: signKeystone.signedRequest
            }
            override val signing: Boolean by derivedStateOf {
                signTrezor.loading || signLedger.loading || signKeystone.loading
            }

            override val failure: AppFailure? by derivedStateOf {
                signTrezor.failure ?: signLedger.failure ?: signKeystone.failure
            }

            override operator fun invoke(hw: HardwareWallet, request: SignRequest) {
                when (hw) {
                    is Trezor -> signTrezor(hw, request)
                    is Ledger -> signLedger.sign(hw, request)
                    is Keystone -> signKeystone.sign(hw, request)
                }
            }
        }
    }
}

@Stable
interface SignState {
    val signedRequest: SignedRequest?
    val signing: Boolean
    val failure: AppFailure?
    operator fun invoke(hw: HardwareWallet, request: SignRequest)
}
