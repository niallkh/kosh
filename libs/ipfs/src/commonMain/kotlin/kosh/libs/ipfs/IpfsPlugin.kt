package kosh.libs.ipfs

import com.eygraber.uri.Uri
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.Send
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponsePipeline
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import io.ktor.utils.io.writer
import kosh.libs.ipfs.car.CarSource
import kosh.libs.ipfs.cid.Cid
import kosh.libs.ipfs.cid.decodeCid
import kosh.libs.ipfs.cid.decodeCidOrNull
import kosh.libs.ipfs.cid.encode
import okio.Buffer
import okio.buffer

internal val ipfsRawContentType = ContentType("application", "vnd.ipld.raw")
internal val ipfsCarContentType = ContentType("application", "vnd.ipld.car")
internal val ipfsIpnsContentType = ContentType("application", "vnd.ipfs.ipns-record")

public val IpfsPlugin: ClientPlugin<Unit> = createClientPlugin("IpfsPlugin") {
    on(Send) { request ->
        val ipfsUri = request.url.toString().toIpfsUri()
        if (ipfsUri != null) {
            val ipfsRequest = HttpRequestBuilder().takeFrom(request).apply {
                val path = ipfsUri.pathSegments.filter { it.isNotEmpty() }
                val cid = ipfsUri.host!!.decodeCid()
                url {
                    takeFrom("https://trustless-gateway.link")
                    appendPathSegments("ipfs", cid.encode(), *path.toTypedArray())
                    parameter("format", "car")
                    parameter("dag-scope", "entity")
                    parameter("car-order", "dfs")
                    parameter("car-dups", "y")
                    parameter("car-version", "1")
                }
            }
            proceed(ipfsRequest)
        } else {
            proceed(request)
        }
    }

    client.responsePipeline.intercept(HttpResponsePipeline.Receive) { container ->
        if (
            context.response.contentType()?.contentType == ipfsCarContentType.contentType
            && context.response.contentType()?.contentSubtype == ipfsCarContentType.contentSubtype
        ) {
            val uri = context.request.url.toString().let(Uri::parse)
            val path: List<String>
            val root: Cid
            if (uri.pathSegments.firstOrNull() == "ipfs") {
                root = uri.pathSegments[1].decodeCidOrNull() ?: error("Invalid ipfs url")
                path = uri.pathSegments.drop(2)
            } else {
                root = uri.host?.split(".")?.first()?.decodeCidOrNull() ?: error("Invalid ipfs url")
                path = uri.pathSegments.toList()
            }

            val writer = writer {
                val carChannel = container.response as ByteReadChannel
                val carBuffer = Buffer()
                val decodedBuffer = CarSource(path, root, carBuffer).buffer()

                val buffer = ByteArray(8192)
                while (!carChannel.isClosedForRead) {
                    val read = carChannel.readAvailable(buffer)
                    if (read == -1) continue
                    carBuffer.write(buffer, 0, read)
                }

                while (!decodedBuffer.exhausted()) {
                    val read = decodedBuffer.read(buffer)
                    if (read == -1) continue
                    channel.writeFully(buffer, 0, read)
                }
            }

            proceedWith(container.copy(response = writer.channel))
        } else {
            proceedWith(container)
        }
    }
}

public fun String.toIpfsUri(): Uri? = try {
    val uri = Uri.parse(this)
    when {
        uri.scheme == "ipfs" -> uri

        uri.pathSegments.firstOrNull() == "ipfs" -> {
            val cid = uri.pathSegments.getOrNull(1)?.decodeCidOrNull()
            if (cid != null) {
                Uri.Builder().run {
                    scheme("ipfs")
                    appendPath(cid.encode())
                    uri.pathSegments.drop(2).forEach {
                        appendPath(it)
                    }

                    build()
                }
            } else {
                null
            }
        }

        else -> null
    }
} catch (e: IllegalArgumentException) {
    null
}
