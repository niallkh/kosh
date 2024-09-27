package kosh.data.wc2

import arrow.core.Either
import arrow.core.left
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.SessionTopic
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcEvent
import kosh.domain.models.wc.WcNamespace
import kosh.domain.models.wc.WcProposal
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.wc.WcSession
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.WcRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

class IosWcRepo : WcRepo {

    override val connected: Flow<Boolean>
        get() = flowOf(false)
    override val sessions: Flow<List<WcSession>>
        get() = flowOf(listOf())
    override val proposals: Flow<List<WcProposal>>
        get() = flowOf(listOf())
    override val requests: Flow<List<WcRequest>>
        get() = flowOf(listOf())
    override val authentications: Flow<List<WcAuthentication>>
        get() = flowOf(listOf())

    override fun proposalQueue(): Flow<WcProposal> {
        return emptyFlow()
    }

    override fun requestQueue(): Flow<WcRequest> {
        return emptyFlow()
    }

    override fun authenticationQueue(): Flow<WcAuthentication> {
        return emptyFlow()
    }

    override suspend fun initialize() {
    }

    override suspend fun connect() {
    }

    override suspend fun disconnect() {
    }

    override suspend fun disconnect(id: WcSession.Id): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun pair(uri: PairingUri): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getProposal(id: WcProposal.Id): Either<WcFailure, WcProposal> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getRequest(id: WcRequest.Id?): Either<WcFailure, WcRequest> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getAuthentication(id: WcAuthentication.Id): Either<WcFailure, WcAuthentication> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getSession(id: WcSession.Id): Either<WcFailure, WcSession> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getNamespace(id: WcProposal.Id): Either<WcFailure, WcNamespace> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun getNamespace(id: WcSession.Id): Either<WcFailure, WcNamespace> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun approveSessionProposal(
        id: WcProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun rejectSessionProposal(
        id: WcProposal.Id,
        reason: String,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        signature: Signature,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
        reason: String,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun rejectSessionRequest(
        id: WcRequest.Id,
        reason: String,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun emitEvent(
        sessionTopic: SessionTopic,
        event: WcEvent,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> {
        return WcFailure.NoConnection().left()
    }

    override suspend fun parseRpcRequest(
        requestChainId: ChainId?,
        method: String,
        params: String,
    ): Either<WcFailure, WcRequest.Call> {
        return WcFailure.NoConnection().left()
    }
}
