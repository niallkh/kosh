package kosh.eth.abi.coder

import kosh.eth.abi.Type

internal class Eip712TypeEncoder : Coder {

    private var builder: StringBuilder = StringBuilder()
    private var arrays = ArrayDeque<UInt?>(0)
    private val tuples = mutableMapOf<String, String>()

    fun type(): String {
        val entries = tuples.entries
        val entryType = entries.last()
        val types = entries.take(entries.size - 1)
            .sortedBy { (name, _) -> name }
        return (listOf(entryType) + types).joinToString(separator = "") { (name, parameters) ->
            name + parameters
        }
    }

    override fun uint(type: Type.UInt) {
        builder.append("uint")
        builder.append(type.bitSize)
        appendArrays()
    }

    override fun int(type: Type.Int) {
        builder.append("int")
        builder.append(type.bitSize)
        appendArrays()
    }

    override fun bool(type: Type.Bool) {
        builder.append("bool")
        appendArrays()
    }

    override fun address(type: Type.Address) {
        builder.append("address")
        appendArrays()
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        builder.append("bytes")
        builder.append(type.byteSize)
        appendArrays()
    }

    override fun function(type: Type.Function) {
        builder.append("function")
        appendArrays()
    }

    override fun dynamicString(type: Type.DynamicString) {
        builder.append("string")
        appendArrays()
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        builder.append("bytes")
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
        builder.append(tuple.name ?: error("Struct must be named in eip712"))
        appendArrays()

        if (tuples.contains(tuple.name)) return

        tuples += tuple.name to rememberBuilder {
            appendTuple(tuple) { parameter ->
                parameter.type.code(this)
                if (parameter.name != null) {
                    builder.append(" ")
                    builder.append(parameter.name)
                }
            }
        }
    }

    private fun <T> appendTuple(
        items: List<T>,
        block: (T) -> Unit,
    ) {
        builder.append("(")
        items.forEachIndexed { index, item ->
            block(item)
            if (index != items.lastIndex) {
                builder.append(",")
            }
        }
        builder.append(")")
    }

    private fun rememberBuilder(block: () -> Unit): String {
        val remembered = builder
        val current = StringBuilder()
        builder = current
        block()
        builder = remembered
        return current.toString()
    }

    private fun appendArrays() {
        for (array in arrays) {
            builder.append("[")
            if (array != null) {
                builder.append(array)
            }
            builder.append("]")
        }
        arrays.clear()
    }
}
