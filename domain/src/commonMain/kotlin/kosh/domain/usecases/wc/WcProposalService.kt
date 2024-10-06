package kosh.domain.usecases.wc

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.wc.PairingUri
import kosh.domain.models.wc.WcAuthentication
import kosh.domain.models.wc.WcSessionProposal
import kosh.domain.models.wc.WcProposalAggregated
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

interface WcProposalService {

    val proposals: Flow<List<WcSessionProposal>>

    suspend fun pair(uri: PairingUri): Either<WcFailure, Either<WcSessionProposal, WcAuthentication>>

    suspend fun get(
        id: WcSessionProposal.Id,
        requestId: Long,
    ): Either<WcFailure, WcProposalAggregated>

    suspend fun approve(
        id: WcSessionProposal.Id,
        approvedAccounts: List<Address>,
        approvedChains: List<ChainId>,
    ): Either<WcFailure, Unit>

    fun reject(id: WcSessionProposal.Id): Job
}

