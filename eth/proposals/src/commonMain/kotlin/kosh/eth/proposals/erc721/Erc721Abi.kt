package kosh.eth.proposals.erc721

import arrow.core.Tuple4
import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.abiAddress
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
            val decoded = Erc721Abi.safeTransferFrom.decodeInputs(input)
            val from: Value.Address by decoded
            val to: Value.Address by decoded
            val tokenId: Value.BigNumber by decoded
            val data: Value.Bytes by decoded
            return Tuple4(from, to, tokenId, data)
        }
    }

    public fun name(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { name.encode() },
        decoder = { name.decode(it).getValue("name").abiString.value },
    )

    public fun symbol(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { symbol.encode() },
        decoder = { symbol.decode(it).getValue("symbol").abiString.value },
    )

    public fun tokenUri(tokenId: BigInteger): FunctionCall<String> = DefaultFunctionCall(
        encoder = { tokenURI.encode("tokenId" to tokenId.abi) },
        decoder = { tokenURI.decode(it).getValue("uri").abiString.value }
    )

    public fun balanceOf(address: Value.Address): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encode("account" to address) },
        decoder = { balanceOf.decode(it).getValue("balance").abiBigNumber.value }
    )

    public fun balanceOf(
        address: Value.Address,
        tokenId: BigInteger,
    ): FunctionCall<BigInteger> =
        DefaultFunctionCall(
            encoder = { ownerOf.encode("tokenId" to tokenId.abi) },
            decoder = {
                val owner = ownerOf.decode(it).getValue("owner").abiAddress
                if (owner == address) BigInteger.ONE else BigInteger.ZERO
            }
        )

    public fun ownerOf(tokenId: BigInteger): FunctionCall<Value.Address> = DefaultFunctionCall(
        encoder = { ownerOf.encode("tokenId" to tokenId.abi) },
        decoder = { ownerOf.decode(it).getValue("owner").abiAddress }
    )

    public fun approve(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val decoded = approve.decodeInputs(data)
        val spender: Value.Address by decoded
        val tokenId: Value.BigNumber by decoded
        return spender to tokenId
    }

    public fun setApprovalForAll(data: ByteString): Pair<Value.Address, Value.Bool> {
        val decoded = setApprovalForAll.decodeInputs(data)
        val operator: Value.Address by decoded
        val approved: Value.Bool by decoded
        return operator to approved
    }

    public fun safeTransferFrom(data: ByteString): Triple<Value.Address, Value.Address, Value.BigNumber> {
        val decoded = safeTransferFrom.decodeInputs(data)
        val from: Value.Address by decoded
        val to: Value.Address by decoded
        val tokenId: Value.BigNumber by decoded
        return Triple(from, to, tokenId)
    }
}
