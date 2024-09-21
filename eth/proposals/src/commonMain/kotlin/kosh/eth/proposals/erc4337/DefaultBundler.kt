package kosh.eth.proposals.erc4337

import kosh.eth.abi.Value
import kosh.eth.proposals.eip55.eip55
import kosh.eth.rpc.Hash
import kosh.eth.rpc.Web3Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement

public class DefaultBundler internal constructor(
    private val web3Provider: Web3Provider,
) : Bundler, Web3Provider by web3Provider {

    public override suspend fun sendUserOperation(
        userOperation: UserOperation,
        entryPoint: Value.Address,
    ): Hash = withContext(Dispatchers.Default) {
        require(userOperation.signature != Value.Bytes())
        val result = call(
            method = "eth_sendUserOperation",
            json.encodeToJsonElement(userOperation),
            json.encodeToJsonElement(entryPoint.eip55()),
        )
        json.decodeFromJsonElement(Hash.serializer(), result)
    }

    public override suspend fun estimateUserOperationGas(
        userOperation: UserOperation,
        entryPoint: Value.Address,
    ): UserOperationGas = withContext(Dispatchers.Default) {
        val result = call(
            method = "eth_estimateUserOperationGas",
            json.encodeToJsonElement(userOperation),
            json.encodeToJsonElement(entryPoint.eip55()),
        )
        json.decodeFromJsonElement(result)
    }

    public override suspend fun getUserOperationReceipt(
        userOpHash: Hash,
    ): UserOperationReceipt = withContext(Dispatchers.Default) {
        val result = call(
            method = "eth_getUserOperationReceipt",
            json.encodeToJsonElement(Hash.serializer(), userOpHash),
        )
        json.decodeFromJsonElement(result)
    }

    public override suspend fun supportedEntryPoints(): List<Value.Address> =
        withContext(Dispatchers.Default) {
            val result = call(
                method = "eth_supportedEntryPoints",
            )
            json.decodeFromJsonElement(ListSerializer(String.serializer()), result)
                .map { Value.Address(it) }
        }
}
