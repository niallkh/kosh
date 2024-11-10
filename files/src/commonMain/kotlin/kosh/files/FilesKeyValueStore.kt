@file:OptIn(UnsafeByteStringApi::class)

package kosh.files

import kosh.data.sources.KeyValueStore
import kosh.domain.utils.suspendLazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.io.buffered
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.unsafe.UnsafeByteStringApi
import kotlinx.io.bytestring.unsafe.UnsafeByteStringOperations
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.serialization.KSerializer
import kotlinx.serialization.cbor.Cbor
import kotlin.uuid.Uuid

class FilesKeyValueStore(
    private val fileSystem: FileSystem,
    private val cbor: Cbor,
    folder: () -> Path,
) : KeyValueStore {

    private val folder = suspendLazy {
        folder().also { fileSystem.createDirectories(it) }
    }

    override suspend fun put(key: Uuid, value: ByteString) = withContext(Dispatchers.IO) {
        val path = path(key)
        val tmpPath = tmpPath(key)

        fileSystem.sink(tmpPath).buffered().use {
            it.write(value)
            it.flush()
        }

        try {
            fileSystem.atomicMove(tmpPath, path)
        } catch (e: UnsupportedOperationException) {
            fileSystem.delete(tmpPath)

            fileSystem.sink(path).buffered().use {
                it.write(value)
                it.flush()
            }
        }
    }

    override suspend fun get(key: Uuid): ByteString? = withContext(Dispatchers.IO) {
        val path = path(key)
        if (fileSystem.exists(path)) {
            fileSystem.source(path).buffered().use {
                it.readByteString()
            }
        } else {
            null
        }
    }

    override suspend fun <T : Any> put(
        key: Uuid,
        value: T,
        serializer: KSerializer<T>,
    ) = withContext(Dispatchers.Default) {
        val byteArray = cbor.encodeToByteArray(serializer, value)
        val byteString = UnsafeByteStringOperations.wrapUnsafe(byteArray)
        put(key, byteString)
    }

    override suspend fun <T : Any> get(
        key: Uuid,
        serializer: KSerializer<T>,
    ): T? = withContext(Dispatchers.Default) {
        get(key)?.let { byteString ->
            lateinit var result: T
            UnsafeByteStringOperations.withByteArrayUnsafe(byteString) {
                result = cbor.decodeFromByteArray(serializer, it)
            }
            result
        }
    }

    override suspend fun remove(key: Uuid) = withContext(Dispatchers.IO) {
        fileSystem.delete(path(key))
    }

    private suspend fun path(key: Uuid) = Path(folder(), key.toHexString())
    private suspend fun tmpPath(key: Uuid) = Path(folder(), "${key.toHexString()}.tmp")
}
