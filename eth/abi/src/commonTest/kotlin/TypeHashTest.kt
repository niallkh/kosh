import kosh.eth.abi.eip712.Eip712Types
import kosh.eth.abi.eip712.Eip712Type
import kosh.eth.abi.eip712.Eip712Type.Tuple.Parameter
import kosh.eth.abi.eip712.typeHash
import kosh.eth.abi.keccak256
import kotlinx.io.bytestring.encodeToByteString
import kotlin.test.Test
import kotlin.test.assertEquals

class TypeHashTest {

    @Test
    fun testType() {
        val expected =
            "Transaction(Person from,Person to,Asset[10][] txs)Asset(address token,uint256 amount)Person(address wallet,string name)"

        val types: Eip712Types = mapOf(
            Eip712Type.Tuple("Transaction") to listOf(
                Parameter(
                    name = "from",
                    type = Eip712Type.Tuple("Person"),
                ),
                Parameter(
                    name = "to",
                    type = Eip712Type.Tuple("Person"),
                ),
                Parameter(
                    name = "txs",
                    type = Eip712Type.DynamicArray(
                        Eip712Type.FixedArray(
                            10u,
                            Eip712Type.Tuple("Asset")
                        )
                    ),
                ),
            ),
            Eip712Type.Tuple("Asset") to listOf(
                Parameter("token", Eip712Type.Address),
                Parameter("amount", Eip712Type.UInt.UInt256),
            ),
            Eip712Type.Tuple("Person") to listOf(
                Parameter("wallet", Eip712Type.Address),
                Parameter("name", Eip712Type.DynamicString),
            )
        )

//        val type = Type.tuple("Transaction") {
//            tuple("from", typeName = "Person") {
//                address("wallet")
//                string("name")
//            }
//
//            tuple("to", typeName = "Person") {
//                address("wallet")
//                string("name")
//            }
//
//            array("txs") {
//                array(10u) {
//                    tuple(typeName = "Asset") {
//                        address("token")
//                        uint256("amount")
//                    }
//                }
//            }
//        }


        assertEquals(
            expected.encodeToByteString().keccak256(),
            types.typeHash(Eip712Type.Tuple("Transaction"))
        )
    }
}
