package kosh.data.web3

import co.touchlab.kermit.Logger
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.toLibUri
import kosh.domain.repositories.NetworkRepo
import kosh.eth.rpc.Web3ProviderFactory

class DefaultNetworkRepo(
    private val web3ProviderFactory: Web3ProviderFactory,
) : NetworkRepo {

    private val logger = Logger.withTag("NetworkRepo")

    override suspend fun validateRpc(
        chainId: ChainId,
        rpc: Uri,
    ): Boolean {
        val web3Provider = web3ProviderFactory(rpc.toLibUri())
        return runCatching { web3Provider.chainId() == chainId.value }
            .getOrElse {
                logger.i(it) { "Rpc url is invalid" }
                false
            }
    }
}
