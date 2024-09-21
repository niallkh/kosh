package kosh.libs.keystore

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.io.bytestring.ByteString
import kotlin.coroutines.ContinuationInterceptor

internal object SecurityDispatcher : ContinuationInterceptor by Dispatchers.IO.limitedParallelism(1)

interface KeyStore {

    suspend fun set(
        listener: KeyStoreListener,
        key: String,
        value: ByteString,
    )

    suspend fun contains(
        key: String,
    ): Boolean

    suspend fun get(
        listener: KeyStoreListener,
        key: String,
    ): ByteString?
}

interface KeyStoreListener {
    suspend fun cipherRequest(request: CipherRequest): Cipher?
}

data class CipherRequest(
    val cipher: Cipher,
    val onlyBiometry: Boolean,
)
