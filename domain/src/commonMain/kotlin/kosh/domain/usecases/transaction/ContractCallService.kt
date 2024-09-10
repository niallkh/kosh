package kosh.domain.usecases.transaction

import arrow.core.raise.either
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either
import kosh.domain.usecases.transaction.parser.ContractCallParser

class ContractCallService(
    private val parsers: List<ContractCallParser>,
) {

    suspend fun parse(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        input: ByteString,
    ): Either<Web3Failure, ContractCall> = either {
        parsers.firstNotNullOf {
            it.parse(chainId, from, to, value, input).bind()
        }
    }
}
