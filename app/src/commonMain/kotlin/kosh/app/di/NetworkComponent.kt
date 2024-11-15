package kosh.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

public interface NetworkComponent {

    public val httpClient: HttpClient
}

internal fun grovePlugin(groveKey: String) = createClientPlugin("GrovePlugin") {
    onRequest { request, _ ->
        if (request.url.host.endsWith("rpc.grove.city")) {
            request.headers {
                append(HttpHeaders.UserAgent, groveKey)
            }
        }
    }
}
