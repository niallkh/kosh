package kosh.data.trezor

import kosh.domain.repositories.LedgerListener
import kosh.libs.ledger.LedgerManager.Listener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LedgerListenerAdapter(
    private val listener: LedgerListener,
) : Listener {

    override suspend fun buttonRequest(type: Listener.ButtonRequest) {
        withContext(Dispatchers.Main) {
            listener.buttonRequest(type.map())
        }
    }
}

internal fun Listener.ButtonRequest.map() = when (this) {
    Listener.ButtonRequest.UnlockDevice -> LedgerListener.ButtonRequest.UnlockDevice
}
