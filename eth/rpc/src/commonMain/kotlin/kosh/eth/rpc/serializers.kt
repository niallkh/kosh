package kosh.eth.rpc

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kosh.eth.abi.Value
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okio.ByteString
import okio.ByteString.Companion.decodeHex

internal object BigIntegerSerializer : KSerializer<BigInteger> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("bignum.BigInteger", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): BigInteger {
        val string = decoder.decodeString()
        return if (string.startsWith("0x")) {
            string
                .removePrefix("0x")
                .toBigInteger(16)
        } else {
            string.toBigInteger(10)
        }
    }

    override fun serialize(encoder: Encoder, value: BigInteger) {
        encoder.encodeString("0x${value.toString(16)}")
    }
}

internal object ByteStringSerializer : KSerializer<ByteString> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("okio.ByteString", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ByteString {
        return decoder.decodeString()
            .removePrefix("0x")
            .decodeHex()
    }

    override fun serialize(encoder: Encoder, value: ByteString) {
        encoder.encodeString("0x${value.hex()}")
    }
}

internal object AddressSerializer : KSerializer<Value.Address> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Value.Address", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Value.Address {
        return ByteStringSerializer.deserialize(decoder)
            .let(Value.Address::invoke)
    }

    override fun serialize(encoder: Encoder, value: Value.Address) {
        ByteStringSerializer.serialize(encoder, value.value)
    }
}
