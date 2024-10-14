package kosh.domain.usecases.account

import arrow.core.Either
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity.Location
import kosh.domain.failure.AccountFailure
import kosh.domain.models.Address
import kosh.domain.models.account.DerivationPath

interface AccountService {

    suspend fun isActive(address: Address): Boolean

    suspend fun create(
        location: Location,
        derivationPath: DerivationPath,
        address: Address,
    ): Either<AccountFailure, AccountEntity.Id>

    suspend fun toggle(id: AccountEntity.Id, enabled: Boolean)

    suspend fun update(id: AccountEntity.Id, name: String): Either<AccountFailure, Unit>

    suspend fun delete(id: AccountEntity.Id)
}
