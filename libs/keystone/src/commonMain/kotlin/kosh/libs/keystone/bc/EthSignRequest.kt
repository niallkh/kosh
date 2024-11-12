package kosh.libs.keystone.bc

import kosh.libs.keystone.cbor.CborElement
import kosh.libs.keystone.cbor.cborByteString
import kosh.libs.keystone.cbor.cborTextString
import kosh.libs.keystone.cbor.cborUInt
import kosh.libs.keystone.coder.Ur
import kotlinx.io.bytestring.ByteString

private const val Type = "eth-sign-request"
private const val Tag = 401uL

data class EthSignRequest(
    val requestId: ULong? = null,
    val signData: ByteString,
    val dataType: DataType,
    val chainId: ULong? = null,
    val derivationPath: CryptoKeypath,
    val address: ByteString? = null,
    val origin: String? = null,
) {

    fun toUr(): String = Ur.build(Type, toCborMap())

    fun toCbor() = CborElement.CborTagged(
        tag = Tag,
        value = toCborMap()
    )

    private fun toCborMap() = CborElement.CborMap(
        requestId?.let { 1u.cborUInt to it.cborUInt },
        2u.cborUInt to signData.cborByteString,
        3u.cborUInt to dataType.code.cborUInt,
        chainId?.let { 4u.cborUInt to it.cborUInt },
        5u.cborUInt to derivationPath.toCbor(),
        address?.let { 6u.cborUInt to it.cborByteString },
        origin?.let { 7u.cborUInt to it.cborTextString },
    )
}

enum class DataType(val code: UInt) {
    Transaction(1u),
    TypedData(2u),
    PersonalMessage(3u),
    TypedTransaction(4u),
}
