package kosh.eth.proposals.erc721

import arrow.core.Tuple4
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.asAddress
import kosh.eth.abi.asBigNumber
import kosh.eth.abi.asString
import kosh.eth.abi.coder.decodeOutputs
import kosh.eth.abi.coder.encodeInputs
import kosh.eth.abi.delegate.decodeInputs
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import okio.ByteString


public object Erc721Abi {
    private val name by abiViewFunction(
        outputs = { string("name") }
    )
    private val symbol by abiViewFunction(
        outputs = { string("symbol") }
    )
    private val tokenURI by abiViewFunction(
        inputs = { uint256("tokenId") },
        outputs = { string("uri") }
    )
    private val balanceOf by abiViewFunction(
        inputs = { address("account") },
        outputs = { uint256("balance") }
    )
    private val ownerOf by abiViewFunction(
        inputs = { uint256("tokenId") },
        outputs = { address("owner") }
    )
    public val approve: Abi.Item.Function by abiFunction(
        inputs = {
            address("spender")
            uint256("tokenId")
        }
    )
    public val transferFrom: Abi.Item.Function by abiFunction(
        inputs = {
            address("from")
            address("to")
            uint256("tokenId")
        }
    )
    public val setApprovalForAll: Abi.Item.Function by abiFunction(
        inputs = {
            address("operator")
            bool("approved")
        }
    )
    public val safeTransferFrom: Abi.Item.Function by abiFunction(
        inputs = {
            address("from")
            address("to")
            uint256("tokenId")
        }
    )

    public object More {
        public val safeTransferFrom: Abi.Item.Function by abiViewFunction(
            inputs = {
                address("from")
                address("to")
                uint256("tokenId")
                bytes("data")
            }
        )

        public fun safeTransferFrom(
            input: ByteString,
        ): Tuple4<Value.Address, Value.Address, Value.BigNumber, Value.Bytes> {
            val from by Erc721Abi.safeTransferFrom.decodeInputs<Value.Address>(input)
            val to by Erc721Abi.safeTransferFrom.decodeInputs<Value.Address>(input)
            val tokenId by Erc721Abi.safeTransferFrom.decodeInputs<Value.BigNumber>(input)
            val data by Erc721Abi.safeTransferFrom.decodeInputs<Value.Bytes>(input)
            return Tuple4(from, to, tokenId, data)
        }
    }

    public fun name(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { name.encodeInputs() },
        decoder = { name.decodeOutputs(it, "name").asString.value },
    )

    public fun symbol(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { symbol.encodeInputs() },
        decoder = { symbol.decodeOutputs(it, "symbol").asString.value },
    )

    public fun tokenUri(tokenId: BigInteger): FunctionCall<String> = DefaultFunctionCall(
        encoder = { tokenURI.encodeInputs(tokenId.abi) },
        decoder = { tokenURI.decodeOutputs(it, "uri").asString.value }
    )

    public fun balanceOf(address: Value.Address): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encodeInputs(address) },
        decoder = { balanceOf.decodeOutputs(it, "balance").asBigNumber.value }
    )

    public fun balanceOf(
        address: Value.Address,
        tokenId: BigInteger,
    ): FunctionCall<BigInteger> =
        DefaultFunctionCall(
            encoder = { ownerOf.encodeInputs(tokenId.abi) },
            decoder = {
                val owner = ownerOf.decodeOutputs(it, "owner").asAddress
                if (owner == address) BigInteger.ONE else BigInteger.ZERO
            }
        )

    public fun ownerOf(tokenId: BigInteger): FunctionCall<Value.Address> = DefaultFunctionCall(
        encoder = { ownerOf.encodeInputs(tokenId.abi) },
        decoder = { ownerOf.decodeOutputs(it, "owner").asAddress }
    )

    public fun approve(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val spender by approve.decodeInputs<Value.Address>(data)
        val tokenId by approve.decodeInputs<Value.BigNumber>(data)
        return spender to tokenId
    }

    public fun setApprovalForAll(data: ByteString): Pair<Value.Address, Value.Bool> {
        val operator by setApprovalForAll.decodeInputs<Value.Address>(data)
        val approved by setApprovalForAll.decodeInputs<Value.Bool>(data)
        return operator to approved
    }

    public fun safeTransferFrom(data: ByteString): Triple<Value.Address, Value.Address, Value.BigNumber> {
        val from by safeTransferFrom.decodeInputs<Value.Address>(data)
        val to by safeTransferFrom.decodeInputs<Value.Address>(data)
        val tokenId by safeTransferFrom.decodeInputs<Value.BigNumber>(data)
        return Triple(from, to, tokenId)
    }
}
