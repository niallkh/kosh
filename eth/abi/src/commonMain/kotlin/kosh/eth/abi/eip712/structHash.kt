package kosh.eth.abi.eip712

import kosh.eth.abi.Value
import kosh.eth.abi.coder.encodeAddress
import kosh.eth.abi.coder.encodeBool
import kosh.eth.abi.coder.encodeFixedBytes
import kosh.eth.abi.coder.encodeInt
import kosh.eth.abi.coder.encodeUInt
import kosh.eth.abi.keccak256
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.readByteString
import kotlinx.io.write

public fun Eip712Types.structHash(entryType: Eip712Type.Tuple, value: Value): ByteString =
    Buffer().run {
        encodeTuple(this@structHash, entryType, value)
        readByteString()
    }

internal fun Sink.encode(eip712: Eip712Types, type: Eip712Type, value: Value): Unit = when (type) {
    is Eip712Type.UInt -> encodeUInt(type.bitSize, value)
    is Eip712Type.Int -> encodeInt(type.bitSize, value)
    Eip712Type.Bool -> encodeBool(value)
    Eip712Type.Address -> encodeAddress(value)
    is Eip712Type.FixedBytes -> encodeFixedBytes(type.byteSize, value)
    Eip712Type.DynamicString -> encodeDynamicString(value)
    Eip712Type.DynamicBytes -> encodeDynamicBytes(value)
    is Eip712Type.DynamicArray -> encodeDynamicArray(eip712, type, value)
    is Eip712Type.FixedArray -> encodeFixedArray(eip712, type, value)
    is Eip712Type.Tuple -> encodeTuple(eip712, type, value)
}

internal fun Sink.encodeDynamicString(value: Value) {
    require(value is Value.String)
    encodeDynamicBytes(Value.Bytes(value.value.encodeToByteString()))
}

internal fun Sink.encodeDynamicBytes(value: Value) {
    require(value is Value.Bytes)
    write(value.value.keccak256())
}

internal fun Sink.encodeDynamicArray(
    eip712: Eip712Types,
    type: Eip712Type.DynamicArray,
    values: Value,
) {
    require(values is Value.Array<*>)

    if (values.isNotEmpty()) {
        encodeFixedArray(eip712, values.size.toUInt(), type.type, values)
    }
}

private fun Sink.encodeFixedArray(eip712: Eip712Types, type: Eip712Type.FixedArray, values: Value) {
    encodeFixedArray(eip712, type.size, type.type, values)
}

private fun Sink.encodeFixedArray(
    eip712: Eip712Types,
    size: UInt,
    type: Eip712Type,
    values: Value,
) {
    require(values is Value.Array<*>)
    require(values.size.toUInt() == size)

    val hash = Buffer().run {
        values.forEach { value ->
            encode(eip712, type, value)
        }

        readByteString()
    }.keccak256()

    encodeFixedBytes(value = Value.Bytes(hash))
}

internal fun Sink.encodeTuple(eip712: Eip712Types, tuple: Eip712Type.Tuple, values: Value) {
    require(values is Value.Tuple)

    val hash = Buffer().run {
        write(eip712.typeHash(tuple))

        eip712.getValue(tuple).forEach { param ->
            encode(eip712, param.type, values.getValue(param.name))
        }

        readByteString()
    }.keccak256()

    encodeFixedBytes(value = Value.Bytes(hash))
}
