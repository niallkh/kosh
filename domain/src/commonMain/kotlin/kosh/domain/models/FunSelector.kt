package kosh.domain.models

import androidx.compose.runtime.Immutable
import arrow.core.memoize
import kosh.eth.abi.functionSelector
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Immutable
@Serializable
value class FunSelector private constructor(val value: ByteString) {
    init {
        check(value.value.size == 4)
    }

    fun bytes() = value.value

    override fun toString(): String {
        return value.toString()
    }

    companion object {
        private val memo = ::FunSelector.memoize()

        private val EMPTY = memo(ByteString(kotlinx.io.bytestring.ByteString(ByteArray(4))))

        operator fun invoke(): FunSelector = EMPTY

        operator fun invoke(byteString: ByteString): FunSelector = memo(byteString)
    }
}

fun ByteString.funSelector() = bytes().functionSelector()?.let { FunSelector(ByteString(it)) }
