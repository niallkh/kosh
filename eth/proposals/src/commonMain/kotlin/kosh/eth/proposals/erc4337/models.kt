@file:UseSerializers(
    ByteStringSerializer::class,
    BigIntegerSerializer::class,
    AddressSerializer::class
)

package kosh.eth.proposals.erc4337

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kosh.eth.rpc.AddressSerializer
import kosh.eth.rpc.BigIntegerSerializer
import kosh.eth.rpc.ByteStringSerializer
import kosh.eth.rpc.EthTransactionReceipt
import kosh.eth.rpc.Hash
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
    val sender: Value.Address,
    val paymaster: Value.Address? = null,
    val nonce: BigInteger,
    val success: Boolean,
    val actualGasCost: BigInteger,
    val actualGasUsed: BigInteger,
    val reason: String? = null,
    val receipt: EthTransactionReceipt,
)
