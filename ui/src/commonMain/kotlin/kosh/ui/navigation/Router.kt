package kosh.ui.navigation

import kosh.ui.navigation.routes.Route

interface Router<R : Route> {

    fun pop(result: RouteResult<R>)

    fun pop()

    fun finish()

    fun navigateUp()

    fun handle(link: R)

    fun reset()
}

sealed class RouteResult<out R : Route> {
    data object Finished : RouteResult<Nothing>()
    data object Canceled : RouteResult<Nothing>()
    data class Up<R : Route>(val route: R?) : RouteResult<R>()
}

inline fun <R1 : Route, R2 : Route> Router<R1>.pop(
    result: RouteResult<R2>,
    map: (R2) -> R1?,
) {
    val mappedResult = when (result) {
        is RouteResult.Finished -> RouteResult.Finished
        is RouteResult.Canceled -> result
        is RouteResult.Up -> RouteResult.Up(result.route?.let(map))
    }

    pop(mappedResult)
}
