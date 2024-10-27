package kosh.app.di

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import com.seiko.imageloader.model.ktorRequest
import com.seiko.imageloader.option.androidContext
import io.ktor.http.CacheControl
import io.ktor.http.HttpHeaders
import kosh.domain.core.provider
import okio.Path.Companion.toPath

internal class AndroidImageComponent(
    networkComponent: NetworkComponent,
    androidComponent: AndroidComponent,
    filesComponent: FilesComponent,
) : ImageComponent,
    NetworkComponent by networkComponent,
    AndroidComponent by androidComponent,
    FilesComponent by filesComponent {

    override val imageLoader: ImageLoader by provider {
        ImageLoader {
            options {
                androidContext(context)
                ktorRequest {
                    headers {
                        put(HttpHeaders.CacheControl, CacheControl.NoStore(null).toString())
                    }
                }
            }
            components {
                setupDefaultComponents(
                    httpClient = { httpClient },
                    context = context,
                )

                add(UriMapper())
                add(IpfsMapper())
                add(ComposeResourceFetcher.Factory())
            }

            interceptor {
                bitmapMemoryCacheConfig {
                    maxSize(64 * 1024 * 1024)
                }
                imageMemoryCacheConfig {
                    maxSize(50)
                }
                painterMemoryCacheConfig {
                    maxSize(50)
                }
                diskCacheConfig {
                    directory(filesComponent.imagePath().toString().toPath())
                    maxSizeBytes(512L * 1024 * 1024) // 512MB
                }
            }
        }
    }
}
