package kosh.eth.rpc

import arrow.resilience.Schedule
import arrow.resilience.retry
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ResponseException
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kosh.eth.rpc.RpcError.ErrorCode.Companion.limitExceeded
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlin.math.absoluteValue
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

private val retrySchedule = Schedule.linear<Throwable>(1.seconds) and Schedule.recurs(3)

internal class DefaultJsonRpcClient(
    private val httpClient: HttpClient,
    override val json: Json,
) : JsonRpcClient {

    override suspend fun call(
        method: String,
        vararg params: JsonElement,
    ): JsonElement {

        return retrySchedule
            .doWhile { throwable, _ -> throwable is JsonRpcResponseException }
            .retry { callInternal(method, *params) }
    }

    private suspend fun callInternal(
        method: String,
        vararg params: JsonElement,
    ): JsonElement {
        val id = Random.nextInt().absoluteValue

        val rpcRequest = RpcRequest(
            jsonrpc = "2.0",
            id = id,
            method = method,
            params = params.toList()
        )

        val response = httpClient.post {
            setBody(rpcRequest)
            contentType(ContentType.Application.Json)
        }

        val rpcResponse = response.body<RpcResponse>()

        rpcResponse.error?.let {
            val error = when (it) {
                is JsonPrimitive -> RpcError(limitExceeded, it.content)
                else -> json.decodeFromJsonElement<RpcError>(it)
            }
            throw JsonRpcResponseException(
                response = response,
                method = method,
                error = error,
            )
        }

        if (rpcResponse.id != id) {
            throw ResponseException(response, "Invalid rpc response id")
        }

        return rpcResponse.result
    }
}
