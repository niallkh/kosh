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
import kotlinx.io.Buffer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.files.Path
import kotlinx.io.readByteString
import kotlinx.io.readString
import kotlinx.io.write
import kotlinx.io.writeString
import okio.ByteString.Companion.toByteString
import okio.Path.Companion.toPath
import java.security.Key
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.spec.MGF1ParameterSpec
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

private const val SCHEME = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
private const val DEFAULT_ALIAS = "Kosh.AndroidRsaKeyStore"

class AndroidRsaKeyStore(
    private val context: Context,
    produceFile: () -> Path,
    private val invalidateByBiometric: Boolean = false,
) : KeyStore {

    private val store by lazy {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { produceFile().toString().toPath() },
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
        val keyValue = Buffer().apply {
            writeInt(key.length)
            writeString(key)
            write(value)
        }.readByteString()

        val cipher = Cipher.getInstance(SCHEME).apply {
            val sp = OAEPParameterSpec(
                "SHA-256",
                "MGF1",
                MGF1ParameterSpec.SHA1,
                PSource.PSpecified.DEFAULT,
            )

            init(Cipher.ENCRYPT_MODE, getOrCreateKey(decrypt = false, alias = alias(key)), sp)
        }

        val cipherText = cipher.doFinal(keyValue.toByteArray()).toByteString()

        store.updateData {
            val preferences = it.toMutablePreferences()
            preferences[preferenceKey(key)] = cipherText.toByteArray()
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

        var cipher = Cipher.getInstance(SCHEME).also {
            it.init(Cipher.DECRYPT_MODE, getOrCreateKey(decrypt = true, alias = alias(key)))
        }

        cipher = withContext(Dispatchers.Main) {
            listener.cipherRequest(CipherRequest(cipher, invalidateByBiometric))
        } ?: return@withContext null

        val message = cipher.doFinal(cipherText)

        val buffer = Buffer().apply { write(message) }
        val keyLen = buffer.readInt()
        val restoredKey = buffer.readString(keyLen.toLong())
        check(key == restoredKey)
        buffer.readByteString()
    }

    private fun getOrCreateKey(
        alias: String,
        decrypt: Boolean,
    ): Key {
        if (keyStore.containsAlias(alias).not()) {
            val keyPairGenerator = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_RSA,
                "AndroidKeyStore"
            )
            keyPairGenerator.initialize(getKeyGenParameterSpec(alias))
            keyPairGenerator.generateKeyPair()
        }

        return if (decrypt) keyStore.getKey(alias, null) as PrivateKey
        else keyStore.getCertificate(alias).publicKey
    }

    private fun getKeyGenParameterSpec(
        alias: String,
    ): KeyGenParameterSpec {
        return KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_DECRYPT).run {
            setDigests(KeyProperties.DIGEST_SHA256)
            setBlockModes(KeyProperties.BLOCK_MODE_ECB)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
            setKeySize(2048)

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

    protected fun preferenceKey(key: String) = byteArrayPreferencesKey(key)
}
