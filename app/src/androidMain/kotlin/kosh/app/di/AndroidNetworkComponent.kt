package kosh.app.di

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.compression.ContentEncoding
import kosh.app.BuildConfig
import kosh.domain.core.provider
import kosh.libs.ipfs.IpfsPlugin
import okhttp3.Cache
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import okio.Path.Companion.toPath

class AndroidNetworkComponent(
    filesComponent: FilesComponent,
) : NetworkComponent,
    FilesComponent by filesComponent {

    override val httpClient: HttpClient by provider {
        HttpClient(OkHttp) {
            engine {
                config {

                    val logger = Logger.withTag("[K]OkHttp")

                    addInterceptor(
                        HttpLoggingInterceptor { message ->
                            logger.v { message }
                        }.setLevel(Level.BASIC)
                    )

                    cache(
                        Cache(
                            directory = filesComponent.httpPath().toString().toPath().toFile(),
                            maxSize = 512L * 1024L * 1024L
                        )
                    )
                }
            }

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

            install(grovePlugin(BuildConfig.GROVE_KEY))
        }
    }
}
