package kosh.eth.abi.coder

import kosh.eth.abi.Type
import kosh.eth.abi.Type.FixedBytes.Companion.Bytes32
import kosh.eth.abi.Value
import kosh.eth.abi.keccak256
import okio.BufferedSink
import okio.ByteString.Companion.encodeUtf8
import okio.use

public class Eip712ValueEncoder(
    private val sink: BufferedSink,
    value: Value,
) : AbiEncoder(sink, value), Coder {

    override fun dynamicString(type: Type.DynamicString) {
        val value = pop<Value.String>()
        stack += Value.Bytes(value.value.encodeUtf8().keccak256())
        fixedBytes(Bytes32)
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        val value = pop<Value.Bytes>()
        stack += Value.Bytes(value.value.keccak256())
        fixedBytes(Bytes32)
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        val value = pop<Value.Array<*>>()

        stack.addLast(value)
        fixedArray(
            size = value.size.toUInt(),
            childType = type.type,
        )
    }

    override fun fixedArray(type: Type.FixedArray) {
        fixedArray(type.size, type.type)
    }

    private fun fixedArray(size: UInt, childType: Type) {
        val value = pop<Value.Array<*>>()

        require(value.size.toUInt() == size)

        keccak256 {
            for (item in value) {
                stack += item
                childType.code(this)
            }
        }
    }

    override fun tuple(tuple: Type.Tuple) {
        val value = pop<Value.Tuple>()
        require(value.size == tuple.size)

        keccak256 {
            val typeHash = tuple.eip712TypeHash()

            stack += Value.Bytes(typeHash)
            fixedBytes(Bytes32)

            for (i in tuple.indices) {
                val parameter = tuple[i]
                val parameterValue = value[i]

                stack += parameterValue
                parameter.type.code(this)
            }
        }
    }

    private inline fun keccak256(block: () -> Unit) {
        val rememberedSize = sink.buffer.size
        block()
        val hash = sink.buffer.peek().apply {
            skip(rememberedSize)
        }.readByteString().keccak256()
        sink.buffer.readAndWriteUnsafe().use {
            it.resizeBuffer(rememberedSize)
        }
        stack += Value.Bytes(hash)
        fixedBytes(Bytes32)
    }
}
