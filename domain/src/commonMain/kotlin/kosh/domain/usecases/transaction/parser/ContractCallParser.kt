package kosh.domain.usecases.transaction.parser

import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.ContractCall
import kosh.domain.serializers.BigInteger
import kosh.domain.serializers.Either

interface ContractCallParser {

    suspend fun parse(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        input: ByteString,
    ): Either<Web3Failure, ContractCall?>
}
