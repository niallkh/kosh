package kosh.eth.abi.coder

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.util.fromTwosComplementByteArray
import kosh.eth.abi.Type
import kosh.eth.abi.Type.UInt.Companion.UInt256
import kosh.eth.abi.Value
import okio.BufferedSource

internal open class AbiDecoder(
    private val source: BufferedSource,
) : Coder {

    protected val stack = ArrayDeque<Value>()

    internal inline fun <reified V : Value> value(): V = stack.last() as V

    protected inline fun <reified V : Value> pop(): V = stack.removeLast() as V

    override fun uint(type: Type.UInt) {
        val byteSize = type.bitSize / 8u
        source.skip((32u - byteSize).toLong())
        stack += BigInteger.fromByteArray(source.readByteArray(byteSize.toLong()), Sign.POSITIVE)
            .let(Value.BigNumber::invoke)
    }

    override fun int(type: Type.Int) {
        val byteSize = type.bitSize / 8u
        source.skip((32u - byteSize).toLong())
        stack += BigInteger.fromTwosComplementByteArray(source.readByteArray(byteSize.toLong()))
            .let(Value.BigNumber::invoke)
    }

    override fun bool(type: Type.Bool) {
        source.skip(31)
        stack += when (source.readByte()) {
            0.toByte() -> false
            1.toByte() -> true
            else -> error("Invalid bool")
        }
            .let(Value.Bool::invoke)
    }

    override fun address(type: Type.Address) {
        source.skip(12)
        stack += source.readByteString(20)
            .let(Value.Address::invoke)
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        stack += source.readByteString(type.byteSize.toLong())
            .also { source.skip(32L - type.byteSize.toLong()) }
            .let(Value.Bytes::invoke)
    }

    override fun function(type: Type.Function) {
        fixedBytes(Type.FixedBytes.Bytes24)
        val bytes = pop<Value.Bytes>()

        val address = Value.Address(bytes.value.substring(0, 20))
        val selector = bytes.value.substring(20, 24)

        stack += Value.Function(address, selector)
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        uint(UInt256)
        val size = (stack.removeLast() as Value.BigNumber).value.longValue()
        stack += source.readByteString(size).also {
            val padding = size % 32L
            if (padding > 0) {
                source.skip(32L - padding)
            }
        }
            .let(Value.Bytes::invoke)
    }

    override fun dynamicString(type: Type.DynamicString) {
        dynamicBytes(Type.DynamicBytes)
        val bytes = pop<Value.Bytes>()
        stack += Value.String(bytes.value.utf8())
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        uint(UInt256)
        val size = pop<Value.BigNumber>().value.uintValue()
        fixedArray(size, type.type)
    }

    override fun fixedArray(type: Type.FixedArray) {
        fixedArray(type.size, type.type)
    }

    private fun fixedArray(size: UInt, childType: Type) {
        if (childType.isDynamic) {
            source.skip(size.toLong() * 32)
        }

        stack.addLast(
            List(size.toInt()) {
                childType.code(this)
                pop<Value>()
            }.let(Value::Array)
        )
    }

    override fun tuple(tuple: Type.Tuple) {
        val heads = tuple.map { parameter ->
            if (parameter.type.isDynamic) {
                source.skip(32)
                null
            } else {
                parameter.type.code(this)
                pop<Value>()
            }
        }

        val tails = tuple.map { param ->
            if (param.type.isDynamic) {
                param.type.code(this)
                pop<Value>()
            } else {
                null
            }
        }

        stack.addLast(List(tuple.size) { index ->
            heads[index] ?: tails[index] ?: error("Smth went wrong")
        }.let(Value::Tuple))
    }
}




