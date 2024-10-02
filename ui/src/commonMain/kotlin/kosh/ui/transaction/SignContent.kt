package kosh.ui.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kosh.domain.models.Address
import kosh.presentation.account.rememberAccount
import kosh.presentation.keystore.rememberKeyStoreListener
import kosh.presentation.ledger.rememberLedgerListener
import kosh.presentation.models.SignRequest
import kosh.presentation.models.SignedRequest
import kosh.presentation.transaction.rememberSign
import kosh.presentation.trezor.rememberTrezorListener
import kosh.ui.failure.AppFailureMessage
import kosh.ui.keystore.KeyStoreListenerContent
import kosh.ui.ledger.LedgerButtonRequest
import kosh.ui.trezor.TrezorListenerContent
import kosh.ui.wallet.HardwareWalletSelector

@Composable
fun SignContent(
    address: Address?,
): SignState {
    val account = address?.let { rememberAccount(address) }

    val keyStoreListener = rememberKeyStoreListener()

    KeyStoreListenerContent(
        keyStoreListener = keyStoreListener
    )

    val trezorListener = rememberTrezorListener(
        account?.entity?.walletId, keyStoreListener.listener
    )

    TrezorListenerContent(trezorListener)

    val ledgerListener = rememberLedgerListener()

    LedgerButtonRequest(
        request = ledgerListener.buttonRequest,
        onConfirm = ledgerListener.confirm,
        onDismiss = ledgerListener.dismiss
    )

    val sign = rememberSign(
        trezorListener = trezorListener.listener,
        ledgerListener = ledgerListener.listener,
    )

    AppFailureMessage(sign.ledgerFailure)

    AppFailureMessage(sign.trezorFailure)

    var selectorVisible by rememberSaveable { mutableStateOf(false) }
    var signRequest by remember { mutableStateOf<SignRequest?>(null) }

    HardwareWalletSelector(
        visible = selectorVisible,
        onDismiss = { selectorVisible = false },
        onSelected = { hw -> signRequest?.let { sign.sign(hw, it) } }
    )

    return SignState(
        signing = sign.loading,
        signedRequest = sign.signedRequest,
        sign = {
            signRequest = it
            selectorVisible = true
        }
    )
}

data class SignState(
    val signedRequest: SignedRequest?,
    val signing: Boolean,
    val sign: (SignRequest) -> Unit,
)
