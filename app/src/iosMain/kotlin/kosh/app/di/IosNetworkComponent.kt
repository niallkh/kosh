package kosh.app.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import kosh.domain.core.provider
import kosh.libs.ipfs.IpfsPlugin
import platform.Foundation.NSBundle

class IosNetworkComponent(
    filesComponent: FilesComponent,
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


            val groveKey = (NSBundle.mainBundle.objectForInfoDictionaryKey("GROVE_KEY") as? String
                ?: error("GROVE_KEY not provided"))
            install(grovePlugin(groveKey))
        }
    }
}
