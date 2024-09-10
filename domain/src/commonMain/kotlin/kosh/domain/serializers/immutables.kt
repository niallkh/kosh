package kosh.domain.serializers

import kotlinx.collections.immutable.ImmutableCollection
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.ImmutableSet
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.PersistentSet
import kotlinx.collections.immutable.toPersistentHashMap
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentMap
import kotlinx.collections.immutable.toPersistentSet
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

private class DefaultImmutableCollectionSerializer<T, C : ImmutableCollection<T>>(
    serializer: KSerializer<T>,
    private val transform: (Collection<T>) -> C,
) : KSerializer<C> {
    private val listSerializer = ListSerializer(serializer)

    override val descriptor: SerialDescriptor = serializer.descriptor

    override fun serialize(encoder: Encoder, value: C) {
        return listSerializer.serialize(encoder, value.toList())
    }

    override fun deserialize(decoder: Decoder): C {
        return listSerializer.deserialize(decoder).let(transform)
    }
}

private class DefaultImmutableMapSerializer<K, V, M : ImmutableMap<K, V>>(
    keySerializer: KSerializer<K>,
    valueSerializer: KSerializer<V>,
    private val transform: (Map<K, V>) -> M,
) : KSerializer<M> {
    private val mapSerializer = MapSerializer(keySerializer, valueSerializer)

    override val descriptor: SerialDescriptor = mapSerializer.descriptor

    override fun serialize(encoder: Encoder, value: M) {
        return mapSerializer.serialize(encoder, value.toMap())
    }

    override fun deserialize(decoder: Decoder): M {
        return mapSerializer.deserialize(decoder).let(transform)
    }
}

typealias ImmutableList<T> = @Serializable(with = ImmutableListSerializer::class) ImmutableList<T>

class ImmutableListSerializer<T>(
    serializer: KSerializer<T>,
) : KSerializer<ImmutableList<T>> by DefaultImmutableCollectionSerializer(
    serializer = serializer,
    transform = { decodedList -> decodedList.toPersistentList() }
)

typealias PersistentList<T> = @Serializable(with = PersistentListSerializer::class) PersistentList<T>

class PersistentListSerializer<T>(
    serializer: KSerializer<T>,
) : KSerializer<PersistentList<T>> by DefaultImmutableCollectionSerializer(
    serializer = serializer,
    transform = { decodedList -> decodedList.toPersistentList() }
)

typealias ImmutableSet<T> = @Serializable(with = ImmutableSetSerializer::class) ImmutableSet<T>

class ImmutableSetSerializer<T>(
    serializer: KSerializer<T>,
) : KSerializer<ImmutableSet<T>> by DefaultImmutableCollectionSerializer(
    serializer = serializer,
    transform = { decodedList -> decodedList.toPersistentSet() }
)

typealias PersistentSet<T> = @Serializable(with = PersistentSetSerializer::class) PersistentSet<T>

class PersistentSetSerializer<T>(
    serializer: KSerializer<T>,
) : KSerializer<PersistentSet<T>> by DefaultImmutableCollectionSerializer(
    serializer = serializer,
    transform = { decodedList -> decodedList.toPersistentSet() }
)

typealias PersistentHashSet<T> = @Serializable(with = PersistentHashSetSerializer::class) PersistentSet<T>

class PersistentHashSetSerializer<T>(
    serializer: KSerializer<T>,
) : KSerializer<PersistentSet<T>> by DefaultImmutableCollectionSerializer(
    serializer = serializer,
    transform = { decodedList -> decodedList.toPersistentHashSet() }
)

typealias ImmutableMap<K, V> = @Serializable(with = ImmutableMapSerializer::class) ImmutableMap<K, V>

class ImmutableMapSerializer<T, V>(
    keySerializer: KSerializer<T>,
    valueSerializer: KSerializer<V>,
) : KSerializer<ImmutableMap<T, V>> by DefaultImmutableMapSerializer(
    keySerializer = keySerializer,
    valueSerializer = valueSerializer,
    transform = { decodedMap -> decodedMap.toPersistentMap() }
)

typealias PersistentMap<K, V> = @Serializable(with = PersistentMapSerializer::class) PersistentMap<K, V>

class PersistentMapSerializer<T, V>(
    keySerializer: KSerializer<T>,
    valueSerializer: KSerializer<V>,
) : KSerializer<PersistentMap<T, V>> by DefaultImmutableMapSerializer(
    keySerializer = keySerializer,
    valueSerializer = valueSerializer,
    transform = { decodedMap -> decodedMap.toPersistentMap() }
)

typealias PersistentHashMap<K, V> = @Serializable(with = PersistentHashMapSerializer::class) PersistentMap<K, V>

class PersistentHashMapSerializer<T, V>(
    keySerializer: KSerializer<T>,
    valueSerializer: KSerializer<V>,
) : KSerializer<PersistentMap<T, V>> by DefaultImmutableMapSerializer(
    keySerializer = keySerializer,
    valueSerializer = valueSerializer,
    transform = { decodedMap -> decodedMap.toPersistentHashMap() }
)
