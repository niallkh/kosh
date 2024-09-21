package kosh.eth.abi.coder

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.util.fromTwosComplementByteArray
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abiBigNumber
import kosh.eth.abi.abiBytes
import kosh.eth.abi.coder.AbiType.UInt.Companion.UInt256
import kosh.eth.abi.keccak256
import kotlinx.io.Buffer
import kotlinx.io.Source
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.readByteArray
import kotlinx.io.readByteString
import kotlinx.io.write

public fun Abi.Item.Function.decode(
    data: ByteString,
): Value.Tuple = decodeData(outputs, data)

public fun Abi.Item.Function.decodeInputs(
    data: ByteString,
): Value.Tuple = decodeData(inputs, data.substring(4))

private fun decodeData(
    type: AbiType.Tuple,
    data: ByteString,
): Value.Tuple = Buffer().apply { write(data) }.decodeTuple(type)

public fun Abi.Item.Event.decodeEventData(
    data: ByteString,
): Value.Tuple = decodeData(AbiType.Tuple(name, inputs.filter { it.indexed.not() }), data)

public fun Abi.Item.Event.decode(
    topics: List<ByteString>,
    data: ByteString,
): Value.Tuple {
    val topicValues = decodeEventTopics(topics).entries.let(::ArrayDeque)
    val parameterValues = decodeEventData(data).entries.let(::ArrayDeque)

    val values = inputs.map { parameter ->
        if (parameter.indexed) {
            topicValues.removeFirst().toPair()
        } else {
            parameterValues.removeFirst().toPair()
        }
    }

    return Value.tuple(*values.toTypedArray())
}

public fun Abi.Item.Event.decodeEventTopics(
    topics: List<ByteString>,
): Value.Tuple {
    val parameters = inputs.filter { it.indexed }

    val topicsWithoutSignature = if (anonymous.not()) {
        require(topics[0] == encodeSignature().keccak256())
        require(topics.size - 1 == inputs.size)
        topics.drop(1)
    } else {
        require(topics.size == inputs.size)
        topics
    }

    val decodedValues = buildList(parameters.size) {
        for (index in parameters.indices) {
            val parameter = parameters[index]
            val topic = topicsWithoutSignature[index]

            if (parameter.type is AbiType.FixedArray
                || parameter.type is AbiType.Tuple
                || parameter.type.isDynamic
            ) {
                add(parameter.name to Value.Bytes(topic))
            } else {
                add(parameter.name to Buffer().apply { write(topic) }.decode(parameter.type))
            }
        }
    }

    return Value.tuple(*decodedValues.toTypedArray())
}

internal fun Source.decode(type: AbiType): Value = when (type) {
    is AbiType.Int -> decodeInt(type)
    is AbiType.UInt -> decodeUInt(type)
    AbiType.Bool -> decodeBool()
    AbiType.Address -> decodeAddress()
    is AbiType.FixedBytes -> decodeFixedBytes(type)
    AbiType.Function -> decodeFunction()
    AbiType.DynamicBytes -> decodeDynamicBytes()
    AbiType.DynamicString -> decodeDynamicString()
    is AbiType.DynamicArray -> decodeDynamicArray(type)
    is AbiType.FixedArray -> decodeFixedArray(type)
    is AbiType.Tuple -> decodeTuple(type)
}

internal fun Source.decodeUInt(type: AbiType.UInt): Value {
    val byteSize = type.bitSize / 8u
    skip(32L - byteSize.toLong())
    val byteArray = readByteArray(byteSize.toInt())
    return Value.BigNumber(BigInteger.fromByteArray(byteArray, Sign.POSITIVE))
}

internal fun Source.decodeInt(type: AbiType.Int): Value {
    val byteSize = type.bitSize / 8u
    skip(32L - byteSize.toLong())
    val byteArray = readByteArray(byteSize.toInt())
    return Value.BigNumber(BigInteger.fromTwosComplementByteArray(byteArray))
}

internal fun Source.decodeBool(): Value {
    skip(31)
    return when (readByte()) {
        0.toByte() -> Value.Bool(false)
        1.toByte() -> Value.Bool(true)
        else -> error("Couldn't decode bool")
    }
}

internal fun Source.decodeAddress(): Value {
    skip(12)
    return Value.Address(readByteString(20))
}

internal fun Source.decodeFixedBytes(type: AbiType.FixedBytes): Value {
    return Value.Bytes(readByteString(type.byteSize.toInt()))
        .also { skip(32L - type.byteSize.toLong()) }
}

internal fun Source.decodeFunction(): Value {
    val address = readByteString(20)
    val selector = readByteString(4)
    skip(8)

    return Value.Function(Value.Address(address), selector)
}

internal fun Source.decodeDynamicBytes(): Value {
    val size = decodeUInt(UInt256).abiBigNumber.value.intValue()
    return Value.Bytes(readByteString(size)).also {
        val padding = size % 32L
        if (padding > 0) {
            skip(32L - padding)
        }
    }
}

internal fun Source.decodeDynamicString(): Value {
    return Value.String(decodeDynamicBytes().abiBytes.value.decodeToString())
}

internal fun Source.decodeDynamicArray(type: AbiType.DynamicArray): Value {
    val size = decodeUInt(UInt256).abiBigNumber.value.uintValue()

    return if (size > 0u) {
        decodeFixedArray(size, type.type)
    } else {
        Value.array<Value>()
    }
}

internal fun Source.decodeFixedArray(type: AbiType.FixedArray): Value {
    return decodeFixedArray(type.size, type.type)
}

private fun Source.decodeFixedArray(size: UInt, type: AbiType): Value {
    if (type.isDynamic) {
        skip(size.toLong() * 32L)
    }

    return Value.Array(List(size.toInt()) {
        decode(type)
    })
}

internal fun Source.decodeTuple(tuple: AbiType.Tuple): Value.Tuple {
    val heads = tuple.map { param ->
        if (!param.type.isDynamic) {
            decode(param.type)
        } else {
            skip(32)
            null
        }
    }

    val tails = tuple.map { param ->
        if (!param.type.isDynamic) {
            null
        } else {
            decode(param.type)
        }
    }


    return Value.Tuple(buildMap(tuple.size) {
        repeat(tuple.size) { index ->
            val value = heads[index] ?: tails[index] ?: error("Smth went wrong")
            val param = tuple[index]
            put(param.name!!, value)
        }
    })
}
