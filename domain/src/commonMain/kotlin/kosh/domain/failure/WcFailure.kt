package kosh.domain.failure

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.eip55

@Immutable
sealed interface WcFailure : AppFailure {

    class NoConnection : WcFailure {
        override val message: String
            get() = "No connection"
    }

    class PairingUriInvalid : WcFailure {
        override val message: String
            get() = "Invalid pairing uri, try again"
    }

    class AlreadyPaired : WcFailure {
        override val message: String
            get() = "Already paired, try again"
    }

    class PairingUriExpired : WcFailure {
        override val message: String
            get() = "Pairing uri expired, try again"
    }

    class TopicInvalid : WcFailure {
        override val message: String
            get() = "Invalid topic, try again"
    }

    class SessionNotFound : WcFailure {
        override val message: String
            get() = "Session not found"
    }

    class ProposalNotFound : WcFailure {
        override val message: String
            get() = "Proposal not found"
    }

    class RequestNotFound : WcFailure {
        override val message: String
            get() = "Request not found"
    }

    class RequestExpired : WcFailure {
        override val message: String
            get() = "Request expired"
    }

    class AuthenticationNotFound : WcFailure {
        override val message: String
            get() = "Authentication not found"
    }

    class VerifyContextNotFound : WcFailure {
        override val message: String
            get() = "Verify Context not found"
    }

    class Other(override val message: String) : WcFailure

    @Immutable
    sealed interface WcInvalidDapp : WcFailure {

        class NoRequiredChains : WcInvalidDapp {
            override val message: String
                get() = "No Required chains"
        }

        class NoSupportedMethods : WcInvalidDapp {
            override val message: String
                get() = "No supported methods"
        }

        class NoSupportedEvents : WcInvalidDapp {
            override val message: String
                get() = "No supported events"
        }

        class NoSupportedAccounts : WcInvalidDapp {
            override val message: String
                get() = "No supported accounts"
        }

        class NoApprovedAccounts : WcInvalidDapp {
            override val message: String
                get() = "No approved accounts"
        }

        class NoMetadata : WcInvalidDapp {
            override val message: String
                get() = "No Dapp Metadata"
        }
    }

    @Immutable
    sealed interface WcInvalidRequest : WcFailure {
        class MethodNotSupported(private val method: String) : WcInvalidRequest {
            override val message: String
                get() = "Method not supported: $method"
        }

        class NetworkDisabled(private val chainId: ChainId) : WcInvalidRequest {
            override val message: String
                get() = "Network disabled: #${chainId.value}"
        }

        class NetworkAlreadyExists(
            val chainId: ChainId,
            val enabled: Boolean,
        ) : WcInvalidRequest {
            override val message: String
                get() = "Network already exists: #${chainId.value}"
        }

        class AccountDisabled(private val address: Address) : WcInvalidRequest {
            override val message: String
                get() = "Account disabled: ${address.eip55()}"
        }

        class Other(override val message: String) : WcInvalidRequest
    }
}
