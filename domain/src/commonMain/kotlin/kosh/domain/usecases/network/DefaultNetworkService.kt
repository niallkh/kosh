package kosh.domain.usecases.network

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.explorers
import kosh.domain.entities.modifiedAt
import kosh.domain.entities.name
import kosh.domain.entities.networkId
import kosh.domain.entities.nullableIcon
import kosh.domain.entities.nullableWriteRpcProvider
import kosh.domain.entities.readRpcProvider
import kosh.domain.entities.symbol
import kosh.domain.failure.NetworkFailure
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.NetworkRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.state
import kosh.domain.state.AppState
import kosh.domain.state.enabledNetworkIds
import kosh.domain.state.nativeToken
import kosh.domain.state.network
import kosh.domain.state.networks
import kosh.domain.state.optionalNetwork
import kosh.domain.state.optionalToken
import kosh.domain.state.token
import kosh.domain.state.tokens
import kosh.domain.state.transactions
import kosh.domain.utils.phset
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.datetime.Clock

class DefaultNetworkService(
    private val appStateRepo: AppStateRepo,
    private val networkRepo: NetworkRepo,
) : NetworkService {

    override suspend fun isActive(chainId: ChainId): Boolean {
        return NetworkEntity.Id(chainId) in appStateRepo.state().enabledNetworkIds
    }

    override suspend fun getRpc(id: NetworkEntity.Id, write: Boolean): Uri {
        val network = AppState.network(id).get(appStateRepo.state())
        val rpc = if (write) network?.writeRpcProvider ?: network?.readRpcProvider
        else network?.readRpcProvider
        return rpc ?: error("No web3 provider for chain: $id")
    }

    override suspend fun add(
        chainId: ChainId,
        name: String,
        readRpcProvider: Uri,
        writeRpcProvider: Uri?,
        explorers: List<Uri>,
        icon: Uri?,
        tokenName: String,
        tokenSymbol: String,
        tokenIcon: Uri?,
    ) = either {
        ensure(networkRepo.validateRpc(chainId, readRpcProvider)) {
            NetworkFailure.InvalidRpcProvider()
        }

        if (writeRpcProvider != null) {
            ensure(networkRepo.validateRpc(chainId, writeRpcProvider)) {
                NetworkFailure.InvalidRpcProvider()
            }
        }

        val network = NetworkEntity(
            chainId = chainId,
            name = name,
            readRpcProvider = readRpcProvider,
            writeRpcProvider = writeRpcProvider,
            explorers = explorers.toPersistentList(),
            icon = icon,
        )

        val token = TokenEntity(
            networkId = network.id,
            name = tokenName,
            symbol = tokenSymbol,
            decimals = 18u,
            type = TokenEntity.Type.Native,
            icon = tokenIcon,
        )

        appStateRepo.modify {
            ensure(network.id !in AppState.networks.get()) {
                NetworkFailure.AlreadyExist()
            }
            ensure(token.id !in AppState.tokens.get()) {
                NetworkFailure.AlreadyExist()
            }

            AppState.network(network.id) set network
            AppState.enabledNetworkIds.at(At.phset(), network.id) set true
            AppState.token(token.id) set token
        }

        network.id
    }

    override suspend fun update(
        id: NetworkEntity.Id,
        name: String,
        readRpcProvider: Uri,
        writeRpcProvider: Uri?,
        explorers: List<Uri>,
        icon: Uri?,
        tokenName: String,
        tokenSymbol: String,
        tokenIcon: Uri?,
    ) = either {
        val appState = appStateRepo.state()
        val chainId = AppState.network(id).get(appState)?.chainId
            ?: raise(NetworkFailure.NotFound())

        ensure(networkRepo.validateRpc(chainId, readRpcProvider)) {
            NetworkFailure.InvalidRpcProvider()
        }

        if (writeRpcProvider != null) {
            ensure(networkRepo.validateRpc(chainId, writeRpcProvider)) {
                NetworkFailure.InvalidRpcProvider()
            }
        }

        appStateRepo.modify {
            ensure(id in AppState.networks.get()) {
                NetworkFailure.NotFound()
            }
            val token = AppState.token(TokenEntity.Id(id)).get() ?: raise(NetworkFailure.NotFound())
            ensure(token.type == TokenEntity.Type.Native) {
                NetworkFailure.NotFound()
            }

            inside(AppState.optionalNetwork(id)) {
                NetworkEntity.name set name
                NetworkEntity.readRpcProvider set readRpcProvider
                NetworkEntity.nullableWriteRpcProvider set writeRpcProvider
                NetworkEntity.explorers set explorers
                NetworkEntity.nullableIcon set icon
                NetworkEntity.modifiedAt set Clock.System.now()
            }

            inside(AppState.optionalToken(token.id)) {
                TokenEntity.name set tokenName
                TokenEntity.symbol set tokenSymbol
                TokenEntity.nullableIcon set tokenIcon
                TokenEntity.modifiedAt set Clock.System.now()
            }
        }
    }

    override fun toggle(id: NetworkEntity.Id, enabled: Boolean) {
        appStateRepo.modify {
            if (enabled) {
                AppState.enabledNetworkIds.at(At.phset(), id) set true
            } else {
                AppState.enabledNetworkIds.at(At.phset(), id) set false
            }
        }
    }

    override fun delete(id: NetworkEntity.Id) {
        appStateRepo.modify {

            AppState.network(id) set null

            AppState.enabledNetworkIds.at(At.phset(), id) set false

            AppState.nativeToken(id) set null

            AppState.tokens transform { map ->
                map.filter { it.value.networkId != id }.toPersistentMap()
            }

            AppState.transactions transform { map ->
                map.filter { it.value.networkId != id }.toPersistentMap()
            }
        }
    }
}
