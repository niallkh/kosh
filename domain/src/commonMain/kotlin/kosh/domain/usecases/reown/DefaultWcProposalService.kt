package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.right
import co.touchlab.kermit.Logger
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.WcFailure
import kosh.domain.models.ChainAddress
import kosh.domain.models.Redirect
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.repositories.WcRepo
import kosh.domain.serializers.ImmutableList
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.networks
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.utils.optic
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.selects.onTimeout
import kotlinx.coroutines.selects.select
import kotlin.time.Duration.Companion.seconds

class DefaultWcProposalService(
    private val reownRepo: WcRepo,
    private val appStateProvider: AppStateProvider,
    private val notificationService: NotificationService,
    private val applicationScope: CoroutineScope,
) : WcProposalService {
    private val logger = Logger.withTag("[K]WcProposalService")

    override val proposals: Flow<ImmutableList<WcSessionProposal>>
        get() = reownRepo.proposals.map { it.toPersistentList() }

    override suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Either<WcSessionProposal, WcAuthentication>> = either {
        logger.i { "pair()" }

        reownRepo.pair(uri = uri).bind()

        getNextProposalOrAuthentication(uri).bind()
    }

    private suspend fun getNextProposalOrAuthentication(
        uri: PairingUri,
    ): Either<WcFailure, Either<WcSessionProposal, WcAuthentication>> = coroutineScope {

        val asyncProposal = async {
            reownRepo.proposals.flatMapConcat { it.asFlow() }
                .first { it.id.pairingTopic.value in uri.value }
        }

        val asyncAuthentication = async {
            reownRepo.authentications.flatMapConcat { it.asFlow() }
                .first { it.pairingTopic.value in uri.value }
        }

        select {
            asyncProposal.onAwait {
                logger.d { "Proposal arrived" }
                asyncAuthentication.cancel()
                notificationService.cancel(it.requestId)
                it.left().right()
            }
            asyncAuthentication.onAwait {
                logger.d { "Authentication arrived" }
                asyncProposal.cancel()
                notificationService.cancel(it.id.value)
                it.right().right()
            }
            onTimeout(10.seconds) {
                asyncAuthentication.cancel()
                asyncProposal.cancel()
                WcFailure.ResponseTimeout().left()
            }
        }
    }

    override suspend fun get(
        id: WcSessionProposal.Id,
        requestId: Long,
    ): Either<WcFailure, WcProposalAggregated> = either {
        logger.i { "get()" }
        val proposal = reownRepo.getProposal(id).bind()

        requestId.let { notificationService.cancel(proposal.requestId) }

        wcProposalAggregated(proposal)
    }

    override suspend fun approve(
        id: WcSessionProposal.Id,
        approvedAccounts: List<AccountEntity>,
        approvedNetworks: List<NetworkEntity>,
    ): Either<WcFailure, Redirect?> {
        logger.i { "approve()" }
        return reownRepo.approveProposal(
            id = id,
            approvedAccounts = approvedNetworks.flatMap { network ->
                approvedAccounts.map { account ->
                    ChainAddress(network.chainId, account.address)
                }
            },
        )
    }

    override suspend fun reject(
        id: WcSessionProposal.Id,
    ): Either<WcFailure, Unit> = either {
        logger.i { "reject()" }
        reownRepo.rejectProposal(id).bind()
    }

    private fun Raise<WcFailure>.wcProposalAggregated(
        proposal: WcSessionProposal,
    ): WcProposalAggregated {
        val networks = appStateProvider.optic(AppState.networks).value.values

        ensure(proposal.required.chains.all { chainId -> networks.any { it.chainId == chainId } }) {
            WcFailure.WcInvalidDapp.NoRequiredChains()
        }

        return WcProposalAggregated(
            proposal = proposal,
            availableNetworks = networks
                .filter { it.chainId in proposal.required.chains || it.chainId in proposal.optional.chains }
                .map { it.id }
                .toPersistentHashSet(),
            requiredNetworks = networks
                .filter { it.chainId in proposal.required.chains }
                .map { it.id }
                .toPersistentHashSet()
        )
    }
}
