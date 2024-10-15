package kosh.data.trezor

import arrow.core.Either
import arrow.core.left
import co.touchlab.kermit.Logger
import kosh.domain.failure.LedgerFailure
import kosh.domain.failure.RequestCanceledException
import kosh.libs.transport.TransportException
import kosh.libs.transport.TransportException.CommunicationFailedException
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.DeviceDisconnectedException
import kosh.libs.transport.TransportException.PermissionNotGrantedException
import kosh.libs.transport.TransportException.ResponseTimeoutException
import kosh.libs.transport.TransportException.TransportDisabledException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

internal fun <T> Flow<Either<LedgerFailure, T>>.catchLedgerFailure(logger: Logger) = catch {
    emit(it.left().mapLedgerFailure(logger))
}

internal fun <T> Either<Throwable, T>.mapLedgerFailure(logger: Logger) = mapLeft {
    it.mapLedgerFailure(logger)
}

internal fun Throwable.mapLedgerFailure(
    logger: Logger,
): LedgerFailure {
    logger.w(this) { "Ledger: Error happened" }
    return when (this) {
        is TransportException -> when (this) {
            is CommunicationFailedException -> LedgerFailure.CommunicationFailed()
            is ConnectionFailedException -> LedgerFailure.ConnectionFailed()
            is DeviceDisconnectedException -> LedgerFailure.Disconnected()
            is PermissionNotGrantedException -> LedgerFailure.PermissionNotGranted()
            is ResponseTimeoutException -> LedgerFailure.CommunicationFailed()
            is TransportDisabledException -> LedgerFailure.Disabled()
        }

        is RequestCanceledException -> LedgerFailure.ActionCanceled()
        else -> LedgerFailure.Other()
    }
}
