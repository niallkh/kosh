import coder.encodeData
import okio.ByteString

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
    val signature: Value.Bytes = Value.Bytes.EMPTY,
)

public fun UserOperation.userOpHash(
    chainId: ULong,
    entryPoint: Value.Address,
): ByteString = encodeData(
    Type.tuple {
        bytes32()
        address()
        uint256()
    },
    encode().keccak256().abi,
    entryPoint,
    chainId.abi
).keccak256()

private fun UserOperation.encode(): ByteString = encodeData(
    Type.tuple {
        address()
        uint256()
        bytes32()
        bytes32()
        uint256()
        uint256()
        uint256()
        uint256()
        uint256()
        bytes32()
    },
    sender,
    nonce,
    initCode.keccak256().abi,
    callData.keccak256().abi,
    callGasLimit,
    verificationGasLimit,
    preVerificationGas,
    maxFeePerGas,
    maxPriorityFeePerGas,
    paymasterAndData.keccak256().abi,
)

