package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.raise.either
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.at
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.web3.Signature
import kosh.domain.repositories.WcRepo
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.activeNetworks
import kosh.domain.usecases.notification.NotificationService
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultWcAuthenticationService(
    private val reownRepo: WcRepo,
    private val notificationService: NotificationService,
    private val appStateProvider: AppStateProvider,
) : WcAuthenticationService {

    private val logger = Logger.withTag("[K]WcAuthenticationService")

    override val authentications: Flow<ImmutableList<WcAuthentication>>
        get() = reownRepo.authentications.map { it.toPersistentList() }

    override suspend fun get(
        id: WcAuthentication.Id,
    ): Either<WcFailure, WcAuthentication> = either {
        logger.i { "get()" }

        val authentication = reownRepo.getAuthentication(id).bind()

        notificationService.cancel(authentication.id.value)

        authentication
    }

    override suspend fun getAuthenticationMessage(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
    ): Either<WcFailure, WcAuthentication.Message> = either {
        logger.i { "getAuthenticationMessage()" }

        val formattedMessage = reownRepo.getAuthenticationMessage(
            id = id,
            account = chainId.at(account),
            supportedChains = listOf(chainId)
        ).bind()

        WcAuthentication.Message(chainId, account, formattedMessage)
    }

    override suspend fun approve(
        id: WcAuthentication.Id,
        account: Address,
        chainId: ChainId,
        signature: Signature,
    ): Either<WcFailure, Unit> = either {
        logger.i { "approve()" }

        reownRepo.approveAuthentication(
            id = id,
            account = chainId.at(account),
            signature = signature,
            supportedChains = listOf(chainId)
        )
    }

    private fun getSupportedChains(): List<ChainId> =
        AppState.activeNetworks().get(appStateProvider.state)
            .map { it.chainId }

    override suspend fun reject(id: WcAuthentication.Id): Either<WcFailure, Unit> = either {
        reownRepo.rejectAuthentication(id).bind()
    }
}
