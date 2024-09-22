package kosh.domain.usecases.network

import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.NetworkFailure
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.serializers.Either

interface NetworkService {

    suspend fun getRpc(
        id: NetworkEntity.Id,
        write: Boolean = false,
    ): Uri

    suspend fun isActive(chainId: ChainId): Boolean

    suspend fun add(
        chainId: ChainId,
        name: String,
        readRpcProvider: Uri,
        writeRpcProvider: Uri?,
        explorers: List<Uri>,
        icon: Uri?,
        tokenName: String,
        tokenSymbol: String,
        tokenIcon: Uri?,
    ): Either<NetworkFailure, NetworkEntity.Id>

    suspend fun update(
        id: NetworkEntity.Id,
        name: String,
        readRpcProvider: Uri,
        writeRpcProvider: Uri?,
        explorers: List<Uri>,
        icon: Uri?,
        tokenName: String,
        tokenSymbol: String,
        tokenIcon: Uri?,
    ): Either<NetworkFailure, Unit>

    fun toggle(id: NetworkEntity.Id, enabled: Boolean)

    fun delete(id: NetworkEntity.Id)
}
