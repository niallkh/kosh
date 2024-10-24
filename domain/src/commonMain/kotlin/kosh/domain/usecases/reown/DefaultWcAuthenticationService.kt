package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.at
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.WcRepo
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.networks
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.utils.optic
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class DefaultWcAuthenticationService(
    private val reownRepo: WcRepo,
    private val applicationScope: CoroutineScope,
    private val notificationService: NotificationService,
    private val appStateProvider: AppStateProvider,
) : WcAuthenticationService {

    private val logger = Logger.withTag("[K]WcAuthenticationService")

    override val authentications: Flow<ImmutableList<WcAuthentication>>
        get() = reownRepo.authentications.map { it.toPersistentList() }

    override suspend fun get(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication> = either {
        notificationService.cancel(id.value)

        val authentication = reownRepo.getAuthentication(id).bind()

        authentication
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
    ): Either<WcFailure, WcAuthentication.Message> = either {

        val formattedMessage = reownRepo.getAuthenticationMessage(
            id = id,
            account = chainId.at(account),
            supportedChains = appStateProvider.optic(AppState.networks).value.map { it.value.chainId }
        ).bind()

        WcAuthentication.Message(chainId, account, formattedMessage)
    }

    override fun approve(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
        signature: Signature,
    ) = applicationScope.launch {
        recover({
            reownRepo.approveAuthentication(
                id = id,
                account = chainId.at(account),
                signature = signature,
                supportedChains = appStateProvider.optic(AppState.networks).value.map { it.value.chainId }
            ).bind()
        }) {
            logger.logFailure(it)
        }
    }

    override fun reject(
        id: WcAuthentication.Id,
    ) = applicationScope.launch {
        recover({
            reownRepo.rejectAuthentication(id).bind()
        }) {
            logger.logFailure(it)
        }
    }
}
