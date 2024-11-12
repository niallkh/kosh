package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt

private const val TYPE = "key-derivation-schema"
private const val TAG = 1302uL

data class KeyDerivationSchema(
    val keypath: CryptoKeypath,
    val curve: Curve = Curve.Secp256k1,
    val algo: DerivationAlgorithm = DerivationAlgorithm.Slip10,
    val chainType: String? = null,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(
        tag = TAG,
        value = CborElement.CborMap(
            1u.cborUInt to keypath.toCbor(),
            2u.cborUInt to curve.code.cborUInt,
            3u.cborUInt to algo.code.cborUInt,
            chainType?.let { 4u.cborUInt to it.cborTextString },
        )
    )
}

enum class Curve(val code: UInt) {
    Secp256k1(0u),
    Ed25519(1u),
}

enum class DerivationAlgorithm(val code: UInt) {
    Slip10(0u),
    Bip32ed25519(1u),
}
