package kosh.domain.usecases.token

import arrow.core.IorNel
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.token.Balance
import kosh.domain.models.token.TokenMetadata

interface TokenBalanceService {

    suspend fun update(): IorNel<Web3Failure, Unit>

    suspend fun getBalances(
        account: Address,
        tokens: List<TokenMetadata>,
    ): IorNel<Web3Failure, List<Balance>>
}
