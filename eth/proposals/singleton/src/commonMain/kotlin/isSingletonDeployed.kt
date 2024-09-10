import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.ByteString

public suspend fun Web3Provider.isSingletonDeployed(): Boolean = withContext(Dispatchers.Default) {
    getCode(SingletonAbi.address.toRpcAddress) != ByteString.EMPTY
}
