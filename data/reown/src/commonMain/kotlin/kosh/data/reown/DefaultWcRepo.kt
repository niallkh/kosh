@file:OptIn(ExperimentalCoroutinesApi::class)

package kosh.data.reown

import arrow.core.Either
import arrow.core.flatten
import arrow.core.left
import arrow.core.right
import arrow.fx.coroutines.raceN
import arrow.resilience.Schedule
import arrow.resilience.retryRaise
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.Redirect
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
import kosh.domain.repositories.WcRepo
import kosh.domain.repositories.WcRepo.Companion.supportedEvents
import kosh.domain.repositories.WcRepo.Companion.supportedMethods
import kosh.domain.repositories.suspendLazy
import kosh.libs.reown.ReownAdapter
import kosh.libs.reown.ReownFailure
import kosh.libs.reown.ReownResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

private val retrySchedule = Schedule.linear<WcFailure>(1.seconds) and Schedule.recurs(5)

class DefaultWcRepo(
    private val adapter: ReownAdapter,
) : WcRepo {

    private val logger = Logger.withTag("[K]DefaultWcRepo")

    private val init = suspendLazy {
        logger.v { "init()" }
        withContext(NonCancellable) {
            adapter.initialize()
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
                    .sortedWith(compareBy<WcSessionProposal> { it.dapp.name }.thenBy { it.id.pairingTopic.value })
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
                    .sortedWith(compareBy<WcAuthentication> { it.dapp.name }.thenBy { it.id.value })
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
                    .sortedWith(compareBy<WcRequest> { it.dapp.name }.thenBy { it.id.value })
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
                    .sortedWith(compareBy<WcSession> { it.dapp.name }.thenBy { it.id.sessionTopic.value })
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
        logger.v { "pair()" }
        init()
        raceN(
            { timer() },
            { adapter.pair(uri.value).mapFailure() }
        ).flatten()
    }

    private suspend fun timer(
        time: Duration = 10.seconds,
    ): WcFailure {
        delay(time)
        return WcFailure.ResponseTimeout()
    }

    override suspend fun getProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, WcSessionProposal> = withContext(Dispatchers.Default) {
        logger.v { "getProposal(${id.pairingTopic.value})" }
        init()
        retrySchedule.retryRaise {
            adapter.getProposal(id.pairingTopic.value)
                .mapFailure().bind()
                .map().bind()
        }
    }

    override suspend fun approveProposal(
        id: WcSessionProposal.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "approveProposal(${id.pairingTopic.value})" }
        init()
        raceN(
            { timer() },
            {
                adapter.approveProposal(
                    pairingTopic = id.pairingTopic.value,
                    chains = approvedAccounts.map { it.chainId.caip2() }.distinct(),
                    accounts = approvedAccounts.map { it.caip10() },
                    methods = supportedMethods,
                    events = supportedEvents,
                )
                    .mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun rejectProposal(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "rejectProposal(${id.pairingTopic.value})" }
        init()
        raceN(
            { timer() },
            {
                adapter.rejectProposal(
                    pairingTopic = id.pairingTopic.value,
                    reason = "User rejected"
                ).mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun getAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication> = withContext(Dispatchers.Default) {
        logger.v { "getAuthentication(${id.value})" }
        init()
        retrySchedule.retryRaise {
            adapter.getAuthentication(id.value)
                .mapFailure().bind()
                .map().bind()
        }
    }

    override suspend fun approveAuthentication(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
        signature: Signature,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "approveAuthentication(${id.value})" }
        init()
        raceN(
            { timer() },
            {
                adapter.approveAuthentication(
                    id = id.value,
                    issuer = account.did,
                    supportedChains = supportedChains.map { it.caip2() },
                    signature = signature.data.toString(),
                    supportedMethods = supportedMethods,
                )
                    .mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: ChainAddress,
        supportedChains: List<ChainId>,
    ): Either<WcFailure, EthMessage> = withContext(Dispatchers.Default) {
        logger.v { "getAuthenticationMessage(${id.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.getAuthenticationMessage(
                    id = id.value,
                    issuer = account.did,
                    supportedChains = supportedChains.map { it.caip2() },
                    supportedMethods = supportedMethods,
                )
                    .mapFailure()
                    .map { EthMessage(it.value) }
            }
        ).flatten()
    }

    override suspend fun rejectAuthentication(
        id: WcAuthentication.Id,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "rejectAuthentication(${id.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.rejectAuthentication(
                    id = id.value,
                    reason = "User rejected"
                )
                    .mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun getSessionRequest(
        id: WcRequest.Id?,
    ): Either<WcFailure, WcRequest> = withContext(Dispatchers.Default) {
        logger.v { "getSessionRequest(${id?.value})" }
        init()
        if (id != null) {
            retrySchedule.retryRaise {
                adapter.getRequest(id.value)
                    .mapFailure().bind()
                    .map().bind()
            }
        } else {
            withTimeoutOrNull(10.seconds) {
                requests.flatMapConcat { it.asFlow() }.first().right()
            } ?: WcFailure.Expired().left()
        }
    }

    override suspend fun approveSessionRequest(
        id: WcRequest.Id,
        response: String,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "approveSessionRequest(${id.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.approveRequest(
                    id = id.value,
                    message = response
                )
                    .mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun rejectSessionRequest(
        id: WcRequest.Id,
    ): Either<WcFailure, Redirect?> = withContext(Dispatchers.Default) {
        logger.v { "rejectSessionRequest(${id.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.rejectRequest(
                    id = id.value,
                    code = 5000,
                    message = "User rejected",
                )
                    .mapFailure()
                    .map { it.takeIf { it.value.isNotEmpty() } }
                    .map { it?.value?.let(::Redirect) }
            }
        ).flatten()
    }

    override suspend fun getSession(
        id: WcSession.Id,
    ): Either<WcFailure, WcSession> = withContext(Dispatchers.Default) {
        logger.v { "getSession(${id.sessionTopic.value})" }
        init()
        retrySchedule.retryRaise {
            adapter.getSession(id.sessionTopic.value)
                .mapFailure().bind()
                .map().bind()
        }
    }

    override suspend fun updateSession(
        id: WcSession.Id,
        approvedAccounts: List<ChainAddress>,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        logger.v { "updateSession(${id.sessionTopic.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.updateSession(
                    sessionTopic = id.sessionTopic.value,
                    chains = approvedAccounts.map { it.chainId.caip2() }.distinct(),
                    accounts = approvedAccounts.map { it.caip10() },
                    methods = supportedMethods,
                    events = supportedEvents,
                ).mapFailure()
            }
        ).flatten()
    }

    override suspend fun disconnectSession(
        id: WcSession.Id,
    ): Either<WcFailure, Unit> = withContext(Dispatchers.Default) {
        logger.v { "disconnectSession(${id.sessionTopic.value})" }
        init()

        raceN(
            { timer() },
            {
                adapter.disconnectSession(
                    sessionTopic = id.sessionTopic.value
                ).mapFailure()
            }
        ).flatten()
    }

    private fun <T> ReownResult<T>.mapFailure() = toEither().mapLeft { it.mapWcFailure() }

    private fun <T> ReownResult<T>.toEither(): Either<ReownFailure, T> = when (this) {
        is ReownResult.Failure -> value.left()
        is ReownResult.Success -> value.right()
    }

    private fun ReownFailure.mapWcFailure(): WcFailure = when (this) {
        is ReownFailure.AlreadyPaired -> WcFailure.AlreadyPaired()
        is ReownFailure.PairingUriInvalid -> WcFailure.PairingUriInvalid()
        is ReownFailure.NotFound -> WcFailure.Expired()
        is ReownFailure.InvalidNamespace -> WcFailure.InvalidNamespace()
        is ReownFailure.NotConnected -> WcFailure.NoConnection()
        is ReownFailure.Other -> WcFailure.Other(message)
        is ReownFailure.ResponseTimeout -> WcFailure.ResponseTimeout()
    }.also {
        logger.e { "WC: Error happened: $message" }
    }
}
