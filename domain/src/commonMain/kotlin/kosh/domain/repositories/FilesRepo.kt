package kosh.domain.repositories

import kosh.domain.models.ByteString
import kosh.domain.serializers.Path
import kotlinx.serialization.KSerializer

interface FilesRepo : Repository {

    suspend fun write(value: ByteString): Path

    suspend fun read(path: Path): ByteString

    suspend fun delete(path: Path)

    suspend fun <T : Any> put(value: T, serializer: KSerializer<T>): Path

    suspend fun <T : Any> get(path: Path, serializer: KSerializer<T>): T
}
