package kosh.domain.usecases.validation

import arrow.core.EitherNel
import arrow.core.NonEmptyList
import arrow.core.flatMap
import arrow.core.raise.either
import arrow.core.raise.zipOrAccumulate
import arrow.core.right

fun interface Validator<I, O, E> : (I) -> EitherNel<E, O>

fun <I, A, B, E> Validator<I, A, E>.map(f: (A) -> B): Validator<I, B, E> =
    Validator { invoke(it).map(f) }

fun <I, O, E : E2, I2 : I, O2, E2> Validator<I, O, E>.flatMap(
    other: (O) -> Validator<I2, O2, E2>,
): Validator<I2, O2, E2> = Validator { i ->
    invoke(i).flatMap { o -> other(o).invoke(i) }
}

fun <I, O, E> Validator<I, O, E>.and(
    combine: (O, O) -> O = { _, o2 -> o2 },
    other: Validator<I, O, E>,
): Validator<I, O, E> = Validator { i ->
    either {
        zipOrAccumulate(
            { invoke(i).bindNel() },
            { other(i).bindNel() }
        ) { o1, o2 -> combine(o1, o2) }
    }
}

fun <I, E : E2, O : O2, I2 : I, E2, O2> Validator<I, O, E>.or(other: Validator<I2, O2, E2>): Validator<I2, O2, E2> =
    Validator { i ->
        invoke(i).fold(
            ifRight = { it.right() },
            ifLeft = { es -> other(i).mapLeft { es2 -> (es as NonEmptyList<E2>) + es2 } }
        )
    }
