package kosh.domain.failure

sealed interface TrezorFailure : AppFailure {
    class Disabled : TrezorFailure {
        override val message: String
            get() = "Transport disabled. Please, enable device and try again."
    }

    class Disconnected : TrezorFailure {
        override val message: String
            get() = "Trezor disconnected. Please, connect device and try again."
    }

    class InvalidState : TrezorFailure {
        override val message: String
            get() = "Trezor is in invalid state. Please, try again."
    }

    class PermissionNotGranted : TrezorFailure {
        override val message: String
            get() = "Permission not granted. Please, grant permission."
    }

    class ConnectionFailed : TrezorFailure {
        override val message: String
            get() = "Couldn't connect to Trezor. Please, try again."
    }

    class CommunicationFailed : TrezorFailure {
        override val message: String
            get() = "Communication with Trezor failed. Please, try again."
    }

    class ActionCanceled : TrezorFailure {
        override val message: String
            get() = "Action canceled."
    }

    class Other(
        override val message: String = "Something went wrong.",
    ) : TrezorFailure
}
