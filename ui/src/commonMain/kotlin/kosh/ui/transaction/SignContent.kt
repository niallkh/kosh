package kosh.ui.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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

    AppFailureMessage(sign.ledgerFailure) {
        sign.retry()
    }

    AppFailureMessage(sign.trezorFailure) {
        sign.retry()
    }

    LaunchedEffect(sign.signedRequest) {
        sign.signedRequest?.let {
        }
    }

    return SignState(
        signing = sign.loading,
        signedRequest = sign.signedRequest,
        sign = sign.sign
    )
}

data class SignState(
    val signedRequest: SignedRequest?,
    val signing: Boolean,
    val sign: (SignRequest) -> Unit,
)
