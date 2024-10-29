package kosh.domain.repositories

import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.models.token.NftExtendedMetadata
import kosh.domain.models.token.NftMetadata
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either

interface TokenRepo : Repository {

    suspend fun getTokenMetadata(
        chainId: ChainId,
        address: Address,
    ): Either<Web3Failure, TokenMetadata?>

    suspend fun getNftMetadata(
        chainId: ChainId,
        address: Address,
        tokenId: BigInteger,
        type: TokenMetadata.Type,
        refresh: Boolean = false,
    ): Either<Web3Failure, NftMetadata?>

    suspend fun getNftMetadata(
        uri: Uri,
        tokenId: BigInteger,
        refresh: Boolean = false,
    ): Either<Web3Failure, NftExtendedMetadata?>
}
