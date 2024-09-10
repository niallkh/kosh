package kosh.data

import kosh.domain.models.keystore.CipherRequest
import kosh.domain.models.keystore.CipherWrapper
import kosh.domain.repositories.KeyStoreListener
import kosh.libs.keystore.Cipher
import kosh.libs.keystore.CipherRequest as CipherRequestLib
import kosh.libs.keystore.KeyStoreListener as KeyStoreListenerLib

internal class KeyStoreListenerAdapter(
    private val listener: KeyStoreListener,
) : KeyStoreListenerLib {

    override suspend fun cipherRequest(request: CipherRequestLib): Cipher? {
        return listener.biometricRequest(
            CipherRequest(
                CipherWrapper(request.cipher),
                request.onlyBiometry
            )
        )?.value as? Cipher
    }
}
