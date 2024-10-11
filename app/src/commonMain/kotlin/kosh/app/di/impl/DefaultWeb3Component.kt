package kosh.app.di.impl

import kosh.app.di.NetworkComponent
import kosh.app.di.SerializationComponent
import kosh.data.web3.Web3Component
import kosh.domain.core.provider
import kosh.eth.rpc.Web3ProviderFactory
import kosh.eth.rpc.web3ProviderFactory

internal class DefaultWeb3Component(
    networkComponent: NetworkComponent,
    serializationComponent: SerializationComponent,
) : Web3Component,
    NetworkComponent by networkComponent,
    SerializationComponent by serializationComponent {

    override val web3ProviderFactory: Web3ProviderFactory by provider {
        web3ProviderFactory(
            httpClient = httpClient,
            json = json,
        )
    }
}
