package kosh.domain.serializers

import arrow.core.Either
import arrow.core.Nel
import arrow.core.serialization.EitherSerializer
import arrow.core.serialization.NonEmptyListSerializer
import kotlinx.serialization.Serializable

typealias Nel<T> = @Serializable(with = NonEmptyListSerializer::class) Nel<T>
typealias Either<A, B> = @Serializable(with = EitherSerializer::class) Either<A, B>

