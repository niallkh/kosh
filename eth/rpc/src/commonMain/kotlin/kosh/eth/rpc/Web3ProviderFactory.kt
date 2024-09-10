package kosh.eth.rpc

import com.eygraber.uri.Uri
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.DefaultJson
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

public fun interface Web3ProviderFactory {
    public operator fun invoke(provider: Uri): Web3Provider
}

public fun web3ProviderFactory(
    httpClient: HttpClient,
    json: Json = Json,
): Web3ProviderFactory = Web3ProviderFactory { provider ->
    DefaultWeb3Provider(
        jsonRpcClient = DefaultJsonRpcClient(
            httpClient = httpClient.config {
                expectSuccess = true

                defaultRequest {
                    url(provider.toString())
                }

                install(HttpRequestRetry) {
                    maxRetries = 3
                    retryOnServerErrors()
                    retryOnException(retryOnTimeout = true)
                    exponentialDelay()
                }

                install(ContentNegotiation) {
                    json(
                        Json(DefaultJson) {
                            ignoreUnknownKeys = true
                        }
                    )
                }
            },
            json = json,
        )
    )
}
