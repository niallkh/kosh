package kosh.eth.rpc

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kosh.eth.rpc.Web3Provider.BlockTag
import okio.ByteString
import kotlin.jvm.JvmInline

public interface Web3Provider : JsonRpcClient {

    public suspend fun chainId(): ULong

    public suspend fun sendRawTransaction(encodedTransaction: ByteString): Hash

    public suspend fun call(
        target: Value.Address?,
        data: ByteString,
        blockTag: BlockTag = BlockTag.Latest,
    ): ByteString

    public suspend fun estimateGas(
        sender: Value.Address,
        target: Value.Address?,
        value: BigInteger?,
        data: ByteString,
        gas: BigInteger?,
    ): ULong

    public suspend fun blockNumber(): BigInteger

    public suspend fun getBlockByNumber(blockTag: BlockTag): EthBlock

    public suspend fun getBlockByHash(hash: Hash): EthBlock

    public suspend fun getNonce(address: Value.Address): ULong

    public suspend fun getTransaction(blockNumber: ULong, transactionIndex: UInt): EthTransaction

    public suspend fun getTransactionReceipt(transactionHash: Hash): EthTransactionReceipt?

    public suspend fun getStorageAt(address: Value.Address, slot: BigInteger): ByteString

    public suspend fun getCode(target: Value.Address): ByteString

    public suspend fun gasPrice(): BigInteger

    public suspend fun maxPriorityFeePerGas(): BigInteger

    public suspend fun feeHistory(
        blockCount: UInt = 4u,
        newestBlock: BlockTag = BlockTag.Pending,
        rewardPercentiles: List<UInt> = emptyList(),
    ): FeeHistory

    public sealed interface BlockTag {

        @JvmInline
        public value class Number(public val number: ULong) : BlockTag

        @JvmInline
        public value class Hash(public val value: kosh.eth.rpc.Hash) : BlockTag
        public data object Latest : BlockTag
        public data object Pending : BlockTag
    }
}

internal fun BlockTag.tag() = when (this) {
    BlockTag.Latest -> "latest"
    BlockTag.Pending -> "pending"
    is BlockTag.Number -> "0x${number.toString(16)}"
    is BlockTag.Hash -> "0x${value.bytes.hex()}"
}

