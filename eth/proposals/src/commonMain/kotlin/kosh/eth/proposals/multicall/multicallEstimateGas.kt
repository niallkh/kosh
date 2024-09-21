package kosh.eth.proposals.multicall

import kosh.eth.abi.Value
import kosh.eth.abi.abi
import kosh.eth.proposals.ContractCall
import kosh.eth.rpc.Web3Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public suspend fun Web3Provider.multicallEstimateGas(
    vararg contractCalls: ContractCall<*>,
): ULong = withContext(Dispatchers.Default) {
    val calls = contractCalls.map { call -> MulticallAbi.Call(call.target, call.encode().abi) }

    estimateGas(
        sender = Value.Address(),
        target = MulticallAbi.address,
        data = MulticallAbi.tryBlockAndAggregate(requireSuccess = false, calls).encode(),
        gas = null,
        value = null,
    )
}
