import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.coder.decodeOutputs
import okio.ByteString.Companion.decodeHex
import kotlin.test.Test
import kotlin.test.assertEquals

class AbiPathDecoderTest {

    @Test
    fun `decode path g`() {
        val data = ("0000000000000000000000000000000000000000000000000000000000000040" +
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
                "7468726565000000000000000000000000000000000000000000000000000000").decodeHex()

        assertEquals(
            expected = Value.tuple(
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
            ),
            actual = Abis.g.decodeOutputs(data)
        )
        val g = Abis.G(data)
        assertEquals(
            expected = Value.array(
                Value.array(
                    1uL.abi,
                    2uL.abi,
                ),
                Value.array(
                    3uL.abi,
                ),
            ),
            actual = g.array1
        )
        assertEquals(
            expected = Value.array(
                "one".abi,
                "two".abi,
                "three".abi,
            ),
            actual = g.array2
        )
        assertEquals(
            expected = Value.array(
                1uL.abi,
                2uL.abi,
            ),
            actual = Abis.g.decodeOutputs(data, "array1", "0")
        )
        assertEquals(
            expected = 1uL.abi,
            actual = Abis.g.decodeOutputs(data, "array1", "0", "0")
        )
        assertEquals(
            expected = 2uL.abi,
            actual = Abis.g.decodeOutputs(data, "array1", "0", "1")
        )
        assertEquals(
            expected = 3uL.abi,
            actual = Abis.g.decodeOutputs(data, "array1", "1", "0")
        )
        assertEquals(
            expected = "one".abi,
            actual = Abis.g.decodeOutputs(data, "array2", "0")
        )
        assertEquals(
            expected = "two".abi,
            actual = Abis.g.decodeOutputs(data, "array2", "1")
        )
        assertEquals(
            expected = "three".abi,
            actual = Abis.g.decodeOutputs(data, "array2", "2")
        )
    }
}
