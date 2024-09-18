package kosh.domain.usecases.account

import arrow.core.IorNel
import kosh.domain.entities.AccountEntity
import kosh.domain.failure.Web3Failure

interface AccountTokensDiscoveryService {

    suspend fun discoverTokens(id: AccountEntity.Id): IorNel<Web3Failure, Unit>
}
