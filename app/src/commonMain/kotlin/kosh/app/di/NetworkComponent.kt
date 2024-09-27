package kosh.app.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders

interface NetworkComponent {

    val httpClient: HttpClient
}

fun grovePlugin(groveKey: String) = createClientPlugin("DRpcPlugin") {
    onRequest { request, _ ->
        if (request.url.host.endsWith("rpc.grove.city")) {
            request.headers {
                append(HttpHeaders.UserAgent, groveKey)
            }
        }
    }
}
