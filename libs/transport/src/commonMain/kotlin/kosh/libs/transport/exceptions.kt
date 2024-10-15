package kosh.libs.transport

import kotlinx.io.IOException

sealed class TransportException(
    message: String? = null,
    cause: Throwable? = null,
) : IOException(message, cause) {
    class TransportDisabledException : TransportException()
    class PermissionNotGrantedException(message: String?) : TransportException(message)
    class ConnectionFailedException(message: String? = null) : TransportException(message)
    class DeviceDisconnectedException(message: String? = null) : TransportException(message)
    class CommunicationFailedException(message: String? = null) : TransportException(message)
    class ResponseTimeoutException(message: String? = null) : TransportException(message)
}
