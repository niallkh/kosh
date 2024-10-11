package kosh.domain.usecases.reown

import arrow.core.Either
import kosh.domain.failure.WcFailure
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.Redirect
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.models.reown.WcSessionProposal
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
    ): Either<WcFailure, Redirect?>

    fun reject(id: WcSessionProposal.Id): Job
}

