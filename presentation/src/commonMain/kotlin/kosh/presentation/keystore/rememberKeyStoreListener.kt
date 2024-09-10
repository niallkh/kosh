package kosh.presentation.keystore

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.node.Ref
import kosh.domain.models.keystore.CipherRequest
import kosh.domain.models.keystore.CipherWrapper
import kosh.domain.repositories.KeyStoreListener
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun rememberKeyStoreListener(): KeyStoreListenerState {
    var cipherRequest by remember { mutableStateOf<CipherRequest?>(null) }
    val cipherContinuation = remember { Ref<CancellableContinuation<CipherWrapper>>() }

    val listener = remember {
        object : KeyStoreListener {
            override suspend fun biometricRequest(request: CipherRequest): CipherWrapper? =
                suspendCancellableCoroutine { cont ->
                    cipherContinuation.value?.cancel()
                    cipherRequest = request
                    cipherContinuation.value = cont
                }
        }
    }

    return KeyStoreListenerState(
        listener = listener,
        cipherRequest = cipherRequest,
        enter = {
            cipherContinuation.value?.resume(it)
            cipherContinuation.value = null
            cipherRequest = null
        },
        cancel = {
            cipherContinuation.value?.cancel()
            cipherContinuation.value = null
            cipherRequest = null
        }
    )
}

data class KeyStoreListenerState(
    val listener: KeyStoreListener,
    val cipherRequest: CipherRequest?,
    val enter: (CipherWrapper) -> Unit,
    val cancel: () -> Unit,
)
