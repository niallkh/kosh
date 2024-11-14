package kosh.ui.navigation

import androidx.compose.runtime.Stable
import kosh.ui.navigation.routes.Route

@Stable
interface Router<R : Route> {

    fun reset(link: R? = null)
}

sealed class RouteResult<out R : Route> {
    data class Finished(
        val redirect: String?,
    ) : RouteResult<Nothing>() {
        companion object {
            private val empty = Finished(null)
            operator fun invoke() = empty
        }
    }

    data class Up<R : Route>(
        val route: R?,
    ) : RouteResult<R>()
}
