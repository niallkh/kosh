package kosh.eth.proposals.multicall

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.abi
import kosh.eth.proposals.ContractCall
import kosh.eth.proposals.call
import kosh.eth.rpc.Web3Provider
import kosh.eth.rpc.Web3Provider.BlockTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.ByteString

public suspend fun Web3Provider.multicall(
    vararg contractCalls: ContractCall<Any>,
): Pair<BigInteger, ByteString> = withContext(Dispatchers.Default) {
    val calls = contractCalls.map { call -> MulticallAbi.Call(call.target, call.encode().abi) }

    val tryBlockAndAggregate = MulticallAbi.tryBlockAndAggregate(
        requireSuccess = false,
        calls = calls,
    )

    val result = call(
        target = MulticallAbi.address,
        call = tryBlockAndAggregate,
    )

    val returnData = result.returnData()

    if (returnData != null) {
        contractCalls.zip(returnData).map { (call, result) ->
            if (result.success.value) {
                call.decodeResult(result.data.value)
            } else {
                call.decodeError(result.data.value)
            }
        }

        result.blockNumber.value to result.blockHash.value
    } else {
        val block = getBlockByNumber(BlockTag.Latest)

        contractCalls.forEach { contractCall ->
            call(
                call = contractCall,
                blockTag = BlockTag.Hash(block.hash)
            )
        }

        block.number to block.hash.bytes
    }
}

