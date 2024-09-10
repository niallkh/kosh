package kosh.ui.navigation.stack

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.RouteContext
import kosh.ui.navigation.Router
import kosh.ui.navigation.routes.Route

interface StackRouter<R : Route> : Router<R>, StackNavigation<R> {

    val stack: Value<ChildStack<R, RouteContext>>
}
