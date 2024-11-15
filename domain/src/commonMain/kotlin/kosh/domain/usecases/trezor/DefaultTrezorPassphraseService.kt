package kosh.domain.usecases.trezor

import arrow.core.raise.catch
import co.touchlab.kermit.Logger
import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase
import kosh.domain.repositories.KeyStoreListener
import kosh.domain.repositories.KeyStoreRepo

class DefaultTrezorPassphraseService(
    private val keyStoreRepo: KeyStoreRepo,
) : TrezorPassphraseService {

    private val logger = Logger.withTag("[K]TrezorPassphraseService")

    override suspend fun get(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
    ): Passphrase? = catch({
        keyStoreRepo.get(keyStoreListener, id)
    }) {
        logger.e(it) { "Could not Get trezor passphrase" }
        null
    }

    override suspend fun contains(
        id: WalletEntity.Id,
    ): Boolean = catch({ keyStoreRepo.contains(id) }) {
        logger.e(it) { "Could not check Contains trezor passphrase" }
        false
    }

    override suspend fun save(
        keyStoreListener: KeyStoreListener,
        id: WalletEntity.Id,
        passphrase: Passphrase,
    ) {
        catch({
            keyStoreRepo.save(keyStoreListener, id, passphrase)
        }) {
            logger.e(it) { "Could not Save trezor passphrase" }
        }
    }
}
