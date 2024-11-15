package kosh.libs.reown

import android.app.Application
import android.content.Context
import co.touchlab.kermit.Logger
import com.reown.android.Core
import com.reown.android.CoreClient
import com.reown.android.relay.ConnectionType
import com.reown.sign.client.Sign
import com.reown.sign.client.SignClient
import com.reown.walletkit.client.Wallet
import com.reown.walletkit.client.WalletKit
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.collections.immutable.persistentHashMapOf
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import com.reown.walletkit.client.Wallet.Model.VerifyContext as ReownVerifyContext

class AndroidReownAdapter(
    private val projectId: String,
    private val context: Context,
    private val applicationScope: CoroutineScope,
) : ReownAdapter, WalletKit.WalletDelegate {
    private val logger = Logger.withTag("[K]AndroidReownAdapter")

    private val proposalRequestIds = atomic(persistentHashMapOf<String, Long>())
    private val newProposalFlow = MutableSharedFlow<SessionProposal>()
    private val proposalsState = MutableStateFlow<List<SessionProposal>>(listOf())

    private val newAuthenticationFlow = MutableSharedFlow<AuthenticationRequest>()
    private val authenticationsState = MutableStateFlow<List<AuthenticationRequest>>(listOf())

    private val newRequestsFlow = MutableSharedFlow<SessionRequest>()
    private val requestsState = MutableStateFlow<List<SessionRequest>>(listOf())

    private val sessionsState = MutableStateFlow<List<Session>>(listOf())

    private val pairCont = atomic<CancellableContinuation<ReownResult<Unit>>?>(null)

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
    }

    override suspend fun initialize() {
        logger.v { "initialize()" }

        val appMetaData = Core.Model.AppMetaData(
            name = "Kosh",
            description = "Mobile Wallet",
            url = "https://kosh.eth.limo",
            icons = persistentListOf("https://kosh.eth.limo/images/icon.webp"),
            redirect = "kosh://request",
            appLink = "https://kosh.eth.limo",
            linkMode = false,
        )

        CoreClient.initialize(
            projectId = projectId,
            connectionType = ConnectionType.MANUAL,
            application = context as Application,
            metaData = appMetaData,
            onError = { logger.e(it.throwable) { "Error happened in WC CoreClient" } },
        )

        suspendCoroutine { cont ->
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

            updateSessions().join()

            sessionsState.collect {
                callback(it)
            }
        }

        return { job.cancel() }
    }

    override suspend fun pair(uri: String): ReownResult<Unit> =
        suspendCancellableCoroutine { cont ->
            pairCont.update { cont }
            cont.invokeOnCancellation { pairCont.update { null } }

            WalletKit.pair(
                params = Wallet.Params.Pair(uri),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(ReownResult.Success())
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Pair failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

    override suspend fun getProposal(
        pairingTopic: String,
    ): ReownResult<SessionProposal> = WalletKit.getSessionProposals()
        .find { it.pairingTopic == pairingTopic }
        ?.let { proposal ->
            val requestIds = proposalRequestIds.value
            mapProposal(
                proposal = proposal,
                context = requestIds[pairingTopic]?.let(WalletKit::getVerifyContext)
            )
                .let { ReownResult.Success(it) }
        }
        ?: ReownResult.Failure(ReownFailure.NotFound("Proposal not found for topic: $pairingTopic"))

    override suspend fun approveProposal(
        pairingTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ): ReownResult<StringWrapper> {
        val proposal = WalletKit.getSessionProposals().find { it.pairingTopic == pairingTopic }
            ?: return ReownResult.Failure(ReownFailure.NotFound("Session proposal not found"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->
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
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(ReownResult.Success(StringWrapper(proposal.redirect)))
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Approve proposal failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateProposals()

        return redirect
    }

    override suspend fun rejectProposal(
        pairingTopic: String,
        reason: String,
    ): ReownResult<StringWrapper> {
        val proposal = WalletKit.getSessionProposals().find { it.pairingTopic == pairingTopic }
            ?: return ReownResult.Failure(ReownFailure.NotFound("Proposal not found for topic: $pairingTopic"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->

            WalletKit.rejectSession(
                params = Wallet.Params.SessionReject(proposal.proposerPublicKey, reason),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(ReownResult.Success(StringWrapper(proposal.redirect)))
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Reject proposal failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        withTimeoutOrNull(10.seconds) {
            do {
                delay(300.milliseconds)
                updateProposals()
            } while (proposalsState.value.any { it.pairingTopic == pairingTopic })
        }

        return redirect
    }

    override suspend fun getAuthentication(
        id: Long,
    ): ReownResult<AuthenticationRequest> = SignClient.getPendingAuthenticateRequests()
        .find { it.id == id }
        ?.toWallet()
        ?.let { mapAuth(it, WalletKit.getVerifyContext(id)) }
        ?.let { ReownResult.Success(it) }
        ?: ReownResult.Failure(ReownFailure.NotFound("Authentication not found for id: $id"))

    override suspend fun approveAuthentication(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
        signature: String,
    ): ReownResult<StringWrapper> {
        val authenticate = SignClient.getPendingAuthenticateRequests().find { it.id == id }
            ?.toWallet()
            ?: return ReownResult.Failure(ReownFailure.NotFound("Authentication not found for id: $id"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->
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
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(
                            (authenticate.participant.metadata?.redirect ?: "")
                                .let { ReownResult.Success(StringWrapper(it)) }
                        )
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Approve authentication failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateAuthentications()

        return redirect
    }

    override suspend fun getAuthenticationMessage(
        id: Long,
        issuer: String,
        supportedChains: List<String>,
        supportedMethods: List<String>,
    ): ReownResult<StringWrapper> {
        val authenticate = SignClient.getPendingAuthenticateRequests().find { it.id == id }
            ?.toWallet()
            ?: return ReownResult.Failure(ReownFailure.NotFound("Authentication not found for id: $id"))

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
            .let { ReownResult.Success(StringWrapper(it)) }
    }

    override suspend fun rejectAuthentication(
        id: Long,
        reason: String,
    ): ReownResult<StringWrapper> {
        val authenticate = SignClient.getPendingAuthenticateRequests().find { it.id == id }
            ?.toWallet()
            ?: return ReownResult.Failure(ReownFailure.NotFound("Authentication not found for id: $id"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->
            WalletKit.rejectSessionAuthenticate(
                params = Wallet.Params.RejectSessionAuthenticate(id, reason),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(
                            (authenticate.participant.metadata?.redirect ?: "")
                                .let { ReownResult.Success(StringWrapper(it)) }
                        )
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Reject authentication failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateAuthentications()

        return redirect
    }

    override suspend fun getRequest(
        id: Long,
    ): ReownResult<SessionRequest> = WalletKit.getListOfActiveSessions().asSequence()
        .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
        .find { it.request.id == id }
        ?.let { mapRequest(it, WalletKit.getVerifyContext(id)) }
        ?.let { ReownResult.Success(it) }
        ?: ReownResult.Failure(ReownFailure.NotFound("Request not found for id: $id"))

    override suspend fun approveRequest(
        id: Long,
        message: String,
    ): ReownResult<StringWrapper> {
        val request = WalletKit.getListOfActiveSessions().asSequence()
            .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
            .find { it.request.id == id }
            ?: return ReownResult.Failure(ReownFailure.NotFound("Request not found for id: $id"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->
            WalletKit.respondSessionRequest(
                params = Wallet.Params.SessionRequestResponse(
                    sessionTopic = request.topic,
                    jsonRpcResponse = Wallet.Model.JsonRpcResponse.JsonRpcResult(
                        id = id,
                        result = message,
                    )
                ),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(
                            (request.peerMetaData?.redirect ?: "")
                                .let { ReownResult.Success(StringWrapper(it)) }
                        )
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Approve request failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateRequests()

        return redirect
    }

    override suspend fun rejectRequest(
        id: Long,
        code: Int,
        message: String,
    ): ReownResult<StringWrapper> {
        val request = WalletKit.getListOfActiveSessions().asSequence()
            .flatMap { WalletKit.getPendingListOfSessionRequests(it.topic) }
            .find { it.request.id == id }
            ?: return ReownResult.Failure(ReownFailure.NotFound("Request not found for id: $id"))

        val redirect: ReownResult<StringWrapper> = suspendCancellableCoroutine { cont ->
            WalletKit.respondSessionRequest(
                params = Wallet.Params.SessionRequestResponse(
                    sessionTopic = request.topic,
                    jsonRpcResponse = Wallet.Model.JsonRpcResponse.JsonRpcError(
                        id = id,
                        code = code,
                        message = message,
                    )
                ),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(
                            (request.peerMetaData?.redirect ?: "")
                                .let { ReownResult.Success(StringWrapper(it)) }
                        )
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Reject request failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateRequests()

        return redirect
    }

    override suspend fun getSession(
        sessionTopic: String,
    ): ReownResult<Session> = WalletKit.getListOfActiveSessions()
        .find { it.topic == sessionTopic }
        ?.let { mapSession(it) }
        ?.let { ReownResult.Success(it) }
        ?: ReownResult.Failure(ReownFailure.NotFound("Session not found for topic: $sessionTopic"))

    override suspend fun updateSession(
        sessionTopic: String,
        chains: List<String>,
        accounts: List<String>,
        methods: List<String>,
        events: List<String>,
    ): ReownResult<Unit> = suspendCancellableCoroutine { cont ->
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
            onSuccess = {
                if (cont.isActive) {
                    cont.resume(ReownResult.Success())
                }
            },
            onError = {
                logger.w(it.throwable) { "Update session failed" }
                if (cont.isActive) {
                    cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                }
            },
        )
    }

    override suspend fun disconnectSession(
        sessionTopic: String,
    ): ReownResult<Unit> {
        val result: ReownResult<Unit> = suspendCancellableCoroutine { cont ->
            WalletKit.disconnectSession(
                params = Wallet.Params.SessionDisconnect(
                    sessionTopic = sessionTopic,
                ),
                onSuccess = {
                    if (cont.isActive) {
                        cont.resume(ReownResult.Success())
                    }
                },
                onError = {
                    logger.w(it.throwable) { "Disconnect session failed" }
                    if (cont.isActive) {
                        cont.resume(ReownResult.Failure(it.throwable.toReownResult()))
                    }
                },
            )
        }

        updateSessions()

        return result
    }

    override fun onConnectionStateChange(state: Wallet.Model.ConnectionState) {
        logger.v { "onConnectionStateChange: $state" }
        state.reason?.let {
            when (it) {
                is Wallet.Model.ConnectionState.Reason.ConnectionClosed -> {
                    logger.i { "Connection closed: ${it.message}" }
                }

                is Wallet.Model.ConnectionState.Reason.ConnectionFailed -> {
                    logger.i(it.throwable) { "Connection failed" }
                }
            }
        } ?: logger.i { "Connection connected" }
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

            updateProposals()
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

                updateAuthentications()
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

            updateRequests()
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
        logger.v { "onRequestExpired: $request" }
        updateRequests()
    }

    override fun onError(error: Wallet.Model.Error) {
        val proposalNotFoundMessage =
            "No proposal or pending session authenticate request for pairing topic"
        if (proposalNotFoundMessage in error.throwable.message.orEmpty()) {
            val continuation = pairCont.value
            if (continuation?.isActive == true) {
                continuation.resume(
                    ReownResult.Failure(ReownFailure.NotFound(proposalNotFoundMessage))
                )
            }
        }
        logger.e(error.throwable) { "Wallet error happened" }
    }

    private fun updateProposals() {
        applicationScope.launch {
            val requestIds = proposalRequestIds.value

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

    private fun updateSessions() = applicationScope.launch {
        val sessions = WalletKit.getListOfActiveSessions().map { session ->
            mapSession(session)
        }

        sessionsState.emit(sessions)
    }
}
