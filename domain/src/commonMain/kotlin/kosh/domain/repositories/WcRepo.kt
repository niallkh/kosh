package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.SessionTopic
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcEvent
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.wc.WcSession
import kosh.domain.models.wc.WcSessionProposal
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kotlinx.coroutines.flow.Flow

@Deprecated("")
interface WcRepo : Repository {

    val connected: Flow<Boolean>

    val sessions: Flow<List<WcSession>>

    val proposals: Flow<List<WcSessionProposal>>

    val requests: Flow<List<WcRequest>>

    val authentications: Flow<List<WcAuthentication>>

    fun proposalQueue(): Flow<WcSessionProposal>

    fun requestQueue(): Flow<WcRequest>

    fun authenticationQueue(): Flow<WcAuthentication>

    suspend fun initialize()

    suspend fun connect()

    suspend fun disconnect()

    suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Unit>

    suspend fun getProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, WcSessionProposal>

    suspend fun getRequest(
        id: WcRequest.Id?,
    ): Either<WcFailure, WcRequest>

    suspend fun getAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication>

    suspend fun getSession(
        id: WcSession.Id,
    ): Either<WcFailure, WcSession>

    suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage>

    suspend fun approveSessionProposal(
        id: WcSessionProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit>

    suspend fun rejectSessionProposal(
        id: WcSessionProposal.Id,
        reason: String,
    ): Either<WcFailure, Unit>

    suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        signature: Signature,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, Unit>

    suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
        reason: String,
    ): Either<WcFailure, Unit>

    suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Unit>

    suspend fun rejectSessionRequest(
        id: WcRequest.Id,
        reason: String,
    ): Either<WcFailure, Unit>

    suspend fun emitEvent(
        sessionTopic: SessionTopic,
        event: WcEvent,
    ): Either<WcFailure, Unit>

    suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit>

    suspend fun disconnect(
        id: WcSession.Id,
    ): Either<WcFailure, Unit>

    suspend fun parseRpcRequest(
        requestChainId: ChainId?,
        method: String,
        params: String,
    ): Either<WcFailure, WcRequest.Call>
}
