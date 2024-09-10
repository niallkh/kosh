package kosh.ui.trezor

import androidx.compose.runtime.Composable
import kosh.domain.models.trezor.Passphrase
import kosh.domain.models.trezor.PinMatrix
import kosh.presentation.trezor.TrezorListenerState

@Composable
fun TrezorListenerContent(
    trezorListener: TrezorListenerState,
) {
    TrezorPassphraseDialog(
        visible = trezorListener.passphraseRequest != null,
        canSave = trezorListener.passphraseRequest?.canSave ?: false,
        onConfirm = { text, save -> trezorListener.enterPassphrase(Passphrase(text), save) },
        onDeviceEnter = { trezorListener.enterPassphrase(Passphrase(null), false) },
        onDismiss = { trezorListener.cancelPassphrase() },
    )

    TrezorPinMatrixDialog(
        visible = trezorListener.pinMatrixRequest != null,
        onConfirm = { trezorListener.enterPinMatrix(PinMatrix(it)) },
        onDismiss = { trezorListener.cancelPinMatrix() },
    )

    TrezorButtonRequest(
        type = trezorListener.buttonRequest,
        onButtonRequestShown = { trezorListener.confirm(it) },
    )
}
