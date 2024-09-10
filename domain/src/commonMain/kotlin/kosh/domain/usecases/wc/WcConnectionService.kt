package kosh.domain.usecases.wc

import kosh.domain.repositories.WcRepo
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class WcConnectionService(
    private val wcRepo: WcRepo,
) {
    val connected: Flow<Boolean>
        get() = wcRepo.connected

    suspend fun initialize() = wcRepo.initialize()

    suspend fun connect() {
        wcRepo.connect()
    }

    suspend fun disconnect() {
        wcRepo.disconnect()
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
