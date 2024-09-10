package kosh.domain.usecases.wc

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
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcProposal
import kosh.domain.models.wc.WcProposalAggregated
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
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds

class DefaultWcProposalService(
    private val wcRepo: WcRepo,
    private val appStateProvider: AppStateProvider,
    private val notificationService: NotificationService,
    private val applicationScope: CoroutineScope,
) : WcProposalService {
    private val logger = Logger.withTag("WcSessionProposal")

    override val proposals: Flow<List<WcProposal>>
        get() = wcRepo.proposals

    override suspend fun pair(
        uri: PairingUri,
    ): Either<WcFailure, Either<WcProposal, WcAuthentication>> = either {
        withTimeoutOrNull(10.seconds) {
            wcRepo.pair(uri = uri).bind()
        } ?: raise(WcFailure.PairingUriInvalid())

        getNextProposalOrAuthentication(uri).bind()
    }

    private suspend fun getNextProposalOrAuthentication(
        uri: PairingUri,
    ): Either<WcFailure, Either<WcProposal, WcAuthentication>> = coroutineScope {

        val asyncProposal = async {
            wcRepo.proposals.flatMapConcat { it.asFlow() }
                .first { it.id.pairingTopic.value in uri.value }
        }

        val asyncAuthentication = async {
            wcRepo.authentications.flatMapConcat { it.asFlow() }
                .first {
                    logger.w { it.toString() }
                    it.pairingTopic.value in uri.value
                }
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
                WcFailure.NoConnection().left()
            }
        }
    }

    override suspend fun get(
        id: WcProposal.Id,
        requestId: Long,
    ): Either<WcFailure, WcProposalAggregated> = either {
        requestId.let { notificationService.cancel(it) }

        val proposal = withTimeoutOrNull(10.seconds) {
            wcRepo.proposals.flatMapConcat { it.asFlow() }
                .first { it.id == id }
        }
            ?: raise(WcFailure.ProposalNotFound())

        wcProposalAggregated(proposal)
    }

    override suspend fun approve(
        id: WcProposal.Id,
        approvedAccounts: List<Address>,
        approvedChains: List<ChainId>,
    ): Either<WcFailure, Unit> = wcRepo.approveSessionProposal(
        id = id,
        approvedAccounts = approvedChains.flatMap { chainId ->
            approvedAccounts.map { account -> ChainAddress(chainId, account) }
        },
    )

    override fun reject(
        id: WcProposal.Id,
    ): Job = applicationScope.launch {

        recover({
            wcRepo.rejectSessionProposal(id, "Rejected").bind()
        }) {
            logger.logFailure(it)
        }
    }

    private suspend fun Raise<WcFailure>.wcProposalAggregated(
        proposal: WcProposal,
    ): WcProposalAggregated {
        val networks = appStateProvider.optic(AppState.networks).value.values

        val namespace = wcRepo.getNamespace(proposal.id).bind()

        ensure(namespace.requiredChains.all { chainId -> networks.any { it.chainId == chainId } }) {
            WcFailure.WcInvalidDapp.NoRequiredChains()
        }

        return WcProposalAggregated(
            proposal = proposal,
            networks = networks
                .filter { it.chainId in namespace.requiredChains || it.chainId in namespace.optionalChains }
                .map { it.id }
                .toPersistentHashSet(),
            required = networks
                .filter { it.chainId in namespace.requiredChains }
                .map { it.id }
                .toPersistentHashSet()
        )
    }
}
