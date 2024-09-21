import kosh.eth.abi.Abi
import kosh.eth.abi.coder.AbiType
import kosh.eth.abi.coder.AbiType.Tuple.Parameter
import kosh.eth.abi.name
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonItemTest {

    @Test
    fun `erc20 json abi`() {
        val abi = Abi.from(erc20Abi)

        val function = abi.find { it.name == "transfer" } as Abi.Item.Function
        assertEquals(function.inputs[0], Parameter(AbiType.Address, "to"))
        assertEquals(function.inputs[1], Parameter(AbiType.UInt.UInt256, "value"))

        val event = abi.find { it.name == "Transfer" } as Abi.Item.Event
        assertEquals(event.inputs[0], Parameter(AbiType.Address, "from", true))
        assertEquals(event.inputs[1], Parameter(AbiType.Address, "to", true))
        assertEquals(event.inputs[2], Parameter(AbiType.UInt.UInt256, "value", false))
    }
}
