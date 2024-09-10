import kosh.eth.abi.Value
import kosh.eth.abi.delegate.decode
import kosh.eth.abi.dsl.abiFunction
import okio.ByteString

object Abis {
    val bar by abiFunction {
        array("array", size = 2u) {
            bytes(size = 3u)
        }
    }

    val bar2 by abiFunction {
        bytes("bytes")
        array("array", size = 2u) {
            bytes(size = 3u)
        }
    }

    val baz by abiFunction {
        uint32("number")
        bool("bool")
    }

    val sam by abiFunction {
        bytes("bytes")
        bool("bool")
        array("array") { uint256() }
    }

    val f by abiFunction {
        uint256("number")
        array("array") { uint32() }
        bytes("bytes10", size = 10u)
        bytes("bytes")
    }

    val g by abiFunction {
        array("array1") {
            array {
                uint256()
            }
        }
        array("array2") {
            string()
        }
    }

    class G(data: ByteString) {
        val array1 by g.decode<Value.Array<*>>(data)
        val array2 by g.decode<Value.Array<*>>(data)
    }

    val tuple by abiFunction {
        tuple("tuple") {
            uint256("number")
            array("array") { uint256() }
            array("arrayt") {
                tuple {
                    uint256("num1")
                    uint256("num2")
                }
            }
        }
        tuple("tuple2") {
            uint256("num1")
            uint256("num2")
        }
    }
}

