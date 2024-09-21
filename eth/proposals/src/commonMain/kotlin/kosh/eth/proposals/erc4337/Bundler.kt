package kosh.eth.proposals.erc4337

import kosh.eth.abi.Value
import kosh.eth.rpc.Hash
import kosh.eth.rpc.Web3Provider

public interface Bundler : Web3Provider {

    public suspend fun sendUserOperation(
        userOperation: UserOperation,
        entryPoint: Value.Address = EntryPointAbi.address,
    ): Hash

    public suspend fun estimateUserOperationGas(
        userOperation: UserOperation,
        entryPoint: Value.Address,
    ): UserOperationGas

    public suspend fun getUserOperationReceipt(
        userOpHash: Hash,
    ): UserOperationReceipt

    public suspend fun supportedEntryPoints(): List<Value.Address>
}


