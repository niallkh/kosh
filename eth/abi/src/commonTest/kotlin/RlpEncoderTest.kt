@file:OptIn(ExperimentalStdlibApi::class)

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.rlp.Rlp
import kosh.eth.abi.rlp.encode
import kosh.eth.abi.rlp.rlpListOf
import kosh.eth.abi.rlp.toRlp
import kotlinx.io.bytestring.encodeToByteString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.io.bytestring.toHexString
import kotlin.test.Test
import kotlin.test.assertEquals

class RlpEncoderTest {

    @Test
    fun dog() {
        assertEquals(
            "dog".encodeToByteString().toRlp.encode().toHexString(),
            "83" + "dog".encodeToByteString().toHexString(),
        )
    }

    @Test
    fun catAndDog() {
        assertEquals(
            rlpListOf(
                "cat".encodeToByteString().toRlp,
                "dog".encodeToByteString().toRlp,
            ).encode().toHexString(),
            "c8" + "83" + "cat".encodeToByteString()
                .toHexString() + "83" + "dog".encodeToByteString().toHexString(),
        )
    }

    @Test
    fun emptyString() {
        assertEquals(
            Rlp.RlpString().encode().toHexString(),
            "80"
        )

        assertEquals(
            "".encodeToByteString().toRlp.encode().toHexString(),
            "80"
        )
        assertEquals(
            BigInteger.ZERO.toRlp.encode().toHexString(),
            "80"
        )
    }

    @Test
    fun emptyList() {
        assertEquals(
            rlpListOf().encode().toHexString(),
            "c0"
        )
    }

    @Test
    fun ints() {
        assertEquals(
            BigInteger(0x0f).toRlp.encode().toHexString(),
            "0f"
        )
        assertEquals(
            BigInteger(0x0400).toRlp.encode().toHexString(),
            "820400"
        )
    }

    @Test
    fun lists() {
        assertEquals(
            "c7c0c1c0c3c0c1c0".hexToByteString(),
            rlpListOf(
                rlpListOf(),
                rlpListOf(rlpListOf()),
                rlpListOf(
                    rlpListOf(),
                    rlpListOf(rlpListOf())
                )
            ).encode(),
        )
    }

    @Test
    fun loremIpsum() {
        val data = "Lorem ipsum dolor sit amet, consectetur adipisicing elit".encodeToByteString()

        assertEquals(
            data.toRlp.encode().toHexString(),
            "b838" + data.toHexString()
        )
    }

    @Test
    fun stringLen55() {
        val data = "%".repeat(55).encodeToByteString()

        assertEquals(
            data.toRlp.encode().toHexString(),
            "b7" + data.toHexString()
        )
    }
}
