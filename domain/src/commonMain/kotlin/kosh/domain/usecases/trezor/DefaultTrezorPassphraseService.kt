package kosh.domain.usecases.trezor

import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase
import kosh.domain.repositories.KeyStoreListener
import kosh.domain.repositories.KeyStoreRepo

class DefaultTrezorPassphraseService(
    private val keyStoreRepo: KeyStoreRepo,
) : TrezorPassphraseService {

    override suspend fun get(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
    ): Passphrase? = keyStoreRepo.get(keyStoreListener, id)

    override suspend fun contains(id: WalletEntity.Id): Boolean = keyStoreRepo.contains(id)

    override suspend fun save(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
        passphrase: Passphrase,
    ) {
        keyStoreRepo.save(keyStoreListener, id, passphrase)
    }
}
