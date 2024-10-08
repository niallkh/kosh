package kosh.domain.usecases.account

import arrow.core.Either
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity.Location
import kosh.domain.failure.AccountFailure
import kosh.domain.models.Address
import kosh.domain.models.account.DerivationPath
import kotlinx.coroutines.flow.Flow

interface AccountService {

    fun get(id: AccountEntity.Id): Flow<AccountEntity?>

    fun get(address: Address): Flow<AccountEntity?>

    fun getEnabled(): Flow<Set<AccountEntity.Id>>

    suspend fun isActive(address: Address): Boolean

    fun create(
        location: Location,
        derivationPath: DerivationPath,
        address: Address,
    ): Either<AccountFailure, AccountEntity.Id>

    fun toggle(id: AccountEntity.Id, enabled: Boolean)

    fun update(id: AccountEntity.Id, name: String): Either<AccountFailure, Unit>

    fun delete(id: AccountEntity.Id)
}
