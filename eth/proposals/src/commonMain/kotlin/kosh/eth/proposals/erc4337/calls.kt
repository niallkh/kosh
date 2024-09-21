package kosh.eth.proposals.erc4337

import kosh.eth.abi.Value
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.ByteString

public suspend fun Bundler.isEntryPointDeployed(): Boolean = withContext(Dispatchers.Default) {
    getCode(EntryPointAbi.address) != ByteString()
}

public suspend fun Bundler.isEntryPointSupported(): Boolean = withContext(Dispatchers.Default) {
    val entryPoint = EntryPointAbi.address
    supportedEntryPoints().any { it == entryPoint }
}

public suspend fun Bundler.isAccountDeployed(account: Value.Address): Boolean =
    withContext(Dispatchers.Default) {
        getCode(account) != ByteString()
    }
