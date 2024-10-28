package kosh.ui.navigation.pages

import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.UiContext
import kosh.ui.navigation.Router
import kosh.ui.navigation.routes.Route

interface PagesRouter<R : Route> : Router<R>, PagesNavigation<R> {

    val pages: Value<ChildPages<R, UiContext>>
}
