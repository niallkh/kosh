package kosh.eth.proposals.erc4337

import kosh.eth.abi.Value
import kosh.eth.abi.abiBigNumber
import kosh.eth.abi.coder.decode
import kosh.eth.abi.coder.encode
import kosh.eth.abi.dsl.abiFunction
import kosh.eth.abi.dsl.abiPayableFunction
import kosh.eth.abi.dsl.abiViewFunction
import kosh.eth.proposals.DefaultFunctionCall
import kosh.eth.proposals.FunctionCall
import kotlinx.io.bytestring.hexToByteString

public object EntryPointAbi {
    public val address: Value.Address =
        Value.Address("5FF137D4b0FDCD49DcA30c7CF57E578a026d2789".hexToByteString())

    private val getNonce by abiViewFunction(
        inputs = {
            address("sender")
            uint192("key")
        },
        outputs = { uint256("nonce") },
    )

    private val balanceOf by abiViewFunction(
        inputs = { address("account") },
        outputs = { uint256("balance") },
    )

    private val depositTo by abiPayableFunction(
        inputs = { address("account") }
    )

    private val withdrawTo by abiFunction {
        address("withdrawAddress")
        uint256("withdrawAmount")
    }

    public fun getNonce(
        sender: Value.Address,
        key: Value.BigNumber,
    ): FunctionCall<Value.BigNumber> = DefaultFunctionCall(
        encoder = { getNonce.encode("sender" to sender, "key" to key) },
        decoder = { getNonce.decode(it).getValue("nonce").abiBigNumber }
    )

    public fun balanceOf(
        account: Value.Address,
    ): FunctionCall<Value.BigNumber> = DefaultFunctionCall(
        encoder = { balanceOf.encode("account" to account) },
        decoder = { balanceOf.decode(it).getValue("balance").abiBigNumber }
    )

    public fun depositTo(
        account: Value.Address,
    ): FunctionCall<Unit> = DefaultFunctionCall(
        encoder = { depositTo.encode("account" to account) },
        decoder = { }
    )

    public fun withdrawTo(
        withdrawAddress: Value.Address,
        withdrawAmount: Value.BigNumber,
    ): FunctionCall<Unit> = DefaultFunctionCall(
        encoder = {
            withdrawTo.encode(
                "withdrawAddress" to withdrawAddress,
                "withdrawAmount" to withdrawAmount
            )
        },
        decoder = {}
    )
}
