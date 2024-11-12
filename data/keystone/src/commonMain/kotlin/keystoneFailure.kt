package kosh.data.keystone

import arrow.core.Either
import arrow.core.left
import co.touchlab.kermit.Logger
import kosh.domain.failure.KeystoneFailure
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

internal fun <T> Flow<Either<KeystoneFailure, T>>.catchKeystoneFailure(logger: Logger) = catch {
    emit(it.left().mapKeystoneFailure(logger))
}

internal fun <T> Either<Throwable, T>.mapKeystoneFailure(logger: Logger) = mapLeft {
    it.mapKeystoneFailure(logger)
}

internal fun Throwable.mapKeystoneFailure(
    logger: Logger,
): KeystoneFailure {
    logger.e(this) { "Keystone: Error happened" }
    return when (this) {
        is TransportException -> when (this) {
            is CommunicationFailedException -> KeystoneFailure.CommunicationFailed()
            is ConnectionFailedException -> KeystoneFailure.ConnectionFailed()
            is DeviceDisconnectedException -> KeystoneFailure.Disconnected()
            is PermissionNotGrantedException -> KeystoneFailure.PermissionNotGranted()
            is ResponseTimeoutException -> KeystoneFailure.CommunicationFailed()
            is TransportDisabledException -> KeystoneFailure.Disabled()
        }

        is RequestCanceledException -> KeystoneFailure.ActionCanceled()
        else -> KeystoneFailure.Other()
    }
}
