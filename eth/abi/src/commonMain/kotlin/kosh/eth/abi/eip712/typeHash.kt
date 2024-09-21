package kosh.eth.abi.eip712

import kosh.eth.abi.keccak256
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString

public fun Eip712Types.typeHash(entryType: Eip712Type.Tuple): ByteString {
    val refs =
        getValue(entryType).flatMap { referenceTypes(it.type) }.distinct().sortedBy { it.name }

    val typeString = (listOf(entryType) + refs).joinToString("") { type ->
        type.name + getValue(type).joinToString(",", "(", ")") {
            "${it.type.encode()} ${it.name}"
        }
    }

    return typeString.encodeToByteString().keccak256()
}

private fun Eip712Type.encode(): String = when (this) {
    is Eip712Type.UInt -> encodeUInt()
    is Eip712Type.Int -> encodeInt()
    Eip712Type.Bool -> encodeBool()
    Eip712Type.Address -> encodeAddress()
    is Eip712Type.FixedBytes -> encodeFixedBytes()
    Eip712Type.DynamicString -> encodeDynamicString()
    Eip712Type.DynamicBytes -> encodeDynamicBytes()
    is Eip712Type.DynamicArray -> encodeDynamicArray()
    is Eip712Type.FixedArray -> encodeFixedArray()
    is Eip712Type.Tuple -> encodeTuple()
}

private fun Eip712Type.UInt.encodeUInt(): String = "uint$bitSize"
private fun Eip712Type.Int.encodeInt(): String = "int$bitSize"
private fun encodeBool(): String = "bool"
private fun Eip712Type.FixedBytes.encodeFixedBytes(): String = "bytes$byteSize"
private fun encodeAddress(): String = "address"
private fun encodeDynamicString(): String = "string"
private fun encodeDynamicBytes(): String = "bytes"
private fun Eip712Type.DynamicArray.encodeDynamicArray(): String = "${type.encode()}[]"
private fun Eip712Type.FixedArray.encodeFixedArray(): String = "${type.encode()}[$size]"
private fun Eip712Type.Tuple.encodeTuple(): String = name

private fun Eip712Types.referenceTypes(type: Eip712Type): List<Eip712Type.Tuple> = when (type) {
    is Eip712Type.UInt,
    is Eip712Type.Int,
    Eip712Type.Bool,
    Eip712Type.Address,
    is Eip712Type.FixedBytes,
    Eip712Type.DynamicString,
    Eip712Type.DynamicBytes,
    -> listOf()

    is Eip712Type.DynamicArray -> referenceTypes(type.type)
    is Eip712Type.FixedArray -> referenceTypes(type.type)
    is Eip712Type.Tuple -> listOf(Eip712Type.Tuple(type.name)) + getValue(Eip712Type.Tuple(type.name)).flatMap {
        referenceTypes(it.type)
    }
}

