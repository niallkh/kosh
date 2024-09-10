import com.ionspin.kotlin.bignum.integer.BigInteger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.ByteString

public suspend fun Bundler.createUserOperation(
    account: Value.Address,
    functionCall: FunctionCall<*>,
    priorityGasPrice: BigInteger,
    gasPrice: BigInteger,
    deployData: () -> ByteString,
    nonce: Value.BigNumber? = null,
    nonceKey: Value.BigNumber = Value.BigNumber.ZERO,
): UserOperation = withContext(Dispatchers.Default) {
    require(isEntryPointDeployed()) { "EntryPoint not deployed" }
    require(isEntryPointSupported()) { "EntryPoint not supported by bundler" }

    val nextNonce = call(
        target = EntryPointAbi.address.toRpcAddress,
        call = EntryPointAbi.getNonce(account, nonceKey)
    )

    if (nonce != null) {
        require(nonce.value >= nextNonce.value) { "Invalid nonce" }
    }

    val userOperation = UserOperation(
        sender = account,
        nonce = nonce ?: nextNonce,
        initCode = if (isAccountDeployed(account)) ByteString.EMPTY else deployData(),
        callData = functionCall.encode(),
        callGasLimit = Value.BigNumber.ZERO,
        verificationGasLimit = Value.BigNumber.ZERO,
        preVerificationGas = Value.BigNumber.ZERO,
        maxFeePerGas = (gasPrice + priorityGasPrice).abi,
        maxPriorityFeePerGas = priorityGasPrice.abi,
        paymasterAndData = ByteString.EMPTY,
        signature = Value.Bytes.EMPTY,
    )

    val userOperationGas = estimateUserOperationGas(
        userOperation = userOperation,
        entryPoint = EntryPointAbi.address.toRpcAddress
    )

    userOperation.copy(
        preVerificationGas = userOperationGas.preVerificationGas.abi,
        verificationGasLimit = userOperationGas.verificationGasLimit.abi,
        callGasLimit = userOperationGas.callGasLimit.abi,
        signature = Value.Bytes.EMPTY,
    )
}
