package kosh.eth.proposals.erc4337

import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.abi.coder.AbiType
import kosh.eth.abi.coder.encode
import kosh.eth.abi.keccak256
import kotlinx.io.bytestring.ByteString

public data class UserOperation(
    val sender: Value.Address,
    val nonce: Value.BigNumber,
    val initCode: ByteString,
    val callData: ByteString,
    val callGasLimit: Value.BigNumber,
    val verificationGasLimit: Value.BigNumber,
    val preVerificationGas: Value.BigNumber,
    val maxFeePerGas: Value.BigNumber,
    val maxPriorityFeePerGas: Value.BigNumber,
    val paymasterAndData: ByteString,
    val signature: Value.Bytes = Value.Bytes(),
)

public fun UserOperation.userOpHash(
    chainId: ULong,
    entryPoint: Value.Address,
): ByteString = AbiType.tuple {
    bytes32("userOp")
    address("entryPoint")
    uint256("chainId")
}.encode(
    "userOp" to encode().keccak256().abi,
    "entryPoint" to entryPoint,
    "chainId" to chainId.abi
).keccak256()

private fun UserOperation.encode(): ByteString = AbiType.tuple {
    address("sender")
    uint256("nonce")
    bytes32("initCode")
    bytes32("data")
    uint256("gasLimit")
    uint256("verificationGasLimit")
    uint256("preVerificationGas")
    uint256("maxFeePerGas")
    uint256("maxPriorityFeePerGas")
    bytes32("paymasterAndData")
}.encode(
    "sender" to sender,
    "nonce" to nonce,
    "initCode" to initCode.keccak256().abi,
    "data" to callData.keccak256().abi,
    "gasLimit" to callGasLimit,
    "verificationGasLimit" to verificationGasLimit,
    "preVerificationGas" to preVerificationGas,
    "maxFeePerGas" to maxFeePerGas,
    "maxPriorityFeePerGas" to maxPriorityFeePerGas,
    "paymasterAndData" to paymasterAndData.keccak256().abi,
)

