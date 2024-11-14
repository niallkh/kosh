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
import kosh.domain.failure.NetworkFailure
import kosh.domain.failure.WcFailure
import kosh.domain.models.reown.WcRequest
import kosh.domain.models.reown.WcRequest.Call.AddNetwork
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.di.rememberSerializable

@Composable
fun rememberAddNetworkRequest(
    id: WcRequest.Id,
    requestService: WcRequestService = di { domain.wcRequestService },
    networkService: NetworkService = di { domain.networkService },
): AddNetworkRequestState {
    var request by rememberSerializable { mutableStateOf<WcRequest?>(null) }
    var call by rememberSerializable { mutableStateOf<AddNetwork?>(null) }
    var loading by remember { mutableStateOf(false) }
    var failure by remember { mutableStateOf<WcFailure?>(null) }
    var networkFailure by remember { mutableStateOf<NetworkFailure?>(null) }
    var retry by remember { mutableIntStateOf(0) }
    var add by remember { mutableStateOf(false) }
    var adding by remember { mutableStateOf(false) }
    var added by remember { mutableStateOf(false) }

    LaunchedEffect(retry, id) {
        if (request != null) return@LaunchedEffect
        if (call != null) return@LaunchedEffect

        loading = true

        recover({
            request = requestService.get(id).bind().also { wcRequest ->
                val addNetwork = wcRequest.call as? AddNetwork
                    ?: error("Request is not an AddNetwork")

                call = addNetwork
            }

            loading = false
            failure = null
        }) {
            failure = it
            request = null
            call = null
            loading = false
        }
    }

    LaunchedEffect(add, retry, call) {
        if (!add) return@LaunchedEffect
        val addNetwork = call ?: return@LaunchedEffect
        val request1 = request ?: return@LaunchedEffect

        adding = true

        recover({
            networkService.add(
                name = addNetwork.chainName,
                chainId = addNetwork.chainId,
                tokenName = addNetwork.tokenName,
                tokenSymbol = addNetwork.tokenSymbol,
                explorers = addNetwork.explorers,
                readRpcProvider = addNetwork.rpcProviders.first(),
                icon = addNetwork.icons.firstOrNull(),
                writeRpcProvider = null,
                tokenIcon = null,
            ).bind()

            requestService.onNetworkAdded(
                id = id,
                sessionTopic = request1.sessionTopic,
                chainId = addNetwork.chainId
            )

            added = true

            adding = false
            networkFailure = null
        }) {
            adding = false
            networkFailure = it
        }
    }

    return AddNetworkRequestState(
        request = request,
        call = call,
        loading = loading,
        adding = adding,
        added = added,
        failure = failure,
        networkFailure = networkFailure,
        reject = { requestService.reject(id) },
        add = {
            added = false
            retry++
            add = true
        },
        retry = {
            retry++
        }
    )
}

@Immutable
data class AddNetworkRequestState(
    val request: WcRequest?,
    val call: AddNetwork?,
    val added: Boolean,
    val loading: Boolean,
    val adding: Boolean,
    val failure: WcFailure?,
    val networkFailure: NetworkFailure?,
    val add: () -> Unit,
    val reject: () -> Unit,
    val retry: () -> Unit,
)
