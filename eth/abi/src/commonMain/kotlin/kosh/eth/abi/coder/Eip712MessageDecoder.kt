package kosh.eth.abi.coder

import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.Type
import kosh.eth.abi.Value
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull
import okio.ByteString.Companion.decodeHex

internal class Eip712MessageDecoder(
    message: JsonElement,
) : Coder {

    private val jsonStack = ArrayDeque(listOf(message))
    internal val valueStack = ArrayDeque<Value>()

    internal inline fun <reified V : Value> value(): V = valueStack.last() as V
    private inline fun <reified V : Value> popValue(): V = valueStack.removeLast() as V

    override fun uint(type: Type.UInt) {
        bigint()
        val value = popValue<Value.BigNumber>()
        check(value.value.getSign() != Sign.NEGATIVE)
        valueStack += value
    }

    override fun int(type: Type.Int) {
        bigint()
    }

    private fun bigint() {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive

        jsonPrimitive.longOrNull?.let {
            valueStack += Value.BigNumber(it.toBigInteger())
        }

        val bigInt = if (jsonPrimitive.content.startsWith("0x")) {
            jsonPrimitive.content.toBigInteger(16)
        } else {
            jsonPrimitive.content.toBigInteger()
        }

        valueStack += Value.BigNumber(bigInt)
    }

    override fun bool(type: Type.Bool) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        valueStack += Value.Bool(jsonPrimitive.boolean)
    }

    override fun address(type: Type.Address) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        valueStack += Value.Address(jsonPrimitive.content.removePrefix("0x").decodeHex())
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        valueStack += Value.Bytes(jsonPrimitive.content.removePrefix("0x").decodeHex())
    }

    override fun function(type: Type.Function) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        val bytes = jsonPrimitive.content.removePrefix("0x").decodeHex()
        valueStack += Value.Function(Value.Address(bytes.substring(0, 20)), bytes.substring(20, 24))
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        valueStack += Value.Bytes(jsonPrimitive.content.removePrefix("0x").decodeHex())
    }

    override fun dynamicString(type: Type.DynamicString) {
        val jsonPrimitive = jsonStack.removeLast().jsonPrimitive
        valueStack += Value.String(jsonPrimitive.content)
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        return fixedArray(
            size = jsonStack.last().jsonArray.size.toUInt(),
            childType = type.type
        )
    }

    override fun fixedArray(type: Type.FixedArray) {
        fixedArray(type.size, type.type)
    }

    private fun fixedArray(size: UInt, childType: Type) {
        val jsonArray = jsonStack.removeLast().jsonArray
        check(jsonArray.size == size.toInt())
        valueStack.addLast(Value.Array(
            jsonArray.map { jsonElement ->
                jsonStack.addLast(jsonElement)
                childType.code(this)
                popValue()
            }
        ))
    }

    override fun tuple(tuple: Type.Tuple) {
        val jsonObject = jsonStack.removeLast().jsonObject
        check(tuple.size == jsonObject.size)
        valueStack.addLast(Value.Tuple(
            tuple.map {
                val jsonElement = jsonObject[it.name] ?: error("no value for param ${it.name}")
                jsonStack.addLast(jsonElement)
                it.type.code(this)
                popValue()
            }
        ))
    }
}
