@file:UseSerializers(
    ByteStringSerializer::class,
    BigIntegerSerializer::class,
    AddressSerializer::class
)

package kosh.eth.rpc

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import okio.ByteString
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
public value class Hash private constructor(public val bytes: ByteString) {
    public companion object {
        public operator fun invoke(byteString: ByteString): Hash {
            require(byteString.size == 32)
            return Hash(byteString)
        }
    }
}

@Serializable
public data class EthCall(
    val from: Value.Address? = null,
    val to: Value.Address? = null,
    val gas: BigInteger? = null,
    val gasPrice: BigInteger? = null,
    val value: BigInteger? = null,
    val data: ByteString,
)

@Serializable
public data class EthBlock(
    val hash: Hash,
    val gasLimit: BigInteger,
    val gasUsed: BigInteger,
    val parentHash: Hash,
    val number: BigInteger,
    val timestamp: BigInteger,
    val miner: Value.Address,
    val size: BigInteger,
    val baseFeePerGas: BigInteger? = null,
)

@Serializable
public data class EthTransaction(
    val hash: Hash,
    val blockHash: Hash,
    val blockNumber: BigInteger,
    val transactionIndex: BigInteger,
    val from: Value.Address,
    val to: Value.Address?,
    val nonce: BigInteger,
    val value: BigInteger,
    val gas: BigInteger,
    val gasPrice: BigInteger,
    val input: ByteString,
)

@Serializable
public data class EthTransactionReceipt(
    val transactionHash: Hash,
    val blockHash: Hash,
    val contractAddress: Value.Address? = null,
    val blockNumber: BigInteger,
    val from: Value.Address,
    val to: Value.Address? = null,
    val transactionIndex: BigInteger,
    val status: BigInteger? = null,
    val type: BigInteger? = null,
    val cumulativeGasUsed: BigInteger,
    val gasUsed: BigInteger,
    val effectiveGasPrice: BigInteger,
    val logs: List<EthLog>,
)

@Serializable
public data class TraceTransaction(
    val blockHash: Hash,
    val blockNumber: BigInteger,
    val transactionHash: Hash,
    val transactionPosition: ByteString,
    val traceByteString: List<Int>,
    val subtraces: Int,
    val type: String,
    val result: TraceResult,
    val action: TraceAction,
)

@Serializable
public data class TraceResult(
    val gasUsed: BigInteger,
    val output: ByteString,
)

@Serializable
public data class TraceAction(
    val callType: String,
    val from: Value.Address,
    val to: Value.Address,
    val input: ByteString,
    val value: BigInteger,
    val gas: BigInteger,
)

@Serializable
public data class EthLog(
    val blockHash: Hash,
    val transactionHash: Hash,
    val blockNumber: BigInteger,
    val transactionIndex: BigInteger,
    val logIndex: BigInteger,
    val address: Value.Address,
    val data: ByteString,
    val topics: List<ByteString>,
    val removed: Boolean = false,
)

@Serializable
public data class FeeHistory(
    val oldestBlock: BigInteger,
    val reward: List<List<BigInteger>> = emptyList(),
    val baseFeePerGas: List<BigInteger> = emptyList(),
    val gasUsedRatio: List<Double> = emptyList(),
    val baseFeePerBlobGas: List<BigInteger> = emptyList(),
    val blobGasUsedRatio: List<Double> = emptyList(),
)
