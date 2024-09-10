package kosh.ui.keystore

import androidx.compose.runtime.Composable
import kosh.domain.models.keystore.CipherRequest
import kosh.domain.models.keystore.CipherWrapper

@Composable
actual fun BiometricDialog(
    title: String,
    subtitle: String,
    cipherRequest: CipherRequest?,
    onSuccess: (CipherWrapper) -> Unit,
    onFailed: () -> Unit,
    onError: () -> Unit,
    onCanceled: () -> Unit,
) {
    TODO("Not yet implemented")
}
