package kosh.libs.keystone.bc

import kosh.libs.keystone.bc.CryptoOutput.CryptoHDKey
import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborArray
import kosh.libs.keystone.cbor.cborTagged
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.cbor.decodeCborElement
import kosh.libs.keystone.cbor.get
import kosh.libs.keystone.coder.Ur

private const val Type = "crypto-multi-accounts"
private const val Tag = 1103uL

data class CryptoMultiAccounts(
    val masterFingerprint: ULong,
    val keys: List<CryptoHDKey>,
    val device: String?,
    val deviceId: String?,
    val version: String?,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(
        tag = Tag,
        value = CborElement.CborMap(
            1u.cborUInt to masterFingerprint.cborUInt,
            2u.cborUInt to keys.map(CryptoHDKey::toCbor).cborArray,
            device?.let { 3u.cborUInt to it.cborTextString },
            deviceId?.let { 4u.cborUInt to it.cborTextString },
            version?.let { 5u.cborUInt to it.cborTextString },
        )
    )

    companion object {
        operator fun invoke(
            ur: String,
        ): CryptoMultiAccounts {
            val (type, body) = Ur.parse(ur)
            require(type == Type)
            return invoke(body.decodeCborElement())
        }

        private operator fun invoke(
            cbor: CborElement,
        ): CryptoMultiAccounts = CryptoMultiAccounts(
            masterFingerprint = cbor["1"]?.cborUInt?.value
                ?: error("missing master fingerprint"),
            keys = cbor["2"]?.cborArray?.map { CryptoHDKey(it.cborTagged) }
                ?: error("missing keys"),
            device = cbor["3"]?.cborTextString?.value,
            deviceId = cbor["4"]?.cborTextString?.value,
            version = cbor["5"]?.cborTextString?.value,
        )
    }
}
