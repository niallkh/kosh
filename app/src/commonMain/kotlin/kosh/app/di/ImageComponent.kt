package kosh.app.di

import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.fetcher.FetchResult
import com.seiko.imageloader.component.fetcher.Fetcher
import com.seiko.imageloader.component.mapper.Mapper
import com.seiko.imageloader.option.Options
import io.ktor.http.Url
import kosh.domain.models.Uri
import kosh.domain.models.toLibUri
import kosh.ui.resources.Res
import okio.Buffer

interface ImageComponent {

    val imageLoader: ImageLoader
}

class IpfsMapper : Mapper<Url> {
    override fun map(data: Any, options: Options): Url? {
        if (data !is String) return null
        if (!isApplicable(data)) return null
        return Url(data)
    }

    private fun isApplicable(data: String): Boolean {
        return data.startsWith("ipfs:")
    }
}

class UriMapper : Mapper<Url> {
    override fun map(data: Any, options: Options): Url? {
        if (data !is Uri) return null
        if (!isApplicable(data)) return null
        return Url(data.toString())
    }

    private fun isApplicable(uri: Uri): Boolean {
        return uri.toLibUri().scheme in arrayOf("http", "https", "ipfs")
    }
}

class ComposeResourceFetcher private constructor(
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
