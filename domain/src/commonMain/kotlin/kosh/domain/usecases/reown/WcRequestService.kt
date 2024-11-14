package kosh.domain.usecases.reown

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.web3.Signature
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface WcRequestService {

    val requests: Flow<ImmutableList<WcRequest>>

    suspend fun get(id: WcRequest.Id?): Either<WcFailure, WcRequest>

    suspend fun onTypedSigned2(id: WcRequest.Id, signature: Signature): Either<WcFailure, Unit>

    suspend fun onPersonalSigned2(id: WcRequest.Id, signature: Signature): Either<WcFailure, Unit>

    suspend fun reject2(id: WcRequest.Id): Either<WcFailure, Unit>

    fun onTransactionSend(id: WcRequest.Id, hash: Hash): Job

    fun onNetworkAdded(id: WcRequest.Id, sessionTopic: SessionTopic, chainId: ChainId): Job

    fun onAssetWatched(id: WcRequest.Id): Job

    fun reject(id: WcRequest.Id): Job
}

