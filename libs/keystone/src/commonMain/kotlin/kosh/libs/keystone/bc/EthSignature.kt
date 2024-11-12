package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborByteString
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.cbor.decodeCborElement
import kosh.libs.keystone.cbor.get
import kosh.libs.keystone.coder.Ur
import kotlinx.io.bytestring.ByteString

private const val Type = "eth-signature"
private const val Tag = 402uL

data class EthSignature(
    val requestId: ULong?,
    val signature: ByteString,
    val origin: String?,
) {

    companion object {
        operator fun invoke(
            ur: String,
        ): EthSignature {
            val (type, body) = Ur.parse(ur)
            require(type == Type)
            return invoke(body.decodeCborElement())
        }

        private operator fun invoke(
            cbor: CborElement,
        ): EthSignature = EthSignature(
            requestId = cbor["1"]?.cborUInt?.value,
            signature = cbor["2"]?.cborByteString?.value
                ?: error("missing signature"),
            origin = cbor["3"]?.cborTextString?.value,
        )
    }
}
