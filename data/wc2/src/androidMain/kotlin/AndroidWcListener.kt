package kosh.data.wc2

import co.touchlab.kermit.Logger
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.Sign.Model.SessionAuthenticate
import com.walletconnect.sign.client.Sign.Model.SessionProposal
import com.walletconnect.sign.client.Sign.Model.SessionRequest
import com.walletconnect.sign.client.Sign.Model.SessionUpdateResponse
import com.walletconnect.sign.client.SignClient
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

class AndroidWcListener : WcListener, SignClient.WalletDelegate {

    private val logger = Logger.withTag("[K]WcListener")

    override val connectionState = MutableStateFlow(false)

    private val proposalChannel = Channel<Verified<SessionProposal>>(Channel.CONFLATED)
    private val requestChannel = Channel<Verified<SessionRequest>>(Channel.CONFLATED)
    private val authenticationChannel =
        Channel<Verified<SessionAuthenticate>>(Channel.CONFLATED)

    override val proposal: Flow<Verified<SessionProposal>>
        get() = proposalChannel.receiveAsFlow()
    override val request: Flow<Verified<SessionRequest>>
        get() = requestChannel.receiveAsFlow()
    override val authentication: Flow<Verified<SessionAuthenticate>>
        get() = authenticationChannel.receiveAsFlow()

    override val proposalRequestIds = MutableStateFlow(persistentHashMapOf<String, Long>())

    override val smthChanged = MutableStateFlow(Any())

    override val onSessionAuthenticate: ((SessionAuthenticate, Sign.Model.VerifyContext) -> Unit) =
        { sessionAuthenticate, verifyContext ->
            logger.d { "onSessionAuthenticate()" }
            authenticationChannel.trySend(sessionAuthenticate to verifyContext)
            smthChanged.tryEmit(Any())
        }

    override fun onConnectionStateChange(state: Sign.Model.ConnectionState) {
        logger.d { "onConnectionStateChange(isAvailable=${state.isAvailable})" }
        connectionState.update { state.isAvailable }
        smthChanged.tryEmit(Any())
    }

    override fun onSessionDelete(deletedSession: Sign.Model.DeletedSession) {
        logger.d { "onSessionDelete()" }
        smthChanged.tryEmit(Any())
    }

    override fun onSessionExtend(session: Sign.Model.Session) {
        logger.d { "onSessionExpired()" }
        smthChanged.tryEmit(Any())
    }

    override fun onSessionProposal(
        sessionProposal: SessionProposal,
        verifyContext: Sign.Model.VerifyContext,
    ) {
        logger.d { "onSessionProposal()" }
        proposalRequestIds.update {
            it + (sessionProposal.pairingTopic to verifyContext.id)
        }
        proposalChannel.trySend(sessionProposal to verifyContext)
        smthChanged.tryEmit(Any())
    }

    override fun onSessionRequest(
        sessionRequest: SessionRequest,
        verifyContext: Sign.Model.VerifyContext,
    ) {
        logger.d { "onSessionRequest(id=${sessionRequest.request.id})" }
        requestChannel.trySend(sessionRequest to verifyContext)
        smthChanged.tryEmit(Any())
    }

    override fun onSessionSettleResponse(settleSessionResponse: Sign.Model.SettledSessionResponse) {
        logger.d { "onSessionSettleResponse()" }
        smthChanged.tryEmit(Any())
    }

    override fun onSessionUpdateResponse(sessionUpdateResponse: SessionUpdateResponse) {
        logger.d { "onSessionUpdateResponse()" }
        smthChanged.tryEmit(Any())
    }

    override fun onError(error: Sign.Model.Error) {
        logger.w(error.throwable) { "Error happened in WC" }
        smthChanged.tryEmit(Any())
    }

    override fun onChanged() {
        smthChanged.tryEmit(Any())
    }
}
