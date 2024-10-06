package kosh.libs.reown

import android.app.Application
import android.content.Context
import arrow.core.continuations.AtomicRef
import arrow.core.continuations.update
import co.touchlab.kermit.Logger
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.sign.client.Sign
import com.reown.sign.client.SignClient
import com.reown.walletkit.client.Wallet
import com.reown.walletkit.client.WalletKit
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.reown.walletkit.client.Wallet.Model.VerifyContext as ReownVerifyContext

class AndroidReownAdapter(
    private val projectId: String,
    private val context: Context,
    private val applicationScope: CoroutineScope,
) : ReownAdapter, WalletKit.WalletDelegate {
    private val proposalRequestIds = AtomicRef(persistentHashMapOf<String, Long>())
    private val newProposalFlow = MutableSharedFlow<SessionProposal>()
    private val proposalsState = MutableStateFlow<List<SessionProposal>>(listOf())

    private val newAuthenticationFlow = MutableSharedFlow<AuthenticationRequest>()
    private val authenticationsState =
        MutableStateFlow<List<AuthenticationRequest>>(listOf())

    private val newRequestsFlow = MutableSharedFlow<SessionRequest>()
    private val requestsState = MutableStateFlow<List<SessionRequest>>(listOf())

    private val sessionsState = MutableStateFlow<List<Session>>(listOf())

    private val logger = Logger.withTag("[K]AndroidReownAdapter")

    init {
        proposalsState.subscriptionCount
            .map { it > 0 }
            .distinctUntilChanged()
            .onEach { if (it) updateProposals() }
            .launchIn(applicationScope)

        newProposalFlow
            .onEach { updateProposals() }
            .launchIn(applicationScope)

        authenticationsState.subscriptionCount
            .map { it > 0 }
            .distinctUntilChanged()
            .onEach { if (it) updateAuthentications() }
            .launchIn(applicationScope)

        newAuthenticationFlow
            .onEach { updateAuthentications() }
            .launchIn(applicationScope)

        requestsState.subscriptionCount
            .map { it > 0 }
            .distinctUntilChanged()
            .onEach { if (it) updateRequests() }
            .launchIn(applicationScope)

        newRequestsFlow
            .onEach { updateRequests() }
            .launchIn(applicationScope)

        sessionsState.subscriptionCount
            .map { it > 0 }
            .distinctUntilChanged()
            .onEach { if (it) updateSessions() }
            .launchIn(applicationScope)
    }

    override suspend fun initialize() {
        logger.v { "initialize()" }

        val appMetaData = Core.Model.AppMetaData(
            name = "Kosh",
            description = "Mobile Wallet",
            url = "https://kosh.eth.limo",
            icons = persistentListOf("https://kosh.eth.limo/images/icon.webp"),
            redirect = "kosh://request",
        )

        CoreClient.initialize(
            projectId = projectId,
            connectionType = ConnectionType.MANUAL,
            application = context as Application,
            metaData = appMetaData,
            onError = { logger.e(it.throwable) { "Error happened core wc" } },
        )

        suspendCancellableCoroutine { cont ->
            SignClient.initialize(
                init = Sign.Params.Init(CoreClient),
                onSuccess = { cont.resume(Unit) },
                onError = { cont.resumeWithException(it.throwable) },
            )
        }

        try {
            WalletKit.initialize(
                params = Wallet.Params.Init(CoreClient),
                onSuccess = { },
                onError = { },
            )
        } catch (_: Throwable) {
        }

        WalletKit.setWalletDelegate(this)
    }

    override suspend fun connect() {
        CoreClient.Relay.connect { err: Core.Model.Error ->
            logger.e(err.throwable) { "Connect failed" }
        }
    }

    override suspend fun disconnect() {
        CoreClient.Relay.disconnect { err: Core.Model.Error ->
            logger.e(err.throwable) { "Disconnect failed" }
        }
    }

    override fun getNewProposal(callback: (SessionProposal) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            newProposalFlow.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getProposals(callback: (List<SessionProposal>) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            proposalsState.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getNewAuthentication(callback: (AuthenticationRequest) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            newAuthenticationFlow.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getAuthentications(callback: (List<AuthenticationRequest>) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            authenticationsState.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getNewRequest(callback: (SessionRequest) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            newRequestsFlow.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getRequests(callback: (List<SessionRequest>) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            requestsState.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override fun getSessions(callback: (List<Session>) -> Unit): () -> Unit {
        val job = applicationScope.launch {
            sessionsState.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override suspend fun pair(uri: String): Unit = suspendCancellableCoroutine { cont ->
        WalletKit.pair(
            params = Wallet.Params.Pair(uri),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Pair failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    override suspend fun getProposal(
        pairingTopic: String,
    ): SessionProposal? = WalletKit.getSessionProposals()
        .find { it.pairingTopic == pairingTopic }
        ?.let { proposal ->
            val requestIds = proposalRequestIds.get()
            mapProposal(
                proposal = proposal,
                context = requestIds[pairingTopic]?.let(WalletKit::getVerifyContext)
            )
        }

    override suspend fun approveProposal(
        pairingTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ) = suspendCancellableCoroutine { cont ->
        val proposal = WalletKit.getSessionProposals().find { it.pairingTopic == pairingTopic }
            ?: error("Session proposal not found")

        WalletKit.approveSession(
            params = Wallet.Params.SessionApprove(
                proposerPublicKey = proposal.proposerPublicKey,
                namespaces = mapOf(
                    EIP155 to Wallet.Model.Namespace.Session(
                        chains = chains,
                        accounts = accounts,
                        events = events,
                        methods = methods,
                    )
                )
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Approve proposal failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateProposals()
        updateSessions()
    }

    override suspend fun rejectProposal(
        pairingTopic: String,
        reason: String,
    ) = suspendCancellableCoroutine { cont ->
        val proposal = WalletKit.getSessionProposals().find { it.pairingTopic == pairingTopic }
            ?: error("Session proposal not found")

        WalletKit.rejectSession(
            params = Wallet.Params.SessionReject(proposal.proposerPublicKey, reason),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Reject proposal failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateProposals()
    }

    override suspend fun getAuthentication(
        id: Long,
    ): AuthenticationRequest? = SignClient.getPendingAuthenticateRequests()
        .find { it.id == id }
        ?.toWallet()
        ?.let { mapAuth(it, WalletKit.getVerifyContext(id)) }

    override suspend fun approveAuthentication(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
        signature: String,
    ) = suspendCancellableCoroutine { cont ->
        val authenticate = SignClient.getPendingAuthenticateRequests().find { it.id == id }
            ?.toWallet()
            ?: error("Authentication not found")

        val payloadParams = WalletKit.generateAuthPayloadParams(
            payloadParams = authenticate.payloadParams,
            supportedChains = supportedChains,
            supportedMethods = supportedMethods,
        )

        val authObject = WalletKit.generateAuthObject(
            payloadParams = payloadParams,
            issuer = issuer,
            signature = Wallet.Model.Cacao.Signature("eip191", signature)
        )

        WalletKit.approveSessionAuthenticate(
            params = Wallet.Params.ApproveSessionAuthenticate(
                id = id,
                auths = listOf(authObject)
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Approve authentication failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateAuthentications()
    }

    override suspend fun getAuthenticationMessage(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
    ): String {
        val authenticate = SignClient.getPendingAuthenticateRequests().find { it.id == id }
            ?.toWallet()
            ?: error("Authentication not found")

        val payloadParams = WalletKit.generateAuthPayloadParams(
            payloadParams = authenticate.payloadParams,
            supportedChains = supportedChains,
            supportedMethods = supportedMethods,
        )

        return WalletKit.formatAuthMessage(
            Wallet.Params.FormatAuthMessage(
                payloadParams = payloadParams,
                issuer = issuer,
            )
        )
    }

    override suspend fun rejectAuthentication(
        id: Long,
        reason: String,
    ) = suspendCancellableCoroutine { cont ->
        WalletKit.rejectSessionAuthenticate(
            params = Wallet.Params.RejectSessionAuthenticate(id, reason),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Reject authentication failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateAuthentications()
    }

    override suspend fun getRequest(
        id: Long,
    ): SessionRequest? = WalletKit.getListOfActiveSessions().asSequence()
        .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
        .find { it.request.id == id }
        ?.let { mapRequest(it, WalletKit.getVerifyContext(id)) }

    override suspend fun approveRequest(
        id: Long,
        message: String,
    ) = suspendCancellableCoroutine { cont ->
        val request = WalletKit.getListOfActiveSessions().asSequence()
            .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
            .find { it.request.id == id }
            ?: error("Session request not found")

        WalletKit.respondSessionRequest(
            params = Wallet.Params.SessionRequestResponse(
                sessionTopic = request.topic,
                jsonRpcResponse = Wallet.Model.JsonRpcResponse.JsonRpcResult(
                    id = id,
                    result = message,
                )
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Approve request failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateRequests()
    }

    override suspend fun rejectRequest(
        id: Long,
        code: Int,
        message: String,
    ) = suspendCancellableCoroutine { cont ->
        val request = WalletKit.getListOfActiveSessions().asSequence()
            .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
            .find { it.request.id == id }
            ?: error("Session request not found")

        WalletKit.respondSessionRequest(
            params = Wallet.Params.SessionRequestResponse(
                sessionTopic = request.topic,
                jsonRpcResponse = Wallet.Model.JsonRpcResponse.JsonRpcError(
                    id = id,
                    code = code,
                    message = message,
                )
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Reject request failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateRequests()
    }

    override suspend fun getSession(
        sessionTopic: String,
    ): Session? = WalletKit.getListOfActiveSessions()
        .find { it.topic == sessionTopic }
        ?.let { mapSession(it) }

    override suspend fun updateSession(
        sessionTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ) = suspendCancellableCoroutine { cont ->
        WalletKit.updateSession(
            params = Wallet.Params.SessionUpdate(
                sessionTopic = sessionTopic,
                namespaces = mapOf(
                    EIP155 to Wallet.Model.Namespace.Session(
                        chains = chains,
                        accounts = accounts,
                        events = events,
                        methods = methods,
                    )
                ),
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Update session failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    override suspend fun disconnectSession(
        sessionTopic: String,
    ) = suspendCancellableCoroutine { cont ->
        WalletKit.disconnectSession(
            params = Wallet.Params.SessionDisconnect(
                sessionTopic = sessionTopic,
            ),
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Disconnect session failed" }
                if (cont.isActive) {
                    cont.resumeWithException(it.throwable)
                }
            },
        )
    }.also {
        updateSessions()
    }

    override fun onConnectionStateChange(state: Wallet.Model.ConnectionState) {
        logger.v { "onConnectionStateChange: $state" }
        state.reason?.let {
            when (it) {
                is Wallet.Model.ConnectionState.Reason.ConnectionClosed -> {
                    logger.i { "Connection closed: ${it.message}" }
                }

                is Wallet.Model.ConnectionState.Reason.ConnectionFailed -> {
                    logger.e(it.throwable) { "Connection failed" }
                }
            }
        }
    }

    override fun onSessionProposal(
        sessionProposal: Wallet.Model.SessionProposal,
        verifyContext: ReownVerifyContext,
    ) {
        logger.v { "onSessionProposal: $sessionProposal" }
        applicationScope.launch {
            proposalRequestIds.update {
                it + (sessionProposal.pairingTopic to verifyContext.id)
            }
            newProposalFlow.emit(mapProposal(sessionProposal, verifyContext))
        }
    }

    override fun onProposalExpired(proposal: Wallet.Model.ExpiredProposal) {
        logger.v { "onProposalExpired: $proposal" }
        updateProposals()
    }

    override val onSessionAuthenticate =
        { auth: Wallet.Model.SessionAuthenticate, ctx: ReownVerifyContext ->
            logger.v { "onSessionAuthenticate: $auth, $ctx" }
            applicationScope.launch {
                newAuthenticationFlow.emit(mapAuth(auth, ctx))
            }
            Unit
        }

    override fun onSessionRequest(
        sessionRequest: Wallet.Model.SessionRequest,
        verifyContext: ReownVerifyContext,
    ) {
        logger.v { "onSessionRequest: $sessionRequest, $verifyContext" }
        applicationScope.launch {
            newRequestsFlow.emit(mapRequest(sessionRequest, verifyContext))
        }
    }

    override fun onSessionDelete(sessionDelete: Wallet.Model.SessionDelete) {
        logger.v { "onSessionDelete: $sessionDelete" }
        updateSessions()
    }

    override fun onSessionExtend(session: Wallet.Model.Session) {
        logger.v { "onSessionExtend: $session" }
        updateSessions()
    }

    override fun onSessionSettleResponse(settleSessionResponse: Wallet.Model.SettledSessionResponse) {
        logger.v { "onSessionSettleResponse: $settleSessionResponse" }
        updateSessions()
    }

    override fun onSessionUpdateResponse(sessionUpdateResponse: Wallet.Model.SessionUpdateResponse) {
        logger.v { "onSessionUpdateResponse: $sessionUpdateResponse" }
        updateSessions()
    }

    override fun onRequestExpired(request: Wallet.Model.ExpiredRequest) {
        logger.v { "onSessionExpired: $request" }
        updateRequests()
    }

    override fun onError(error: Wallet.Model.Error) {
        logger.e(error.throwable) { "Error happened" }
    }

    private fun updateProposals() {
        applicationScope.launch {
            val requestIds = proposalRequestIds.get()

            val proposals = WalletKit.getSessionProposals().map { proposal ->
                mapProposal(
                    proposal = proposal,
                    context = requestIds[proposal.pairingTopic]?.let(WalletKit::getVerifyContext)
                )
            }

            proposalsState.emit(proposals)
        }
    }

    private fun updateAuthentications() {
        applicationScope.launch {
            val authentications = SignClient.getPendingAuthenticateRequests().map { auth ->
                mapAuth(
                    authenticate = auth.toWallet(),
                    context = WalletKit.getVerifyContext(auth.id)
                )
            }

            authenticationsState.emit(authentications)
        }
    }

    private fun updateRequests() {
        applicationScope.launch {
            val requests = WalletKit.getListOfActiveSessions().asSequence()
                .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
                .map { request ->
                    mapRequest(
                        request = request,
                        context = WalletKit.getVerifyContext(request.request.id)
                    )
                }

            requestsState.emit(requests.toList())
        }
    }

    private fun updateSessions() {
        applicationScope.launch {
            val sessions = WalletKit.getListOfActiveSessions().map { session ->
                mapSession(session)
            }

            sessionsState.emit(sessions)
        }
    }
}
