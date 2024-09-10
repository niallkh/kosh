package kosh.eth.proposals

import kosh.eth.abi.Value.Address
import kosh.eth.rpc.Web3Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public suspend fun <T : Any> Web3Provider.call(
    target: Address,
    call: FunctionCall<T>,
): T = withContext(Dispatchers.Default) {
    val result = call(
        target = target,
        data = call.encode()
    )
    call.decode(result)
}

public suspend fun <T : Any> Web3Provider.call(
    call: ContractCall<T>,
    blockTag: Web3Provider.BlockTag = Web3Provider.BlockTag.Latest,
): Result<T> = withContext(Dispatchers.Default) {
    val result = call(
        target = call.target,
        data = call.encode(),
        blockTag = blockTag,
    )
    call.decodeResult(result)
    call.result
}
