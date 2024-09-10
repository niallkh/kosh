package kosh.ui.navigation.listdetails

import com.arkivanov.decompose.Child
import com.arkivanov.decompose.router.children.ChildNavState
import com.arkivanov.decompose.router.children.NavState
import com.arkivanov.decompose.router.children.SimpleChildNavState
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.RouteContext
import kosh.ui.navigation.Router
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable

interface ListDetailRouter<R : Route> : Router<R> {

    val children: Value<Children<R>>

    fun multipane(multipane: Boolean)

    fun push(route: R)

    data class Children<C : Any>(
        val multipane: Boolean,
        val list: Child.Created<C, RouteContext>,
        val detail: Child.Created<C, RouteContext>?,
    )
}

@Serializable
data class ListDetailState<C : Any>(
    val list: C,
    val detail: C? = null,
    val multipane: Boolean = false,
) : NavState<C> {

    override val children: List<ChildNavState<C>>
        get() = listOfNotNull(
            SimpleChildNavState(
                configuration = list,
                status = if (multipane || (detail == null)) ChildNavState.Status.RESUMED
                else ChildNavState.Status.CREATED
            ),
            if (detail != null) SimpleChildNavState(
                configuration = detail,
                status = ChildNavState.Status.RESUMED
            )
            else null
        )
}

