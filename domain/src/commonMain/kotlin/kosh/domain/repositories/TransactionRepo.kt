package kosh.domain.repositories

import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.web3.ReceiptLogs
import kosh.domain.models.web3.Signature
import kosh.domain.serializers.Either

interface TransactionRepo : Repository {

    suspend fun nextNonce(
        chainId: ChainId,
        address: Address,
    ): Either<Web3Failure, ULong>

    suspend fun send(
        chainId: ChainId,
        transaction: Signature,
    ): Either<Web3Failure, Hash>

    suspend fun receipt(
        networkId: NetworkEntity.Id,
        hash: Hash,
    ): Either<Web3Failure, ReceiptLogs?>
}
