package kosh.domain.repositories

import kosh.domain.failure.Web3Failure
import kosh.domain.models.FunSelector
import kosh.domain.serializers.Either

interface FunctionSignatureRepo : Repository {

    suspend fun get(funSelector: FunSelector): Either<Web3Failure, String?>
}
