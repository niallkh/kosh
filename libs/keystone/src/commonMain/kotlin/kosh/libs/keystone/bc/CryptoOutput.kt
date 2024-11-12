package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborBoolean
import kosh.libs.keystone.cbor.cborByteString
import kosh.libs.keystone.cbor.cborTagged
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.cbor.get
import kotlinx.io.bytestring.ByteString

private const val Type = "crypto-hdkey"
private const val Tag = 303uL

sealed class CryptoOutput {

    data class CryptoHDKey(
        val master: Boolean?,
        val privateKey: Boolean?,
        val key: ByteString?,
        val chainCode: ByteString?,
        val useInfo: CryptoCoinInfo?,
        val origin: CryptoKeypath?,
        val children: CryptoKeypath?,
        val parentFingerprint: ULong?,
        val name: String?,
        val note: String?,
    ) : CryptoOutput() {

        fun toCbor(): CborElement = CborElement.CborTagged(
            tag = Tag,
            value = CborElement.CborMap(
                master?.let { 1u.cborUInt to it.cborBoolean },
                privateKey?.let { 2u.cborUInt to it.cborBoolean },
                key?.let { 3u.cborUInt to it.cborByteString },
                chainCode?.let { 4u.cborUInt to it.cborByteString },
                useInfo?.let { 5u.cborUInt to it.toCbor() },
                origin?.let { 6u.cborUInt to it.toCbor() },
                children?.let { 7u.cborUInt to it.toCbor() },
                parentFingerprint?.let { 8u.cborUInt to it.cborUInt },
                name?.let { 9u.cborUInt to it.cborTextString },
                note?.let { 10u.cborUInt to it.cborTextString },
            )
        )

        companion object {

            operator fun invoke(
                cbor: CborElement.CborTagged,
            ): CryptoHDKey {
                require(cbor.tag == Tag)
                return invoke(cbor.value)
            }

            operator fun invoke(
                ur: String,
                cbor: CborElement,
            ): CryptoHDKey {
                require(ur == Type)
                return invoke(cbor)
            }

            private operator fun invoke(
                cbor: CborElement,
            ): CryptoHDKey = CryptoHDKey(
                master = cbor["1"]?.cborBoolean?.value,
                privateKey = cbor["2"]?.cborBoolean?.value,
                key = cbor["3"]?.cborByteString?.value,
                chainCode = cbor["4"]?.cborByteString?.value,
                useInfo = cbor["5"]?.cborTagged?.let(CryptoCoinInfo::invoke),
                origin = cbor["6"]?.cborTagged?.let(CryptoKeypath::invoke),
                children = cbor["7"]?.cborTagged?.let(CryptoKeypath::invoke),
                parentFingerprint = cbor["8"]?.cborUInt?.value,
                name = cbor["9"]?.cborTextString?.value,
                note = cbor["10"]?.cborTextString?.value,
            )
        }
    }
}
