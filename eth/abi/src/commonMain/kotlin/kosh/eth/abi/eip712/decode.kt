package kosh.eth.abi.eip712

import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.Value
import kotlinx.io.bytestring.hexToByteString
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

internal fun JsonElement.decode(types: Eip712Types, type: Eip712Type): Value = when (type) {
    is Eip712Type.UInt -> decodeUInt()
    is Eip712Type.Int -> decodeInt()
    Eip712Type.Address -> decodeAddress()
    Eip712Type.Bool -> decodeBool()
    is Eip712Type.FixedBytes -> decodeFixedBytes()
    Eip712Type.DynamicBytes -> decodeDynamicBytes()
    Eip712Type.DynamicString -> decodeDynamicString()
    is Eip712Type.FixedArray -> decodeFixedArray(types, type)
    is Eip712Type.DynamicArray -> decodeDynamicArray(types, type)
    is Eip712Type.Tuple -> decodeTuple(types, type)
}

private fun JsonElement.decodeUInt(): Value {
    return decodeBigNumber()
}

private fun JsonElement.decodeInt(): Value {
    return decodeBigNumber()
}

private fun JsonElement.decodeBigNumber(): Value {
    jsonPrimitive.longOrNull?.let {
        return Value.BigNumber(it.toBigInteger())
    }

    return if (jsonPrimitive.content.startsWith("0x")) {
        Value.BigNumber(jsonPrimitive.content.toBigInteger(16))
    } else {
        Value.BigNumber(jsonPrimitive.content.toBigInteger())
    }
}

private fun JsonElement.decodeBool(): Value {
    return Value.Bool(jsonPrimitive.boolean)
}

private fun JsonElement.decodeAddress(): Value {
    return Value.Address(jsonPrimitive.content.removePrefix("0x").hexToByteString())
}

private fun JsonElement.decodeFixedBytes(): Value {
    return Value.Bytes(jsonPrimitive.content.removePrefix("0x").hexToByteString())
}

private fun JsonElement.decodeDynamicString(): Value.String {
    return Value.String(jsonPrimitive.content)
}

private fun JsonElement.decodeDynamicBytes(): Value {
    return Value.Bytes(jsonPrimitive.content.removePrefix("0x").hexToByteString())
}

private fun JsonElement.decodeDynamicArray(
    types: Eip712Types,
    type: Eip712Type.DynamicArray,
): Value {
    return decodeFixedArray(types, jsonArray.size.toUInt(), type.type)
}

private fun JsonElement.decodeFixedArray(types: Eip712Types, type: Eip712Type.FixedArray): Value {
    return decodeFixedArray(types, type.size, type.type)
}

private fun JsonElement.decodeFixedArray(types: Eip712Types, size: UInt, type: Eip712Type): Value {
    val jsonArray = jsonArray
    check(jsonArray.size == size.toInt())
    return Value.Array(jsonArray.map { it.decode(types, type) })
}

internal fun JsonElement.decodeTuple(types: Eip712Types, tuple: Eip712Type.Tuple): Value.Tuple {
    val parameters = types.getValue(tuple)
    val jsonObject = jsonObject
    check(parameters.size == jsonObject.size)
    return Value.tuple(
        *parameters.map {
            val jsonElement = jsonObject[it.name] ?: error("No value for param: ${it.name}")
            it.name to jsonElement.decode(types, it.type)
        }.toTypedArray()
    )
}
