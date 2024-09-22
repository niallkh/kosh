package kosh.data.wc2

import android.app.Application
import android.content.Context
import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.catch
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.withError
import arrow.core.right
import co.touchlab.kermit.Logger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.walletconnect.android.Core
import com.walletconnect.android.CoreClient
import com.walletconnect.android.internal.common.di.AndroidCommonDITags
import com.walletconnect.android.internal.common.exception.CannotFindSequenceForTopic
import com.walletconnect.android.internal.common.exception.ExpiredPairingException
import com.walletconnect.android.internal.common.exception.ExpiredPairingURIException
import com.walletconnect.android.internal.common.exception.MalformedWalletConnectUri
import com.walletconnect.android.internal.common.exception.NoConnectivityException
import com.walletconnect.android.internal.common.exception.PairWithExistingPairingIsNotAllowed
import com.walletconnect.android.internal.common.exception.RequestExpiredException
import com.walletconnect.android.internal.common.wcKoinApp
import com.walletconnect.android.relay.ConnectionType
import com.walletconnect.sign.client.Sign
import com.walletconnect.sign.client.Sign.Model.Validation
import com.walletconnect.sign.client.SignClient
import com.walletconnect.sign.client.SignClient.formatAuthMessage
import com.walletconnect.sign.client.utils.generateAuthObject
import com.walletconnect.sign.client.utils.generateAuthPayloadParams
import kosh.domain.failure.WcFailure
import kosh.domain.failure.WcFailure.WcInvalidDapp
import kosh.domain.models.Address
import kosh.domain.models.ByteString
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainAddress.Companion.fromCaip10
import kosh.domain.models.ChainId
import kosh.domain.models.ChainId.Companion.fromCaip2
import kosh.domain.models.Uri
import kosh.domain.models.caip10
import kosh.domain.models.caip2
import kosh.domain.models.did
import kosh.domain.models.wc.DappMetadata
import kosh.domain.models.wc.PairingTopic
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.SessionTopic
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcEvent
import kosh.domain.models.wc.WcNamespace
import kosh.domain.models.wc.WcProposal
import kosh.domain.models.wc.WcRequest
import kosh.domain.models.wc.WcSession
import kosh.domain.models.wc.WcVerifyContext
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.JsonTypeData
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.WcRepo
import kosh.domain.repositories.suspendLazy
import kosh.domain.serializers.BigInteger
import kosh.eth.abi.json.JsonEip712
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.bytestring.hexToByteString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.core.qualifier.named
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import com.walletconnect.foundation.util.Logger as WcLogger

private const val EIP155 = "eip155"

class AndroidWcRepo(
    private val context: Context,
    private val projectId: String,
    private val json: Json,
    private val wcConnectionController: WcConnectionController,
    val wcListener: WcListener,
) : WcRepo {

    private val logger = Logger.withTag("[K]WcRepo")

    private val sign = suspendLazy {
        wc()
        SignClient
    }

    private val core = suspendLazy {
        wc()
        CoreClient
    }

    private val wc = suspendLazy {
        withContext(NonCancellable) {
            init()
        }
    }

    override val connected: Flow<Boolean>
        get() = wcListener.connectionState
            .flowOn(Dispatchers.Default)

    override val sessions: Flow<List<WcSession>>
        get() = wcListener.smthChanged
            .conflate()
            .map { sign().getListOfActiveSessions().map { wcSession(it) } }
            .flowOn(Dispatchers.Default)

    override val proposals: Flow<List<WcProposal>>
        get() = wcListener.smthChanged
            .conflate()
            .map {
                sign().getSessionProposals()
                    .mapNotNull { wcProposal(it).getOrNull() }
                    .reversed()
            }
            .flowOn(Dispatchers.Default)

    override val requests: Flow<List<WcRequest>>
        get() = wcListener.smthChanged
            .conflate()
            .map {
                sign().getListOfActiveSessions()
                    .flatMap { sign().getPendingSessionRequests(it.topic) }
                    .mapNotNull { request -> wcRequest(request).getOrNull() }
                    .reversed()
            }
            .flowOn(Dispatchers.Default)

    override val authentications: Flow<List<WcAuthentication>>
        get() = wcListener.smthChanged
            .conflate()
            .map {
                sign().getPendingAuthenticateRequests()
                    .mapNotNull { authenticate -> wcAuthentication(authenticate).getOrNull() }
                    .reversed()
            }
            .flowOn(Dispatchers.Default)

    override suspend fun initialize(): Unit = withContext(Dispatchers.Default) {
        logger.d { "initialize()" }
        wc()
    }

    private suspend fun init() = withContext(Dispatchers.Default) {
        logger.d { "init()" }

        wcKoinApp.koin.declare<WcLogger>(WcLoggerAdapter, named(AndroidCommonDITags.LOGGER))

        val serverUrl = "wss://relay.walletconnect.com?projectId=$projectId"

        val appMetaData = Core.Model.AppMetaData(
            name = "Kosh",
            description = "Mobile Wallet",
            url = "https://kosh.eth.limo",
            icons = persistentListOf("https://kosh.eth.limo/images/icon.webp"),
            redirect = "kosh://request",
        )

        CoreClient.initialize(
            relayServerUrl = serverUrl,
            connectionType = ConnectionType.MANUAL,
            application = context as Application,
            metaData = appMetaData,
            onError = {
                logger.e(it.throwable) { "Error happened core wc" }
            },
        )

        suspendCancellableCoroutine { cont ->
            SignClient.initialize(
                init = Sign.Params.Init(CoreClient),
                onSuccess = { cont.resume(Unit) },
                onError = {
                    cont.resumeWithException(it.throwable)
                },
            )
        }

        SignClient.setWalletDelegate(wcListener as SignClient.WalletDelegate)

        logger.d { "init finished" }
    }

    override suspend fun connect() = withContext(Dispatchers.Default) {
        logger.d { "connect()" }
        wc()
        wcConnectionController.connect()
    }

    override suspend fun disconnect() = withContext(Dispatchers.Default + NonCancellable) {
        logger.d { "disconnect()" }
        wc()
        wcConnectionController.disconnect()
    }

    override fun proposalQueue(): Flow<WcProposal> = wcListener.proposal
        .filter { (proposal) ->
            sign().getSessionProposals().any {
                it.pairingTopic == proposal.pairingTopic
            }
        }
        .mapNotNull { (proposal, verify) -> wcProposal(proposal, verify).getOrNull() }
        .flowOn(Dispatchers.Default)

    override fun authenticationQueue(): Flow<WcAuthentication> = wcListener.authentication
        .filter { (authenticate, _) ->
            sign().getPendingAuthenticateRequests().any {
                it.id == authenticate.id
            }
        }
        .mapNotNull { (authenticate, verify) -> wcAuthentication(authenticate, verify).getOrNull() }
        .flowOn(Dispatchers.Default)

    override fun requestQueue(): Flow<WcRequest> = wcListener.request
        .filter { (request, _) ->
            sign().getPendingSessionRequests(request.topic).any {
                it.request.id == request.request.id
            }
        }
        .mapNotNull { (request, verify) -> wcRequest(request, verify).getOrNull() }
        .flowOn(Dispatchers.Default)

    override suspend fun getProposal(
        id: WcProposal.Id,
    ): Either<WcFailure, WcProposal> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getProposal(id=${id.pairingTopic.value})" }
            getSessionProposal(id.pairingTopic)?.let {
                wcProposal(proposal = it).bind()
            } ?: raise(WcFailure.ProposalNotFound())
        }
    }

    override suspend fun getSession(
        id: WcSession.Id,
    ): Either<WcFailure, WcSession> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getSession(id=${id.sessionTopic.value})" }
            getActiveSession(id.sessionTopic)
                ?.let { wcSession(it) }
                ?: raise(WcFailure.SessionNotFound())
        }
    }

    override suspend fun getRequest(
        id: WcRequest.Id?,
    ): Either<WcFailure, WcRequest> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getRequest(id=${id?.value})" }

            wcRequest(getPendingRequest(id)).bind()
        }
    }

    private suspend fun Raise<WcFailure>.getPendingRequest(
        id: WcRequest.Id?,
    ): Sign.Model.SessionRequest = sign().getListOfActiveSessions()
        .flatMap { sign().getPendingSessionRequests(it.topic) }
        .find { id == null || it.request.id == id.value }
        ?: raise(WcFailure.RequestNotFound())

    override suspend fun getAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getAuthentication(id=${id.value})" }

            wcAuthentication(getPendingAuthentication(id)).bind()
        }
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getAuthenticationMessage(id=${id.value})" }
            val authenticate = getPendingAuthentication(id)

            val payloadParams = generateAuthPayloadParams(
                payloadParams = authenticate.payloadParams,
                supportedChains = supportedChains.map { it.caip2() },
                supportedMethods = createNamespace().methods,
            )

            formatAuthMessage(
                Sign.Params.FormatMessage(
                    payloadParams = payloadParams,
                    iss = ChainAddress(chain, account).did,
                )
            ).let {
                EthMessage(it)
            }
        }
    }

    override suspend fun getNamespace(
        id: WcProposal.Id,
    ): Either<WcFailure, WcNamespace> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getNamespace(id=${id.pairingTopic.value})" }
            getSessionProposal(id.pairingTopic)?.let {
                wcNamespace(
                    requiredNamespace = it.requiredNamespaces[EIP155],
                    optionalNamespace = it.optionalNamespaces[EIP155],
                    approvedNamespace = null,
                )
            }
                ?: raise(WcFailure.ProposalNotFound())
        }
    }

    override suspend fun getNamespace(
        id: WcSession.Id,
    ): Either<WcFailure, WcNamespace> = withContext(Dispatchers.Default) {
        either {
            logger.d { "getNamespace(id=${id.sessionTopic.value})" }
            getActiveSession(id.sessionTopic)?.let {
                wcNamespace(
                    requiredNamespace = it.requiredNamespaces[EIP155],
                    optionalNamespace = it.optionalNamespaces?.get(EIP155),
                    approvedNamespace = it.namespaces[EIP155],
                )
            } ?: raise(WcFailure.SessionNotFound())
        }
    }

    private suspend fun getSessionProposal(pairingTopic: PairingTopic) =
        sign().getSessionProposals()
            .find { it.pairingTopic == pairingTopic.value }

    private suspend fun getActiveSession(sessionTopic: SessionTopic): Sign.Model.Session? =
        sign().getActiveSessionByTopic(sessionTopic.value)

    override suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "pair()" }
            core().pair(pair = Core.Params.Pair(uri.value)).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun approveSessionProposal(
        id: WcProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "approveSessionProposal(id=${id.pairingTopic.value})" }
            ensure(approvedAccounts.isNotEmpty()) {
                WcInvalidDapp.NoApprovedAccounts()
            }

            val proposal = getSessionProposal(id.pairingTopic)
                ?: raise(WcFailure.SessionNotFound())

            val params = Sign.Params.Approve(
                proposerPublicKey = proposal.proposerPublicKey,
                namespaces = mapOf(
                    EIP155 to createNamespace(approvedAccounts)
                ),
            )

            sign().approveSession(params).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun rejectSessionProposal(
        id: WcProposal.Id,
        reason: String,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "rejectSessionProposal(id=${id.pairingTopic.value})" }
            val proposal = getSessionProposal(id.pairingTopic)
                ?: raise(WcFailure.SessionNotFound())

            val params = Sign.Params.Reject(proposal.proposerPublicKey, reason)
            sign().rejectSession(params).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: Address,
        chain: ChainId,
        signature: Signature,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "approveAuthentication(id=${id.value})" }
            val authenticate = getPendingAuthentication(id)

            val payloadParams = generateAuthPayloadParams(
                payloadParams = authenticate.payloadParams,
                supportedChains = supportedChains.map { it.caip2() },
                supportedMethods = createNamespace().methods,
            )

            val authObject = generateAuthObject(
                payload = payloadParams,
                issuer = ChainAddress(chain, account).did,
                signature = Sign.Model.Cacao.Signature("eip191", signature.data.toString())
            )

            sign().approveAuthentication(
                Sign.Params.ApproveAuthenticate(
                    id = authenticate.id,
                    cacaos = persistentListOf(authObject),
                )
            ).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
        reason: String,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "rejectAuthentication(id=${id.value})" }

            sign().rejectAuthenticate(Sign.Params.RejectAuthenticate(id.value, reason)).bind()
            wcListener.onChanged()
        }
    }

    private suspend fun Raise<WcFailure>.getPendingAuthentication(
        id: WcAuthentication.Id,
    ): Sign.Model.SessionAuthenticate = sign().getPendingAuthenticateRequests()
        .find { it.id == id.value }
        ?: raise(WcFailure.AuthenticationNotFound())

    override suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "updateSession(id=${id.sessionTopic.value})" }
            ensure(approvedAccounts.isNotEmpty()) {
                WcInvalidDapp.NoApprovedAccounts()
            }

            val session = getActiveSession(id.sessionTopic)
                ?: raise(WcFailure.SessionNotFound())

            val params = Sign.Params.Update(
                sessionTopic = session.topic,
                namespaces = mapOf(
                    EIP155 to createNamespace(approvedAccounts)
                )
            )

            sign().update(params)
            wcListener.onChanged()
        }
    }

    override suspend fun emitEvent(
        sessionTopic: SessionTopic,
        event: WcEvent,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "emitEvent(${sessionTopic.value})" }

            val params = Sign.Params.Emit(
                topic = sessionTopic.value,
                chainId = event.chainId.caip2(),
                event = event.data.toWcEvent(),
            )

            sign().emit(params)
            wcListener.onChanged()
        }
    }

    override suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "approveSessionRequest(id=${id.value})" }

            val request = getPendingRequest(id)

            val params = Sign.Params.Response(
                sessionTopic = request.topic,
                jsonRpcResponse = Sign.Model.JsonRpcResponse.JsonRpcResult(
                    id = request.request.id,
                    result = response
                )
            )

            sign().respondRequest(params).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun rejectSessionRequest(
        id: WcRequest.Id,
        reason: String,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "rejectSessionRequest(id=${id.value})" }

            val request = getPendingRequest(id)

            val params = Sign.Params.Response(
                sessionTopic = request.topic,
                jsonRpcResponse = Sign.Model.JsonRpcResponse.JsonRpcError(
                    id = request.request.id,
                    code = 200,
                    message = reason,
                )
            )

            sign().respondRequest(params).bind()
            wcListener.onChanged()
        }
    }

    override suspend fun disconnect(
        id: WcSession.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        either {
            logger.d { "disconnectSession(id=${id.sessionTopic.value})" }
            val session = (getActiveSession(id.sessionTopic)
                ?: raise(WcFailure.SessionNotFound()))
            val params = Sign.Params.Disconnect(session.topic)
            sign().disconnect(params).bind()
            wcListener.onChanged()
        }
    }

    private suspend fun CoreClient.pair(
        pair: Core.Params.Pair,
    ) = suspendCancellableCoroutine { cont ->
        Pairing.pair(
            pair = pair,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't pair" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.approveSession(
        sessionApprove: Sign.Params.Approve,
    ): Either<WcFailure, Unit> = suspendCancellableCoroutine { cont ->
        approveSession(
            approve = sessionApprove,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't approve session proposal" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.rejectSession(
        reject: Sign.Params.Reject,
    ): Either<WcFailure, Unit> = suspendCancellableCoroutine { cont ->
        rejectSession(
            reject = reject,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't reject session proposal" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.approveAuthentication(
        approve: Sign.Params.ApproveAuthenticate,
    ): Either<WcFailure, Unit> = suspendCancellableCoroutine { cont ->
        approveAuthenticate(
            approve = approve,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't approve session authentication" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.rejectAuthenticate(
        reject: Sign.Params.RejectAuthenticate,
    ): Either<WcFailure, Unit> = suspendCancellableCoroutine { cont ->
        rejectAuthenticate(
            reject = reject,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't reject session authentication" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.update(
        update: Sign.Params.Update,
    ) = suspendCancellableCoroutine { cont ->
        update(
            update = update,
            onSuccess = { cont.resume(Unit) },
            onError = {
                logger.w(it.throwable) { "Couldn't update session" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.emit(
        emit: Sign.Params.Emit,
    ) = suspendCancellableCoroutine { cont ->
        emit(
            emit = emit,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't reject session proposal" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.disconnect(
        disconnect: Sign.Params.Disconnect,
    ) = suspendCancellableCoroutine { cont ->
        disconnect(
            disconnect = disconnect,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't reject session proposal" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private suspend fun SignClient.respondRequest(
        response: Sign.Params.Response,
    ): Either<WcFailure, Unit> = suspendCancellableCoroutine { cont ->
        respond(
            response = response,
            onSuccess = { cont.resume(Unit.right()) },
            onError = {
                logger.w(it.throwable) { "Couldn't respond session request" }
                if (cont.isActive) {
                    mapWcFailure(it.throwable)?.left()?.let(cont::resume)
                        ?: cont.resumeWithException(it.throwable)
                }
            },
        )
    }

    private fun createNamespace(
        accounts: List<ChainAddress> = persistentListOf(),
    ): Sign.Model.Namespace.Session = Sign.Model.Namespace.Session(
        chains = accounts.map { it.chainId.caip2() }.distinct(),
        accounts = accounts.map { chainAddress -> chainAddress.caip10() },
        methods = persistentListOf(
            "personal_sign",
            "eth_signTypedData_v4",
            "eth_sendTransaction",
            "wallet_addEthereumChain",
            "wallet_watchAsset",
        ),
        events = persistentListOf(
            "message",
            "chainChanged",
            "accountsChanged",
        ),
    )

    private fun wcNamespace(
        requiredNamespace: Sign.Model.Namespace.Proposal?,
        optionalNamespace: Sign.Model.Namespace.Proposal?,
        approvedNamespace: Sign.Model.Namespace.Session?,
    ): WcNamespace = WcNamespace(
        requiredChains = requiredNamespace?.chains?.map { fromCaip2(it) } ?: listOf(),
        requiredMethods = requiredNamespace?.methods ?: listOf(),
        requiredEvents = requiredNamespace?.events ?: listOf(),

        optionalChains = optionalNamespace?.chains?.map { fromCaip2(it) } ?: listOf(),
        optionalMethods = optionalNamespace?.methods ?: listOf(),
        optionalEvents = optionalNamespace?.events ?: listOf(),

        approvedChainIds = approvedNamespace?.chains?.map { fromCaip2(it) } ?: listOf(),
        approvedAccounts = approvedNamespace?.accounts?.map { fromCaip10(it) } ?: listOf(),
        approvedMethods = approvedNamespace?.methods ?: listOf(),
        approvedEvents = approvedNamespace?.events ?: listOf()
    )

    private suspend fun wcProposal(
        proposal: Sign.Model.SessionProposal,
        verifyContext: Sign.Model.VerifyContext? = null,
    ): Either<WcFailure, WcProposal> = either {
        val requestId = wcListener.proposalRequestIds.value[proposal.pairingTopic]
            ?: raise(WcFailure.ProposalNotFound())

        WcProposal(
            id = WcProposal.Id(PairingTopic(proposal.pairingTopic)),
            requestId = requestId,
            dapp = DappMetadata(
                name = proposal.name,
                description = proposal.description,
                url = proposal.url.let(Uri::invoke),
                icon = proposal.icons.firstOrNull()?.toString()?.let(Uri::invoke),
            ),
            verifyContext = verifyContext?.let { verifyContext(it) }
                ?: getVerifyContext(requestId)
        )
    }

    private suspend fun wcAuthentication(
        authenticate: Sign.Model.SessionAuthenticate,
        verifyContext: Sign.Model.VerifyContext? = null,
    ): Either<WcFailure, WcAuthentication> = either {
        WcAuthentication(
            id = WcAuthentication.Id(authenticate.id),
            pairingTopic = PairingTopic(authenticate.topic),
            dapp = with(authenticate.participant) {
                DappMetadata(
                    name = metadata?.name!!,
                    description = metadata?.description,
                    url = metadata?.url?.let(Uri::invoke),
                    icon = metadata?.icons?.firstOrNull()?.let(Uri::invoke),
                )
            },
            verifyContext = verifyContext?.let { verifyContext(it) }
                ?: getVerifyContext(authenticate.id)
        )
    }

    private fun wcSession(
        session: Sign.Model.Session,
    ) = WcSession(
        id = WcSession.Id(SessionTopic(session.topic)),
        dapp = DappMetadata(
            name = session.metaData?.name!!,
            description = session.metaData?.description,
            url = session.metaData?.url?.let(Uri::invoke),
            icon = session.metaData?.icons?.firstOrNull()?.let(Uri::invoke),
        ),
    )

    private suspend fun wcRequest(
        request: Sign.Model.SessionRequest,
        verifyContext: Sign.Model.VerifyContext? = null,
    ): Either<WcFailure, WcRequest> = either {
        WcRequest(
            id = WcRequest.Id(request.request.id),
            sessionTopic = SessionTopic(request.topic),
            dapp = DappMetadata(
                name = request.peerMetaData!!.name,
                description = request.peerMetaData?.description,
                url = request.peerMetaData?.url?.let(Uri::invoke),
                icon = request.peerMetaData?.icons?.firstOrNull()?.let(Uri::invoke),
            ),
            call = parseRpcRequest(
                requestChainId = request.chainId?.let { fromCaip2(it) },
                method = request.request.method,
                params = request.request.params
            ).bind(),
            verifyContext = verifyContext?.let { verifyContext(it) }
                ?: getVerifyContext(request.request.id),
        )
    }

    private suspend fun Raise<WcFailure>.getVerifyContext(id: Long): WcVerifyContext =
        sign().getVerifyContext(id)
            ?.let { verifyContext(it) }
            ?: raise(WcFailure.VerifyContextNotFound())

    private fun verifyContext(
        verifyContext: Sign.Model.VerifyContext,
    ): WcVerifyContext = when (verifyContext.isScam ?: false) {
        true -> WcVerifyContext.Threat
        false -> when (verifyContext.validation) {
            Validation.VALID -> WcVerifyContext.Match
            Validation.INVALID -> WcVerifyContext.Mismatch
            Validation.UNKNOWN -> WcVerifyContext.Unverified
        }
    }

    override suspend fun parseRpcRequest(
        requestChainId: ChainId?,
        method: String,
        params: String,
    ): Either<WcFailure, WcRequest.Call> = catch({
        logger.v { "Request:$requestChainId:$method:$params" }
        either {
            when (method) {
                "personal_sign" -> {
                    val decodeParams = json.decodeFromString<List<String>>(params)
                    val message =
                        decodeParams[0].removePrefix("0x").hexToByteString().decodeToString()
                    val address = parseAddress(decodeParams[1])

                    WcRequest.Call.SignPersonal(
                        message = EthMessage(message),
                        account = address,
                    )
                }

                "eth_signTypedData_v4" -> {
                    val decodeParams = json.decodeFromString<List<JsonElement>>(params)
                    val address = parseAddress(decodeParams[0].jsonPrimitive.content)
                    val json = json.encodeToString(decodeParams[1].jsonObject)
                    val domain = JsonEip712.from(json).domain

                    WcRequest.Call.SignTyped(
                        chainId = domain.chainId?.let { ChainId(it) },
                        json = JsonTypeData(json),
                        account = address,
                    )
                }

                "wallet_addEthereumChain" -> {
                    val addNetwork = json.decodeFromString<List<AddEthereumNetwork>>(params).first()

                    WcRequest.Call.AddNetwork(
                        chainId = ChainId(addNetwork.chainId.parseHexNumber().ulongValue()),
                        chainName = checkNotNull(addNetwork.chainName) {
                            "Expected network name"
                        },
                        tokenName = checkNotNull(addNetwork.nativeCurrency?.name) {
                            "Expected token name"
                        },
                        tokenSymbol = checkNotNull(addNetwork.nativeCurrency?.symbol) {
                            "Expected token symbol"
                        },
                        tokenDecimals = checkNotNull(addNetwork.nativeCurrency?.decimals?.toUByte()) {
                            "Expected token decimals"
                        },
                        rpcProviders = checkNotNull(
                            addNetwork.rpcUrls?.map { Uri(it) }?.takeIf { it.isNotEmpty() }
                        ) {
                            "Expected rpc provider"
                        },
                        explorers = addNetwork.blockExplorerUrls?.map { Uri(it) } ?: listOf(),
                        icons = addNetwork.iconUrls?.map { Uri(it) } ?: listOf(),
                    )
                }

                "wallet_watchAsset" -> {
                    val watchAsset = json.decodeFromString<WatchAsset>(params)
                    checkNotNull(requestChainId) { "Chain Id expected" }

                    WcRequest.Call.WatchAsset(
                        chainId = requestChainId,
                        address = parseAddress(watchAsset.options.address),
                        tokenId = watchAsset.options.tokenId?.let(BigInteger::parseString),
                        icon = watchAsset.options.image?.let(Uri::invoke),
                    )
                }

                "eth_sendTransaction" -> {
                    val transaction = json.decodeFromString<List<SignTransaction>>(params)[0]
                    val from = parseAddress(transaction.from)

                    WcRequest.Call.SendTransaction(
                        chainId = transaction.chainId
                            ?.parseHexNumber()?.ulongValue()?.let(ChainId::invoke)
                            ?: requestChainId
                            ?: error("Expected chain id"),
                        from = from,
                        to = transaction.to?.let { parseAddress(transaction.to) },
                        data = (transaction.data ?: transaction.input)?.parseHex() ?: ByteString(),
                        value = transaction.value?.parseHexNumber() ?: BigInteger.ZERO,
                        gas = transaction.gas?.parseHexNumber()?.ulongValue(),
                    )
                }

                else -> raise(WcFailure.WcInvalidRequest.MethodNotSupported(method))
            }
        }
    }, {
        logger.w(it) { "Invalid Request" }
        WcFailure.WcInvalidRequest.Other("$method: Invalid Request: ${it.message}").left()
    })

    private fun String.parseHexNumber() = removePrefix("0x").toBigInteger(16)

    private fun String.parseHex() = ByteString(removePrefix("0x").hexToByteString())

    private fun Raise<WcFailure>.parseAddress(address: String) =
        withError({ WcFailure.WcInvalidRequest.Other(it.message) }) { Address(address).bind() }

    private fun WcEvent.Data.toWcEvent() = when (this) {
        is WcEvent.Data.Message -> Sign.Model.SessionEvent(
            name = "message",
            data = json.encodeToString<WcEvent.Data.Message>(this),
        )
    }

    private fun mapWcFailure(throwable: Throwable): WcFailure? = when (throwable) {
        is MalformedWalletConnectUri -> WcFailure.PairingUriInvalid()
        is PairWithExistingPairingIsNotAllowed -> WcFailure.AlreadyPaired()
        is ExpiredPairingException -> WcFailure.PairingUriExpired()
        is ExpiredPairingURIException -> WcFailure.PairingUriExpired()
        is CannotFindSequenceForTopic -> WcFailure.TopicInvalid()
        is NoConnectivityException -> WcFailure.NoConnection()
        is RequestExpiredException -> WcFailure.RequestExpired()
        else -> null
    }
}
