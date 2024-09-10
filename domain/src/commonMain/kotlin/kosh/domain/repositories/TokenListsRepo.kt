package kosh.domain.repositories

import kosh.domain.models.ChainId
import kosh.domain.models.token.TokenMetadata
import kotlinx.coroutines.flow.Flow

interface TokenListsRepo : Repository {

    suspend fun tokens(
        chainId: ChainId,
    ): Flow<TokenMetadata>
}
