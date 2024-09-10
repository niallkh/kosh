import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.ByteString

public suspend fun Bundler.isEntryPointDeployed(): Boolean = withContext(Dispatchers.Default) {
    getCode(EntryPointAbi.address.toRpcAddress) != ByteString.EMPTY
}

public suspend fun Bundler.isEntryPointSupported(): Boolean = withContext(Dispatchers.Default) {
    val entryPoint = EntryPointAbi.address.toRpcAddress
    supportedEntryPoints().any { it == entryPoint }
}

public suspend fun Bundler.isAccountDeployed(account: Value.Address): Boolean =
    withContext(Dispatchers.Default) {
        getCode(account.toRpcAddress) != ByteString.EMPTY
    }
