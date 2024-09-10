package kosh.eth.proposals.erc20

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Abi
import kosh.eth.abi.Value
import kosh.eth.abi.asBigNumber
import kosh.eth.abi.asBool
import kosh.eth.abi.asString
import kosh.eth.abi.coder.decodeOutputs
import kosh.eth.abi.coder.encodeInputs
import kosh.eth.abi.coder.encodeTopics
import kosh.eth.abi.delegate.decode
import kosh.eth.abi.delegate.decodeInputs
import kosh.eth.abi.dsl.abiEvent
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultEventFilter
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.EventFilter
import kosh.eth.proposals.FunctionCall
import okio.ByteString

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
        encoder = { name.encodeInputs() },
        decoder = {
            name.decodeOutputs(it, "name").asString.value
        }
    )

    public fun symbol(): FunctionCall<String> = DefaultFunctionCall(
        encoder = { symbol.encodeInputs() },
        decoder = { symbol.decodeOutputs(it, "symbol").asString.value }
    )

    public fun decimals(): FunctionCall<UInt> = DefaultFunctionCall(
        encoder = { decimals.encodeInputs() },
        decoder = { decimals.decodeOutputs(it, "decimals").asBigNumber.value.uintValue() }
    )

    public fun totalSupply(): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { totalSupply.encodeInputs() },
        decoder = { totalSupply.decodeOutputs(it, "totalSupply").asBigNumber.value }
    )

    public fun balanceOf(address: Value.Address): FunctionCall<BigInteger> = DefaultFunctionCall(
        encoder = { balanceOf.encodeInputs(address) },
        decoder = { balanceOf.decodeOutputs(it, "balance").asBigNumber.value }
    )

    public fun transfer(to: Value.Address, value: Value.BigNumber): FunctionCall<Boolean> =
        DefaultFunctionCall(
            encoder = { transfer.encodeInputs(to, value) },
            decoder = {
                val success by transfer.decode<Value.Bool>(it)
                success.value
            }
        )

    public fun transfer(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val to by transfer.decodeInputs<Value.Address>(data)
        val value by transfer.decodeInputs<Value.BigNumber>(data)
        return to to value
    }

    public fun transferFrom(
        from: Value.Address,
        to: Value.Address,
        value: Value.BigNumber,
    ): FunctionCall<Boolean> = DefaultFunctionCall(
        encoder = { transferFrom.encodeInputs(from, to, value) },
        decoder = { transferFrom.decodeOutputs(it, "success").asBool.value }
    )

    public fun transferFrom(data: ByteString): Triple<Value.Address, Value.Address, Value.BigNumber> {
        val from by transferFrom.decodeInputs<Value.Address>(data)
        val to by transferFrom.decodeInputs<Value.Address>(data)
        val value by transferFrom.decodeInputs<Value.BigNumber>(data)
        return Triple(from, to, value)
    }

    public fun allowance(
        owner: Value.Address,
        spender: Value.Address,
    ): FunctionCall<Value.BigNumber> =
        DefaultFunctionCall(
            encoder = { allowance.encodeInputs(owner, spender) },
            decoder = { allowance.decodeOutputs(it, "remaining").asBigNumber }
        )

    public fun approve(spender: Value.Address, value: Value.BigNumber): FunctionCall<Boolean> =
        DefaultFunctionCall(
            encoder = { approve.encodeInputs(spender, value) },
            decoder = { approve.decodeOutputs(it, "success").asBool.value }
        )

    public fun approve(data: ByteString): Pair<Value.Address, Value.BigNumber> {
        val spender by approve.decodeInputs<Value.Address>(data)
        val value by approve.decodeInputs<Value.BigNumber>(data)
        return spender to value
    }

    @Suppress("FunctionName")
    public fun TransferFilter(to: Value.Address, from: Value.Address): EventFilter<TransferEvent> =
        DefaultEventFilter(
            filter = { Transfer.encodeTopics(to, from) },
            decoder = { topics, data -> TransferEvent(topics, data) }
        )

    @Suppress("FunctionName")
    public fun ApproveFilter(to: Value.Address, from: Value.Address): EventFilter<ApproveEvent> =
        DefaultEventFilter(
            filter = { Approve.encodeTopics(to, from) },
            decoder = { topics, data -> ApproveEvent(topics, data) }
        )

    public class TransferEvent(topics: List<ByteString>, data: ByteString) {
        public val from: Value.Address by Transfer.decode(topics, data)
        public val to: Value.Address by Transfer.decode(topics, data)
        public val value: Value.BigNumber by Transfer.decode(topics, data)
    }

    public class ApproveEvent(topics: List<ByteString>, data: ByteString) {
        public val owner: Value.Address by Transfer.decode(topics, data)
        public val spender: Value.Address by Transfer.decode(topics, data)
        public val value: Value.BigNumber by Transfer.decode(topics, data)
    }
}
