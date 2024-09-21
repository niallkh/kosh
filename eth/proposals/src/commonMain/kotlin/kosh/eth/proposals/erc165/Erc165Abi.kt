package kosh.eth.proposals.erc165

import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.abiBool
import kosh.eth.abi.coder.decode
import kosh.eth.abi.coder.encode
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import kotlinx.io.bytestring.hexToByteString

public object Erc165Abi {
    private val supportsInterface by abiViewFunction(
        inputs = { bytes4("interfaceId") },
        outputs = { bool("supports") }
    )

    public fun supportsInterface(
        interfaceId: Value.Bytes,
    ): FunctionCall<Boolean> = DefaultFunctionCall(
        encoder = { supportsInterface.encode("interfaceId" to interfaceId) },
        decoder = { supportsInterface.decode(it).getValue("supports").abiBool.value }
    )

    public val Erc165InterfaceId: Value.Bytes = "01ffc9a7".hexToByteString().abi
    public val Erc165InvalidInterfaceId: Value.Bytes = "ffffffff".hexToByteString().abi
    public val Erc20InterfaceId: Value.Bytes = "36372b07".hexToByteString().abi
    public val Erc721InterfaceId: Value.Bytes = "80ac58cd".hexToByteString().abi
    public val Erc721MetadataInterfaceId: Value.Bytes = "5b5e139f".hexToByteString().abi
    public val Erc721EnumerableInterfaceId: Value.Bytes = "780e9d63".hexToByteString().abi
    public val Erc1155InterfaceId: Value.Bytes = "d9b67a26".hexToByteString().abi
    public val Erc1155MetadataInterfaceId: Value.Bytes = "0e89341c".hexToByteString().abi
}
