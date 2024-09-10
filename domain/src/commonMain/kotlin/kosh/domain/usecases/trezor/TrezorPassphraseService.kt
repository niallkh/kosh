package kosh.domain.usecases.trezor

import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase
import kosh.domain.repositories.KeyStoreListener

interface TrezorPassphraseService {
    suspend fun get(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
    ): Passphrase?

    suspend fun contains(id: WalletEntity.Id): Boolean

    suspend fun save(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
        passphrase: Passphrase,
    )
}
