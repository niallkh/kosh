package kosh.eth.abi

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.hexToByteString
import kotlin.jvm.JvmInline

public sealed interface Value {

    @JvmInline
    public value class BigNumber private constructor(public val value: BigInteger) : Value {

        public companion object {
            private val ZERO: BigNumber = BigNumber(BigInteger.ZERO)

            public operator fun invoke(bigInteger: BigInteger): BigNumber =
                if (bigInteger.isZero()) ZERO else BigNumber(bigInteger)

            public operator fun invoke(): BigNumber = ZERO

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
    public value class Bytes(public val value: ByteString) : Value {
        public companion object {
            private val EMPTY: Bytes = Bytes(ByteString())

            public operator fun invoke(byteString: ByteString = ByteString()): Bytes {
                return if (byteString.size == 0) EMPTY else Bytes(byteString)
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
            private val ZERO: Address = Address(ByteString(ByteArray(20)))

            public operator fun invoke(byteString: ByteString? = null): Address {
                require(byteString == null || byteString.size == 20)
                return if (byteString == null || byteString.size == 0) ZERO else Address(byteString)
            }

            public operator fun invoke(string: kotlin.String): Address {
                return Address(string.removePrefix("0x").hexToByteString())
            }
        }
    }

    public data class Function(val address: Address, val selector: ByteString) : Value

    @JvmInline
    public value class Array<T : Value>(private val list: List<T>) : Value, List<T> by list

    @JvmInline
    public value class Tuple(private val map: Map<kotlin.String, Value>) : Value,
        Map<kotlin.String, Value> by map

    public companion object {
        public fun <T : Value> array(vararg values: T): Array<T> = Array(values.toList())
        public fun <T : Value> array(values: List<T>): Array<T> = Array(values.toList())
        public fun tuple(vararg values: Pair<kotlin.String, Value>): Tuple = Tuple(values.toMap())
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

public val Value.Bytes.address: Value.Address
    inline get() = Value.Address(value)

public val ByteString.abi: Value.Bytes
    inline get() = Value.Bytes(this)

public val Value.abiBigNumber: Value.BigNumber
    inline get() = this as Value.BigNumber

public val Value.abiBool: Value.Bool
    inline get() = this as Value.Bool

public val Value.abiAddress: Value.Address
    inline get() = this as Value.Address

public val Value.abiBytes: Value.Bytes
    inline get() = this as Value.Bytes

public val Value.abiString: Value.String
    inline get() = this as Value.String

public fun Value.at(vararg path: String): Value = at(path.toList())

public fun Value.at(path: List<String>): Value = when (this) {
    is Value.Array<*> -> {
        val index = path.first().toInt()
        get(index).at(path.drop(1))
    }

    is Value.Tuple -> {
        val name = path.first()
        getValue(name).at(path.drop(1))

    }

    else -> this.also { check(path.isEmpty()) }
}
