package kosh.domain.usecases.reown

import arrow.core.Either
import kosh.domain.entities.AccountEntity
import kosh.domain.entities.NetworkEntity
import kosh.domain.failure.WcFailure
import kosh.domain.models.Redirect
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcProposalAggregated
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.ImmutableList
import kotlinx.coroutines.flow.Flow

interface WcProposalService {

    val proposals: Flow<ImmutableList<WcSessionProposal>>

    suspend fun pair(uri: PairingUri): Either<WcFailure, Either<WcSessionProposal, WcAuthentication>>

    suspend fun get(
        id: WcSessionProposal.Id,
        requestId: Long,
    ): Either<WcFailure, WcProposalAggregated>

    suspend fun approve(
        id: WcSessionProposal.Id,
        approvedAccounts: List<AccountEntity>,
        approvedNetworks: List<NetworkEntity>,
    ): Either<WcFailure, Redirect?>

    suspend fun reject(id: WcSessionProposal.Id): Either<WcFailure, Unit>
}

