package kosh.domain.serializers

import com.eygraber.uri.Uri
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias Uri = @Serializable(UriSerializer::class) Uri

object UriSerializer : KSerializer<Uri> {
    private val delegate = String.serializer()
    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun deserialize(decoder: Decoder): Uri {
        return Uri.parse(delegate.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: Uri) {
        delegate.serialize(encoder, value.toString())
    }
}

