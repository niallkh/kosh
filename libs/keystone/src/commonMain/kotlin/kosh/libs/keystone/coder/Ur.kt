package kosh.libs.keystone.coder

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.encode
import kotlinx.io.bytestring.ByteString

object Ur {

    fun parse(
        ur: String,
    ): Pair<String, ByteString> {
        require(ur.startsWith("ur:", ignoreCase = true)) { "Invalid ur: $ur" }
        val components = ur.substring(3).split("/")
        require(components.size == 2) { "Invalid ur size ${components.size}" }
        val decoded = Bytewords.decodeMinimal(components.last())
        val data = decoded.substring(0, decoded.size - 4)
        val crc = decoded.substring(decoded.size - 4, decoded.size)
        check(crc.toInt() == CRC32.calculate(data)) { "Wrong checksum" }
        return components.first().lowercase() to data
    }

    fun build(
        type: String,
        cbor: CborElement,
    ): String {
        val encoded = cbor.encode()
        val data = Bytewords.encodeMinimal(encoded)
        val crc = Bytewords.encodeMinimal(CRC32.calculate(encoded).toByteString())
        return "ur:${type}/$data$crc"
    }
}

