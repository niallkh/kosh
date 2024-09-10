package kosh.data

import kosh.domain.entities.WalletEntity
import kosh.domain.models.trezor.Passphrase
import kosh.domain.repositories.KeyStoreListener
import kosh.domain.repositories.KeyStoreRepo
import kosh.libs.keystore.KeyStore
import okio.ByteString.Companion.encodeUtf8

class DefaultKeyStoreRepo(
    private val keyStore: KeyStore,
) : KeyStoreRepo {

    override suspend fun get(
        listener: KeyStoreListener,
        id: WalletEntity.Id,
    ): Passphrase? {
        val key = id.value.toString()
        val value = keyStore.get(
            listener = KeyStoreListenerAdapter(listener),
            key = key,
        ) ?: return null

        return Passphrase(value.utf8())
    }

    override suspend fun contains(id: WalletEntity.Id): Boolean {
        val key = id.value.toString()
        return keyStore.contains(key)
    }

    override suspend fun save(
        listener: KeyStoreListener,
        id: WalletEntity.Id,
        passphrase: Passphrase,
    ) {
        val key = id.value.toString()
        keyStore.set(
            listener = KeyStoreListenerAdapter(listener),
            key = key,
            value = checkNotNull(passphrase.value).encodeUtf8()
        )
    }
}
