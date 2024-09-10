package kosh.data.trezor

import arrow.core.Either
import arrow.core.left
import co.touchlab.kermit.Logger
import com.satoshilabs.trezor.lib.protobuf.Failure
import kosh.domain.failure.RequestCanceledException
import kosh.domain.failure.TrezorFailure
import kosh.libs.trezor.TrezorManager.Connection.Listener.TrezorFailureException
import kosh.libs.usb.PermissionNotGrantedException
import kosh.libs.usb.UsbInterfaceNotClaimedException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch


fun <T> Either<Throwable, T>.mapTrezorFailure(logger: Logger) = mapLeft {
    logger.w(it) { "Error happened" }
    when (it) {
        is PermissionNotGrantedException -> TrezorFailure.PermissionNotGranted()
        is UsbInterfaceNotClaimedException -> TrezorFailure.UsbInterfaceNotClaimed()
        is TrezorFailureException -> when (it.type) {
            Failure.FailureType.Failure_ActionCancelled -> TrezorFailure.ActionCanceled()
            else -> TrezorFailure.Internal("${it.type}: ${it.message}")
        }

        is RequestCanceledException -> TrezorFailure.ActionCanceled()
        else -> TrezorFailure.Other()
    }
}

fun <T> Flow<Either<TrezorFailure, T>>.catchTrezorFailure(logger: Logger) = catch {
    emit(it.left().mapTrezorFailure(logger))
}
