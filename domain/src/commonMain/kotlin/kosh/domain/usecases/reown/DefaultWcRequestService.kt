package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSession
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.WcRepo
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.isActive
import kosh.domain.state.network
import kosh.domain.usecases.notification.NotificationService
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultWcRequestService(
    private val reownRepo: WcRepo,
    private val applicationScope: CoroutineScope,
    private val notificationService: NotificationService,
    private val sessionService: WcSessionService,
    private val appStateProvider: AppStateProvider,
) : WcRequestService {

    private val logger = Logger.withTag("WcRequestProcessor")

    override val requests: Flow<ImmutableList<WcRequest>>
        get() = reownRepo.requests
            .map { requests -> requests.mapNotNull { validate(it).getOrNull() } }
            .map { it.toPersistentList() }

    override suspend fun get(
        id: WcRequest.Id?,
    ): Either<WcFailure, WcRequest> = either {
        val request = reownRepo.getSessionRequest(id).bind()

        notificationService.cancel(request.id.value)

        validate(request).bind()
    }

    override suspend fun onPersonalSigned2(
        id: WcRequest.Id,
        signature: Signature,
    ): Either<WcFailure, Unit> = either {
        reownRepo.approveSessionRequest(
            id = id,
            response = signature.data.toString()
        ).bind()
    }

    override suspend fun onTypedSigned2(
        id: WcRequest.Id,
        signature: Signature,
    ): Either<WcFailure, Unit> = either {
        reownRepo.approveSessionRequest(
            id = id,
            response = signature.data.toString()
        ).bind()
    }

    override suspend fun onTransactionSend(
        id: WcRequest.Id,
        hash: Hash,
    ): Either<WcFailure, Unit> = either {
        reownRepo.approveSessionRequest(
            id = id,
            response = hash.toString()
        ).bind()
    }

    override suspend fun onNetworkAdded(
        id: WcRequest.Id,
        sessionTopic: SessionTopic,
        chainId: ChainId,
    ): Either<WcFailure, Unit> = either {
        sessionService.addNetwork(WcSession.Id(sessionTopic), chainId).bind()

        reownRepo.approveSessionRequest(id, "null").bind()
    }

    override fun onAssetWatched(
        id: WcRequest.Id,
    ): Job = applicationScope.launch {
        recover({
            reownRepo.approveSessionRequest(id, "true").bind()
        }) {
            logger.logFailure(it)
        }
    }

    override fun reject(
        id: WcRequest.Id,
    ): Job = applicationScope.launch {
        recover({
            reownRepo.rejectSessionRequest(id).bind()
        }, {
            logger.logFailure(it)
        })
    }

    override suspend fun reject2(
        id: WcRequest.Id,
    ): Either<WcFailure, Unit> = either {
        reownRepo.rejectSessionRequest(id).bind()
    }

    private fun validate(
        request: WcRequest,
    ): Either<WcFailure, WcRequest> = either {
        val appState = appStateProvider.state

        when (val call = request.call) {
            is WcRequest.Call.AddNetwork -> {
                ensure(AppState.network(call.chainId).get(appState) == null) {
                    WcFailure.WcInvalidRequest.NetworkAlreadyExists(
                        chainId = call.chainId,
                        enabled = AppState.isActive(call.chainId).get(appState)
                    )
                }
            }

            is WcRequest.Call.SignPersonal -> {
                ensure(AppState.isActive(call.account).get(appState)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.account)
                }
            }

            is WcRequest.Call.SendTransaction -> {
                ensure(AppState.isActive(call.chainId).get(appState)) {
                    WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                }
                ensure(AppState.isActive(call.from).get(appState)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.from)
                }
            }

            is WcRequest.Call.SignTyped -> {
                if (call.chainId != null) {
                    ensure(AppState.isActive(call.chainId).get(appState)) {
                        WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                    }
                }
                ensure(AppState.isActive(call.account).get(appState)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.account)
                }
            }

            is WcRequest.Call.WatchAsset -> {
                ensure(AppState.isActive(call.chainId).get(appState)) {
                    WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                }
            }
        }

        request
    }
}
