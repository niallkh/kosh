package kosh.data.web3

import arrow.core.raise.either
import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.HttpHeaders
import kosh.domain.failure.Web3Failure
import kosh.domain.models.FunSelector
import kosh.domain.repositories.FunctionSignatureRepo
import kosh.domain.serializers.Either
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource

private const val REGISTRY = "https://www.4byte.directory/api/v1/signatures"

class DefaultFunctionSignatureRepo(
    private val client: HttpClient,
    private val json: Json = Json,
) : FunctionSignatureRepo {

    private val logger = Logger.withTag("FunctionSignatureRepo")

    override suspend fun get(
        funSelector: FunSelector,
    ): Either<Web3Failure, String?> = withContext(Dispatchers.IO) {
        either {
            val response = catchWeb3Failure(logger) {
                client.get(REGISTRY) {
                    url {
                        parameter("hex_signature", funSelector.toString())
                    }
                    headers {
                        header(HttpHeaders.Accept, "application/json")
                    }
                }
                    .let {
                        json.decodeFromBufferedSource<Response>(it.bodyAsChannel().readBuffer())
                    }
            }

            response.results.minByOrNull { it.createdAt }?.textSignature
        }
    }

    @Serializable
    private data class Response(
        val count: Int,
        val next: String? = null,
        val previous: String? = null,
        val results: List<Result> = emptyList(),
    )

    @Serializable
    private data class Result(
        val id: Long,
        @SerialName("created_at")
        val createdAt: String,
        @SerialName("text_signature")
        val textSignature: String,
        @SerialName("hex_signature")
        val hexSignature: String,
        @SerialName("bytes_signature")
        val bytesSignature: String,
    )
}

