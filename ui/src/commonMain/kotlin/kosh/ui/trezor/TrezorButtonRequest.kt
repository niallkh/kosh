package kosh.ui.trezor

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import kosh.domain.repositories.TrezorListener
import kosh.ui.component.scaffold.LocalSnackbarHostState
import kosh.ui.resources.Res
import kosh.ui.resources.trezor_button_request_other
import kosh.ui.resources.trezor_button_request_pin_code
import org.jetbrains.compose.resources.getString

@Composable
fun TrezorButtonRequest(
    type: TrezorListener.ButtonRequest?,
    onButtonRequestShown: (TrezorListener.ButtonRequest) -> Unit = {},
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val hapticFeedback = LocalHapticFeedback.current

    LaunchedEffect(type) {
        type?.let {
            try {
                snackbarHostState.currentSnackbarData?.dismiss()

                hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                snackbarHostState.showSnackbar(
                    message = it.toMessage(),
                    duration = SnackbarDuration.Short,
                    withDismissAction = true,
                )
            } finally {
                onButtonRequestShown(it)
            }
        }
    }
}

suspend fun TrezorListener.ButtonRequest.toMessage() = when (this) {
    TrezorListener.ButtonRequest.Other -> getString(Res.string.trezor_button_request_other)
    TrezorListener.ButtonRequest.FeeOverThreshold -> name
    TrezorListener.ButtonRequest.ConfirmOutput -> name
    TrezorListener.ButtonRequest.ResetDevice -> name
    TrezorListener.ButtonRequest.ConfirmWord -> name
    TrezorListener.ButtonRequest.WipeDevice -> name
    TrezorListener.ButtonRequest.ProtectCall -> name
    TrezorListener.ButtonRequest.SignTx -> name
    TrezorListener.ButtonRequest.FirmwareCheck -> name
    TrezorListener.ButtonRequest.Address -> name
    TrezorListener.ButtonRequest.PublicKey -> name
    TrezorListener.ButtonRequest.MnemonicWordCount -> name
    TrezorListener.ButtonRequest.MnemonicInput -> name
    TrezorListener.ButtonRequest.UnknownDerivationPath -> name
    TrezorListener.ButtonRequest.RecoveryHomepage -> name
    TrezorListener.ButtonRequest.Success -> name
    TrezorListener.ButtonRequest.Warning -> name
    TrezorListener.ButtonRequest.PassphraseEntry -> name
    TrezorListener.ButtonRequest.PinEntry -> getString(Res.string.trezor_button_request_pin_code)
}
