package kosh.eth.proposals.erc4337

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.proposals.FunctionCall
import kosh.eth.proposals.call
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.ByteString

public suspend fun Bundler.createUserOperation(
    account: Value.Address,
    functionCall: FunctionCall<*>,
    priorityGasPrice: BigInteger,
    gasPrice: BigInteger,
    deployData: () -> ByteString,
    nonce: Value.BigNumber? = null,
    nonceKey: Value.BigNumber = Value.BigNumber(),
): UserOperation = withContext(Dispatchers.Default) {
    require(isEntryPointDeployed()) { "EntryPoint not deployed" }
    require(isEntryPointSupported()) { "EntryPoint not supported by bundler" }

    val nextNonce = call(
        target = EntryPointAbi.address,
        call = EntryPointAbi.getNonce(account, nonceKey)
    )

    if (nonce != null) {
        require(nonce.value >= nextNonce.value) { "Invalid nonce" }
    }

    val userOperation = UserOperation(
        sender = account,
        nonce = nonce ?: nextNonce,
        initCode = if (isAccountDeployed(account)) ByteString() else deployData(),
        callData = functionCall.encode(),
        callGasLimit = Value.BigNumber(),
        verificationGasLimit = Value.BigNumber(),
        preVerificationGas = Value.BigNumber(),
        maxFeePerGas = (gasPrice + priorityGasPrice).abi,
        maxPriorityFeePerGas = priorityGasPrice.abi,
        paymasterAndData = ByteString(),
        signature = Value.Bytes(),
    )

    val userOperationGas = estimateUserOperationGas(
        userOperation = userOperation,
        entryPoint = EntryPointAbi.address
    )

    userOperation.copy(
        preVerificationGas = userOperationGas.preVerificationGas.abi,
        verificationGasLimit = userOperationGas.verificationGasLimit.abi,
        callGasLimit = userOperationGas.callGasLimit.abi,
        signature = Value.Bytes(),
    )
}
