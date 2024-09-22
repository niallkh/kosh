package kosh.domain.usecases.token

import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.entities.TokenEntity.Type
import kosh.domain.failure.TokenFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kotlinx.coroutines.flow.Flow

interface TokenService {

    fun get(id: TokenEntity.Id): Flow<TokenEntity?>

    fun getNativeToken(id: NetworkEntity.Id): Flow<TokenEntity?>

    suspend fun add(
        chainId: ChainId,
        address: Address?,
        tokenId: BigInteger?,
        name: String,
        symbol: String,
        decimals: UByte,
        type: Type,
        tokenName: String?,
        icon: Uri?,
        uri: Uri?,
        external: Uri?,
        image: Uri?,
    ): Either<TokenFailure, TokenEntity.Id>

    suspend fun update(
        id: TokenEntity.Id,
        name: String,
        symbol: String,
        decimals: UByte,
        type: Type,
        tokenName: String?,
        icon: Uri?,
        uri: Uri?,
        external: Uri?,
        image: Uri?,
    ): Either<TokenFailure, Unit>

    fun delete(id: TokenEntity.Id)
}

