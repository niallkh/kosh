package kosh.eth.abi

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import okio.ByteString
import okio.ByteString.Companion.toByteString
import kotlin.jvm.JvmInline

public sealed interface Value {

    @JvmInline
    public value class BigNumber private constructor(public val value: BigInteger) : Value {

        public companion object {
            private val ZERO: BigNumber = BigNumber(BigInteger.ZERO)

            public operator fun invoke(bigInteger: BigInteger): BigNumber =
                if (bigInteger.isZero()) ZERO else BigNumber(bigInteger)

            public val UINT256_MAX: BigInteger by lazy {
                BigInteger.parseString(
                    "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff",
                    base = 16
                )
            }
        }
    }

    @JvmInline
    public value class Bool private constructor(public val value: Boolean) : Value {
        public companion object {
            private val TRUE: Bool = Bool(true)
            private val FALSE: Bool = Bool(false)

            public operator fun invoke(bool: Boolean): Bool = if (bool) TRUE else FALSE
        }
    }

    @JvmInline
    public value class Bytes private constructor(public val value: ByteString) : Value {
        public companion object {
            private val EMPTY: Bytes = Bytes(ByteString.EMPTY)

            public operator fun invoke(byteString: ByteString? = null): Bytes {
                return if (byteString == null || byteString.size == 0) EMPTY else Bytes(byteString)
            }
        }
    }

    @JvmInline
    public value class String(public val value: kotlin.String) : Value

    @JvmInline
    public value class Address private constructor(public val value: ByteString) : Value {

        init {
            require(value.size == 20)
        }

        public companion object {
            private val ZERO: Address = Address(ByteArray(20).toByteString())

            public operator fun invoke(byteString: ByteString? = null): Address {
                require(byteString == null || byteString.size == 20)
                return if (byteString == null || byteString.size == 0) ZERO else Address(byteString)
            }
        }
    }

    public data class Function(val address: Address, val selector: ByteString) : Value

    @JvmInline
    public value class Array<T : Value>(private val values: List<T>) : Value, List<T> by values

    @JvmInline
    public value class Tuple(private val values: List<Value>) : Value, List<Value> by values

    public companion object {
        public fun <T : Value> array(vararg values: T): Array<T> = Array(values.toList())
        public fun tuple(vararg values: Value): Tuple = Tuple(values.toList())
        public fun tuple(values: List<Value>): Tuple = Tuple(values)
    }
}

public val UInt.abi: Value.BigNumber
    inline get() = Value.BigNumber(toBigInteger())

public val ULong.abi: Value.BigNumber
    inline get() = Value.BigNumber(toBigInteger())

public val BigInteger.abi: Value.BigNumber
    inline get() = Value.BigNumber(this)

public val String.abi: Value.String
    inline get() = Value.String(this)

public val Boolean.abi: Value.Bool
    inline get() = Value.Bool(this)

public val ByteString.abiAddress: Value.Address
    inline get() = Value.Address(this)

public val ByteString.abi: Value.Bytes
    inline get() = Value.Bytes(this)

public val Value.asBigNumber: Value.BigNumber
    inline get() = this as Value.BigNumber

public val Value.asBool: Value.Bool
    inline get() = this as Value.Bool

public val Value.asAddress: Value.Address
    inline get() = this as Value.Address

public val Value.asBytes: Value.Bytes
    inline get() = this as Value.Bytes

public val Value.asString: Value.String
    inline get() = this as Value.String
