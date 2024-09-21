package kosh.eth.proposals.erc1155

import arrow.core.Tuple5
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.coder.decodeOutputs
import kosh.eth.abi.coder.encode
import kosh.eth.abi.delegate.decodeInputs
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import okio.ByteString

public object Erc1155Abi {

    private val uri by abiViewFunction(
        inputs = { uint256("id") },
        outputs = { string("uri") }
    )
    private val balanceOf by abiViewFunction(
        inputs = {
            address("owner")
            uint256("id")
        },
        outputs = { uint256("balance") }
    )
    public val safeTransferFrom: Abi.Item.Function by abiFunction {
        address("from")
        address("to")
        uint256("id")
        uint256("value")
        bytes("data")
    }
    public val safeBatchTransferFrom: Abi.Item.Function by abiFunction {
        address("from")
        address("to")
        array("ids") { uint256() }
        array("values") { uint256() }
        bytes("data")
    }
    public val setApprovalForAll: Abi.Item.Function by abiFunction {
        address("operator")
        bool("approved")
    }

    public fun balanceOf(
        address: Value.Address,
        tokenId: BigInteger,
    ): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encode(address, tokenId.abi) },
        decoder = { balanceOf.decodeOutputs(it, "balance").asBigNumber.value }
    )

    public fun uri(
        tokenId: BigInteger,
    ): FunctionCall<String> = DefaultFunctionCall(
        encoder = { uri.encode(tokenId.abi) },
        decoder = { uri.decodeOutputs(it, "uri").asString.value }
    )

    public fun setApprovalForAll(data: ByteString): Pair<Value.Address, Value.Bool> {
        val operator by setApprovalForAll.decodeInputs<Value.Address>(data)
        val approved by setApprovalForAll.decodeInputs<Value.Bool>(data)
        return operator to approved
    }

    public fun safeTransferFrom(
        input: ByteString,
    ): Tuple5<Value.Address, Value.Address, Value.BigNumber, Value.BigNumber, Value.Bytes> {
        val from by safeTransferFrom.decodeInputs<Value.Address>(input)
        val to by safeTransferFrom.decodeInputs<Value.Address>(input)
        val id by safeTransferFrom.decodeInputs<Value.BigNumber>(input)
        val value by safeTransferFrom.decodeInputs<Value.BigNumber>(input)
        val data by safeTransferFrom.decodeInputs<Value.Bytes>(input)
        return Tuple5(from, to, id, value, data)
    }

    public fun safeBatchTransferFrom(
        input: ByteString,
    ): Tuple5<Value.Address, Value.Address, Value.Array<Value.BigNumber>, Value.Array<Value.BigNumber>, Value.Bytes> {
        val from by safeTransferFrom.decodeInputs<Value.Address>(input)
        val to by safeTransferFrom.decodeInputs<Value.Address>(input)
        val ids by safeTransferFrom.decodeInputs<Value.Array<Value.BigNumber>>(input)
        val values by safeTransferFrom.decodeInputs<Value.Array<Value.BigNumber>>(input)
        val data by safeTransferFrom.decodeInputs<Value.Bytes>(input)
        return Tuple5(from, to, ids, values, data)
    }
}
