package kosh.eth.proposals.erc20

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.abiBigNumber
import kosh.eth.abi.abiBool
import kosh.eth.abi.abiString
import kosh.eth.abi.coder.decode
import kosh.eth.abi.coder.decodeInputs
import kosh.eth.abi.coder.encode
import kosh.eth.abi.coder.encodeTopics
import kosh.eth.abi.dsl.abiEvent
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultEventFilter
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.EventFilter
import kosh.eth.proposals.FunctionCall
import kotlinx.io.bytestring.ByteString

public object Erc20Abi {
    private val name by abiViewFunction(
        outputs = { string("name") }
    )
    private val symbol by abiViewFunction(
        outputs = { string("symbol") }
    )
    private val decimals by abiViewFunction(
        outputs = { uint8("decimals") }
    )
    private val totalSupply by abiViewFunction(
        outputs = { uint256("totalSupply") }
    )
    private val balanceOf by abiViewFunction(
        inputs = { address("owner") },
        outputs = { uint256("balance") }
    )
    public val transfer: Abi.Item.Function by abiFunction(
        inputs = { address("to"); uint256("value") },
        outputs = { bool("success") }
    )
    public val transferFrom: Abi.Item.Function by abiFunction(
        inputs = {
            address("from")
            address("to")
            uint256("value")
        },
        outputs = {
            bool("success")
        }
    )
    private val allowance by abiViewFunction(
        inputs = {
            address("owner")
            address("spender")
        },
        outputs = {
            uint256("remaining")
        }
    )
    public val approve: Abi.Item.Function by abiFunction(
        inputs = {
            address("spender")
            uint256("value")
        },
        outputs = {
            bool("success")
        }
    )
    private val Transfer by abiEvent {
        address("from", indexed = true)
        address("to", indexed = true)
        uint256("value")
    }
    private val Approve by abiEvent {
        address("owner", indexed = true)
        address("spender", indexed = true)
        uint256("value")
    }

    public fun name(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { name.encode() },
        decoder = { name.decode(it).getValue("name").abiString.value }
    )

    public fun symbol(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { symbol.encode() },
        decoder = { symbol.decode(it).getValue("symbol").abiString.value }
    )

    public fun decimals(): FunctionCall<UInt> = DefaultFunctionCall(
        encoder = { decimals.encode() },
        decoder = { decimals.decode(it).getValue("decimals").abiBigNumber.value.uintValue() }
    )

    public fun totalSupply(): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { totalSupply.encode() },
        decoder = { totalSupply.decode(it).getValue("totalSupply").abiBigNumber.value }
    )

    public fun balanceOf(address: Value.Address): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encode("owner" to address) },
        decoder = { balanceOf.decode(it).getValue("balance").abiBigNumber.value }
    )

    public fun transfer(to: Value.Address, value: Value.BigNumber): FunctionCall<Boolean> =
        DefaultFunctionCall(
            encoder = { transfer.encode("to" to to, "value" to value) },
            decoder = { transfer.decode(it).getValue("success").abiBool.value }
        )

    public fun transfer(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val decoded = transfer.decodeInputs(data)
        val to: Value.Address by decoded
        val value: Value.BigNumber by decoded
        return to to value
    }

    public fun transferFrom(
        from: Value.Address,
        to: Value.Address,
        value: Value.BigNumber,
    ): FunctionCall<Boolean> = DefaultFunctionCall(
        encoder = { transferFrom.encode("from" to from, "to" to to, "value" to value) },
        decoder = { transferFrom.decode(it).getValue("success").abiBool.value }
    )

    public fun transferFrom(data: ByteString): Triple<Value.Address, Value.Address, Value.BigNumber> {
        val decoded = transferFrom.decodeInputs(data)
        val from: Value.Address by decoded
        val to: Value.Address by decoded
        val value: Value.BigNumber by decoded
        return Triple(from, to, value)
    }

    public fun allowance(
        owner: Value.Address,
        spender: Value.Address,
    ): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { allowance.encode("owner" to owner, "spender" to spender) },
        decoder = { allowance.decode(it).getValue("remaining").abiBigNumber.value }
    )

    public fun approve(
        spender: Value.Address,
        value: Value.BigNumber,
    ): FunctionCall<Boolean> = DefaultFunctionCall(
        encoder = { approve.encode("spender" to spender, "value" to value) },
        decoder = { approve.decode(it).getValue("success").abiBool.value }
    )

    public fun approve(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val decoded = approve.decodeInputs(data)
        val spender: Value.Address by decoded
        val value: Value.BigNumber by decoded
        return spender to value
    }

    @Suppress("FunctionName")
    public fun TransferFilter(
        to: Value.Address,
        from: Value.Address,
    ): EventFilter<TransferEvent> = DefaultEventFilter(
        filter = { Transfer.encodeTopics(to, from) },
        decoder = { topics, data -> TransferEvent(topics, data) }
    )

    @Suppress("FunctionName")
    public fun ApproveFilter(
        to: Value.Address,
        from: Value.Address,
    ): EventFilter<ApproveEvent> = DefaultEventFilter(
        filter = { Approve.encodeTopics(to, from) },
        decoder = { topics, data -> ApproveEvent(topics, data) }
    )

    public class TransferEvent(topics: List<ByteString>, data: ByteString) {
        private val decoded by lazy { Transfer.decode(topics, data) }
        public val from: Value.Address by decoded
        public val to: Value.Address by decoded
        public val value: Value.BigNumber by decoded
    }

    public class ApproveEvent(topics: List<ByteString>, data: ByteString) {
        private val decoded by lazy { Approve.decode(topics, data) }
        public val owner: Value.Address by decoded
        public val spender: Value.Address by decoded
        public val value: Value.BigNumber by decoded
    }
}
