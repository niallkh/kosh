package kosh.data

import kosh.data.sources.KeyValueStore
import kosh.data.sources.put
import kosh.domain.entities.Reference
import kosh.domain.models.ByteString
import kosh.domain.repositories.ReferenceRepo
import kotlinx.serialization.KSerializer

class DefaultReferenceRepo(
    private val keyValueStore: KeyValueStore,
) : ReferenceRepo {

    override suspend fun get(
        reference: Reference<ByteString>,
    ): ByteString? {
        return keyValueStore.get(reference.key)?.let { ByteString(it) }
    }

    override suspend fun <T : Any> get(
        reference: Reference<T>,
        serializer: KSerializer<T>,
    ): T? {
        return keyValueStore.get(reference.key, serializer)
    }

    override suspend fun save(value: ByteString): Reference<ByteString> {
        return Reference(keyValueStore.put(value.bytes()))
    }

    override suspend fun <T : Any> save(value: T, serializer: KSerializer<T>): Reference<T> {
        return Reference(keyValueStore.put(value, serializer))
    }

    override suspend fun remove(reference: Reference<*>) {
        keyValueStore.remove(reference.key)
    }
}
