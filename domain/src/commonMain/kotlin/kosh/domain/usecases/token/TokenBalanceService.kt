package kosh.domain.usecases.token

import arrow.core.Ior
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.token.AccountBalance
import kosh.domain.models.token.BalanceFilters
import kosh.domain.models.token.TokenBalance
import kosh.domain.serializers.ImmutableList
import kosh.domain.serializers.Nel
import kotlinx.coroutines.flow.Flow

interface TokenBalanceService {

    fun getBalances(filters: BalanceFilters): Flow<ImmutableList<TokenBalance>>

    fun getTokenBalances(
        id: TokenEntity.Id,
        hideZeros: Boolean = true,
    ): Flow<List<AccountBalance>>

    suspend fun update(): Ior<Nel<Web3Failure>, Unit>
}
