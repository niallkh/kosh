package kosh.eth.abi.coder

import kosh.eth.abi.Value
import kotlin.math.ceil

internal fun estimate(type: AbiType, value: Value): UInt = when (type) {
    is AbiType.UInt -> estimateUInt()
    is AbiType.Int -> estimateInt()
    AbiType.Bool -> estimateBool()
    AbiType.Address -> estimateAddress()
    is AbiType.FixedBytes -> estimateFixedBytes()
    AbiType.Function -> estimateFunction()
    AbiType.DynamicBytes -> estimateDynamicBytes(value)
    AbiType.DynamicString -> estimateDynamicString(value)
    is AbiType.DynamicArray -> estimateDynamicArray(type, value)
    is AbiType.FixedArray -> estimateFixedArray(type, value)
    is AbiType.Tuple -> estimateTuple(type, value)
}

internal fun estimateUInt(): UInt = 32u
internal fun estimateInt(): UInt = 32u
internal fun estimateBool(): UInt = 32u
internal fun estimateAddress(): UInt = 32u
internal fun estimateFixedBytes(): UInt = 32u
internal fun estimateFunction(): UInt = 32u

internal fun estimateDynamicString(value: Value): UInt {
    require(value is Value.String)
    return 32u + ceil(value.value.length / 32f).toUInt() * 32u
}

internal fun estimateDynamicBytes(value: Value): UInt {
    require(value is Value.Bytes)
    return 32u + ceil(value.value.size / 32f).toUInt() * 32u
}

internal fun estimateDynamicArray(type: AbiType.DynamicArray, values: Value): UInt {
    require(values is Value.Array<*>)
    return 32u + if (values.isNotEmpty()) {
        estimateFixedArray(values.size.toUInt(), type.type, values)
    } else {
        0u
    }
}

internal fun estimateFixedArray(type: AbiType.FixedArray, values: Value): UInt {
    require(values is Value.Array<*>)
    return estimateFixedArray(type.size, type.type, values)
}

private fun estimateFixedArray(size: UInt, type: AbiType, values: Value.Array<*>): UInt {
    require(size == values.size.toUInt())

    return if (!type.isDynamic) {
        values.fold(0u) { acc, v -> acc + estimate(type, v) }
    } else {
        values.fold(0u) { acc, v -> acc + 32u + estimate(type, v) }
    }
}

internal fun estimateTuple(tuple: AbiType.Tuple, values: Value): UInt {
    require(values is Value.Tuple)

    return tuple.zip(values.values).fold(0u) { acc, (param, v) ->
        if (!param.type.isDynamic) {
            acc + estimate(param.type, v)
        } else {
            acc + 32u + estimate(param.type, v)
        }
    }
}
