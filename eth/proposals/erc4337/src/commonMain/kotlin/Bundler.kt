public interface Bundler : Web3Provider {

    public suspend fun sendUserOperation(
        userOperation: UserOperation,
        entryPoint: Address = EntryPointAbi.address.toRpcAddress,
    ): Hash

    public suspend fun estimateUserOperationGas(
        userOperation: UserOperation,
        entryPoint: Address,
    ): UserOperationGas

    public suspend fun getUserOperationReceipt(
        userOpHash: Hash,
    ): UserOperationReceipt

    public suspend fun supportedEntryPoints(): List<Address>
}


