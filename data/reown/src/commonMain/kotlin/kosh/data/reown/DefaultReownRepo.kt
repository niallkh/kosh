package kosh.data.reown

import arrow.core.Either
import arrow.core.flatten
import arrow.core.left
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.caip10
import kosh.domain.models.caip2
import kosh.domain.models.did
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.models.web3.EthMessage
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.ReownRepo
import kosh.domain.repositories.ReownRepo.Companion.supportedEvents
import kosh.domain.repositories.ReownRepo.Companion.supportedMethods
import kosh.domain.repositories.suspendLazy
import kosh.libs.reown.ReownAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class DefaultReownRepo(
    private val adapter: ReownAdapter,
) : ReownRepo {

    private val logger = Logger.withTag("[K]DefaultReownRepo")

    private val init = suspendLazy {
        logger.v { "init()" }
        withContext(NonCancellable + Dispatchers.Main) {
            try {
                adapter.initialize()
            } catch (e: Throwable) {
                logger.e(e) { "Init failed: ${e.message}" }
                throw e
            }
        }
    }

    override val newProposal: Flow<WcSessionProposal>
        get() = callbackFlow {
            logger.v { "newProposal" }
            init()
            val cancelable = adapter.getNewProposal { proposal ->
                proposal.map().fold(
                    { logger.w { "Session proposal error: ${it.message}" } },
                    { trySend(it) }
                )
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val proposals: Flow<List<WcSessionProposal>>
        get() = callbackFlow {
            logger.v { "proposals" }
            init()
            val cancelable = adapter.getProposals { proposals ->
                proposals.mapNotNull { proposal ->
                    proposal.map().fold(
                        { logger.w { "Session proposal error: ${it.message}" }; null },
                        { it }
                    )
                }
                    .let { trySend(it) }
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val newAuthentication: Flow<WcAuthentication>
        get() = callbackFlow {
            logger.v { "newAuthentication" }
            init()
            val cancelable = adapter.getNewAuthentication { authentication ->
                authentication.map().fold(
                    { logger.w { "Authentication request error: ${it.message}" } },
                    { trySend(it) }
                )
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val authentications: Flow<List<WcAuthentication>>
        get() = callbackFlow {
            logger.v { "authentications" }
            init()
            val cancelable = adapter.getAuthentications { authentications ->
                authentications.mapNotNull { authentication ->
                    authentication.map().fold(
                        { logger.w { "Authentication request error: ${it.message}" }; null },
                        { it }
                    )
                }
                    .let { trySend(it) }
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val newRequest: Flow<WcRequest>
        get() = callbackFlow {
            logger.v { "newRequest" }
            init()
            val cancelable = adapter.getNewRequest { request ->
                request.map().fold(
                    { logger.w { "Session request error: ${it.message}" } },
                    { trySend(it) }
                )
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val requests: Flow<List<WcRequest>>
        get() = callbackFlow {
            logger.v { "requests" }
            init()
            val cancelable = adapter.getRequests { requests ->
                requests.mapNotNull { authentication ->
                    authentication.map().fold(
                        { logger.w { "Session request error: ${it.message}" }; null },
                        { it }
                    )
                }
                    .let { trySend(it) }
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)

    override val sessions: Flow<List<WcSession>>
        get() = callbackFlow {
            logger.v { "sessions" }
            init()
            val cancelable = adapter.getSessions { sessions ->
                sessions.mapNotNull { session ->
                    session.map().fold(
                        { logger.w { "Session error: ${it.message}" }; null },
                        { it }
                    )
                }
                    .let { trySend(it) }
            }

            awaitClose { cancelable() }
        }
            .flowOn(Dispatchers.Default)


    override suspend fun initialize() = withContext(Dispatchers.Default) {
        logger.v { "initialize()" }
        init()
    }

    override suspend fun connect() = withContext(Dispatchers.Default) {
        logger.v { "connect()" }
        init()
        adapter.connect()
    }

    override suspend fun disconnect() = withContext(Dispatchers.Default) {
        logger.v { "disconnect()" }
        init()
        adapter.disconnect()
    }

    override suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "pair()" }
            init()
            adapter.pair(uri.value)
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun getProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, WcSessionProposal> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "getProposal(${id.pairingTopic.value})" }
            init()
            adapter.getProposal(id.pairingTopic.value)?.map()
                ?: WcFailure.ProposalNotFound().left()
        }
            .mapLeft { it.mapWcFailure() }
            .flatten()
    }

    override suspend fun approveProposal(
        id: WcSessionProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "approveProposal(${id.pairingTopic.value})" }
            init()
            adapter.approveProposal(
                pairingTopic = id.pairingTopic.value,
                chains = approvedAccounts.map { it.chainId.caip2() }.distinct(),
                accounts = approvedAccounts.map { it.caip10() },
                methods = supportedMethods,
                events = supportedEvents,
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun rejectProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "rejectProposal(${id.pairingTopic.value})" }
            init()
            adapter.rejectProposal(
                pairingTopic = id.pairingTopic.value,
                reason = "User rejected"
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun getAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "getAuthentication(${id.value})" }
            init()
            adapter.getAuthentication(id.value)?.map()
                ?: WcFailure.AuthenticationNotFound().left()
        }
            .mapLeft { it.mapWcFailure() }
            .flatten()
    }

    override suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
        signature: Signature,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "approveAuthentication(${id.value})" }
            init()
            adapter.approveAuthentication(
                id = id.value,
                issuer = account.did,
                supportedChains = supportedChains.map { it.caip2() },
                signature = signature.data.toString(),
                supportedMethods = supportedMethods,
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "getAuthenticationMessage(${id.value})" }
            init()
            adapter.getAuthenticationMessage(
                id = id.value,
                issuer = account.did,
                supportedChains = supportedChains.map { it.caip2() },
                supportedMethods = supportedMethods,
            )
                .let { EthMessage(it) }
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "rejectAuthentication(${id.value})" }
            init()
            adapter.rejectAuthentication(
                id = id.value,
                reason = "User rejected"
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun getSessionRequest(
        id: WcRequest.Id,
    ): Either<WcFailure, WcRequest> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "getSessionRequest(${id.value})" }
            init()
            adapter.getRequest(id.value)?.map()
                ?: WcFailure.RequestNotFound().left()
        }
            .mapLeft { it.mapWcFailure() }
            .flatten()
    }

    override suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "approveSessionRequest(${id.value})" }
            init()
            adapter.approveRequest(
                id = id.value,
                message = response
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun rejectSessionRequest(
        id: WcRequest.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "rejectSessionRequest(${id.value})" }
            init()
            adapter.rejectRequest(
                id = id.value,
                code = 5000,
                message = "User rejected",
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun getSession(
        id: WcSession.Id,
    ): Either<WcFailure, WcSession> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "getSession(${id.sessionTopic.value})" }
            init()
            adapter.getSession(id.sessionTopic.value)?.map()
                ?: WcFailure.SessionNotFound().left()
        }
            .mapLeft { it.mapWcFailure() }
            .flatten()
    }

    override suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "updateSession(${id.sessionTopic.value})" }
            init()
            adapter.updateSession(
                sessionTopic = id.sessionTopic.value,
                chains = approvedAccounts.map { it.chainId.caip2() }.distinct(),
                accounts = approvedAccounts.map { it.caip10() },
                methods = supportedMethods,
                events = supportedEvents,
            )
        }.mapLeft { it.mapWcFailure() }
    }

    override suspend fun disconnectSession(
        id: WcSession.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        Either.catch {
            logger.v { "disconnectSession(${id.sessionTopic.value})" }
            init()
            adapter.disconnectSession(
                sessionTopic = id.sessionTopic.value
            )
        }.mapLeft { it.mapWcFailure() }
    }
}

internal fun Throwable.mapWcFailure(): WcFailure = when (this) {
    else -> WcFailure.Other(message ?: "Something went wrong")
}
