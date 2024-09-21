@file:OptIn(ExperimentalSerializationApi::class)

package kosh.data

import com.benasher44.uuid.uuid4
import kosh.domain.models.ByteString
import kosh.domain.repositories.FilesRepo
import kosh.domain.repositories.suspendLazy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.FileSystem
import kotlinx.io.files.Path
import kotlinx.io.readByteString
import kotlinx.io.write
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.io.decodeFromSource
import kotlinx.serialization.json.io.encodeToSink

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

        val path = Path(folder(), key)
        val tmpPath = Path(folder(), "$key.tmp")

        try {
            fileSystem.sink(tmpPath).buffered().use {
                it.write(value.bytes())
                it.flush()
            }

            fileSystem.atomicMove(tmpPath, path)

        } finally {
            fileSystem.delete(tmpPath)
        }

        Path(key)
    }

    override suspend fun read(path: Path): ByteString = withContext(Dispatchers.IO) {
        fileSystem.source(Path(folder(), path.name)).buffered().use {
            ByteString(it.readByteString())
        }
    }

    override suspend fun delete(path: Path) = withContext(Dispatchers.IO) {
        fileSystem.delete(Path(folder(), path.name))
    }

    override suspend fun <T : Any> put(
        value: T,
        serializer: KSerializer<T>,
    ): Path = withContext(Dispatchers.Default) {
        val buffer = Buffer()
        json.encodeToSink(serializer, value, buffer)
        write(ByteString(buffer.readByteString()))
    }

    override suspend fun <T : Any> get(
        path: Path,
        serializer: KSerializer<T>,
    ): T = withContext(Dispatchers.Default) {
        json.decodeFromSource(serializer, Buffer().apply { write(read(path)) })
    }
}
