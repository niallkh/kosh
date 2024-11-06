package kosh.ui.navigation

import androidx.compose.runtime.Stable
import kosh.ui.navigation.routes.Route

@Stable
interface Router<R : Route> {

    fun pop(result: RouteResult<R>)

    fun pop()

    fun result(redirect: String? = null)

    fun navigateUp()
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

inline fun <R1 : Route, R2 : Route> Router<R1>.pop(
    result: RouteResult<R2>,
    map: (R2) -> R1?,
) = pop(
    when (result) {
        is RouteResult.Finished -> result
        is RouteResult.Up -> RouteResult.Up(result.route?.let(map))
    }
)
