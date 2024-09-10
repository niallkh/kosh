package kosh.eth.abi.coder

import com.ionspin.kotlin.bignum.integer.Sign
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import kosh.eth.abi.Type
import kosh.eth.abi.Type.UInt.Companion.UInt256
import kosh.eth.abi.Value
import okio.Buffer
import okio.BufferedSink
import okio.ByteString.Companion.encodeUtf8

public open class AbiEncoder(
    private val sink: BufferedSink,
    value: Value,
) : Coder {

    protected val stack: ArrayDeque<Value> = ArrayDeque(listOf(value))
    private val abiLengthEstimator = AbiLengthEstimator()

    protected inline fun <reified V : Value> pop(): V =
        stack.removeLast() as? V ?: error("Invalid value")

    override fun uint(type: Type.UInt) {
        val value = pop<Value.BigNumber>().value
        require(type.bitSize in 8u..256u && type.bitSize % 8u == 0u)
        require(value.getSign() != Sign.NEGATIVE)
        val byteArray = value.toByteArray()
        require(byteArray.size.toUInt() <= type.bitSize / 8u)

        sink.padding(32 - byteArray.size)
        sink.write(byteArray)
    }

    override fun int(type: Type.Int) {
        val value = pop<Value.BigNumber>().value
        require(type.bitSize in 8u..256u && type.bitSize % 8u == 0u)
        val byteArray = value.toTwosComplementByteArray()
        require(byteArray.size.toUInt() <= type.bitSize / 8u)

        sink.padding(32 - byteArray.size)
        sink.write(byteArray)
    }

    override fun bool(type: Type.Bool) {
        val value = pop<Value.Bool>().value

        sink.padding(31)
        sink.writeByte(if (value) 1 else 0)
    }

    override fun address(type: Type.Address) {
        val value = pop<Value.Address>().value

        sink.padding(12)
        sink.write(value)
    }

    override fun function(type: Type.Function) {
        val function = pop<Value.Function>()
        val buffer = Buffer().apply {
            write(function.address.value)
            write(function.selector)
        }

        stack += Value.Bytes(buffer.readByteString())
        fixedBytes(Type.FixedBytes.Bytes24)
    }

    override fun fixedBytes(type: Type.FixedBytes) {
        val value = pop<Value.Bytes>().value
        require(type.byteSize in 1u..32u)
        require(value.size.toUInt() == type.byteSize)

        sink.write(value)
        sink.padding(32 - value.size)
    }

    override fun dynamicString(type: Type.DynamicString) {
        val value = pop<Value.String>().value
        stack += Value.Bytes(value.encodeUtf8())
        dynamicBytes(Type.DynamicBytes)
    }

    override fun dynamicBytes(type: Type.DynamicBytes) {
        val value = pop<Value.Bytes>().value

        stack += Value.BigNumber(value.size.toBigInteger())
        uint(UInt256)

        sink.write(value)

        val rest = value.size % 32
        if (rest > 0) {
            sink.padding(32 - rest)
        }
    }

    override fun dynamicArray(type: Type.DynamicArray) {
        val value = pop<Value.Array<*>>()

        stack += Value.BigNumber(value.size.toBigInteger())
        uint(UInt256)

        if (value.isNotEmpty()) {
            stack.addLast(value)
            fixedArray(value.size.toUInt(), type.type)
        }
    }

    override fun fixedArray(type: Type.FixedArray) {
        fixedArray(type.size, type.type)
    }

    private fun fixedArray(size: UInt, childType: Type) {
        val value = pop<Value.Array<*>>()
        require(value.size.toUInt() == size)

        if (childType.isDynamic) {
            var offset = value.size.toUInt() * 32u

            for (childValue in value) {
                stack += Value.BigNumber(offset.toBigInteger())
                uint(UInt256)
                offset += abiLengthEstimator.estimate(childValue) { childType.code(this) }
            }
        }

        for (childValue in value) {
            stack += childValue
            childType.code(this)
        }
    }

    override fun tuple(tuple: Type.Tuple) {
        val value = pop<Value.Tuple>()
        require(tuple.size == value.size)

        var offset = 0uL
        for (i in tuple.indices) {
            val parameter = tuple[i]
            val parameterValue = value[i]

            offset += if (parameter.type.isDynamic) {
                32u
            } else {
                abiLengthEstimator.estimate(parameterValue) { parameter.type.code(this) }
            }
        }

        for (i in tuple.indices) {
            val parameter = tuple[i]
            val parameterValue = value[i]

            if (parameter.type.isDynamic) {
                stack += Value.BigNumber(offset.toBigInteger())
                uint(UInt256)
                offset += abiLengthEstimator.estimate(parameterValue) { parameter.type.code(this) }
            } else {
                stack += parameterValue
                parameter.type.code(this)
            }
        }

        for (i in tuple.indices) {
            val parameter = tuple[i]
            val parameterValue = value[i]

            if (parameter.type.isDynamic) {
                stack += parameterValue
                parameter.type.code(this)
            }
        }
    }
}

//public fun BigInteger.toUIntPacked(bits: UInt = 256u): ByteString {
//    require(bits in 8u..256u && bits % 8u == 0u)
//    require(getSign() != Sign.NEGATIVE)
//    require(bitLength().toUInt() <= bits)
//
//    val byteArray = toByteArray()
//
//    return when {
//        byteArray.isEmpty() -> ByteString.EMPTY
//        byteArray[0] == 0.toByte() -> byteArray.toByteString(1, byteArray.size - 1)
//        else -> byteArray.toByteString()
//    }
//}
