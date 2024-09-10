package kosh.domain.failure

import kosh.domain.serializers.Either


typealias NetworkTokenFailure = Either<NetworkFailure, TokenFailure>


