import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.coder.encodeInputs
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.encodeUtf8
import kotlin.test.Test
import kotlin.test.assertEquals

class AbiEncoderTest {

    @Test
    fun `encode bar`() {
        assertEquals(
            expected = ("fce353f6" +
                    "6162630000000000000000000000000000000000000000000000000000000000" +
                    "6465660000000000000000000000000000000000000000000000000000000000").decodeHex(),

            actual = Abis.bar.encodeInputs(
                Value.array(
                    "abc".encodeUtf8().abi,
                    "def".encodeUtf8().abi,
                )
            ),
        )
    }

    @Test
    fun `encode bar2`() {
        assertEquals(
            expected = ("172bfeb9" +
                    "0000000000000000000000000000000000000000000000000000000000000060" +
                    "6162630000000000000000000000000000000000000000000000000000000000" +
                    "6465660000000000000000000000000000000000000000000000000000000000" +
                    "000000000000000000000000000000000000000000000000000000000000000d" +
                    "48656c6c6f2c20776f726c642100000000000000000000000000000000000000").decodeHex(),

            actual = Abis.bar2.encodeInputs(
                "Hello, world!".encodeUtf8().abi,
                Value.array(
                    "abc".encodeUtf8().abi,
                    "def".encodeUtf8().abi,
                ),
            )
        )
    }

    @Test
    fun `encode baz`() {
        assertEquals(
            expected = ("cdcd77c0" +
                    "0000000000000000000000000000000000000000000000000000000000000045" +
                    "0000000000000000000000000000000000000000000000000000000000000001").decodeHex(),

            actual = Abis.baz.encodeInputs(
                69u.abi,
                Value.Bool(true)
            ),
        )
    }

    @Test
    fun `encode sam`() {
        assertEquals(
            expected = ("a5643bf2" +
                    "0000000000000000000000000000000000000000000000000000000000000060" +
                    "0000000000000000000000000000000000000000000000000000000000000001" +
                    "00000000000000000000000000000000000000000000000000000000000000a0" +
                    "0000000000000000000000000000000000000000000000000000000000000004" +
                    "6461766500000000000000000000000000000000000000000000000000000000" +
                    "0000000000000000000000000000000000000000000000000000000000000003" +
                    "0000000000000000000000000000000000000000000000000000000000000001" +
                    "0000000000000000000000000000000000000000000000000000000000000002" +
                    "0000000000000000000000000000000000000000000000000000000000000003").decodeHex(),

            actual = Abis.sam.encodeInputs(
                "dave".encodeUtf8().abi,
                Value.Bool(true),
                Value.array(
                    1u.abi,
                    2u.abi,
                    3u.abi,
                )
            )
        )
    }

    @Test
    fun `encode f`() {
        assertEquals(
            expected = ("8be65246" +
                    "0000000000000000000000000000000000000000000000000000000000000123" +
                    "0000000000000000000000000000000000000000000000000000000000000080" +
                    "3132333435363738393000000000000000000000000000000000000000000000" +
                    "00000000000000000000000000000000000000000000000000000000000000e0" +
                    "0000000000000000000000000000000000000000000000000000000000000002" +
                    "0000000000000000000000000000000000000000000000000000000000000456" +
                    "0000000000000000000000000000000000000000000000000000000000000789" +
                    "000000000000000000000000000000000000000000000000000000000000000d" +
                    "48656c6c6f2c20776f726c642100000000000000000000000000000000000000").decodeHex(),

            actual = Abis.f.encodeInputs(
                0x123uL.abi,
                Value.array(
                    0x456uL.abi,
                    0x789uL.abi,
                ),
                "1234567890".encodeUtf8().abi,
                "Hello, world!".encodeUtf8().abi,
            ),
        )
    }

    @Test
    fun `encode g`() {
        assertEquals(
            expected = ("2289b18c" +
                    "0000000000000000000000000000000000000000000000000000000000000040" +
                    "0000000000000000000000000000000000000000000000000000000000000140" +
                    "0000000000000000000000000000000000000000000000000000000000000002" +
                    "0000000000000000000000000000000000000000000000000000000000000040" +
                    "00000000000000000000000000000000000000000000000000000000000000a0" +
                    "0000000000000000000000000000000000000000000000000000000000000002" +
                    "0000000000000000000000000000000000000000000000000000000000000001" +
                    "0000000000000000000000000000000000000000000000000000000000000002" +
                    "0000000000000000000000000000000000000000000000000000000000000001" +
                    "0000000000000000000000000000000000000000000000000000000000000003" +
                    "0000000000000000000000000000000000000000000000000000000000000003" +
                    "0000000000000000000000000000000000000000000000000000000000000060" +
                    "00000000000000000000000000000000000000000000000000000000000000a0" +
                    "00000000000000000000000000000000000000000000000000000000000000e0" +
                    "0000000000000000000000000000000000000000000000000000000000000003" +
                    "6f6e650000000000000000000000000000000000000000000000000000000000" +
                    "0000000000000000000000000000000000000000000000000000000000000003" +
                    "74776f0000000000000000000000000000000000000000000000000000000000" +
                    "0000000000000000000000000000000000000000000000000000000000000005" +
                    "7468726565000000000000000000000000000000000000000000000000000000").decodeHex(),

            actual = Abis.g.encodeInputs(
                Value.array(
                    Value.array(
                        1uL.abi,
                        2uL.abi,
                    ),
                    Value.array(
                        3uL.abi,
                    ),
                ),
                Value.array(
                    "one".abi,
                    "two".abi,
                    "three".abi,
                )
            )
        )
    }
}