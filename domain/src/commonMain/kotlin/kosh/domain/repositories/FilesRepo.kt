package kosh.domain.repositories

import kotlinx.serialization.KSerializer
import okio.ByteString
import okio.Path

interface FilesRepo : Repository {

    suspend fun write(value: ByteString): Path

    suspend fun read(path: Path): ByteString

    suspend fun delete(path: Path)

    suspend fun <T : Any> put(value: T, serializer: KSerializer<T>): Path

    suspend fun <T : Any> get(path: Path, serializer: KSerializer<T>): T
}
