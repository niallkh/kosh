package kosh.domain.serializers

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.bytes
import com.benasher44.uuid.uuidOf
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias Uuid = @Serializable(UuidSerializer::class) Uuid

object UuidSerializer : KSerializer<Uuid> {
    private val delegate = ByteArraySerializer()

    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: Uuid) {
        delegate.serialize(encoder, value.bytes)
    }

    override fun deserialize(decoder: Decoder): Uuid {
        return delegate.deserialize(decoder).let(::uuidOf)
    }
}
