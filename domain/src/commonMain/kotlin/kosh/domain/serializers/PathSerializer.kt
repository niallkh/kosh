package kosh.domain.serializers

import kotlinx.io.files.Path
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias Path = @Serializable(PathSerializer::class) Path

object PathSerializer : KSerializer<Path> {
    private val delegate = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Path {
        return Path(delegate.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: Path) {
        delegate.serialize(encoder, value.toString())
    }
}
