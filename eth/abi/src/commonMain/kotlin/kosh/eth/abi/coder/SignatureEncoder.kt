package kosh.eth.abi.coder

import kosh.eth.abi.Type
import okio.Buffer

internal class SignatureEncoder(
    private val buffer: Buffer = Buffer(),
) : Coder {

    private var arrays = ArrayDeque<UInt?>(0)

    override fun uint(type: Type.UInt) {
        buffer.writeUtf8("uint")
        buffer.writeUtf8(type.bitSize.toString())
        appendArrays()
    }

    override fun int(type: Type.Int) {
        buffer.writeUtf8("int")
        buffer.writeUtf8(type.bitSize.toString())
        appendArrays()
    }

    override fun bool(type: Type.Bool) {
        buffer.writeUtf8("bool")
        appendArrays()
    }

    override fun address(type: Type.Address) {
        buffer.writeUtf8("address")
        appendArrays()
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        buffer.writeUtf8("bytes")
        buffer.writeUtf8(type.byteSize.toString())
        appendArrays()
    }

    override fun function(type: Type.Function) {
        buffer.writeUtf8("function")
        appendArrays()
    }

    override fun dynamicString(type: Type.DynamicString) {
        buffer.writeUtf8("string")
        appendArrays()
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        buffer.writeUtf8("bytes")
        appendArrays()
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        arrays.add(0, null)
        type.type.code(this)
    }

    override fun fixedArray(type: Type.FixedArray) {
        arrays.add(0, type.size)
        type.type.code(this)
    }

    override fun tuple(tuple: Type.Tuple) {
        rememberArrays {
            appendTuple(items = tuple) { parameter ->
                parameter.type.code(this)
            }
        }
        appendArrays()
    }

    private fun appendArrays() {
        for (arraySize in arrays) {
            buffer.writeUtf8("[")
            if (arraySize != null) {
                buffer.writeUtf8(arraySize.toString())
            }
            buffer.writeUtf8("]")
        }
        arrays = ArrayDeque(0)
    }

    private fun rememberArrays(block: () -> Unit) {
        val remembered = arrays
        arrays = ArrayDeque(0)
        block()
        arrays = remembered
    }

    private fun <T> appendTuple(
        items: List<T>,
        block: (T) -> Unit,
    ) {
        buffer.writeUtf8("(")
        items.forEachIndexed { index, item ->
            block(item)
            if (index != items.lastIndex) {
                buffer.writeUtf8(",")
            }
        }
        buffer.writeUtf8(")")
    }
}
