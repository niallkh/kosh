package kosh.libs.trezor.cmds

import com.satoshilabs.trezor.lib.protobuf.EthereumMessageSignature
import com.satoshilabs.trezor.lib.protobuf.EthereumSignMessage
import kosh.libs.trezor.TrezorManager
import kotlinx.io.bytestring.ByteString

suspend fun TrezorManager.Connection.signPersonalMessage(
    derivationPath: List<UInt>,
    message: ByteString,
    networkDefinition: ByteString?,
): Pair<ByteString, String> {
    val response = exchange(
        EthereumSignMessage(
            address_n = derivationPath.map { it.toInt() },
            message = message.toOkio(),
            chunkify = true,
            encoded_network = networkDefinition?.toOkio(),
        )
    )

    val signature = response.expect<EthereumMessageSignature>()

    return signature.signature.toIo() to signature.address
}
