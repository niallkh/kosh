package kosh.domain.usecases.wc

import kosh.domain.repositories.ReownRepo
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.withContext

class WcConnectionService(
    private val reownRepo: ReownRepo,
) {

    suspend fun connect() {
        reownRepo.connect()
    }

    suspend fun disconnect() {
        reownRepo.disconnect()
    }
}

suspend fun WcConnectionService.useConnection(): Nothing {
    connect()
    try {
        awaitCancellation()
    } finally {
        withContext(NonCancellable) {
            disconnect()
        }
    }
}
