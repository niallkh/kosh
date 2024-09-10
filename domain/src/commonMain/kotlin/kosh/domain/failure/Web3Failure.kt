package kosh.domain.failure

sealed interface Web3Failure : AppFailure {

    class NoInternet : Web3Failure {
        override val message: String
            get() = "No Internet"
    }

    class NetworkNotSupported : Web3Failure {
        override val message: String
            get() = "Network Not Supported"
    }

    class NoOnChainData : Web3Failure {
        override val message: String
            get() = "There is no on chain data"
    }

    class ServerNotRespond(message: String?) : Web3Failure {
        override val message: String = "Server not respond: $message"
    }

    class RpcException(override val message: String) : Web3Failure

    class HttpException(override val message: String) : Web3Failure

    class Other(override val message: String) : Web3Failure
}
