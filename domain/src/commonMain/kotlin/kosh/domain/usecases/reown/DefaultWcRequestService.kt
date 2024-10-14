package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.ByteString
import kosh.domain.models.ChainId
import kosh.domain.models.Hash
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcSession
import kosh.domain.repositories.WcRepo
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.network
import kosh.domain.usecases.account.AccountService
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.utils.optic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultWcRequestService(
    private val reownRepo: WcRepo,
    private val applicationScope: CoroutineScope,
    private val notificationService: NotificationService,
    private val networkService: NetworkService,
    private val accountService: AccountService,
    private val sessionService: WcSessionService,
    private val appStateProvider: AppStateProvider,
) : WcRequestService {

    private val logger = Logger.withTag("WcRequestProcessor")

    override val requests: Flow<List<WcRequest>>
        get() = reownRepo.requests
            .map { requests -> requests.mapNotNull { validate(it).getOrNull() } }

    override suspend fun get(
        id: WcRequest.Id?,
    ): Either<WcFailure, WcRequest> = either {
        id?.value?.let { notificationService.cancel(id.value) }

        val request = reownRepo.getSessionRequest(id).bind()

        notificationService.cancel(request.id.value)

        validate(request).bind()
    }

    override fun onTypedSigned(
        id: WcRequest.Id,
        data: ByteString,
    ) = applicationScope.launch {
        recover({
            reownRepo.approveSessionRequest(
                id = id,
                response = data.toString()
            ).bind()
        }) {
            logger.logFailure(it)
        }
    }

    override fun onPersonalSigned(
        id: WcRequest.Id,
        data: ByteString,
    ) = applicationScope.launch {
        recover({
            reownRepo.approveSessionRequest(
                id = id,
                response = data.toString()
            ).bind()
        }) {
            logger.logFailure(it)
        }
    }

    override fun onTransactionSend(
        id: WcRequest.Id,
        hash: Hash,
    ) = applicationScope.launch {
        recover({
            reownRepo.approveSessionRequest(
                id = id,
                response = hash.toString()
            ).bind()
        }) {
            logger.logFailure(it)
        }
    }

    override fun onNetworkAdded(
        id: WcRequest.Id,
        sessionTopic: SessionTopic,
        chainId: ChainId,
    ): Job = applicationScope.launch {
        recover({
            sessionService.addNetwork(WcSession.Id(sessionTopic), chainId).bind()

            reownRepo.approveSessionRequest(id, "null").bind()
        }, {
            logger.logFailure(it)
        })
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

    private suspend fun validate(request: WcRequest): Either<WcFailure, WcRequest> = either {
        when (val call = request.call) {
            is WcRequest.Call.AddNetwork -> {
                ensure(appStateProvider.optic(AppState.network(call.chainId)).value == null) {
                    WcFailure.WcInvalidRequest.NetworkAlreadyExists(
                        chainId = call.chainId,
                        enabled = networkService.isActive(call.chainId)
                    )
                }
            }

            is WcRequest.Call.SignPersonal -> {
                ensure(accountService.isActive(call.account)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.account)
                }
            }

            is WcRequest.Call.SendTransaction -> {
                ensure(networkService.isActive(call.chainId)) {
                    WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                }
                ensure(accountService.isActive(call.from)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.from)
                }
            }

            is WcRequest.Call.SignTyped -> {
                if (call.chainId != null) {
                    ensure(networkService.isActive(call.chainId)) {
                        WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                    }
                }
                ensure(accountService.isActive(call.account)) {
                    WcFailure.WcInvalidRequest.AccountDisabled(call.account)
                }
            }

            is WcRequest.Call.WatchAsset -> {
                ensure(networkService.isActive(call.chainId)) {
                    WcFailure.WcInvalidRequest.NetworkDisabled(call.chainId)
                }
            }
        }

        request
    }
}
