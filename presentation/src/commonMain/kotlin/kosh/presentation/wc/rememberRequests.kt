package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kosh.domain.models.wc.WcRequest
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.wc.WcRequestService
import kosh.presentation.di.di
import kosh.presentation.di.rememberLifecycleState
import kosh.presentation.di.rememberRetainable
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

@Composable
fun rememberRequests(
    requestService: WcRequestService = di { domain.wcRequestService },
): RequestsState {
    var requests by rememberRetainable { mutableStateOf(persistentListOf<WcRequest>()) }

    if (rememberLifecycleState()) {
        LaunchedEffect(Unit) {
            requestService.requests.collect {
                requests = it.toPersistentList()
            }
        }
    }

    return RequestsState(
        requests = requests,
    )
}

data class RequestsState(
    val requests: ImmutableList<WcRequest>,
)
