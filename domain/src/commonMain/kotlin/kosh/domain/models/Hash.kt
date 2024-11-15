@file:UseSerializers(ByteStringSerializer::class)

package kosh.domain.models

import androidx.compose.runtime.Immutable
import kosh.domain.serializers.ByteStringSerializer
import kosh.domain.serializers.HashSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.jvm.JvmInline

@JvmInline
@Serializable(HashSerializer::class)
@Immutable
value class Hash private constructor(val value: ByteString) {
    init {
        check(value.value.size == 32)
    }

    fun bytes() = value.value

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        private val memo = ::Hash

        private val EMPTY = memo(ByteString(32))

        operator fun invoke(): Hash = EMPTY

        operator fun invoke(byteString: ByteString): Hash = memo(byteString)
    }
}
