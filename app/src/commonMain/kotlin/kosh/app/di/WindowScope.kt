package kosh.app.di

import co.touchlab.kermit.Logger
import kosh.domain.WindowRepositoriesComponent
import kosh.presentation.core.AppContext
import kosh.presentation.di.RouteScopeFactory

interface WindowScope {

    val routeScopeFactory: RouteScopeFactory

    val windowRepositoriesComponent: WindowRepositoriesComponent

    val appContext: AppContext

    val deeplinkHandler: DeeplinkHandler
}

class DeeplinkHandler {

    private val logger = Logger.withTag("[K]")

    private var subscriber: ((String) -> Unit)? = null
    private var deeplink: String? = null

    fun handle(url: String) {
        logger.d { "handle(url: $url)" }
        if (subscriber != null) {
            subscriber?.invoke(url)
        } else {
            deeplink = url
        }
    }

    fun subscribe(subscriber: (String) -> Unit) {
        require(this.subscriber == null) {
            "DeeplinkHandler is already subscribed"
        }
        this.subscriber = subscriber
        deeplink?.let(subscriber)
        deeplink = null
    }
}
