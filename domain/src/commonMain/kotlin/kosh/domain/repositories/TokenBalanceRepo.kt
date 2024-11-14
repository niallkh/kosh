package kosh.domain.repositories

import kosh.domain.entities.NetworkEntity
import kosh.domain.entities.TokenEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenMetadata
import kosh.domain.serializers.Either

interface TokenBalanceRepo : Repository {

    suspend fun getBalances(
        networkId: NetworkEntity.Id,
        account: Address,
        tokens: List<TokenEntity>,
    ): Either<Web3Failure, List<Balance>>

    suspend fun getBalancesForMetadata(
        networkId: NetworkEntity.Id,
        account: Address,
        tokens: List<TokenMetadata>,
    ): Either<Web3Failure, List<Balance>>
}
