package kosh.domain.usecases.network

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.toEitherNel
import kosh.domain.failure.NetworkFailure
import kosh.domain.models.ChainId
import kosh.domain.models.Uri
import kosh.domain.usecases.validation.Validator

val networkNameValidator: Validator<String, String, NetworkFailure> = Validator {
    either {
        ensure(it.length in 2..50) {
            NetworkFailure.InvalidNetworkName()
        }
        it
    }.toEitherNel()
}

val urlRegex = "^(https|ipfs)://(\\.?[a-zA-Z0-9-]+)+(/[a-zA-Z0-9-:,._?&=/%#]*)?\$".toRegex()
val resRegex = "^(res)://(\\.?[a-zA-Z0-9-]+)+(/[a-zA-Z0-9-._?&=/%#]*)?\$".toRegex()

val rpcProviderValidator: Validator<String, Uri, NetworkFailure> = Validator {
    either {
        ensure(urlRegex.matches(it)) {
            NetworkFailure.InvalidRpcProvider()
        }

        Uri(it)
    }.toEitherNel()
}

val writeProviderValidator: Validator<String, Uri?, NetworkFailure> = Validator {
    either {
        if (it.isEmpty()) {
            null
        } else {
            ensure(urlRegex.matches(it)) {
                NetworkFailure.InvalidRpcProvider()
            }

            Uri(it)
        }
    }.toEitherNel()
}

val chainIdValidator: Validator<String, ChainId, NetworkFailure> = Validator {
    either {
        if (it.startsWith("0x")) {
            it.removePrefix("0x").toULongOrNull(16)
        } else {
            it.toULongOrNull()
        }
            ?.let { ChainId(it) }
            ?: raise(NetworkFailure.InvalidChainId())
    }.toEitherNel()
}

val explorerValidator: Validator<String, Uri?, NetworkFailure> = Validator {
    either {
        if (it.isEmpty()) {
            null
        } else {
            ensure(urlRegex.matches(it)) {
                NetworkFailure.InvalidRpcProvider()
            }

            Uri(it)
        }
    }.toEitherNel()
}

val iconValidator: Validator<String, Uri?, NetworkFailure> = Validator {
    either {
        when {
            it.isEmpty() -> null
            urlRegex.matches(it) -> Uri(it)
            resRegex.matches(it) -> Uri(it)
            else -> raise(NetworkFailure.InvalidRpcProvider())
        }
    }.toEitherNel()
}
