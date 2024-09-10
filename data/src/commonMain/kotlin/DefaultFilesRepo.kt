@file:OptIn(ExperimentalSerializationApi::class)

package kosh.data

import com.benasher44.uuid.uuid4
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.suspendLazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.okio.decodeFromBufferedSource
import kotlinx.serialization.json.okio.encodeToBufferedSink
import okio.Buffer
import okio.ByteString
import okio.FileSystem
import okio.IOException
import okio.Path

class DefaultFilesRepo(
    private val fileSystem: FileSystem,
    folder: () -> Path,
    private val json: Json = Json,
) : FilesRepo {

    private val folder = suspendLazy {
        folder().also { fileSystem.createDirectories(it) }
    }

    override suspend fun write(value: ByteString): Path = withContext(Dispatchers.IO) {
        val key = uuid4().toString()

        val path = folder().resolve(key)
        val tmpPath = folder().resolve("$key.tmp")

        try {
            fileSystem.write(tmpPath) {
                write(value)
                flush()
            }

            try {
                fileSystem.atomicMove(tmpPath, path)
            } catch (e: IOException) {
                fileSystem.copy(tmpPath, path)
            }
        } finally {
            fileSystem.delete(tmpPath)
        }

        path.relativeTo(folder())
    }

    override suspend fun read(path: Path): ByteString = withContext(Dispatchers.IO) {
        fileSystem.read(folder().resolve(path)) {
            readByteString()
        }
    }

    override suspend fun delete(path: Path) = withContext(Dispatchers.IO) {
        fileSystem.delete(folder().resolve(path))
    }

    override suspend fun <T : Any> put(
        value: T,
        serializer: KSerializer<T>,
    ): Path = withContext(Dispatchers.Default) {
        val buffer = Buffer()
        json.encodeToBufferedSink(serializer, value, buffer)
        write(buffer.readByteString())
    }

    override suspend fun <T : Any> get(
        path: Path,
        serializer: KSerializer<T>,
    ): T = withContext(Dispatchers.Default) {
        json.decodeFromBufferedSource(serializer, Buffer().write(read(path)))
    }
}
