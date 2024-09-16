package kosh.domain.usecases.account

import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.optics.dsl.at
import arrow.optics.typeclasses.At
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.WalletEntity
import kosh.domain.entities.accountId
import kosh.domain.entities.name
import kosh.domain.failure.AccountFailure
import kosh.domain.models.Address
import kosh.domain.models.account.DerivationPath
import kosh.domain.models.account.ethereumAddressIndex
import kosh.domain.models.account.ledgerAddressIndex
import kosh.domain.repositories.AppStateRepo
import kosh.domain.repositories.modify
import kosh.domain.repositories.optic
import kosh.domain.repositories.state
import kosh.domain.state.AppState
import kosh.domain.state.account
import kosh.domain.state.accounts
import kosh.domain.state.enabledAccountIds
import kosh.domain.state.optionalAccount
import kosh.domain.state.transactions
import kosh.domain.state.wallet
import kosh.domain.state.wallets
import kosh.domain.utils.phmap
import kosh.domain.utils.phset
import kosh.domain.utils.pmap
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.coroutines.flow.Flow
import kotlin.math.max

class DefaultAccountService(
    private val appStateRepo: AppStateRepo,
) : AccountService {

    override fun getEnabled(): Flow<Set<AccountEntity.Id>> =
        appStateRepo.optic(AppState.enabledAccountIds)

    override suspend fun isActive(address: Address): Boolean {
        return AccountEntity.Id(address) in appStateRepo.state().enabledAccountIds
    }

    override fun get(address: Address): Flow<AccountEntity?> =
        get(id = AccountEntity.Id(address))

    override fun get(
        id: AccountEntity.Id,
    ): Flow<AccountEntity?> = appStateRepo.optic(AppState.account(id))

    override fun create(
        raise: Raise<AccountFailure>,
        location: WalletEntity.Location,
        derivationPath: DerivationPath,
        address: Address,
    ): WalletEntity.Id = raise.run {
        val walletsAmount = appStateRepo.state().wallets.size

        val wallet = WalletEntity(
            name = "Wallet #$walletsAmount",
            location = location,
        )

        val addressIndex = max(
            derivationPath.ethereumAddressIndex,
            derivationPath.ledgerAddressIndex,
        )

        val account = AccountEntity(
            address = address,
            derivationPath = derivationPath,
            walletId = wallet.id,
            name = "Account #$addressIndex"
        )

        appStateRepo.modify {
            ensure(account.id !in AppState.accounts.get()) {
                AccountFailure.AlreadyExist()
            }

            if (wallet.id !in AppState.wallets.get()) {
                AppState.wallets.at(At.phmap(), wallet.id) set wallet
            }

            AppState.accounts.at(At.pmap(), account.id) set account
            AppState.enabledAccountIds.at(At.phset(), account.id) set true
        }

        wallet.id
    }

    override fun update(id: AccountEntity.Id, name: String) = either {
        appStateRepo.modify {
            ensure(id in AppState.accounts.get()) {
                AccountFailure.NotFound()
            }

            inside(AppState.optionalAccount(id)) {
                AccountEntity.name set name
            }
        }
    }

    override fun toggle(id: AccountEntity.Id, enabled: Boolean) {
        appStateRepo.modify {
            AppState.enabledAccountIds.at(At.phset(), id) set enabled
        }
    }

    override fun delete(id: AccountEntity.Id) {
        appStateRepo.modify {
            val account = AppState.account(id).get()

            AppState.account(id) set null

            AppState.enabledAccountIds.at(At.phset(), id) set false

            if (account != null) {
                val accountsInWallet = AppState.accounts(account.walletId).get()

                if (accountsInWallet.isEmpty()) {
                    AppState.wallet(account.walletId) set null
                }
            }

            AppState.transactions transform { map ->
                map.filter { it.value.accountId != id }.toPersistentMap()
            }
        }
    }
}
