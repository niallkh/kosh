package kosh.ui.navigation

import com.eygraber.uri.Uri
import kosh.domain.models.reown.PairingUri
import kosh.domain.models.reown.WcRequest
import kosh.ui.navigation.routes.RootRoute
import kosh.ui.navigation.routes.Route
import kosh.ui.navigation.routes.wc.WcProposalRoute
import kosh.ui.navigation.routes.wc.WcRequestRoute
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.cbor.Cbor
import okio.ByteString.Companion.decodeBase64
import okio.ByteString.Companion.toByteString

@Serializable
data object Deeplink : Route

fun parseDeeplink(uriStr: String): RootRoute? {
    val uri = Uri.parseOrNull(uriStr)
    return when (uri?.scheme) {

        "kosh" -> when {
            uri.host == "request" -> RootRoute.WcRequest(WcRequestRoute.Request(null, Deeplink))
            else -> null
        }

        "wc" -> when {
            arrayOf("@2", "symKey").all { it in uriStr } -> wcPairRoute(uriStr)
            arrayOf("@2").all { it in uriStr } -> wcRequestRoute(uriStr)
            else -> null
        }

        "route" -> appRoute(uri)

        else -> null
    }
}

private fun wcPairRoute(uriStr: String): RootRoute? = PairingUri(uriStr).getOrNull()?.let {
    RootRoute.WcProposal(WcProposalRoute.Pair(it, Deeplink))
}

private fun wcRequestRoute(uriStr: String) = RootRoute.WcRequest(
    WcRequestRoute.Request(
        id = uriStr.substringAfter("requestId=")
            .substringBefore("&")
            .toLongOrNull()
            ?.let { WcRequest.Id(it) },
        Deeplink,
    )
)

private fun appRoute(uri: Uri): RootRoute? = uri.host?.decodeBase64()
    ?.let { Cbor.decodeFromByteArray(RootRoute.serializer().nullable, it.toByteArray()) }

fun deeplink(route: RootRoute?): kosh.domain.models.Uri {
    val screen = Cbor.encodeToByteArray(RootRoute.serializer().nullable, route).toByteString()
        .base64Url()

    return kosh.domain.models.Uri("route://${screen}")
}

tailrec fun Route?.isDeeplink(): Boolean {
    if (this == null) return false
    return if (link == Deeplink) true
    else link.isDeeplink()
}
