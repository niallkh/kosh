package kosh.eth.abi.coder

import kosh.eth.abi.Type
import kosh.eth.abi.Value
import okio.utf8Size
import kotlin.math.ceil

internal class AbiLengthEstimator : Coder {

    private val stack = ArrayDeque<Value>()

    private inline fun <reified V : Value> pop(): V = stack.removeLast() as V

    private var length: UInt = 0u

    fun addValue(value: Value) {
        stack += value
    }

    fun reset(): UInt {
        return length.also {
            length = 0u
            stack.clear()
        }
    }

    override fun uint(type: Type.UInt) {
        length += 32u
    }

    override fun int(type: Type.Int) {
        length += 32u
    }

    override fun bool(type: Type.Bool) {
        length += 32u
    }

    override fun address(type: Type.Address) {
        length += 32u
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        length += 32u
    }

    override fun function(type: Type.Function) {
        length += 32u
    }

    override fun dynamicString(type: Type.DynamicString) {
        val value = pop<Value.String>()
        length += 32u + ceil(value.value.utf8Size() / 32f).toUInt() * 32u
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        val value = pop<Value.Bytes>()
        length += 32u + ceil(value.value.size / 32f).toUInt() * 32u
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        val value = pop<Value.Array<*>>()
        length += 32u
        stack.addLast(value)
        fixedArray(value.size.toUInt(), type.type)
    }

    override fun fixedArray(type: Type.FixedArray) {
        fixedArray(type.size, type.type)
    }

    private fun fixedArray(size: UInt, childType: Type) {
        if (childType.isDynamic) {
            length += size * 32u
            val value = pop<Value.Array<*>>()
            repeat(size.toInt()) {
                stack += value[it]
                childType.code(this)
            }
        } else {
            repeat(size.toInt()) {
                childType.code(this)
            }
        }
    }

    override fun tuple(tuple: Type.Tuple) {
        val value by lazy { pop<Value.Tuple>() }
        for (index in tuple.indices) {
            val parameter = tuple[index]
            if (parameter.type.isDynamic) {
                length += 32u
                stack += value[index]
                parameter.type.code(this)
            } else {
                parameter.type.code(this)
            }
        }
    }
}

internal inline fun AbiLengthEstimator.estimate(
    value: Value? = null,
    block: AbiLengthEstimator.() -> Unit,
): UInt {
    val len: UInt
    try {
        if (value != null) {
            addValue(value)
        }
        block()
    } finally {
        len = reset()
    }
    return len
}
