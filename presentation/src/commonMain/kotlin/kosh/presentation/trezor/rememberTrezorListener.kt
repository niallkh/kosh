package kosh.presentation.trezor

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.node.Ref
import kosh.domain.entities.WalletEntity
import kosh.domain.failure.RequestCanceledException
import kosh.domain.models.trezor.Passphrase
import kosh.domain.models.trezor.PinMatrix
import kosh.domain.models.trezor.enterOnDevice
import kosh.domain.repositories.KeyStoreListener
import kosh.domain.repositories.TrezorListener
import kosh.domain.repositories.TrezorListener.ButtonRequest
import kosh.domain.usecases.trezor.TrezorPassphraseService
import kosh.presentation.core.di
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun rememberTrezorListener(
    id: WalletEntity.Id?,
    keyStoreListener: KeyStoreListener,
    trezorPassphraseService: TrezorPassphraseService = di { domain.trezorPassphraseService },
): TrezorListenerState {
    var buttonRequest by remember { mutableStateOf<ButtonRequest?>(null) }
    var passphraseRequest by remember { mutableStateOf<PassphraseRequest?>(null) }
    var pinMatrixRequest by remember { mutableStateOf<PinMatrixRequest?>(null) }

    var savedPassphrase by remember { mutableStateOf<Passphrase?>(null) }

    val passphraseContinuation = remember { Ref<CancellableContinuation<Passphrase>>() }
    val pinMatrixContinuation = remember { Ref<CancellableContinuation<PinMatrix>>() }

    val coroutineScope = rememberCoroutineScope()

    val listener = remember(id, keyStoreListener) {
        object : TrezorListener {

            override suspend fun passphraseRequest(): Passphrase =
                id?.let { trezorPassphraseService.get(keyStoreListener, id) } ?: run {
                    val canSave = id?.let { !trezorPassphraseService.contains(it) } ?: true
                    suspendCancellableCoroutine { cont ->
                        passphraseContinuation.value?.cancel()
                        passphraseRequest = PassphraseRequest(canSave)
                        passphraseContinuation.value = cont
                    }
                }

            override suspend fun pinMatrixRequest(): PinMatrix =
                suspendCancellableCoroutine { cont ->
                    pinMatrixContinuation.value?.cancel()
                    pinMatrixRequest = PinMatrixRequest
                    pinMatrixContinuation.value = cont
                }

            override fun onButtonRequest(request: ButtonRequest) {
                buttonRequest = request
            }

            override fun onConnected(id: WalletEntity.Id) {
                coroutineScope.launch {
                    savedPassphrase?.let { passphrase ->
                        trezorPassphraseService.save(keyStoreListener, id, passphrase)
                        savedPassphrase = null
                    }
                }
            }
        }
    }

    return TrezorListenerState(
        listener = listener,
        buttonRequest = buttonRequest,
        passphraseRequest = passphraseRequest,
        pinMatrixRequest = pinMatrixRequest,
        confirm = { buttonRequest = null },
        enterPassphrase = { passphrase, save ->
            passphraseContinuation.value?.resume(passphrase)
            passphraseRequest = null
            savedPassphrase = passphrase.takeIf { save && !passphrase.enterOnDevice }
            passphraseContinuation.value = null
        },
        cancelPassphrase = {
            passphraseContinuation.value?.cancel(RequestCanceledException())
            passphraseRequest = null
            savedPassphrase = null
            passphraseContinuation.value = null
        },
        enterPinMatrix = { pinMatrix ->
            pinMatrixContinuation.value?.resume(pinMatrix)
            pinMatrixRequest = null
            pinMatrixContinuation.value = null
        },
        cancelPinMatrix = {
            pinMatrixContinuation.value?.cancel(RequestCanceledException())
            pinMatrixRequest = null
            pinMatrixContinuation.value = null
        },
    )
}

@Immutable
data class TrezorListenerState(
    val listener: TrezorListener,
    val buttonRequest: ButtonRequest?,
    val passphraseRequest: PassphraseRequest?,
    val pinMatrixRequest: PinMatrixRequest?,

    val confirm: (ButtonRequest) -> Unit,
    val enterPassphrase: (Passphrase, Boolean) -> Unit,
    val cancelPassphrase: () -> Unit,
    val enterPinMatrix: (PinMatrix) -> Unit,
    val cancelPinMatrix: () -> Unit,
)

@Immutable
data class PassphraseRequest(val canSave: Boolean)

@Immutable
data object PinMatrixRequest
