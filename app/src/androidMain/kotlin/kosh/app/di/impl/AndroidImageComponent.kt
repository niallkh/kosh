package kosh.app.di.impl

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.fetcher.FetchResult
import com.seiko.imageloader.component.fetcher.Fetcher
import com.seiko.imageloader.component.mapper.Mapper
import com.seiko.imageloader.component.setupDefaultComponents
import com.seiko.imageloader.intercept.bitmapMemoryCacheConfig
import com.seiko.imageloader.intercept.imageMemoryCacheConfig
import com.seiko.imageloader.intercept.painterMemoryCacheConfig
import com.seiko.imageloader.model.ktorRequest
import com.seiko.imageloader.option.Options
import com.seiko.imageloader.option.androidContext
import io.ktor.http.CacheControl
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import kosh.app.di.AndroidComponent
import kosh.app.di.FilesComponent
import kosh.app.di.ImageComponent
import kosh.app.di.NetworkComponent
import kosh.domain.core.provider
import kosh.domain.models.Uri
import kosh.domain.models.toLibUri
import kosh.ui.resources.Res
import okio.Buffer
import okio.Path.Companion.toPath

class AndroidImageComponent(
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

private class IpfsMapper : Mapper<Url> {
    override fun map(data: Any, options: Options): Url? {
        if (data !is String) return null
        if (!isApplicable(data)) return null
        return Url(data)
    }

    private fun isApplicable(data: String): Boolean {
        return data.startsWith("ipfs:")
    }
}

private class UriMapper : Mapper<Url> {
    override fun map(data: Any, options: Options): Url? {
        if (data !is Uri) return null
        if (!isApplicable(data)) return null
        return Url(data.toString())
    }

    private fun isApplicable(uri: Uri): Boolean {
        return uri.toLibUri().scheme in arrayOf("http", "https", "ipfs")
    }
}

private class ComposeResourceFetcher private constructor(
    private val uri: Uri,
) : Fetcher {
    override suspend fun fetch(): FetchResult {
        val uri1 = uri.toLibUri()
        return FetchResult.OfSource(
            source = Buffer().apply { write(Res.readBytes(uri1.host + uri1.encodedPath)) },
        )
    }

    class Factory : Fetcher.Factory {
        override fun create(data: Any, options: Options): Fetcher? {
            if (data !is Uri) return null
            return ComposeResourceFetcher(data)
        }
    }
}
