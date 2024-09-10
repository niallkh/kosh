import kosh.eth.abi.Type
import kosh.eth.abi.coder.Eip712TypeEncoder
import kotlin.test.Test
import kotlin.test.assertEquals

class Eip712TypeEncoderTest {

    @Test
    fun testType() {
        val expected =
            "Transaction(Person from,Person to,Asset[10][] txs)Asset(address token,uint256 amount)Person(address wallet,string name)"
        val type = Type.tuple("Transaction") {
            tuple("from", typeName = "Person") {
                address("wallet")
                string("name")
            }

            tuple("to", typeName = "Person") {
                address("wallet")
                string("name")
            }

            array("txs") {
                array(10u) {
                    tuple(typeName = "Asset") {
                        address("token")
                        uint256("amount")
                    }
                }
            }
        }

        val encoder = Eip712TypeEncoder()
        type.code(encoder)
        assertEquals(expected, encoder.type())
    }
}
