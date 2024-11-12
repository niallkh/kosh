package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborArray
import kosh.libs.keystone.cbor.cborBoolean
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.cbor.get

private const val Tag = 304uL
private const val Type = "crypto-keypath"
private const val hardenedIndex: UInt = 0x80000000u

data class CryptoKeypath(
    val components: List<UInt>,
    val sourceFingerprint: ULong? = null,
    val depth: UByte? = null,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(
        tag = Tag,
        value = CborElement.CborMap(
            1u.cborUInt to components.flatMap {
                if (it.isHardened) listOf(
                    it.unhardened.cborUInt,
                    true.cborBoolean
                ) else listOf(
                    it.cborUInt,
                    false.cborBoolean
                )
            }.cborArray,
            sourceFingerprint?.let { 2u.cborUInt to it.cborUInt },
            depth?.let { 3u.cborUInt to it.toUInt().cborUInt },
        )
    )

    companion object {
        operator fun invoke(cbor: CborElement.CborTagged): CryptoKeypath {
            require(cbor.tag == Tag)
            return invoke(cbor.value)
        }

        operator fun invoke(
            ur: String,
            cbor: CborElement,
        ): CryptoKeypath {
            require(ur == Type)
            return invoke(cbor)
        }

        private operator fun invoke(
            cbor: CborElement,
        ) = CryptoKeypath(
            components = cbor["1"]?.cborArray?.chunked(2)?.map {
                val component = it[0].cborUInt.value.toUInt()
                val hardened = it[1].cborBoolean.value
                if (hardened) component.hardened else component
            } ?: listOf(),
            sourceFingerprint = cbor["2"]?.cborUInt?.value,
            depth = cbor["3"]?.cborUInt?.value?.toUByte()
        )
    }
}

private val UInt.hardened: UInt
    inline get() = hardenedIndex + this

private val UInt.unhardened: UInt
    inline get() = this - hardenedIndex

private val UInt.isHardened: Boolean
    inline get() = this >= hardenedIndex

