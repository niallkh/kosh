package kosh.domain.failure

sealed interface KeystoneFailure : AppFailure {

    class Disabled : KeystoneFailure {
        override val message: String
            get() = "Transport disabled. Please, enable device and try again."
    }

    class Disconnected : KeystoneFailure {
        override val message: String
            get() = "Keystone disconnected. Please, connect device and try again."
    }

    class InvalidState : KeystoneFailure {
        override val message: String
            get() = "Keystone is in invalid state. Please, try again."
    }

    class PermissionNotGranted : KeystoneFailure {
        override val message: String
            get() = "Permission not granted. Please, grant permission."
    }

    class ConnectionFailed : KeystoneFailure {
        override val message: String
            get() = "Couldn't connect to Keystone. Please, try again."
    }

    class CommunicationFailed : KeystoneFailure {
        override val message: String
            get() = "Communication with Keystone failed. Please, try again."
    }

    class ActionCanceled : KeystoneFailure {
        override val message: String
            get() = "Action canceled."
    }

    class WrongState : KeystoneFailure {
        override val message: String
            get() = "Keystone is in wrong state. Please, go to home screen and try again."
    }

    class Other(
        override val message: String = "Something went wrong.",
    ) : KeystoneFailure
}
