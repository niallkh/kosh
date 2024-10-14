package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.raise.either
import arrow.core.raise.recover
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.reown.WcSession
import kosh.domain.models.reown.WcSessionAggregated
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
    private val reownRepo: WcRepo,
    private val appStateProvider: AppStateProvider,
) {
    private val logger = Logger.withTag("[K]WcSessionService")

    val sessions: Flow<ImmutableList<WcSession>>
        get() = reownRepo.sessions.map { it.toImmutableList() }

    suspend fun get(
        id: WcSession.Id,
    ): Either<WcFailure, WcSessionAggregated> = either {

        val session = reownRepo.getSession(id).bind()

        wcSessionAggregated(session)
    }

    suspend fun update(
        id: WcSession.Id,
        approvedAccounts: List<Address>,
        approvedChains: List<ChainId>,
    ): Either<WcFailure, Unit> {
        return reownRepo.updateSession(
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

        val session = reownRepo.getSession(id).bind()

        if (chainId !in session.approved.chains) {
            val approvedAccounts = session.approved.accounts

            val newAccounts = approvedAccounts.distinctBy { it.address }
                .map { ChainAddress(chainId, it.address) }

            reownRepo.updateSession(
                id = id,
                approvedAccounts = approvedAccounts + newAccounts,
            )
        }
    }

    fun disconnect(
        id: WcSession.Id,
    ) = applicationScope.launch {
        recover({
            reownRepo.disconnectSession(id).bind()
        }) {
            logger.logFailure(it)
        }
    }

    private fun wcSessionAggregated(
        session: WcSession,
    ): WcSessionAggregated {

        val networks = appStateProvider.optic(AppState.networks).value.values
        val accounts = appStateProvider.optic(AppState.accounts).value.values
        val approvedAccounts = session.approved.accounts.map { it.address }.distinct()

        return WcSessionAggregated(
            session = session,
            availableNetworks = networks
                .filter { it.chainId in session.required.chains || it.chainId in session.optional.chains }
                .map { it.id }
                .toPersistentHashSet(),
            requiredNetworks = networks
                .filter { it.chainId in session.required.chains }
                .map { it.id }
                .toPersistentHashSet(),
            approvedAccounts = accounts
                .filter { it.address in approvedAccounts }
                .map { it.id }
                .toPersistentHashSet(),
            approvedNetworks = networks
                .filter { it.chainId in session.approved.chains }
                .map { it.id }
                .toPersistentHashSet(),
        )
    }
}
