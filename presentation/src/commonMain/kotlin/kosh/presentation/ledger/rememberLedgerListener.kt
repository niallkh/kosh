package kosh.presentation.ledger

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.node.Ref
import kosh.domain.failure.RequestCanceledException
import kosh.domain.repositories.LedgerListener
import kosh.domain.repositories.LedgerListener.ButtonRequest
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun rememberLedgerListener(): LedgerListenerState {
    var buttonRequest by remember { mutableStateOf<ButtonRequest?>(null) }
    val continuation = remember { Ref<CancellableContinuation<Unit>>() }

    val listener = remember {
        object : LedgerListener {
            override suspend fun buttonRequest(request: ButtonRequest) {
                suspendCancellableCoroutine { cont ->
                    continuation.value?.cancel()
                    buttonRequest = request
                    continuation.value = cont
                }
            }
        }
    }

    return LedgerListenerState(
        listener = listener,
        buttonRequest = buttonRequest,
        confirm = {
            buttonRequest = null
            continuation.value?.resume(Unit)
            continuation.value = null
        },
        dismiss = {
            buttonRequest = null
            continuation.value?.cancel(RequestCanceledException())
            continuation.value = null
        }
    )
}


@Immutable
data class LedgerListenerState(
    val listener: LedgerListener,
    val buttonRequest: ButtonRequest?,
    val confirm: (ButtonRequest) -> Unit,
    val dismiss: (ButtonRequest) -> Unit,
)
