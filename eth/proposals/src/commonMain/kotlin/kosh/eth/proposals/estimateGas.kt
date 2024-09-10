package kosh.eth.proposals

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.eth.abi.Value.Address
import kosh.eth.rpc.Web3Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

public suspend fun Web3Provider.estimateGas(
    sender: Address,
    target: Address,
    value: BigInteger?,
    call: FunctionCall<*>,
    gas: BigInteger?,
): ULong = withContext(Dispatchers.Default) {
    estimateGas(
        sender = sender,
        target = target,
        value = value,
        data = call.encode(),
        gas = gas,
    )
}

public suspend fun Web3Provider.estimateGas(
    sender: Address,
    value: BigInteger?,
    call: ContractCall<*>,
    gas: BigInteger?,
): ULong = withContext(Dispatchers.Default) {
    estimateGas(
        sender = sender,
        target = call.target,
        value = value,
        data = call.encode(),
        gas = gas
    )
}
