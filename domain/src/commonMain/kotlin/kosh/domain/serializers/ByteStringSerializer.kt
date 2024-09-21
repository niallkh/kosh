@file:OptIn(ExperimentalSerializationApi::class)

package kosh.domain.serializers

import kosh.domain.models.ByteString
import kotlinx.io.Buffer
import kotlinx.io.readByteString
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.listSerialDescriptor
import kotlinx.serialization.encoding.CompositeDecoder.Companion.DECODE_DONE
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure

object ByteStringSerializer : KSerializer<ByteString> {
    override val descriptor: SerialDescriptor =
        SerialDescriptor("ByteString", listSerialDescriptor<Byte>())

    override fun serialize(encoder: Encoder, value: ByteString) {
        val bytes = value.bytes()
        val compositeEncoder = encoder.beginCollection(descriptor, bytes.size)
        repeat(bytes.size) {
            compositeEncoder.encodeByteElement(descriptor, it, bytes[it])
        }
        compositeEncoder.endStructure(descriptor)
    }

    override fun deserialize(decoder: Decoder): ByteString {
        val buffer = Buffer()
        decoder.decodeStructure(descriptor) {
            if (decodeSequentially()) {
                val size = decodeCollectionSize(descriptor).takeIf { it != -1 }
                    ?: error("Expected size of byte string")

                repeat(size) {
                    buffer.writeByte(decodeByteElement(descriptor, it))
                }
            } else {
                var index = decodeElementIndex(descriptor)
                while (index != DECODE_DONE) {
                    buffer.writeByte(decodeByteElement(descriptor, index))
                    index = decodeElementIndex(descriptor)
                }
            }
        }
        return ByteString(buffer.readByteString())
    }
}
