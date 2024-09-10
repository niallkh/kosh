@file:UseSerializers(ByteStringSerializer::class, BigIntegerSerializer::class)

import com.ionspin.kotlin.bignum.integer.BigInteger
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
public data class UserOperationGas(
    val preVerificationGas: BigInteger,
    val verificationGasLimit: BigInteger,
    val callGasLimit: BigInteger,
)

@Serializable
public data class UserOperationReceipt(
    val userOpHash: Hash,
    val sender: Address,
    val paymaster: Address? = null,
    val nonce: BigInteger,
    val success: Boolean,
    val actualGasCost: BigInteger,
    val actualGasUsed: BigInteger,
    val reason: String? = null,
    val receipt: EthTransactionReceipt,
)
