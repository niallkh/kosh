import coder.decodeOutputs
import coder.encodeInputs
import dsl.abiFunction
import dsl.abiPayableFunction
import dsl.abiViewFunction
import okio.ByteString.Companion.decodeHex

public object EntryPointAbi {
    public val address: Value.Address =
        Value.Address("5FF137D4b0FDCD49DcA30c7CF57E578a026d2789".decodeHex())

    private val getNonce by abiViewFunction(
        inputs = { address("sender"); uint192("key"); },
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
        address("withdrawAddress"); uint256("withdrawAmount")
    }

    public fun getNonce(
        sender: Value.Address,
        key: Value.BigNumber,
    ): FunctionCall<Value.BigNumber> = DefaultFunctionCall(
        encoder = { getNonce.encodeInputs(sender, key) },
        decoder = { getNonce.decodeOutputs(it, "nonce").asBigNumber }
    )

    public fun balanceOf(
        account: Value.Address,
    ): FunctionCall<Value.BigNumber> = DefaultFunctionCall(
        encoder = { balanceOf.encodeInputs(account) },
        decoder = { balanceOf.decodeOutputs(it, "balance").asBigNumber }
    )

    public fun depositTo(
        account: Value.Address,
    ): FunctionCall<Unit> = DefaultFunctionCall(
        encoder = { depositTo.encodeInputs(account) },
        decoder = { }
    )

    public fun withdrawTo(
        withdrawAddress: Value.Address,
        withdrawAmount: Value.BigNumber,
    ): FunctionCall<Unit> = DefaultFunctionCall(
        encoder = { withdrawTo.encodeInputs(withdrawAddress, withdrawAmount) },
        decoder = {}
    )
}

//public val EntryPoint.UserOperation.gasLimit: BigInteger
//    get() = preVerificationGas + verificationGasLimit + callGasLimit