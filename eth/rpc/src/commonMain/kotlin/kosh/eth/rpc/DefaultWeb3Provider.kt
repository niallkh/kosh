package kosh.eth.rpc

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value
import kosh.eth.rpc.Web3Provider.BlockTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import okio.ByteString

internal class DefaultWeb3Provider(
    private val jsonRpcClient: JsonRpcClient,
) : Web3Provider, JsonRpcClient by jsonRpcClient {

    override suspend fun chainId(): ULong = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_chainId"
        )
        json.decodeFromJsonElement(BigIntegerSerializer, result).ulongValue()
    }

    override suspend fun sendRawTransaction(
        encodedTransaction: ByteString,
    ): Hash = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_sendRawTransaction",
            json.encodeToJsonElement(ByteStringSerializer, encodedTransaction),
        )
        json.decodeFromJsonElement(result)
    }

    override suspend fun call(
        target: Value.Address?,
        data: ByteString,
        blockTag: BlockTag,
    ): ByteString = withContext(Dispatchers.Default) {
        val ethCall = EthCall(
            to = target,
            data = data,
        )
        val result = jsonRpcClient.call(
            method = "eth_call",
            json.encodeToJsonElement(ethCall),
            JsonPrimitive(blockTag.tag()),
        )
        json.decodeFromJsonElement(ByteStringSerializer, result)
    }

    override suspend fun estimateGas(
        sender: Value.Address,
        target: Value.Address?,
        value: BigInteger?,
        data: ByteString,
        gas: BigInteger?,
    ): ULong = withContext(Dispatchers.Default) {
        val ethCall = EthCall(
            from = sender,
            to = target,
            data = data,
            value = value,
            gas = gas,
        )

        val result = jsonRpcClient.call(
            method = "eth_estimateGas",
            json.encodeToJsonElement(ethCall),
            JsonPrimitive(BlockTag.Latest.tag()),
        )

        json.decodeFromJsonElement(BigIntegerSerializer, result).ulongValue()
    }

    override suspend fun blockNumber(): BigInteger = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_blockNumber",
        )
        json.decodeFromJsonElement(BigIntegerSerializer, result)
    }

    override suspend fun getBlockByNumber(
        blockTag: BlockTag,
    ): EthBlock = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getBlockByNumber",
            JsonPrimitive(blockTag.tag()),
            json.encodeToJsonElement(false),
        )
        json.decodeFromJsonElement(result)
    }

    override suspend fun getBlockByHash(hash: Hash): EthBlock = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getBlockByHash",
            JsonPrimitive("0x${hash.bytes.hex()}"),
            json.encodeToJsonElement(false),
        )
        json.decodeFromJsonElement(result)
    }

    override suspend fun getTransaction(
        blockNumber: ULong,
        transactionIndex: UInt,
    ): EthTransaction = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getTransactionByBlockNumberAndIndex",
            json.encodeToJsonElement("0x${blockNumber.toString(16)}"),
            json.encodeToJsonElement("0x${transactionIndex.toString(16)}"),
        )
        json.decodeFromJsonElement(result)
    }

    override suspend fun getTransactionReceipt(
        transactionHash: Hash,
    ): EthTransactionReceipt? = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getTransactionReceipt",
            json.encodeToJsonElement(ByteStringSerializer, transactionHash.bytes),
        )
        json.decodeFromJsonElement<EthTransactionReceipt?>(result)
    }

    override suspend fun getStorageAt(
        address: Value.Address,
        slot: BigInteger,
    ): ByteString = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getStorageAt",
            json.encodeToJsonElement(AddressSerializer, address),
            json.encodeToJsonElement(BigIntegerSerializer, slot),
            JsonPrimitive(BlockTag.Latest.tag()),
        )
        json.decodeFromJsonElement(ByteStringSerializer, result)
    }

    override suspend fun getCode(
        target: Value.Address,
    ): ByteString = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getCode",
            json.encodeToJsonElement(AddressSerializer, target),
            JsonPrimitive(BlockTag.Latest.tag()),
        )
        json.decodeFromJsonElement(ByteStringSerializer, result)
    }

    override suspend fun getNonce(
        address: Value.Address,
    ): ULong = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_getTransactionCount",
            json.encodeToJsonElement(AddressSerializer, address),
            JsonPrimitive(BlockTag.Latest.tag()),
        )
        json.decodeFromJsonElement(BigIntegerSerializer, result).ulongValue()
    }

    override suspend fun gasPrice(): BigInteger = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_gasPrice",
        )
        json.decodeFromJsonElement(BigIntegerSerializer, result)
    }

    override suspend fun maxPriorityFeePerGas(): BigInteger = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_maxPriorityFeePerGas",
        )
        json.decodeFromJsonElement(BigIntegerSerializer, result)
    }

    override suspend fun feeHistory(
        blockCount: UInt,
        newestBlock: BlockTag,
        rewardPercentiles: List<UInt>,
    ): FeeHistory = withContext(Dispatchers.Default) {
        val result = jsonRpcClient.call(
            method = "eth_feeHistory",
            JsonPrimitive(blockCount.toString()),
            JsonPrimitive(newestBlock.tag()),
            json.encodeToJsonElement(rewardPercentiles)
        )
        json.decodeFromJsonElement(FeeHistory.serializer(), result)
    }
}
