package kosh.data.web3

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import kosh.domain.failure.Web3Failure
import kosh.eth.rpc.JsonRpcResponseException
import kosh.eth.rpc.Web3Provider

inline fun <T> Web3Provider.catch(
    logger: Logger,
    block: Web3Provider.() -> T,
): Either<Web3Failure, T> = Either.catch { block() }.mapLeft { error ->
    logger.w(error) { "Error happened during web3 call" }
    when (error) {
        is JsonRpcResponseException ->
            Web3Failure.RpcException(error.error.message ?: error.error.data.toString())

        is ResponseException ->
            Web3Failure.HttpException(error.message ?: "Http response exception")

        is HttpRequestTimeoutException,
        is ConnectTimeoutException,
        is SocketTimeoutException,
        -> Web3Failure.ServerNotRespond(error.message)

        else -> Web3Failure.Other(error.message ?: "Error happened during web3 call")
    }
}

inline fun <T> HttpClient.catch(
    logger: Logger,
    block: HttpClient.() -> T,
): Either<Web3Failure, T> = Either.catch { block() }.mapLeft { error ->
    logger.w(error) { "Error happened during http call" }
    when (error) {
        is ResponseException ->
            Web3Failure.HttpException(error.message ?: "Http response exception")

        is HttpRequestTimeoutException,
        is ConnectTimeoutException,
        is SocketTimeoutException,
        -> Web3Failure.ServerNotRespond(error.message)

        else -> Web3Failure.Other(error.message ?: "Error happened during web3 call")
    }
}

inline fun <T> Raise<Web3Failure>.catchWeb3Failure(
    logger: Logger,
    block: Raise<Web3Failure>.() -> T,
): T = recover(
    block,
    { raise(it) }
) { error ->
    logger.w(error) { "Error happened during web3 call" }
    when (error) {
        is ResponseException ->
            Web3Failure.HttpException(error.message ?: "Http response exception")

        is HttpRequestTimeoutException,
        is ConnectTimeoutException,
        is SocketTimeoutException,
        -> Web3Failure.ServerNotRespond(error.message)

        else -> Web3Failure.Other(error.message ?: "Error happened during web3 call")
    }
        .let { raise(it) }
}
