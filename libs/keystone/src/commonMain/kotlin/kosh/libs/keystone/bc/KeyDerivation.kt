package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborArray
import kosh.libs.keystone.cbor.cborUInt

private const val Type = "key-derivation-call"
private const val Tag = 1301uL

data class KeyDerivation(
    val schemas: List<KeyDerivationSchema>,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(
        tag = Tag,
        value = CborElement.CborMap(
            1u.cborUInt to schemas.map { it.toCbor() }.cborArray
        )
    )
}
