package kosh.domain.repositories

import com.ionspin.kotlin.bignum.integer.BigInteger
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.Web3Failure
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.web3.GasEstimation
import kosh.domain.models.web3.GasPrices
import kosh.domain.serializers.Either

interface GasRepo : Repository {

    suspend fun gasPrices(id: NetworkEntity.Id): Either<Web3Failure, GasPrices>

    suspend fun estimate(
        chainId: ChainId,
        from: Address,
        to: Address?,
        value: BigInteger,
        data: ByteString?,
        gas: ULong?,
    ): Either<Web3Failure, GasEstimation>
}
