package kosh.presentation.di

import co.touchlab.kermit.Logger

class DeeplinkHandler {

    private val logger = Logger.withTag("[K]")

    private var subscriber: ((String?) -> Unit)? = null
    private var deeplink: String? = null

    fun handle(url: String?) {
        logger.d { "handle(url: $url)" }
        if (subscriber != null) {
            subscriber?.invoke(url)
        } else {
            deeplink = url
        }
    }

    fun subscribe(subscriber: (String?) -> Unit) {
        require(this.subscriber == null) {
            "DeeplinkHandler is already subscribed"
        }
        this.subscriber = subscriber
        deeplink?.let(subscriber)
        deeplink = null
    }
}
