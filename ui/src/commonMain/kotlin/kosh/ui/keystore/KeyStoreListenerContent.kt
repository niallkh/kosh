package kosh.ui.keystore

import androidx.compose.runtime.Composable
import kosh.presentation.keystore.KeyStoreListenerState
import kosh.ui.resources.Res
import kosh.ui.resources.trezor_passphrase_biometric_subtitle
import kosh.ui.resources.trezor_passphrase_biometric_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun KeyStoreListenerContent(
    keyStoreListener: KeyStoreListenerState,
) {
    BiometricDialog(
        title = stringResource(Res.string.trezor_passphrase_biometric_title),
        subtitle = stringResource(Res.string.trezor_passphrase_biometric_subtitle),
        cipherRequest = keyStoreListener.cipherRequest,
        onSuccess = { keyStoreListener.enter(it) },
        onFailed = { keyStoreListener.cancel() },
        onError = { keyStoreListener.cancel() },
        onCanceled = { keyStoreListener.cancel() },
    )
}
