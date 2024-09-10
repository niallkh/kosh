package kosh.domain.usecases.wc

import arrow.core.Either
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.wc.WcSession
import kosh.domain.models.wc.WcSessionAggregated
import kosh.domain.repositories.WcRepo
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.accounts
import kosh.domain.state.networks
import kosh.domain.utils.optic
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class WcSessionService(
    private val applicationScope: CoroutineScope,
    private val wcRepo: WcRepo,
    private val appStateProvider: AppStateProvider,
) {
    private val logger = Logger.withTag("[K]WcSessionService")

    val sessions: Flow<ImmutableList<WcSession>>
        get() = wcRepo.sessions.map { it.toImmutableList() }

    suspend fun get(
        id: WcSession.Id,
    ): Either<WcFailure, WcSessionAggregated> = either {
        wcSessionAggregated(
            session = wcRepo.getSession(id).bind(),
        )
    }

    suspend fun update(
        id: WcSession.Id,
        approvedAccounts: List<Address>,
        approvedChains: List<ChainId>,
    ): Either<WcFailure, Unit> {
        return wcRepo.updateSession(
            id = id,
            approvedAccounts = approvedChains.flatMap { chainId ->
                approvedAccounts.map { account -> ChainAddress(chainId, account) }
            }
        )
    }

    suspend fun addNetwork(
        id: WcSession.Id,
        chainId: ChainId,
    ): Either<WcFailure, Unit> = either {

        val namespace = wcRepo.getNamespace(id).bind()

        if (chainId !in namespace.approvedChainIds) {
            val approvedAccounts = namespace.approvedAccounts
            val newAccounts = approvedAccounts.distinctBy { it.address }
                .map { ChainAddress(chainId, it.address) }

            wcRepo.updateSession(
                id = id,
                approvedAccounts = approvedAccounts + newAccounts,
            )
        }
    }

    fun disconnect(
        id: WcSession.Id,
    ) = applicationScope.launch {
        recover({
            wcRepo.disconnect(id).bind()
        }) {
            logger.logFailure(it)
        }
    }

    private suspend fun Raise<WcFailure>.wcSessionAggregated(
        session: WcSession,
    ): WcSessionAggregated {
        val namespace = wcRepo.getNamespace(session.id).bind()

        val networks = appStateProvider.optic(AppState.networks).value.values
        val accounts = appStateProvider.optic(AppState.accounts).value.values
        val approvedAccounts = namespace.approvedAccounts.map { it.address }.distinct()

        return WcSessionAggregated(
            session = session,
            availableNetworks = networks
                .filter { it.chainId in namespace.requiredChains || it.chainId in namespace.optionalChains }
                .map { it.id }
                .toPersistentHashSet(),
            requiredNetworks = networks
                .filter { it.chainId in namespace.requiredChains }
                .map { it.id }
                .toPersistentHashSet(),
            approvedAccounts = accounts
                .filter { it.address in approvedAccounts }
                .map { it.id }
                .toPersistentHashSet(),
            approvedNetworks = networks
                .filter { it.chainId in namespace.approvedChainIds }
                .map { it.id }
                .toPersistentHashSet(),
        )
    }
}
