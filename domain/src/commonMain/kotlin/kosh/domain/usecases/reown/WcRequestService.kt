package kosh.domain.usecases.reown

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcRequest
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface WcRequestService {

    val requests: Flow<List<WcRequest>>

    suspend fun get(id: WcRequest.Id?): Either<WcFailure, WcRequest>

    fun onTypedSigned(id: WcRequest.Id, data: ByteString): Job

    fun onPersonalSigned(id: WcRequest.Id, data: ByteString): Job

    fun onTransactionSend(id: WcRequest.Id, hash: Hash): Job

    fun onNetworkAdded(id: WcRequest.Id, sessionTopic: SessionTopic, chainId: ChainId): Job

    fun onAssetWatched(id: WcRequest.Id): Job

    fun reject(id: WcRequest.Id): Job
}

