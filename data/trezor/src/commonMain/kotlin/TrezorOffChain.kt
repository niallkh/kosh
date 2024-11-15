package kosh.data.trezor

import io.ktor.client.HttpClient
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.appendPathSegments
import io.ktor.http.isSuccess
import io.ktor.utils.io.readBuffer
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.slip44
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.io.readByteString

class TrezorOffChain(
    client: HttpClient,
) {
    private val client = client.config {
        defaultRequest {
            url("https://data.trezor.io/firmware/eth-definitions/")
        }
    }

    suspend fun getNetworkDefinition(chainId: ChainId): ByteString? {
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

    suspend fun getTokenDefinition(chainId: ChainId, address: Address): ByteString? {
        val response = client.get {
            url {
                appendPathSegments(
                    "chain-id", chainId.toString(), "token-${address.bytes().toHexString()}.dat"
                )
            }
        }

        return if (response.status.isSuccess()) response.bodyAsChannel().readBuffer()
            .readByteString()
        else null
    }

    suspend fun getNetworkDefinition(derivationPath: DerivationPath): ByteString? {
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
