package kosh.libs.keystore

import kotlinx.io.bytestring.ByteString

class IosKeyStore : KeyStore {
    override suspend fun set(listener: KeyStoreListener, key: String, value: ByteString) {
    }

    override suspend fun contains(key: String): Boolean {
        return false
    }

    override suspend fun get(listener: KeyStoreListener, key: String): ByteString? {
        return null
    }
}
