package kosh.datastore.migrations

import kosh.domain.models.Uri
import kosh.domain.models.arbitrumOne
import kosh.domain.models.avalanche
import kosh.domain.models.base
import kosh.domain.models.bnbChain
import kosh.domain.models.ethereum
import kosh.domain.models.gnosis
import kosh.domain.models.optimism
import kosh.domain.models.polygonPos
import kosh.domain.models.sepolia
import kosh.domain.state.AppState
import kosh.domain.utils.Copy

internal fun Copy<AppState>.chains() {
    ethereum()
    arbitrumOne()
    optimism()
    base()
    gnosis()
    avalanche()
    polygonPos()
    bnbChain()
}

internal fun Copy<AppState>.ethereum() {
    network(
        chainId = ethereum,
        name = "Ethereum Mainnet",
        readRpcProvider = Uri("https://eth-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://etherscan.io"),
        enabled = true,
        icon = "eth",
    )

    token(
        chainId = ethereum,
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.optimism() {
    network(
        chainId = optimism,
        name = "OP Mainnet",
        readRpcProvider = Uri("https://optimism-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://optimistic.etherscan.io"),
        enabled = true,
        icon = "op"
    )

    token(
        chainId = optimism,
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.arbitrumOne() {
    network(
        chainId = arbitrumOne,
        name = "Arbitrum One",
        readRpcProvider = Uri("https://arbitrum-one.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://arbiscan.io"),
        enabled = true,
        icon = "arb",
    )

    token(
        chainId = arbitrumOne,
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.base() {
    network(
        chainId = base,
        name = "Base",
        readRpcProvider = Uri("https://base-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://basescan.org"),
        enabled = true,
        icon = "base"
    )

    token(
        chainId = base,
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.polygonPos() {
    network(
        chainId = polygonPos,
        name = "Polygon Mainnet",
        readRpcProvider = Uri("https://poly-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://celoscan.io"),
        enabled = true,
        icon = "matic"
    )

    token(
        chainId = polygonPos,
        name = "Polygon",
        symbol = "MATIC",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.bnbChain() {
    network(
        chainId = bnbChain,
        name = "BNB Chain",
        readRpcProvider = Uri("https://bsc-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://bscscan.com"),
        enabled = true,
        icon = "bnb"
    )

    token(
        chainId = bnbChain,
        name = "BNB",
        symbol = "BNB",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.avalanche() {
    network(
        chainId = avalanche,
        name = "Avalanche C-Chain",
        readRpcProvider = Uri("https://avax-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://snowtrace.io"),
        enabled = true,
        icon = "avax"
    )

    token(
        chainId = avalanche,
        name = "Avalanche",
        symbol = "AVAX",
        decimals = 18u,
    )
}

internal fun Copy<AppState>.gnosis() {
    network(
        chainId = gnosis,
        name = "Gnosis",
        readRpcProvider = Uri("https://gnosischain-mainnet.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://gnosisscan.io"),
        enabled = true,
        icon = "gnosis"
    )

    token(
        chainId = gnosis,
        name = "xDAI",
        symbol = "XDAI",
        decimals = 18u,
        icon = "dai"
    )
}

internal fun Copy<AppState>.sepolia() {
    network(
        chainId = sepolia,
        name = "Sepolia Testnet",
        readRpcProvider = Uri("https://sepolia.rpc.grove.city/v1/e2c5489a"),
        explorers = listOf("https://sepolia.etherscan.io"),
        testnet = true,
        icon = "testnet"
    )

    token(
        chainId = sepolia,
        name = "Ether",
        symbol = "ETH",
        decimals = 18u,
    )
}
