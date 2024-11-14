package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import arrow.core.raise.recover
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcAuthentication
import kosh.domain.models.reown.WcSessionProposal
import kosh.domain.serializers.Either
import kosh.domain.usecases.reown.WcProposalService
import kosh.presentation.core.di
import kosh.presentation.di.rememberRetained

@Composable
fun rememberPair(
    initial: PairingUri?,
    proposalService: WcProposalService = di { domain.wcProposalService },
): PairState {
    var pairingUri by rememberRetained { mutableStateOf(initial) }
    var proposal by rememberRetained {
        mutableStateOf<Either<WcSessionProposal, WcAuthentication>?>(null)
    }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }

    LaunchedEffect(retry, pairingUri) {
        val uri = pairingUri ?: return@LaunchedEffect

        loading = true

        recover({
            proposal = proposalService.pair(uri).bind()

            loading = false
            failure = null
        }) {
            failure = it
            loading = false
        }
    }

    return PairState(
        proposal = proposal,
        loading = loading,
        failure = failure,
        pair = {
            retry++
            pairingUri = it
        },
        retry = { retry++ }
    )
}

@Immutable
data class PairState(
    val proposal: Either<WcSessionProposal, WcAuthentication>?,
    val loading: Boolean,
    val failure: WcFailure?,
    val pair: (PairingUri) -> Unit,
    val retry: () -> Unit,
)
