package kosh.presentation.wc

import androidx.compose.runtime.Composable
import kosh.domain.models.reown.WcRequest
import kosh.domain.serializers.ImmutableList
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberCollect
import kotlinx.collections.immutable.persistentListOf

@Composable
fun rememberRequests(
    requestService: WcRequestService = di { domain.wcRequestService },
): RequestsState {
    val requests = rememberCollect(persistentListOf()) {
        requestService.requests
    }

    return RequestsState(
        requests = requests.result,
    )
}

data class RequestsState(
    val requests: ImmutableList<WcRequest>,
)
