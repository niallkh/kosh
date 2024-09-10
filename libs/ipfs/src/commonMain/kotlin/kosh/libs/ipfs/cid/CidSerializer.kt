package kosh.libs.ipfs.cid

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

public object CidSerializer : KSerializer<Cid> {

    private val delegate = String.serializer()

    override val descriptor: SerialDescriptor
        get() = delegate.descriptor

    override fun deserialize(decoder: Decoder): Cid {
        return delegate.deserialize(decoder).decodeCid()
    }

    override fun serialize(encoder: Encoder, value: Cid) {
        delegate.serialize(encoder, value.encode())
    }
}
