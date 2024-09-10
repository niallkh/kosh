package kosh.eth.abi.coder

import kosh.eth.abi.Type
import kosh.eth.abi.Type.UInt.Companion.UInt256
import kosh.eth.abi.Value
import okio.BufferedSource

internal class AbiPathDecoder(
    private val source: BufferedSource,
    path: List<String>,
) : AbiDecoder(source), Coder {

    private val path = ArrayDeque(path)

    private val abiLengthEstimator = AbiLengthEstimator()

    override fun dynamicArray(type: Type.DynamicArray) {
        when (val index = path.removeFirstOrNull()) {
            null -> super.dynamicArray(type)

            else -> {
                uint(UInt256)
                val size = pop<Value.BigNumber>().value.uintValue(exactRequired = true)
                stack += lookupInFixedArray(index, size, type.type)
            }
        }
    }

    override fun fixedArray(
        type: Type.FixedArray,
    ) = when (val index = path.removeFirstOrNull()) {
        null -> super.fixedArray(type)
        else -> stack += lookupInFixedArray(index, type.size, type.type)
    }

    override fun tuple(
        tuple: Type.Tuple,
    ) = when (val name = path.removeFirstOrNull()) {
        null -> super.tuple(tuple)
        else -> stack += lookupInTuple(name, tuple)
    }

    private fun lookupInFixedArray(
        index: String,
        size: UInt,
        childType: Type,
    ): Value {
        val parsedIndex = index.toUIntOrNull() ?: error("Invalid index: $index")
        require(parsedIndex < size)

        if (childType.isDynamic) {
            var position = parsedIndex.toInt() * 32L
            source.skip(position)
            uint(UInt256)
            val offset = pop<Value.BigNumber>().value.longValue()
            position += 32
            source.skip(offset - position)
        } else {
            val length = abiLengthEstimator.estimate { childType.code(this) }
            source.skip(parsedIndex.toInt() * length.toLong())
        }

        childType.code(this)
        return pop()
    }

    private fun lookupInTuple(
        name: String,
        parameters: List<Type.Tuple.Parameter>,
    ): Value {
        val parsedIndex = name.toIntOrNull()
        var position = 0u

        for (index in parameters.indices) {
            val parameter = parameters[index]
            if (parameter.name == name || index == parsedIndex) {
                if (parameter.type.isDynamic) {
                    uint(UInt256)
                    val offset = pop<Value.BigNumber>().value.longValue()
                    position += 32u
                    source.skip(offset - position.toLong())
                }
                parameter.type.code(this)
                return pop()
            } else {
                if (parameter.type.isDynamic) {
                    source.skip(32)
                    position += 32u
                } else {
                    val length = abiLengthEstimator.estimate { parameter.type.code(this) }
                    source.skip(length.toLong())
                    position += length
                }
            }
        }

        error("Not found")
    }
}
