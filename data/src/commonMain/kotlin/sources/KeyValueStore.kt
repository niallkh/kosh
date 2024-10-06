package kosh.data.sources

import kosh.domain.models.ByteString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlin.uuid.Uuid

interface KeyValueStore {

    suspend fun get(key: Uuid): ByteString

    suspend fun put(key: Uuid, value: ByteString)

    suspend fun <T : Any> put(key: Uuid, value: T, serializer: KSerializer<T>)

    suspend fun <T : Any> get(key: Uuid, serializer: KSerializer<T>): T

    suspend fun remove(key: Uuid)
}

//suspend fun <T : Any> KeyValueStore.get(store: Store<T>): T = get(store.key, store.serializer)
//
//suspend fun <T : Any> KeyValueStore.put(store: Store<T>, value: T) {
//    put(store.key, value, store.serializer)
//}

data class Store<T : Any>(
    val key: Uuid,
    val serializer: KSerializer<T>,
    val default: () -> T,
)

inline fun <reified T : Any> storeOf(
    key: Uuid,
    serializer: KSerializer<T> = serializer(),
    noinline default: () -> T,
) = Store(
    key = key,
    serializer = serializer,
    default = default,
)
