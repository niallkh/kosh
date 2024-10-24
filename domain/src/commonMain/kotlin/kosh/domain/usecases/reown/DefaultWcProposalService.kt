package kosh.domain.usecases.reown

import arrow.core.Either
import arrow.core.left
import arrow.core.raise.Raise
import arrow.core.raise.either
import arrow.core.raise.ensure
import arrow.core.raise.recover
import arrow.core.right
import co.touchlab.kermit.Logger
import kosh.domain.failure.WcFailure
import kosh.domain.failure.logFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainAddress
import kosh.domain.models.ChainId
import kosh.domain.models.Redirect
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.repositories.WcRepo
import kosh.domain.state.AppState
import kosh.domain.state.AppStateProvider
import kosh.domain.state.networks
import kosh.domain.usecases.notification.NotificationService
import kosh.domain.utils.optic
import kotlinx.collections.immutable.toPersistentHashSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
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

    override val proposals: Flow<List<WcSessionProposal>>
        get() = reownRepo.proposals

    override suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Either<WcSessionProposal, WcAuthentication>> = either {

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
                asyncAuthentication.cancel()
                notificationService.cancel(it.requestId)
                it.left().right()
            }
            asyncAuthentication.onAwait {
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
        requestId.let { notificationService.cancel(it) }

        val proposal = reownRepo.getProposal(id).bind()

        wcProposalAggregated(proposal)
    }

    override suspend fun approve(
        id: WcSessionProposal.Id,
        approvedAccounts: List<Address>,
        approvedChains: List<ChainId>,
    ): Either<WcFailure, Redirect?> = reownRepo.approveProposal(
        id = id,
        approvedAccounts = approvedChains.flatMap { chainId ->
            approvedAccounts.map { account -> ChainAddress(chainId, account) }
        },
    )

    override fun reject(
        id: WcSessionProposal.Id,
    ): Job = applicationScope.launch {

        recover({
            reownRepo.rejectProposal(id).bind()
        }) {
            logger.logFailure(it)
        }
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
            networks = networks
                .filter { it.chainId in proposal.required.chains || it.chainId in proposal.optional.chains }
                .map { it.id }
                .toPersistentHashSet(),
            required = networks
                .filter { it.chainId in proposal.required.chains }
                .map { it.id }
                .toPersistentHashSet()
        )
    }
}
