package kosh.domain.usecases.token

import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.serializers.Uri
import kotlinx.coroutines.flow.Flow

interface TokenDiscoveryService {

    suspend fun getVerifiedTokens(): Flow<TokenMetadata>

    suspend fun searchToken(
        query: String,
    ): Either<Web3Failure, List<TokenMetadata>>

    suspend fun searchNft(
        token: TokenMetadata,
        query: String,
    ): Either<Web3Failure, NftMetadata?>

    suspend fun getTokenMetadata(
        networkId: NetworkEntity.Id,
        address: Address,
    ): Either<Web3Failure, TokenMetadata?>

    suspend fun getNftMetadata(
        networkId: NetworkEntity.Id,
        address: Address,
        tokenId: BigInteger,
        type: TokenMetadata.Type,
        refresh: Boolean = false,
    ): Either<Web3Failure, NftMetadata?>

    suspend fun getNftExtendedMetadata(
        uri: Uri,
        refresh: Boolean = false,
    ): Either<Web3Failure, NftExtendedMetadata?>
}
