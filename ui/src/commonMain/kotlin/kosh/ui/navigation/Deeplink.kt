package kosh.ui.navigation

import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcRequest
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.Route
import kosh.ui.navigation.routes.TransactionsRoute
import kosh.ui.navigation.routes.WalletConnectRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.cbor.Cbor
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.toByteString
import kotlin.LazyThreadSafetyMode.NONE
import kotlin.uuid.Uuid

@Serializable
data class Deeplink(
    @Transient
    private val uuid: Uuid = Uuid.random(),
) : Route

fun parseDeeplink(uriStr: String): RootRoute? {

    val path by lazy(NONE) {
        uriStr.substringAfter(":", "")
            .removePrefix("//")
            .substringBefore("?")
            .removeSuffix("/")
    }

    val pairingUri by lazy(NONE) {
        uriStr
            .substringAfter("uri=", "")
            .substringBefore("&")
    }

    val requestId by lazy(NONE) {
        uriStr
            .substringAfter("requestId=")
            .substringBefore("&")
            .toLongOrNull()
    }

    val scheme = uriStr.substringBefore(":")

    return when (scheme) {
        "kosh", "metamask" -> when (path) {
            "wc" -> when {
                pairingUri.isNotEmpty() -> wcPairRoute(UrlDecoder.decode(pairingUri))
                else -> wcRequestRoute(requestId)
            }

            "request" -> wcRequestRoute(requestId)
            else -> null
        }

        "wc" -> when {
            arrayOf("requestId").all { it in uriStr } -> wcRequestRoute(requestId)
            else -> wcPairRoute(uriStr)
        }

        "route" -> appRoute(path)

        else -> null
    }
}

private fun wcPairRoute(uriStr: String): RootRoute? = PairingUri(uriStr).getOrNull()?.let {
    RootRoute.WalletConnect(WalletConnectRoute.Pair(it, Deeplink()))
}

private fun wcRequestRoute(requestId: Long?) = RootRoute.Transactions(
    TransactionsRoute.Request(requestId?.let { WcRequest.Id(it) }, Deeplink())
)

private fun appRoute(path: String): RootRoute? = path.decodeBase64()
    ?.let { Cbor.decodeFromByteArray(RootRoute.serializer().nullable, it.toByteArray()) }

fun deeplink(route: RootRoute?): kosh.domain.models.Uri {
    val screen = Cbor.encodeToByteArray(RootRoute.serializer().nullable, route).toByteString()
        .base64Url()

    return kosh.domain.models.Uri("route://${screen}")
}

tailrec fun Route?.isDeeplink(): Boolean {
    if (this == null) return false
    return if (link is Deeplink) true
    else link.isDeeplink()
}
