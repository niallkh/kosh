package kosh.domain.usecases.account

import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.toEitherNel
import kosh.domain.failure.AccountFailure
import kosh.domain.usecases.validation.Validator

val accountNameValidator: Validator<String, String, AccountFailure> = Validator {
    either {
        ensure(it.length in 2..50) {
            AccountFailure.InvalidAccountName()
        }
        it
    }.toEitherNel()
}
