package kosh.app.di

import co.touchlab.kermit.Logger
import kosh.domain.WindowRepositoriesComponent
import kosh.presentation.core.AppContext
import kosh.presentation.di.RouteScopeFactory

public interface WindowScope {

    public val routeScopeFactory: RouteScopeFactory

    public val windowRepositoriesComponent: WindowRepositoriesComponent

    public val appContext: AppContext

    public val deeplinkHandler: DeeplinkHandler
}

public class DeeplinkHandler {

    private val logger = Logger.withTag("[K]")

    private var subscriber: ((String?) -> Unit)? = null
    private var deeplink: String? = null

    public fun handle(url: String?) {
        logger.d { "handle(url: $url)" }
        if (subscriber != null) {
            subscriber?.invoke(url)
        } else {
            deeplink = url
        }
    }

    public fun subscribe(subscriber: (String?) -> Unit) {
        require(this.subscriber == null) {
            "DeeplinkHandler is already subscribed"
        }
        this.subscriber = subscriber
        deeplink?.let(subscriber)
        deeplink = null
    }
}
