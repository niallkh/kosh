import kosh.eth.abi.coder.encodeSignature
import kotlin.test.Test
import kotlin.test.assertEquals

class SignatureEncoderTest {

    @Test
    fun `encode bar`() {
        assertEquals("bar(bytes3[2])", Abis.bar.encodeSignature())
    }

    @Test
    fun `encode baz`() {
        assertEquals("baz(uint32,bool)", Abis.baz.encodeSignature())
    }

    @Test
    fun `encode sam`() {
        assertEquals("sam(bytes,bool,uint256[])", Abis.sam.encodeSignature())
    }

    @Test
    fun `encode f`() {
        assertEquals("f(uint256,uint32[],bytes10,bytes)", Abis.f.encodeSignature())
    }

    @Test
    fun `encode g`() {
        assertEquals("g(uint256[][],string[])", Abis.g.encodeSignature())
    }

    @Test
    fun `encode tuple`() {
        assertEquals(
            expected = "tuple((uint256,uint256[],(uint256,uint256)[]),(uint256,uint256))",
            actual = Abis.tuple.encodeSignature()
        )
    }
}
