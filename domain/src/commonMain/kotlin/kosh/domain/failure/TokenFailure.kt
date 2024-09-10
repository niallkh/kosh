package kosh.domain.failure

import kotlinx.serialization.Serializable

@Serializable
sealed interface TokenFailure : AppFailure {

    class AlreadyExist : TokenFailure {
        override val message: String
            get() = "Token is already exist"
    }

    class NotFound : TokenFailure {
        override val message: String
            get() = "Token not found"
    }

    class NotDeleted : TokenFailure {
        override val message: String
            get() = "Can't delete"
    }

    class NoOnChainData : TokenFailure {
        override val message: String
            get() = "There is no on chain data"
    }

    class InvalidTokenName : TokenFailure {
        override val message: String
            get() = "Invalid Token Name"
    }

    class InvalidTokenSymbol : TokenFailure {
        override val message: String
            get() = "Invalid Token Symbol"
    }
}
