package kosh.domain.models

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.ByteStringSerializer
import kotlinx.io.bytestring.ByteString
import kotlinx.io.bytestring.toHexString
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Immutable
@Serializable(ByteStringSerializer::class)
value class ByteString private constructor(
    internal val value: ByteString,
) {

    fun bytes() = value

    override fun toString(): String {
        return "0x${value.toHexString()}"
    }

    companion object {
        private val EMPTY = ByteString(ByteString())

        operator fun invoke() = EMPTY

        operator fun invoke(byteString: ByteString) = ByteString(byteString)

        operator fun invoke(size: Int) = ByteString(ByteString(ByteArray(size)))
    }
}
