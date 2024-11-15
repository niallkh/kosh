package kosh.presentation.wc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import kosh.domain.failure.AppFailure
import kosh.domain.models.reown.SessionTopic
import kosh.domain.models.reown.WcRequest
import kosh.domain.usecases.network.NetworkService
import kosh.domain.usecases.reown.WcRequestService
import kosh.presentation.core.di
import kosh.presentation.rememberEitherEffect

@Composable
fun rememberAddNetwork(
    id: WcRequest.Id,
    onAdded: () -> Unit,
    requestService: WcRequestService = di { domain.wcRequestService },
    networkService: NetworkService = di { domain.networkService },
): AddNetworkState {
    val addNetwork = rememberEitherEffect(
        id,
        onFinish = { onAdded() }
    ) { (sessionTopic, addNetwork): Pair<SessionTopic, WcRequest.Call.AddNetwork> ->

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
            sessionTopic = sessionTopic,
            chainId = addNetwork.chainId
        ).bind()
    }

    return remember {
        object : AddNetworkState {
            override val adding: Boolean get() = addNetwork.inProgress
            override val failure: AppFailure? get() = addNetwork.failure
            override fun invoke(topic: SessionTopic, call: WcRequest.Call.AddNetwork) {
                addNetwork(topic to call)
            }
        }
    }
}

@Stable
interface AddNetworkState {
    val adding: Boolean
    val failure: AppFailure?
    operator fun invoke(topic: SessionTopic, call: WcRequest.Call.AddNetwork)
}
