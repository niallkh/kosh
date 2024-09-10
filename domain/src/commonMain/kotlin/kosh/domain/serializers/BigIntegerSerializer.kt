package kosh.domain.serializers

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.util.fromTwosComplementByteArray
import com.ionspin.kotlin.bignum.integer.util.toTwosComplementByteArray
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ByteArraySerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

typealias BigInteger = @Serializable(BigIntegerSerializer::class) BigInteger

object BigIntegerSerializer : KSerializer<BigInteger> {
    private val delegate = ByteArraySerializer()

    override val descriptor: SerialDescriptor = delegate.descriptor

    override fun serialize(encoder: Encoder, value: BigInteger) {
        delegate.serialize(encoder, value.toTwosComplementByteArray())
    }

    override fun deserialize(decoder: Decoder): BigInteger {
        return BigInteger.fromTwosComplementByteArray(delegate.deserialize(decoder))
    }
}

//object BigIntegerBase64Serializer : KSerializer<BigInteger> {
//    override val descriptor: SerialDescriptor =
//        PrimitiveSerialDescriptor("bignum.BigInteger", PrimitiveKind.STRING)
//
//    override fun serialize(encoder: Encoder, value: BigInteger) {
//        ByteStringBase64Serializer.serialize(encoder, value.toTwosComplementByteArray().toByteString())
//    }
//
//    override fun deserialize(decoder: Decoder): BigInteger {
//        return BigInteger.fromTwosComplementByteArray(
//            ByteStringBase64Serializer.deserialize(decoder).toByteArray()
//        )
//    }
//}
