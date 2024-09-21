package kosh.eth.proposals.multicall

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.abiBigNumber
import kosh.eth.abi.abiBool
import kosh.eth.abi.abiBytes
import kosh.eth.abi.coder.decode
import kosh.eth.abi.coder.encode
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.hexToByteString

public object MulticallAbi {

    public val address: Value.Address =
        Value.Address("ca11bde05977b3631167028862be2a173976ca11".hexToByteString())

    private val tryBlockAndAggregate by abiFunction(
        inputs = {
            bool("requireSuccess")
            array("calls") {
                tuple { address("target"); bytes("data") }
            }
        },
        outputs = {
            uint256("blockNumber")
            bytes32("blockHash")
            array("returnData") {
                tuple { bool("success"); bytes("data") }
            }
        }
    )

    private val getEthBalance by abiFunction(
        inputs = { address("account") },
        outputs = { uint256("balance") },
    )

    public fun tryBlockAndAggregate(
        requireSuccess: Boolean,
        calls: List<Call>,
    ): FunctionCall<TryBlockAndAggregateResult> = DefaultFunctionCall(
        encoder = {
            tryBlockAndAggregate.encode(
                "requireSuccess" to requireSuccess.abi,
                "calls" to Value.Array(calls.map {
                    Value.tuple(
                        "target" to it.target,
                        "data" to it.data
                    )
                })
            )
        },
        decoder = {
            tryBlockAndAggregate.decode(it)
            TryBlockAndAggregateResult(it)
        }
    )

    public fun getEthBalance(
        address: Value.Address,
    ): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { getEthBalance.encode("account" to address) },
        decoder = { getEthBalance.decode(it).getValue("balance").abiBigNumber.value }
    )

    public data class Call(
        val target: Value.Address,
        val data: Value.Bytes,
    )

    public class TryBlockAndAggregateResult(
        private val data: ByteString,
    ) {
        private val decoded by lazy { tryBlockAndAggregate.decode(data) }
        public val blockNumber: Value.BigNumber by decoded
        public val blockHash: Value.Bytes by decoded
        private val returnData: Value.Array<Value.Tuple> by decoded

        public fun returnData(): List<Return>? {
            if (data.size == 0) return null
            return returnData.map {
                val success by it
                val data by it
                Return(success.abiBool, data.abiBytes)
            }
        }
    }

    public data class Return(
        val success: Value.Bool,
        val data: Value.Bytes,
    )
}
