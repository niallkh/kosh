package kosh.data.sources

import kotlinx.io.bytestring.ByteString
import kotlinx.serialization.KSerializer
import kotlin.uuid.Uuid

interface KeyValueStore {

    suspend fun put(key: Uuid, value: ByteString)

    suspend fun get(key: Uuid): ByteString?

    suspend fun <T : Any> put(key: Uuid, value: T, serializer: KSerializer<T>)

    suspend fun <T : Any> get(key: Uuid, serializer: KSerializer<T>): T?

    suspend fun remove(key: Uuid)
}

suspend fun KeyValueStore.put(value: ByteString): Uuid {
    val key = Uuid.random()
    put(key, value)
    return key
}

suspend fun <T : Any> KeyValueStore.put(value: T, serializer: KSerializer<T>): Uuid {
    val key = Uuid.random()
    put(key, value, serializer)
    return key
}
