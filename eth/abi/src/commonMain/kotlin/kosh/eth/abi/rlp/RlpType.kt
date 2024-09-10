package kosh.eth.abi.rlp

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.toByteString
import kotlin.jvm.JvmInline

public sealed interface RlpType {

    public fun encode(rlpEncoder: RlpEncoder)

    @JvmInline
    public value class RlpString(public val bytes: ByteString) : RlpType {

        override fun encode(rlpEncoder: RlpEncoder) {
            rlpEncoder.string(this)
        }

        public companion object {
            public val EMPTY: RlpString = RlpString(ByteString.EMPTY)
        }
    }

    @JvmInline
    public value class RlpList(private val values: List<RlpType>) : RlpType,
        List<RlpType> by values {

        override fun encode(rlpEncoder: RlpEncoder) {
            rlpEncoder.list(this)
        }

        public companion object {
            public val EMPTY: RlpList = RlpList(emptyList())
        }
    }
}

public val BigInteger.toRlp: RlpType.RlpString
    inline get() {
        check(getSign() != Sign.NEGATIVE)

        val bytes: ByteArray = toByteArray()

        return when {
            bytes.isEmpty() -> RlpType.RlpString.EMPTY
            bytes[0] == 0.toByte() -> RlpType.RlpString(bytes.toByteString(1, bytes.size - 1))
            else -> RlpType.RlpString(bytes.toByteString())
        }
    }

public val ByteString.toRlp: RlpType.RlpString
    inline get() = RlpType.RlpString(this)

public fun rlpListOf(vararg values: RlpType): RlpType.RlpList = RlpType.RlpList(values.toList())

public fun rlpListOfNotNull(vararg values: RlpType?): RlpType.RlpList =
    RlpType.RlpList(values.filterNotNull())

public fun RlpType.encode(): ByteString {
    val buffer = Buffer()
    encode(DefaultRlpEncoder(buffer))
    return buffer.readByteString()
}
