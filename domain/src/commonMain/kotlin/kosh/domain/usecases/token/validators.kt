package kosh.domain.usecases.token

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.toEitherNel
import kosh.domain.failure.TokenFailure
import kosh.domain.usecases.validation.Validator

val tokenNameValidator: Validator<String, String, TokenFailure> = Validator {
    either {
        ensure(it.length in 2..50) {
            TokenFailure.InvalidTokenName()
        }
        it
    }.toEitherNel()
}

val tokenSymbolValidator: Validator<String, String, TokenFailure> = Validator {
    either {
        ensure(it.length in 2..10) {
            TokenFailure.InvalidTokenSymbol()
        }
        it
    }.toEitherNel()
}
