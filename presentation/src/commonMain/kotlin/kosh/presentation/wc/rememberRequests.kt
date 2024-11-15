package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
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

    return remember {
        object : RequestsState {
            override val requests get() = requests.result
            override fun retry() {
                requests.retry()
            }
        }
    }
}

@Stable
interface RequestsState {
    val requests: ImmutableList<WcRequest>
    fun retry()
}
