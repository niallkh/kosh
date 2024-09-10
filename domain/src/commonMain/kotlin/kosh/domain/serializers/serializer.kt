package kosh.domain.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

inline fun <P : Any, T : Any> serializer(
    serializer: KSerializer<P>,
    crossinline getter: (T) -> P,
    crossinline factory: (P) -> T,
): KSerializer<T> = object : KSerializer<T> {

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun deserialize(decoder: Decoder): T {
        return factory(serializer.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: T) {
        serializer.serialize(encoder, getter(value))
    }
}
