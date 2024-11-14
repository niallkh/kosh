package kosh.domain.utils

import arrow.core.Either
import arrow.core.Ior
import arrow.core.Nel
import arrow.core.raise.IorRaise

inline fun <Error> IorRaise<Nel<Error>>.ensureAccumulating(condition: Boolean, raise: () -> Error) {
    return if (condition) Unit else accumulate(raise(), Unit)
}

inline fun <Error, A> IorRaise<Nel<Error>>.accumulate(error: Error, default: A): A {
    return Ior.bothNel(error, default).bind()
}

inline fun <Error, A> IorRaise<Nel<Error>>.nelAccumulate(errors: Nel<Error>, default: A): A {
    return Ior.Both(errors, default).bind()
}

inline fun <Error, A> IorRaise<Nel<Error>>.nelAccumulate(
    errors: Either<Nel<Error>, A>,
    default: () -> A,
): A = errors.fold({ nelAccumulate(it, default()) }, { it })

inline fun <Error, A> IorRaise<Nel<Error>>.accumulate(
    error: Either<Error, A>,
    default: () -> A,
): A = error.fold({ accumulate(it, default()) }, { it })
