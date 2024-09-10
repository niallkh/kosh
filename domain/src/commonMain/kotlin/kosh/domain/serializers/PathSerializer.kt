package kosh.domain.serializers

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okio.Path
import okio.Path.Companion.toPath

typealias Path = @Serializable(PathSerializer::class) Path

object PathSerializer : KSerializer<Path> {
    private val delegate = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Path {
        return delegate.deserialize(decoder).toPath()
    }

    override fun serialize(encoder: Encoder, value: Path) {
        delegate.serialize(encoder, value.toString())
    }
}
