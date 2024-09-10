package kosh.eth.rpc

import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlin.jvm.JvmInline

public interface JsonRpcClient {

    public val json: Json

    public suspend fun call(
        method: String,
        vararg params: JsonElement,
    ): JsonElement
}

public class JsonRpcResponseException(
    response: HttpResponse,
    method: String,
    public val error: RpcError,
) : ResponseException(
    response = response,
    cachedResponseText = "$method: #${error.code.code}: ${error.message}"
)

@Serializable
internal data class RpcRequest(
    val jsonrpc: String,
    val method: String,
    val params: List<JsonElement>,
    val id: Int,
)

@Serializable
internal data class RpcResponse(
    val jsonrpc: String = "",
    val id: Int = 0,
    val result: JsonElement = JsonNull,
    val error: JsonElement? = null,
)

@Serializable
public data class RpcError(
    val code: ErrorCode,
    val message: String? = null,
    val data: JsonElement? = null,
) {

    @JvmInline
    @Serializable
    public value class ErrorCode(public val code: Long) {

        public companion object {
            public val invalidInput: ErrorCode = ErrorCode(-32000)
            public val resourceNotFound: ErrorCode = ErrorCode(-32001)
            public val resourceUnavailable: ErrorCode = ErrorCode(-32002)
            public val transactionRejected: ErrorCode = ErrorCode(-32003)
            public val methodNotSupported: ErrorCode = ErrorCode(-32004)
            public val limitExceeded: ErrorCode = ErrorCode(-32005)
            public val parse: ErrorCode = ErrorCode(-32700)
            public val invalidRequest: ErrorCode = ErrorCode(-32600)
            public val methodNotFound: ErrorCode = ErrorCode(-32601)
            public val invalidParams: ErrorCode = ErrorCode(-32602)
            public val internal: ErrorCode = ErrorCode(-32603)
            public val userRejectedRequest: ErrorCode = ErrorCode(4001)
            public val unauthorized: ErrorCode = ErrorCode(4100)
            public val unsupportedMethod: ErrorCode = ErrorCode(4200)
            public val disconnected: ErrorCode = ErrorCode(4900)
            public val chainDisconnected: ErrorCode = ErrorCode(4901)
        }
    }
}

