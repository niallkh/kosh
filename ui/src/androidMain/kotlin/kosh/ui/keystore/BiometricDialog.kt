package kosh.ui.keystore

import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import co.touchlab.kermit.Logger
import kosh.domain.models.keystore.CipherRequest
import kosh.domain.models.keystore.CipherWrapper
import javax.crypto.Cipher

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
    if (cipherRequest != null) {
        val context = LocalContext.current

        DisposableEffect(Unit) {
            var dismissed = false

            val cryptoObject = BiometricPrompt.CryptoObject(cipherRequest.cipher.value as Cipher)

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(title)
                .setSubtitle(subtitle)
                .run {
                    if (cipherRequest.onlyBiometry) {
                        setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                        setNegativeButtonText("Cancel")
                    } else {
                        setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                    }
                }
                .build()

            val biometricPrompt = BiometricPrompt(
                context as FragmentActivity,
                ContextCompat.getMainExecutor(context),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        dismissed = true
                        val value = result.cryptoObject?.cipher
                            ?: error("No cipher from biometric prompt")
                        onSuccess(CipherWrapper(value))
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Logger.w { "Authentication failed, wrong biometry" }
                        dismissed = true
                        onFailed()
                    }

                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        Logger.w { "Authentication failed: #$errorCode: $errString" }
                        dismissed = true
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                            onCanceled()
                        } else {
                            onError()
                        }
                    }
                }
            )

            biometricPrompt.authenticate(
                promptInfo,
                cryptoObject,
            )

            onDispose {
                if (dismissed.not()) {
                    biometricPrompt.cancelAuthentication()
                    dismissed = true
                }
            }
        }
    }
}
