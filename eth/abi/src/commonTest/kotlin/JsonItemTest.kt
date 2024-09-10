import kosh.eth.abi.Abi
import kosh.eth.abi.Type
import kosh.eth.abi.Type.Tuple.Parameter
import kosh.eth.abi.name
import kotlin.test.Test
import kotlin.test.assertEquals

class JsonItemTest {

    @Test
    fun `erc20 json abi`() {
        val abi = Abi.from(erc20Abi)

        val function = abi.find { it.name == "transfer" } as Abi.Item.Function
        assertEquals(function.inputs[0], Parameter(Type.Address, "to"))
        assertEquals(function.inputs[1], Parameter(Type.UInt.UInt256, "value"))

        val event = abi.find { it.name == "Transfer" } as Abi.Item.Event
        assertEquals(event.inputs[0], Parameter(Type.Address, "from", true))
        assertEquals(event.inputs[1], Parameter(Type.Address, "to", true))
        assertEquals(event.inputs[2], Parameter(Type.UInt.UInt256, "value", false))
    }
}
