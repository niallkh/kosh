package kosh.domain.failure

import arrow.core.left
import kosh.domain.serializers.Either

typealias TrezorAccountFailure = Either<TrezorFailure, AccountFailure>

fun <T> Either<TrezorFailure, T>.mapTF(): Either<TrezorAccountFailure, T> = mapLeft { it.left() }
