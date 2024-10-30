package kosh.ui.navigation.stack

import androidx.compose.runtime.Stable
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import kosh.presentation.core.PresentationContext
import kosh.ui.navigation.Router
import kosh.ui.navigation.routes.Route

@Stable
interface StackRouter<R : Route> : Router<R>, StackNavigation<R>, BackHandlerOwner {

    val stack: Value<ChildStack<R, PresentationContext>>
}
