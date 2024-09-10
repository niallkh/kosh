package kosh.domain.models

import androidx.compose.runtime.Immutable
import arrow.core.Either
import arrow.core.memoize
import arrow.core.raise.either
import arrow.core.raise.ensure
import kosh.domain.failure.AppFailure
import kosh.domain.serializers.AddressSerializer
import kosh.eth.proposals.eip55.eip55
import kotlinx.serialization.Serializable
import okio.ByteString.Companion.decodeHex
import okio.ByteString.Companion.toByteString
import kotlin.jvm.JvmInline

private val AddressRegex = Regex("^0x[0-9a-fA-F]{40}$")
private val AddressLCRegex = Regex("^0x[0-9a-f]{40}$")

@JvmInline
@Immutable
@Serializable(AddressSerializer::class)
value class Address private constructor(
    internal val value: ByteString,
) {

    init {
        require(value.value.size == 20)
    }

    fun bytes(): okio.ByteString = value.value

    override fun toString(): String = eip55()

    companion object {
        private val memo = ::Address.memoize()

        private val EMPTY = memo(ByteString(ByteArray(20).toByteString()))

        operator fun invoke() = EMPTY

        operator fun invoke(byteString: ByteString) = memo(byteString)

        operator fun invoke(address: String): Either<Failure, Address> = either {
            ensure(AddressRegex.matches(address)) { Failure.Invalid }

            val decodedAddress = address
                .removePrefix("0x")
                .decodeHex()
                .let { memo(ByteString(it)) }

            if (AddressLCRegex.matches(address).not()) {
                val eip55 = decodedAddress.eip55()
                ensure(eip55 == address) { Failure.WrongChecksum }
            }

            decodedAddress
        }
    }

    sealed interface Failure : AppFailure {
        data object Invalid : Failure {
            override val message: String
                get() = "Invalid Address"
        }

        data object WrongChecksum : Failure {
            override val message: String
                get() = "Wrong Checksum"
        }
    }
}

fun Address.eip55() = value.value.eip55()

fun Address?.orZero() = this ?: Address()
