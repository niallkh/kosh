package kosh.libs.keystore

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okio.Buffer
import okio.ByteString
import okio.ByteString.Companion.encodeUtf8
import okio.ByteString.Companion.toByteString
import okio.Path
import java.security.Key
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

private const val DEFAULT_ALIAS = "Kosh.AndroidAesKeyStore"
private const val SCHEME = "AES/GCM/NoPadding"

class AndroidAesKeyStore(
    private val context: Context,
    produceFile: () -> Path,
    private val invalidateByBiometric: Boolean = false,
) : KeyStore {

    private val store by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { produceFile() },
        )
    }

    private val keyStore by lazy {
        java.security.KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
    }

    private fun alias(key: String) = "${DEFAULT_ALIAS}#$key"

    override suspend fun set(
        listener: KeyStoreListener,
        key: String,
        value: ByteString,
    ): Unit = withContext(SecurityDispatcher) {
        var cipher = Cipher.getInstance(SCHEME).apply {
            init(Cipher.ENCRYPT_MODE, getOrCreateKey(alias(key)))
        }

        cipher = withContext(Dispatchers.Main) {
            listener.cipherRequest(CipherRequest(cipher, invalidateByBiometric))
        } ?: return@withContext

        val parameterSpec = cipher.parameters.getParameterSpec(GCMParameterSpec::class.java)
        val tLen = parameterSpec.tLen
        val iv = parameterSpec.iv

        cipher.updateAAD(key.encodeUtf8().toByteArray())
        val cipherText = cipher.doFinal(value.toByteArray())

        val buffer = Buffer().apply {
            writeInt(tLen)
            writeInt(iv.size)
            write(iv)
            write(cipherText)
        }

        store.updateData {
            val preferences = it.toMutablePreferences()
            preferences[preferenceKey(key)] = buffer.readByteArray()
            preferences
        }
    }

    override suspend fun contains(key: String): Boolean = withContext(SecurityDispatcher) {
        val preferences = store.data.first()
        preferences.contains(preferenceKey(key))
    }

    override suspend fun get(
        listener: KeyStoreListener,
        key: String,
    ): ByteString? = withContext(SecurityDispatcher) {
        val preferences = store.data.first()
        val cipherText = preferences[preferenceKey(key)]
            ?: return@withContext null

        val buffer = Buffer().apply { write(cipherText) }
        val tLen = buffer.readInt()
        val ivLen = buffer.readInt()
        val iv = buffer.readByteArray(ivLen.toLong())

        var cipher = Cipher.getInstance(SCHEME).also {
            it.init(Cipher.DECRYPT_MODE, getOrCreateKey(alias(key)), GCMParameterSpec(tLen, iv))
        }

        cipher = withContext(Dispatchers.Main) {
            listener.cipherRequest(CipherRequest(cipher, invalidateByBiometric))
        } ?: return@withContext null

        cipher.updateAAD(key.encodeUtf8().toByteArray())
        cipher.doFinal(buffer.readByteArray()).toByteString()
    }

    private fun getOrCreateKey(
        alias: String,
    ): Key {
        if (keyStore.containsAlias(alias).not()) {
            val keyGenerator = KeyGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_AES,
                "AndroidKeyStore"
            )
            keyGenerator.init(getKeyGenParameterSpec(alias))
            keyGenerator.generateKey()
        }

        return keyStore.getKey(alias, null) as SecretKey
    }

    private fun getKeyGenParameterSpec(
        alias: String,
    ): KeyGenParameterSpec {
        return KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)

            setUserAuthenticationRequired(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                setUserAuthenticationParameters(
                    0,
                    KeyProperties.AUTH_BIOMETRIC_STRONG or KeyProperties.AUTH_DEVICE_CREDENTIAL
                )
            } else {
                @Suppress("DEPRECATION")
                setUserAuthenticationValidityDurationSeconds(0)
            }

            if (context.packageManager.hasSystemFeature(PackageManager.FEATURE_STRONGBOX_KEYSTORE)) {
                setIsStrongBoxBacked(true)
            }

            if (invalidateByBiometric) {
                setInvalidatedByBiometricEnrollment(true)
            }

            build()
        }
    }

    private fun preferenceKey(key: String) = byteArrayPreferencesKey(key)
}
