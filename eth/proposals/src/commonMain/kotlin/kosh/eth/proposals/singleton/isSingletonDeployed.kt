package kosh.eth.proposals.singleton

import kosh.eth.rpc.Web3Provider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.ByteString

public suspend fun Web3Provider.isSingletonDeployed(): Boolean = withContext(Dispatchers.Default) {
    getCode(SingletonAbi.address) != ByteString()
}
