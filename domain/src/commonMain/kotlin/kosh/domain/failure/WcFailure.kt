package kosh.domain.failure

import androidx.compose.runtime.Immutable
import kosh.domain.models.Address
import kosh.domain.models.ChainId
import kosh.domain.models.eip55

@Immutable
sealed interface WcFailure : AppFailure {

    class NoConnection : WcFailure {
        override val message: String
            get() = "No connection"
    }

    class ResponseTimeout : WcFailure {
        override val message: String
            get() = "Timeout, no response"
    }

    class PairingUriInvalid : WcFailure {
        override val message: String
            get() = "Invalid pairing uri, try again"
    }

    class AlreadyPaired : WcFailure {
        override val message: String
            get() = "Already paired, try again"
    }

    class Expired : WcFailure {
        override val message: String
            get() = "Expired or not found, please try a new one"
    }

    class InvalidNamespace : WcFailure {
        override val message: String
            get() = "Invalid namespace"
    }

    class Other(override val message: String) : WcFailure

    @Immutable
    sealed interface WcInvalidDapp : WcFailure {

        class NoRequiredChains : WcInvalidDapp {
            override val message: String
                get() = "No Required chains"
        }

        class NoSupportedMethods : WcInvalidDapp {
            override val message: String
                get() = "No supported methods"
        }

        class NoSupportedEvents : WcInvalidDapp {
            override val message: String
                get() = "No supported events"
        }

        class NoSupportedAccounts : WcInvalidDapp {
            override val message: String
                get() = "No supported accounts"
        }

        class NoApprovedAccounts : WcInvalidDapp {
            override val message: String
                get() = "No approved accounts"
        }

        class NoMetadata : WcInvalidDapp {
            override val message: String
                get() = "No Dapp Metadata"
        }
    }

    @Immutable
    sealed interface WcInvalidRequest : WcFailure {
        class MethodNotSupported(private val method: String) : WcInvalidRequest {
            override val message: String
                get() = "Method not supported: $method"
        }

        class NetworkDisabled(private val chainId: ChainId) : WcInvalidRequest {
            override val message: String
                get() = "Network disabled: #${chainId.value}"
        }

        class NetworkAlreadyExists(
            val chainId: ChainId,
            val enabled: Boolean,
        ) : WcInvalidRequest {
            override val message: String
                get() = "Network already exists: #${chainId.value}"
        }

        class AccountDisabled(private val address: Address) : WcInvalidRequest {
            override val message: String
                get() = "Account disabled: ${address.eip55()}"
        }

        class Other(override val message: String) : WcInvalidRequest
    }
}

// 2024-10-29 13:35:29.300 29067-29961 [K]AndroidReownAdapter  eth.kosh.app.debug
// V  onSessionRequest: SessionRequest(topic=6e7d4fb408f21e00295f475a0a9cce940a488eca02221dde25cf72339d7c9ab8, chainId=eip155:1, peerMetaData=AppMetaData(name=Zerion Web App, description=Zerion is the easiest way to build and manage your entire DeFi portfolio from one place. Discover the world of decentralized finance today., url=https://app.zerion.io/, icons=[https://s3.amazonaws.com/icons.assets/zerion/zerion_desktop_icon.png], redirect=, appLink=null, linkMode=false, verifyUrl=null), request=JSONRPCRequest(id=1730205253272813, method=eth_sendTransaction, params=[{"from":"0xab35439b3d35f39d1389b3e69dac0396d241b547","to":"0x5eddc8a7669274a1506828bb6389a4c20feeec49","value":"0x2386f26fc10000","chainId":"0x1","data":"0x",
// "gas":"0x5a3c",
// "maxFeePerGas":"0x6a3813fe0",
// "maxPriorityFeePerGas":"0xf52a6"}])), VerifyContext(id=1730205253272813, origin=https://app.zerion.io, validation=VALID, verifyUrl=https://verify.walletconnect.org/, isScam=false)
