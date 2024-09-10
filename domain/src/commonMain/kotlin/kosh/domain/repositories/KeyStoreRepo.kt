package kosh.domain.repositories

import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase

interface KeyStoreRepo : Repository {

    suspend fun save(
        listener: KeyStoreListener,
        id: WalletEntity.Id,
        passphrase: Passphrase,
    )

    suspend fun contains(id: WalletEntity.Id): Boolean

    suspend fun get(
        listener: KeyStoreListener,
        id: WalletEntity.Id,
    ): Passphrase?
}
