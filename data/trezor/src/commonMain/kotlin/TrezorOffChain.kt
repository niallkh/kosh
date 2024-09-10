package kosh.data.trezor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.readAvailable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.slip44
import kotlinx.serialization.json.Json
import okio.Buffer
import okio.BufferedSource

class TrezorOffChain(
    client: HttpClient,
    private val json: Json = Json,
) {
    private val client = client.config {
        defaultRequest {
            url("https://data.trezor.io/firmware/eth-definitions/")
        }
    }

    suspend fun getNetworkDefinition(chainId: ChainId): okio.ByteString? {
        val response = client.get {
            url {
                appendPathSegments(
                    "chain-id", chainId.toString(), "network.dat"
                )
            }
        }
        return if (response.status.isSuccess()) response.bodyAsChannel().readBuffer()
            .readByteString()
        else null
    }

    suspend fun getTokenDefinition(chainId: ChainId, address: Address): okio.ByteString? {
        val response = client.get {
            url {
                appendPathSegments(
                    "chain-id", chainId.toString(), "token-${address.bytes().hex()}.dat"
                )
            }
        }

        return if (response.status.isSuccess()) response.bodyAsChannel().readBuffer()
            .readByteString()
        else null
    }

    suspend fun getNetworkDefinition(derivationPath: DerivationPath): okio.ByteString? {
        val response = client.get {
            url {
                appendPathSegments("slip44", derivationPath.slip44.toString(), "network.dat")
            }
        }
        return if (response.status.isSuccess()) response.bodyAsChannel().readBuffer()
            .readByteString()
        else null
    }
}

suspend fun ByteReadChannel.readBuffer(): BufferedSource {
    val buffer = Buffer()
    val buff = ByteArray(8192)
    while (!isClosedForRead) {
        val read = readAvailable(buff)
        if (read == -1) continue
        buffer.write(buff, 0, read)
    }
    return buffer
}
