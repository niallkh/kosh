package kosh.eth.abi.json

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object ChainIdSerializer : KSerializer<ULong> {
    private val delegate = ULong.serializer()

    override val descriptor: SerialDescriptor
        get() = delegate.descriptor

    override fun deserialize(decoder: Decoder): ULong {
        return try {
            decoder.decodeLong().toULong()
        } catch (e: SerializationException) {
            val string = decoder.decodeString()
            if (string.startsWith("0x")) {
                string.removePrefix("0x").toULong(16)
            } else {
                string.toULong()
            }
        }
    }

    override fun serialize(encoder: Encoder, value: ULong) {
        encoder.encodeString("0x${value.toString(16)}")
    }
}
