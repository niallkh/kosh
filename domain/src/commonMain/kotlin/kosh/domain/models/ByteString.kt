package kosh.domain.models

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.ByteStringSerializer
import kotlinx.serialization.Serializable
import okio.ByteString
import kotlin.jvm.JvmInline

@JvmInline
@Immutable
@Serializable(ByteStringSerializer::class)
value class ByteString private constructor(
    internal val value: ByteString,
) {

    fun bytes(): ByteString = value

    override fun toString(): String {
        return "0x${value.hex()}"
    }

    companion object {
        private val EMPTY = ByteString(ByteString.EMPTY)

        operator fun invoke() = EMPTY

        operator fun invoke(byteString: ByteString) = ByteString(byteString)
    }
}
