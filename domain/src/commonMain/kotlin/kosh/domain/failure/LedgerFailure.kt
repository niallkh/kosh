package kosh.domain.failure

sealed interface LedgerFailure : AppFailure {

    class Disabled : LedgerFailure {
        override val message: String
            get() = "Transport disabled. Please, enable device and try again."
    }

    class PermissionNotGranted : LedgerFailure {
        override val message: String
            get() = "Permission not granted. Please, grant permission."
    }

    class ConnectionFailed : LedgerFailure {
        override val message: String
            get() = "Couldn't connect to Ledger. Please, try again."
    }

    class Disconnected : LedgerFailure {
        override val message: String
            get() = "Ledger disconnected. Please, connect device and try again."
    }

    class InvalidState : LedgerFailure {
        override val message: String
            get() = "Ledger is in invalid state. Please, try again."
    }

    class NoEthereumApp : LedgerFailure {
        override val message: String
            get() = "Ethereum app not opened. Please, open Ethereum app."
    }

    class ActionCanceled : LedgerFailure {
        override val message: String
            get() = "Action canceled."
    }

    class CommunicationFailed : LedgerFailure {
        override val message: String
            get() = "Communication with Ledger failed. Please, try again."
    }

    class Other(
        override val message: String = "Something went wrong.",
    ) : LedgerFailure
}
