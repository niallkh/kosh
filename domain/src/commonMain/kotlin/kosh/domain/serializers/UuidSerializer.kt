package kosh.domain.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.uuid.Uuid

typealias Uuid = @Serializable(UuidSerializer::class) Uuid

object UuidSerializer : KSerializer<Uuid> {
    private val delegate = ByteArraySerializer()

    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: Uuid) {
        delegate.serialize(encoder, value.toByteArray())
    }

    override fun deserialize(decoder: Decoder): Uuid {
        return delegate.deserialize(decoder).let(Uuid::fromByteArray)
    }
}
