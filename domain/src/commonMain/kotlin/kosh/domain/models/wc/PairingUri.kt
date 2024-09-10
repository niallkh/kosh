package kosh.domain.models.wc

import androidx.compose.runtime.Immutable
import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import kosh.domain.failure.WcFailure
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@Serializable
@JvmInline
@Immutable
value class PairingUri private constructor(val value: String) {

    companion object {
        operator fun invoke(uri: String): Either<WcFailure.PairingUriInvalid, PairingUri> = either {
            ensure(uri.startsWith("wc:")) {
                WcFailure.PairingUriInvalid()
            }
            ensure(arrayOf("@2", "relay-protocol=irn", "symKey").all { it in uri }) {
                WcFailure.PairingUriInvalid()
            }
//            withError({
//                WcFailure.PairingUriInvalid()
//            }) {
//                wcValidator(uri).bind()
//            }

            PairingUri(uri)
        }
    }
}

