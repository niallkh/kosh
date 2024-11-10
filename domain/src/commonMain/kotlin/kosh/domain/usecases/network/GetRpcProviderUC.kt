package kosh.domain.usecases.network

import kosh.domain.entities.NetworkEntity
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.repositories.AppStateRepo
import kosh.domain.state.AppState
import kosh.domain.state.network

fun interface GetRpcProvidersUC {
    suspend operator fun invoke(
        chainId: ChainId,
        write: Boolean,
    ): Uri
}

fun getRpcProvidersUC(appStateRepo: AppStateRepo) = GetRpcProvidersUC { chainId, write ->
    val state = appStateRepo.state
    val id = NetworkEntity.Id(chainId)
    val network = AppState.network(id).get(state)
    if (write) {
        network?.writeRpcProvider ?: network?.readRpcProvider
    } else {
        network?.readRpcProvider
    } ?: error("No web3 provider for chain: $chainId")
}

suspend operator fun GetRpcProvidersUC.invoke(chainId: ChainId) = invoke(chainId, false)
