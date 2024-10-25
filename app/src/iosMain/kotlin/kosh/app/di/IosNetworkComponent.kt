package kosh.app.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import kosh.domain.core.provider
import kosh.libs.ipfs.IpfsPlugin

internal class IosNetworkComponent(
    filesComponent: FilesComponent,
    appComponent: AppComponent,
) : NetworkComponent,
    FilesComponent by filesComponent {

    override val httpClient: HttpClient by provider {
        HttpClient(Darwin) {
            engine {
                configureRequest {
                    setAllowsCellularAccess(true)
                }
            }

//             TODO install http cache
//            install(HttpCache) {
//                privateStorage()
//            }

            install(HttpTimeout) {
                requestTimeoutMillis = 10_000
                connectTimeoutMillis = 10_000
            }

            install(ContentEncoding) {
                gzip()
            }

            install(HttpRequestRetry) {
                maxRetries = 3
                retryOnServerErrors()
                exponentialDelay()
            }

            install(IpfsPlugin)

            install(grovePlugin(appComponent.groveKey))
        }
    }
}
