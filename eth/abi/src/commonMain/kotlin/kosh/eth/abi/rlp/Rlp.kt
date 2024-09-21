package kosh.eth.abi.rlp

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.readByteString
import kotlin.jvm.JvmInline

public sealed interface Rlp {

    @JvmInline
    public value class RlpString private constructor(public val bytes: ByteString) : Rlp {

        public companion object {
            public val EMPTY: RlpString = RlpString(ByteString())

            public operator fun invoke(bytes: ByteString): RlpString {
                return if (bytes.size == 0) EMPTY else RlpString(bytes)
            }

            public operator fun invoke(): RlpString = EMPTY
        }
    }

    @JvmInline
    public value class RlpList(private val values: List<Rlp>) : Rlp, List<Rlp> by values {

        public companion object {
            public val EMPTY: RlpList = RlpList(emptyList())
        }
    }
}

public val BigInteger.toRlp: Rlp.RlpString
    get() {
        check(getSign() != Sign.NEGATIVE)

        val bytes = toByteArray()

        return when {
            bytes.isEmpty() -> Rlp.RlpString()
            bytes.size == 1 && bytes[0] == 0.toByte() -> Rlp.RlpString()
            else -> Rlp.RlpString(UnsafeByteStringOperations.wrapUnsafe(bytes))
        }
    }

public val ByteString.toRlp: Rlp.RlpString
    inline get() = Rlp.RlpString(this)

public fun rlpListOf(vararg values: Rlp): Rlp.RlpList = Rlp.RlpList(values.toList())

public fun rlpListOfNotNull(vararg values: Rlp?): Rlp.RlpList =
    Rlp.RlpList(values.filterNotNull())

public fun Rlp.encode(): ByteString = Buffer().run {
    encode(this@encode)
    readByteString()
}
