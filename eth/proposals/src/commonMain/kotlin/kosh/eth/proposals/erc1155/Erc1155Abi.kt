package kosh.eth.proposals.erc1155

import arrow.core.Tuple5
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.abiBigNumber
import kosh.eth.abi.abiString
import kosh.eth.abi.coder.decode
import kosh.eth.abi.coder.decodeInputs
import kosh.eth.abi.coder.encode
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import kotlinx.io.bytestring.ByteString

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
    private val setApprovalForAll: Abi.Item.Function by abiFunction {
        address("operator")
        bool("approved")
    }

    public fun balanceOf(
        address: Value.Address,
        tokenId: BigInteger,
    ): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encode("owner" to address, "id" to tokenId.abi) },
        decoder = { balanceOf.decode(it).getValue("balance").abiBigNumber.value }
    )

    public fun uri(
        tokenId: BigInteger,
    ): FunctionCall<String> = DefaultFunctionCall(
        encoder = { uri.encode("id" to tokenId.abi) },
        decoder = { uri.decode(it).getValue("uri").abiString.value }
    )

    public fun setApprovalForAll(data: ByteString): Pair<Value.Address, Value.Bool> {
        val decoded = setApprovalForAll.decodeInputs(data)
        val operator: Value.Address by decoded
        val approved: Value.Bool by decoded
        return operator to approved
    }

    public fun safeTransferFrom(
        input: ByteString,
    ): Tuple5<Value.Address, Value.Address, Value.BigNumber, Value.BigNumber, Value.Bytes> {
        val decoded = safeTransferFrom.decodeInputs(input)
        val from: Value.Address by decoded
        val to: Value.Address by decoded
        val id: Value.BigNumber by decoded
        val value: Value.BigNumber by decoded
        val data: Value.Bytes by decoded
        return Tuple5(from, to, id, value, data)
    }

    public fun safeBatchTransferFrom(
        input: ByteString,
    ): Tuple5<Value.Address, Value.Address, Value.Array<Value.BigNumber>, Value.Array<Value.BigNumber>, Value.Bytes> {
        val decoded = safeBatchTransferFrom.decodeInputs(input)
        val from: Value.Address by decoded
        val to: Value.Address by decoded
        val ids: Value.Array<Value.BigNumber> by decoded
        val values: Value.Array<Value.BigNumber> by decoded
        val data: Value.Bytes by decoded
        return Tuple5(from, to, ids, values, data)
    }
}
