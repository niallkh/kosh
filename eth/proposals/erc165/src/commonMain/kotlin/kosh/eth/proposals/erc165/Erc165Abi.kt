package kosh.eth.proposals.erc165

import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.asBool
import kosh.eth.abi.coder.decodeOutputs
import kosh.eth.abi.coder.encodeInputs
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import okio.ByteString.Companion.decodeHex

public object Erc165Abi {
    private val supportsInterface by abiViewFunction(
        inputs = { bytes4("interfaceId") },
        outputs = { bool("supports") }
    )

    public fun supportsInterface(
        interfaceId: Value.Bytes,
    ): FunctionCall<Boolean> = DefaultFunctionCall(
        encoder = { supportsInterface.encodeInputs(interfaceId) },
        decoder = { supportsInterface.decodeOutputs(it, "supports").asBool.value }
    )

    public val Erc165InterfaceId: Value.Bytes = "01ffc9a7".decodeHex().abi
    public val Erc165InvalidInterfaceId: Value.Bytes = "ffffffff".decodeHex().abi
    public val Erc20InterfaceId: Value.Bytes = "36372b07".decodeHex().abi
    public val Erc721InterfaceId: Value.Bytes = "80ac58cd".decodeHex().abi
    public val Erc721MetadataInterfaceId: Value.Bytes = "5b5e139f".decodeHex().abi
    public val Erc721EnumerableInterfaceId: Value.Bytes = "780e9d63".decodeHex().abi
    public val Erc1155InterfaceId: Value.Bytes = "d9b67a26".decodeHex().abi
    public val Erc1155MetadataInterfaceId: Value.Bytes = "0e89341c".decodeHex().abi
}
