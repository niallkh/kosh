package kosh.eth.abi.coder

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.keccak256
import kosh.eth.abi.padding
import kosh.eth.abi.plus
import kotlinx.io.Buffer
import kotlinx.io.Sink
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.readByteString

public fun Abi.Item.Function.encode(
    vararg values: Pair<String, Value>,
): ByteString = encodeSignature().keccak256().substring(0, 4) + encodeData(inputs, *values)

public fun Abi.Item.Event.encodeTopics(vararg values: Value?): List<ByteString?> {
    val parameters = inputs.filter { it.indexed }
    val size = if (anonymous) inputs.size else inputs.size + 1
    require(size == values.size)

    return buildList(size) {
        if (anonymous.not()) {
            add(encodeSignature().keccak256())
        }

        for (index in parameters.indices) {
            val parameter = parameters[index]
            val value = values[index]

            if (value == null) {
                add(null)
                continue
            }

            if (parameter.type is AbiType.FixedArray ||
                parameter.type is AbiType.Tuple ||
                parameter.type.isDynamic
            ) {
                TODO("Dynamic types in even topic not implemented")
            } else {
                add(encodeData(parameter.type, value))
            }
        }
    }
}

internal fun Abi.Item.Constructor.encodeDeploy(
    byteCode: ByteString,
    vararg values: Pair<String, Value>,
): ByteString = byteCode + encodeData(inputs, *values)

internal fun encodeData(
    tuple: AbiType.Tuple,
    vararg values: Pair<String, Value>,
): ByteString = encodeData(tuple = tuple, value = Value.tuple(*values))

internal fun encodeData(
    tuple: AbiType,
    value: Value,
): ByteString = Buffer().run {
    encode(tuple, value)
    readByteString()
}

internal fun Sink.encode(type: AbiType, value: Value): Unit = when (type) {
    is AbiType.UInt -> encodeUInt(type.bitSize, value)
    is AbiType.Int -> encodeInt(type.bitSize, value)
    AbiType.Bool -> encodeBool(value)
    AbiType.Address -> encodeAddress(value)
    is AbiType.FixedBytes -> encodeFixedBytes(type.byteSize, value)
    AbiType.Function -> encodeFunction(value)
    AbiType.DynamicString -> encodeDynamicString(value)
    AbiType.DynamicBytes -> encodeDynamicBytes(value)
    is AbiType.DynamicArray -> encodeDynamicArray(type, value)
    is AbiType.FixedArray -> encodeFixedArray(type, value)
    is AbiType.Tuple -> encodeTuple(type, value)
}

internal fun Sink.encodeUInt(bitSize: UInt = 256u, value: Value) {
    require(value is Value.BigNumber)
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    require(value.value.getSign() != Sign.NEGATIVE)
    val byteArray = value.value.toByteArray()
    require(byteArray.size.toUInt() <= bitSize / 8u)

    padding(32 - byteArray.size)
    write(byteArray)
}

internal fun Sink.encodeInt(bitSize: UInt = 256u, value: Value) {
    require(value is Value.BigNumber)
    require(bitSize in 8u..256u && bitSize % 8u == 0u)
    val byteArray = value.value.toTwosComplementByteArray()
    require(byteArray.size.toUInt() <= bitSize / 8u)

    padding(32 - byteArray.size)
    write(byteArray)
}

internal fun Sink.encodeBool(value: Value) {
    require(value is Value.Bool)

    padding(31)
    writeByte(if (value.value) 1 else 0)
}

internal fun Sink.encodeAddress(value: Value) {
    require(value is Value.Address)

    padding(12)
    write(value.value.toByteArray())
}

internal fun Sink.encodeFixedBytes(byteSize: UInt = 32u, value: Value) {
    require(value is Value.Bytes)
    require(byteSize in 1u..32u)
    require(value.value.size.toUInt() == byteSize)

    write(value.value.toByteArray())
    padding(32 - value.value.size)
}

internal fun Sink.encodeFunction(
    value: Value,
) {
    require(value is Value.Function)
    require(value.address.value.size == 20)
    require(value.selector.size == 4)

    write(value.address.value.toByteArray())
    write(value.selector.toByteArray())
    padding(8)
}

internal fun Sink.encodeDynamicString(value: Value) {
    require(value is Value.String)
    encodeDynamicBytes(Value.Bytes(value.value.encodeToByteString()))
}

internal fun Sink.encodeDynamicBytes(value: Value) {
    require(value is Value.Bytes)
    encodeUInt(value = Value.BigNumber(BigInteger(value.value.size)))

    write(value.value.toByteArray())

    val rest = value.value.size % 32
    if (rest >= 0) {
        padding(32 - rest)
    }
}

internal fun Sink.encodeDynamicArray(type: AbiType.DynamicArray, values: Value) {
    require(values is Value.Array<*>)

    encodeUInt(value = Value.BigNumber(values.size.toBigInteger()))

    if (values.isNotEmpty()) {
        encodeFixedArray(values.size.toUInt(), type.type, values)
    }
}

private fun Sink.encodeFixedArray(type: AbiType.FixedArray, values: Value) {
    encodeFixedArray(type.size, type.type, values)
}

private fun Sink.encodeFixedArray(size: UInt, type: AbiType, values: Value) {
    require(values is Value.Array<*>)
    require(values.size.toUInt() == size)

    if (type.isDynamic) {
        var offset = values.size.toUInt() * 32u

        for (value in values) {
            encodeUInt(value = Value.BigNumber(BigInteger.fromUInt(offset)))
            offset += estimate(type, value)
        }
    }

    for (value in values) {
        encode(type, value)
    }
}

internal fun Sink.encodeTuple(tuple: AbiType.Tuple, values: Value) {
    require(values is Value.Tuple)
    require(tuple.size == values.size)

    var offset = 0u

    for (param in tuple) {
        offset += if (!param.type.isDynamic) {
            estimate(param.type, values.getValue(param.name))
        } else {
            32u
        }
    }

    for (param in tuple) {
        if (!param.type.isDynamic) {
            encode(param.type, values.getValue(param.name))
        } else {
            encodeUInt(value = Value.BigNumber(offset.toBigInteger()))
            offset += estimate(param.type, values.getValue(param.name))
        }
    }

    for (param in tuple) {
        if (param.type.isDynamic) {
            encode(param.type, values.getValue(param.name))
        }
    }
}
