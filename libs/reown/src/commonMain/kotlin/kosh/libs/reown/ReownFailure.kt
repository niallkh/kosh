package kosh.libs.reown

sealed class ReownFailure(val message: String) {
    class NotConnected(message: String?) : ReownFailure(message ?: "Not connected")
    class ResponseTimeout(message: String?) : ReownFailure(message ?: "Response timeout")

    class AlreadyPaired(message: String?) : ReownFailure(message ?: "Already paired")
    class PairingUriInvalid(message: String?) : ReownFailure(message ?: "Pairing URI invalid")

    class NotFound(message: String?) : ReownFailure(message ?: "Not found")

    class InvalidNamespace(message: String?) : ReownFailure(message ?: "Invalid namespace")

    class Other(message: String?) : ReownFailure(message ?: "Something went wrong")
}
