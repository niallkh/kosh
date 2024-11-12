package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.cbor.get

private const val Type = "crypto-coin-info"
private const val Tag = 305uL

data class CryptoCoinInfo(
    val type: ULong?,
    val network: ULong?,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(
        tag = Tag,
        value = CborElement.CborMap(
            type?.let { 1u.cborUInt to it.cborUInt },
            network?.let { 2u.cborUInt to it.cborUInt },
        )
    )

    companion object {
        operator fun invoke(
            cbor: CborElement.CborTagged,
        ): CryptoCoinInfo {
            require(cbor.tag == 305uL)
            return invoke(cbor.value)
        }

        private operator fun invoke(
            cbor: CborElement,
        ): CryptoCoinInfo {

            return CryptoCoinInfo(
                type = cbor["1"]?.cborUInt?.value,
                network = cbor["2"]?.cborUInt?.value,
            )
        }
    }
}
