package kosh.domain.failure

sealed interface LedgerFailure : AppFailure {

    class PermissionNotGranted : LedgerFailure {
        override val message: String
            get() = "Permission not granted"
    }

    class UsbInterfaceNotClaimed : LedgerFailure {
        override val message: String
            get() = "Usb interface not claimed"
    }

    class NotConnected : LedgerFailure {
        override val message: String
            get() = "Ledger not connected"
    }

    class InvalidState : LedgerFailure {
        override val message: String
            get() = "Ledger is in invalid state"
    }

    class NotEthereumApp : LedgerFailure {
        override val message: String
            get() = "Ethereum app not opened"
    }

    class RequestCanceled : LedgerFailure {
        override val message: String
            get() = "Request canceled"
    }

    class Other : LedgerFailure {
        override val message: String
            get() = "Something went wrong"
    }
}
