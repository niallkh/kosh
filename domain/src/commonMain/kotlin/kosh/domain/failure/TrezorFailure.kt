package kosh.domain.failure

sealed interface TrezorFailure : AppFailure {
    class NotConnected : TrezorFailure {
        override val message: String
            get() = "Trezor not connected"
    }

    class InvalidState : TrezorFailure {
        override val message: String
            get() = "Trezor is in invalid state"
    }

    class PermissionNotGranted : TrezorFailure {
        override val message: String
            get() = "Permission not granted"
    }

    class UsbInterfaceNotClaimed : TrezorFailure {
        override val message: String
            get() = "Usb interface not claimed"
    }

    class ActionCanceled : TrezorFailure {
        override val message: String
            get() = "Action canceled"
    }

    class RequestCanceled : TrezorFailure {
        override val message: String
            get() = "Request canceled"
    }

    class Internal(override val message: String) : TrezorFailure

    class Other : TrezorFailure {
        override val message: String
            get() = "Something went wrong"
    }
}
