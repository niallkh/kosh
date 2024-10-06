package kosh.domain.repositories

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.wc.WcSession
import kosh.domain.models.wc.WcSessionProposal
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.Flow

interface ReownRepo {

    val newProposal: Flow<WcSessionProposal>

    val proposals: Flow<List<WcSessionProposal>>

    val newAuthentication: Flow<WcAuthentication>

    val authentications: Flow<List<WcAuthentication>>

    val newRequest: Flow<WcRequest>

    val requests: Flow<List<WcRequest>>

    val sessions: Flow<List<WcSession>>

    suspend fun initialize()

    suspend fun connect()

    suspend fun disconnect()

    suspend fun pair(uri: PairingUri): Either<WcFailure, Unit>

    suspend fun getProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, WcSessionProposal>

    suspend fun approveProposal(
        id: WcSessionProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit>

    suspend fun rejectProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, Unit>

    suspend fun getAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication>

    suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
        signature: Signature,
    ): Either<WcFailure, Unit>

    suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage>

    suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, Unit>

    suspend fun getSessionRequest(
        id: WcRequest.Id,
    ): Either<WcFailure, WcRequest>

    suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Unit>

    suspend fun rejectSessionRequest(
        id: WcRequest.Id,
    ): Either<WcFailure, Unit>

    suspend fun getSession(
        id: WcSession.Id,
    ): Either<WcFailure, WcSession>

    suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit>

    suspend fun disconnectSession(
        id: WcSession.Id,
    ): Either<WcFailure, Unit>

    companion object {
        val supportedMethods = persistentListOf(
            "personal_sign",
            "eth_signTypedData_v4",
            "eth_sendTransaction",
            "wallet_addEthereumChain",
            "wallet_watchAsset",
        )

        val supportedEvents = persistentListOf(
            "message",
            "chainChanged",
            "accountsChanged",
        )
    }
}
