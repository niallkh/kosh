package kosh.domain.usecases.reown

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.web3.Signature
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow

interface WcAuthenticationService {

    val authentications: Flow<ImmutableList<WcAuthentication>>

    suspend fun get(id: WcAuthentication.Id): Either<WcFailure, WcAuthentication>

    suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
    ): Either<WcFailure, WcAuthentication.Message>

    suspend fun approve(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
        signature: Signature,
    ): Either<WcFailure, Unit>

    suspend fun reject(id: WcAuthentication.Id): Either<WcFailure, Unit>
}

