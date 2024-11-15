package kosh.data.web3

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import kosh.domain.failure.Web3Failure
import kosh.eth.rpc.JsonRpcResponseException

inline fun <T> Raise<Web3Failure>.catchWeb3Failure(
    logger: Logger,
    block: Raise<Web3Failure>.() -> T,
): T = recover(block, { raise(it) }) { error ->
    raise(error.mapToWeb3Failure(logger))
}

inline fun <T> Either<Throwable, T>.mapToWeb3Failure(logger: Logger) =
    mapLeft { it.mapToWeb3Failure(logger) }

fun Throwable.mapToWeb3Failure(logger: Logger): Web3Failure {
    logger.w(this) { "Error happened during web3 call" }
    return when (this) {
        is JsonRpcResponseException -> Web3Failure.RpcException(
            message ?: "Server Json Rpc call failed"
        )

        is ResponseException -> Web3Failure.HttpException(
            message ?: "Server Request failed"
        )

        is HttpRequestTimeoutException,
        is ConnectTimeoutException,
        is SocketTimeoutException,
            -> Web3Failure.ServerNotRespond(message)

        else -> Web3Failure.Other(message ?: "Error happened during web3 call")
    }
}


