import kosh.eth.abi.coder.encodeSignature
import kotlinx.io.bytestring.decodeToString
import kotlin.test.Test
import kotlin.test.assertEquals

class SignatureEncoderTest {

    @Test
    fun `encode bar`() {
        assertEquals(
            "bar(bytes3[2])",
            Abis.bar.encodeSignature().decodeToString()
        )
    }

    @Test
    fun `encode baz`() {
        assertEquals(
            "baz(uint32,bool)",
            Abis.baz.encodeSignature().decodeToString()
        )
    }

    @Test
    fun `encode sam`() {
        assertEquals(
            "sam(bytes,bool,uint256[])",
            Abis.sam.encodeSignature().decodeToString()
        )
    }

    @Test
    fun `encode f`() {
        assertEquals(
            "f(uint256,uint32[],bytes10,bytes)",
            Abis.f.encodeSignature().decodeToString()
        )
    }

    @Test
    fun `encode g`() {
        assertEquals(
            "g(uint256[][],string[])",
            Abis.g.encodeSignature().decodeToString()
        )
    }

    @Test
    fun `encode tuple`() {
        assertEquals(
            expected = "tuple((uint256,uint256[],(uint256,uint256)[]),(uint256,uint256))",
            actual = Abis.tuple.encodeSignature().decodeToString()
        )
    }
}
