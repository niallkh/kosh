package kosh.eth.abi.coder

import kosh.eth.abi.Abi
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.readByteString
import kotlinx.io.writeString

public fun Abi.Item.Function.encodeSignature(): ByteString = encodeSignature(name, inputs)
public fun Abi.Item.Error.encodeSignature(): ByteString = encodeSignature(name, inputs)
public fun Abi.Item.Event.encodeSignature(): ByteString = encodeSignature(name, inputs)

internal fun encodeSignature(
    name: String,
    inputs: AbiType.Tuple,
): ByteString = Buffer().run {
    writeString(name)
    writeString(inputs.signature())

    readByteString()
}

internal fun AbiType.signature(): String = when (this) {
    is AbiType.UInt -> encodeUInt()
    is AbiType.Int -> encodeInt()
    AbiType.Bool -> encodeBool()
    AbiType.Address -> encodeAddress()
    is AbiType.FixedBytes -> encodeFixedBytes()
    AbiType.Function -> encodeFunction()
    AbiType.DynamicString -> encodeDynamicString()
    AbiType.DynamicBytes -> encodeDynamicBytes()
    is AbiType.DynamicArray -> encodeDynamicArray()
    is AbiType.FixedArray -> encodeFixedArray()
    is AbiType.Tuple -> encodeTuple()
}

private fun AbiType.UInt.encodeUInt(): String = "uint$bitSize"
private fun AbiType.Int.encodeInt(): String = "int$bitSize"
private fun encodeBool(): String = "bool"
private fun AbiType.FixedBytes.encodeFixedBytes(): String = "bytes$byteSize"
private fun encodeAddress(): String = "address"
private fun encodeFunction(): String = "function"
private fun encodeDynamicString(): String = "string"
private fun encodeDynamicBytes(): String = "bytes"
private fun AbiType.DynamicArray.encodeDynamicArray(): String = "${type.signature()}[]"
private fun AbiType.FixedArray.encodeFixedArray(): String = "${type.signature()}[$size]"
private fun AbiType.Tuple.encodeTuple(): String =
    joinToString(prefix = "(", separator = ",", postfix = ")") { param ->
        param.type.signature()
    }
