package kosh.libs.trezor.cmds

import com.satoshilabs.trezor.lib.protobuf.EthereumAddress
import com.satoshilabs.trezor.lib.protobuf.EthereumGetAddress
import kosh.libs.trezor.TrezorManager
import kotlinx.io.bytestring.ByteString

suspend fun TrezorManager.Connection.ethereumAddress(
    derivationPath: List<UInt>,
    showDisplay: Boolean = false,
    networkDefinition: ByteString?,
): String {
    val ethereumAddress = exchange(
        EthereumGetAddress(
            address_n = derivationPath.map { it.toInt() },
            show_display = showDisplay,
            chunkify = showDisplay,
            encoded_network = networkDefinition?.toOkio()
        )
    ).expect<EthereumAddress>()

    return ethereumAddress.address ?: error("Expected ethereum address")
}
