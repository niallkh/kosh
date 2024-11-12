package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.coder.Ur

private const val Type = "qr-hardware-call"
private const val Tag = 1201uL

data class QRHardwareCall(
    val type: QRHardwareCallType,
    val params: KeyDerivation,
    val origin: String? = null,
    val version: QRHardwareCallVersion? = null,
) {

    fun toCbor(): CborElement = CborElement.CborTagged(Tag, toCborMap())

    fun toUr(): String = Ur.build(Type, toCborMap())

    private fun toCborMap() = CborElement.CborMap(
        1u.cborUInt to type.code.cborUInt,
        2u.cborUInt to params.toCbor(),
        origin?.let { 3u.cborUInt to origin.cborTextString },
        version?.let { 4u.cborUInt to version.code.cborUInt },
    )
}

enum class QRHardwareCallType(val code: UInt) {
    KeyDerivation(0u),
}

enum class QRHardwareCallVersion(val code: UInt) {
    V0(0u),
    V1(1u),
}

