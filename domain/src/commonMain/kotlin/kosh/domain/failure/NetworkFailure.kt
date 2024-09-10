package kosh.domain.failure

import kotlinx.serialization.Serializable

@Serializable
sealed interface NetworkFailure : AppFailure {
    class InvalidRpcProvider : NetworkFailure {
        override val message: String
            get() = "Invalid Rpc Provider"
    }

    class AlreadyExist : NetworkFailure {
        override val message: String
            get() = "Network is invalid"
    }

    class NotFound : NetworkFailure {
        override val message: String
            get() = "Network isn't exist"
    }

    class NoActiveNetworks : NetworkFailure {
        override val message: String
            get() = "No Active Networks"
    }

    class InvalidNetworkName : NetworkFailure {
        override val message: String
            get() = "Invalid Network Name"
    }

    class InvalidChainId : NetworkFailure {
        override val message: String
            get() = "Invalid Chain Id"
    }
}
