package kosh.domain.repositories

import kosh.domain.models.keystore.CipherRequest
import kosh.domain.models.keystore.CipherWrapper

interface KeyStoreListener {

    suspend fun biometricRequest(request: CipherRequest): CipherWrapper?
}
