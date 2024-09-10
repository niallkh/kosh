package kosh.domain.failure

import kotlinx.serialization.Serializable

@Serializable
sealed interface AccountFailure : AppFailure {

    class WalletNotCreated : AccountFailure {
        override val message: String
            get() = "Wallet not created"
    }

    class WalletAlreadyExist : AccountFailure {
        override val message: String
            get() = "Wallet already exist"
    }

    class AlreadyExist : AccountFailure {
        override val message: String
            get() = "Account already exist"
    }

    class NotFound : AccountFailure {
        override val message: String
            get() = "Account not found"
    }

    class NoActiveAccounts : AccountFailure {
        override val message: String
            get() = "No Active Accounts"
    }

    class InvalidAccountName : AccountFailure {
        override val message: String
            get() = "Invalid Account Name"
    }
}
