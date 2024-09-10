package kosh.data.web3

import kosh.eth.rpc.Web3ProviderFactory

interface Web3Component {
    val web3ProviderFactory: Web3ProviderFactory
}
