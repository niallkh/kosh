package kosh.domain.models

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import kosh.eth.abi.selector
import kotlinx.serialization.Serializable
import okio.ByteString.Companion.toByteString
import kotlin.jvm.JvmInline


@JvmInline
@Immutable
@Serializable
value class FunSelector private constructor(val value: ByteString) {
    init {
        check(value.value.size == 4)
    }

    fun bytes(): okio.ByteString = value.value

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        private val memo = ::FunSelector.memoize()

        private val EMPTY = memo(ByteString(ByteArray(4).toByteString()))

        operator fun invoke(): FunSelector = EMPTY

        operator fun invoke(byteString: ByteString): FunSelector = memo(byteString)
    }
}

fun ByteString.funSelector() = bytes().selector()?.let { FunSelector(ByteString(it)) }
