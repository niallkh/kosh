package kosh.libs.ipfs

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kotlinx.coroutines.runBlocking
import okio.Buffer
import okio.BufferedSource
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import kotlin.test.Test

class IpfsTest {

    @Test
    fun test() {
        val httpClient = HttpClient(CIO) {

            install(IpfsPlugin)

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

            expectSuccess = true
        }

        runBlocking {
//            val bytes = ipfs.get("https://ipfs.io/ipfs/QmS2LyUCyYDwjf4RxTrbsN8bgn8zdvkJphqWUGBwFHtBT4/1.json")
//            val bufferedSource = httpClient.get("ipfs://bafybeifnlsrgj2o7n2jxgvzreliusycsmdttevnrmh2vqvre6qdqn3riza/1010.png").bodyAsChannel().readBuffer()
//            val bytes = ipfs.get("ipfs://bafybeig7wnfegihtcophpylbmk6f2ailpdlfb45q3uixajboy6jtozc5bm/2015/09/15/hosting-a-website-on-ipfs/index.html")
//            val bufferedSource = httpClient.get("ipfs://QmRjgEp89ovHdr1M8rkjoC6iEgbNna5kBDjbfubm4zeVDd/1")
//                    .bodyAsChannel().readBuffer()

//            val bufferedSource =
//                httpClient.get("ipfs://bafybeidam7rjf32dspxqnnleykdt52ctg2iu6anjlvu36djinjquwnbmz4/3")
//                    .bodyAsChannel().readBuffer()
            val bufferedSource =
                httpClient.get("ipfs://bafybeifcsnb5ruh5zsh2j56estrv6wfdte7bxqgyzviloxcemwwhe6rwtm")
                    .bodyAsChannel().readBuffer()
//            val bufferedSource = httpClient.get("ipfs://QmYwAPJzv5CZsnA625s3Xf2nemtYgPpHdWEz79ojWnPbdG/readme").bodyAsChannel().readBuffer()
            FileSystem.SYSTEM.sink("src/jvmTest/kotlin/ipfs.json".toPath()).buffer().use {
                it.write(bufferedSource.readByteString())
            }
        }
    }
}

internal suspend fun ByteReadChannel.readBuffer(): BufferedSource {
    val buffer = Buffer()
    val buff = ByteArray(8192)
    while (!isClosedForRead) {
        val read = readAvailable(buff)
        if (read == -1) continue
        buffer.write(buff, 0, read)
    }
    return buffer
}
