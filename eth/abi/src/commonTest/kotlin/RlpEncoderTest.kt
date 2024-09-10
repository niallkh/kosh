import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.rlp.encode
import kosh.eth.abi.rlp.rlpListOf
import kosh.eth.abi.rlp.toRlp
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import kotlin.test.Test
import kotlin.test.assertEquals

class RlpEncoderTest {

    @Test
    fun dog() {
        assertEquals(
            "dog".encodeUtf8().toRlp.encode().hex(),
            "83" + "dog".encodeUtf8().hex(),
        )
    }

    @Test
    fun catAndDog() {
        assertEquals(
            rlpListOf(
                "cat".encodeUtf8().toRlp,
                "dog".encodeUtf8().toRlp,
            ).encode().hex(),
            "c8" + "83" + "cat".encodeUtf8().hex() + "83" + "dog".encodeUtf8().hex(),
        )
    }

    @Test
    fun emptyString() {
        assertEquals(
            ByteString.EMPTY.toRlp.encode().hex(),
            "80"
        )

        assertEquals(
            "".encodeUtf8().toRlp.encode().hex(),
            "80"
        )
        assertEquals(
            BigInteger.ZERO.toRlp.encode().hex(),
            "80"
        )
    }

    @Test
    fun emptyList() {
        assertEquals(
            rlpListOf().encode().hex(),
            "c0"
        )
    }

    @Test
    fun ints() {
        assertEquals(
            BigInteger(0x0f).toRlp.encode().hex(),
            "0f"
        )
        assertEquals(
            BigInteger(0x0400).toRlp.encode().hex(),
            "820400"
        )
    }

    @Test
    fun lists() {
        assertEquals(
            rlpListOf(
                rlpListOf(),
                rlpListOf(rlpListOf()),
                rlpListOf(
                    rlpListOf(),
                    rlpListOf(rlpListOf())
                )
            ).encode().hex(),
            "c7c0c1c0c3c0c1c0"
        )
    }

    @Test
    fun loremIpsum() {
        val data = "Lorem ipsum dolor sit amet, consectetur adipisicing elit".encodeUtf8()

        assertEquals(
            data.toRlp.encode().hex(),
            "b838" + data.hex()
        )
    }

    @Test
    fun stringLen55() {
        val data = "%".repeat(55).encodeUtf8()

        assertEquals(
            data.toRlp.encode().hex(),
            "b7" + data.hex()
        )
    }
}
