package kosh.ui.navigation.stack

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kosh.presentation.core.PresentationContext
import kosh.ui.navigation.RouteResult
import kosh.ui.navigation.Router
import kosh.ui.navigation.routes.Route

@Stable
interface StackRouter<R : Route> :
    Router<R>,
    StackNavigation<R>,
    BackHandlerOwner {

    val stack: Value<ChildStack<R, PresentationContext>>
}


inline fun <R1 : Route, R2 : Route> StackRouter<R1>.pop(
    result: RouteResult<R2>,
    crossinline map: (R2) -> R1?,
    crossinline onResult: (RouteResult<R1>) -> Unit,
) = pop {
    if (!it) {
        val mappedResult = when (result) {
            is RouteResult.Finished -> result
            is RouteResult.Up -> RouteResult.Up(result.route?.let(map))
        }
        onResult(mappedResult)
    }
}

inline fun StackRouter<*>.popOr(
    crossinline block: () -> Unit,
) = pop {
    if (!it) {
        block()
    }
}
