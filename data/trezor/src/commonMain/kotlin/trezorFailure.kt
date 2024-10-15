package kosh.data.trezor

import arrow.core.Either
import arrow.core.left
import co.touchlab.kermit.Logger
import com.satoshilabs.trezor.lib.protobuf.Failure
import kosh.domain.failure.RequestCanceledException
import kosh.domain.failure.TrezorFailure
import kosh.libs.transport.TransportException
import kosh.libs.transport.TransportException.CommunicationFailedException
import kosh.libs.transport.TransportException.ConnectionFailedException
import kosh.libs.transport.TransportException.DeviceDisconnectedException
import kosh.libs.transport.TransportException.PermissionNotGrantedException
import kosh.libs.transport.TransportException.ResponseTimeoutException
import kosh.libs.transport.TransportException.TransportDisabledException
import kosh.libs.trezor.TrezorManager.Connection.Listener.TrezorFailureException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

fun <T> Flow<Either<TrezorFailure, T>>.catchTrezorFailure(logger: Logger) = catch {
    emit(it.left().mapTrezorFailure(logger))
}

fun <T> Either<Throwable, T>.mapTrezorFailure(logger: Logger) = mapLeft {
    logger.e(it) { "Trezor: Error happened" }
    when (it) {
        is TransportException -> when (it) {
            is CommunicationFailedException -> TrezorFailure.CommunicationFailed()
            is ConnectionFailedException -> TrezorFailure.ConnectionFailed()
            is DeviceDisconnectedException -> TrezorFailure.Disconnected()
            is PermissionNotGrantedException -> TrezorFailure.PermissionNotGranted()
            is ResponseTimeoutException -> TrezorFailure.CommunicationFailed()
            is TransportDisabledException -> TrezorFailure.Disabled()
        }

        is TrezorFailureException -> when (it.type) {
            Failure.FailureType.Failure_ActionCancelled -> TrezorFailure.ActionCanceled()
            else -> TrezorFailure.Other("${it.type}: ${it.message}")
        }

        is RequestCanceledException -> TrezorFailure.ActionCanceled()
        else -> TrezorFailure.Other()
    }
}

