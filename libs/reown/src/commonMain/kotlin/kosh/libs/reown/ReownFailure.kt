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

/**
 * class GenericException(override val message: String?) : WalletConnectException(message)
 *
 * class MalformedWalletConnectUri(override val message: String?) : WalletConnectException(message)
 * class PairWithExistingPairingIsNotAllowed(override val message: String?) : WalletConnectException(message)
 * class ExpiredPairingException(override val message: String?) : WalletConnectException(message)
 * class ExpiredPairingURIException(override val message: String?) : WalletConnectException(message)
 * class CannotFindSequenceForTopic(override val message: String?) : WalletConnectException(message)
 *
 * class InvalidProjectIdException(override val message: String?) : WalletConnectException(message)
 * class UnableToConnectToWebsocketException(override val message: String?) : WalletConnectException(message)
 * class ProjectIdDoesNotExistException(override val message: String?) : WalletConnectException(message)
 * open class NoConnectivityException(override val message: String?) : WalletConnectException(message)
 * class NoInternetConnectionException(override val message: String?) : NoConnectivityException(message)
 * class NoRelayConnectionException(override val message: String?) : NoConnectivityException(message)
 * class CannotFindKeyPairException(override val message: String?) : WalletConnectException(message)
 * class InvalidExpiryException(override val message: String? = "Request expiry validation failed. Expiry must be between current timestamp + MIN_INTERVAL and current timestamp + MAX_INTERVAL (MIN_INTERVAL: 300, MAX_INTERVAL: 604800)") :
 *     WalletConnectException(message)
 *
 * class RequestExpiredException(override val message: String?) : WalletConnectException(message)
 *
 * abstract class WalletConnectException(override val message: String?) : Exception(message)
 * internal class UnableToExtractDomainException(keyserverUrl: String) : WalletConnectException("Unable to extract domain from: $keyserverUrl")
 * internal class InvalidAccountIdException(accountId: AccountId) : WalletConnectException("AccountId: $accountId is not CAIP-10 complaint") // todo: https://github.com/WalletConnect/WalletConnectKotlinV2/issues/768
 * internal class UserRejectedSigning() : WalletConnectException("User rejected signing")
 * internal class InvalidIdentityCacao() : WalletConnectException("Invalid identity cacao")
 * internal class AccountHasNoIdentityStored(accountId: AccountId) : WalletConnectException("AccountId: $accountId has no identity stored")
 * class AccountHasNoCacaoPayloadStored(accountId: AccountId) : WalletConnectException("AccountId: $accountId has no message stored")
 * class AccountHasDifferentStatementStored(accountId: AccountId) : WalletConnectException("AccountId: $accountId has old statement stored")
 *
 */

//when (throwable) {
//    is MalformedWalletConnectUri -> WcFailure.PairingUriInvalid()
//    is PairWithExistingPairingIsNotAllowed -> WcFailure.AlreadyPaired()
//    is ExpiredPairingException -> WcFailure.PairingUriExpired()
//    is ExpiredPairingURIException -> WcFailure.PairingUriExpired()
//    is CannotFindSequenceForTopic -> WcFailure.TopicInvalid()
//    is NoConnectivityException -> WcFailure.NoConnection()
//    is RequestExpiredException -> WcFailure.RequestExpired()
//    else -> null
//}

/**
 * enum WalletConnectError: Error {
 *
 *     case pairingProposalFailed
 *     case noPairingMatchingTopic(String)
 *     case noSessionMatchingTopic(String)
 *     case sessionNotAcknowledged(String)
 *     case pairingNotSettled(String)
 *     case invalidMethod
 *     case invalidEvent
 *     case invalidUpdateExpiryValue
 *     case unauthorizedNonControllerCall
 *     case pairingAlreadyExist
 *     case topicGenerationFailed
 *     case invalidPermissions // TODO: Refactor into actual cases
 *     case unsupportedNamespace(SignReasonCode)
 *     case emptySessionProperties
 *
 *     case `internal`(_ reason: InternalReason)
 *
 *     enum InternalReason: Error {
 *         case jsonRpcDuplicateDetected
 *         case noJsonRpcRequestMatchingResponse
 *     }
 * }
 *
 */
