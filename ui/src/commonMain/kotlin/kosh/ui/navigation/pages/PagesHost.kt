package kosh.ui.navigation.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.Child
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.pages.ChildPages
import kosh.presentation.core.LocalUiContext
import kosh.presentation.core.UiContext
import kosh.ui.navigation.koshAnimation
import kosh.ui.navigation.routes.Route

@Composable
fun <R : Route> PagesHost(
    pagesRouter: PagesRouter<R>,
    modifier: Modifier = Modifier,
    content: @Composable PagesRouter<R>.(R) -> Unit,
) {
    val holder = rememberSaveableStateHolder()

    val pages by pagesRouter.pages.subscribeAsState()

    holder.retainStates(pages.getKeys())

    koshAnimation(
        modifier = modifier,
        state = pages.items[pages.selectedIndex] as Child.Created<R, UiContext>,
        contentKey = { it.configuration },
    ) {
        holder.SaveableStateProvider(it.keyHashString()) {
            CompositionLocalProvider(LocalUiContext provides it.instance) {
                pagesRouter.content(it.configuration)
            }
        }
    }
}

fun Child<*, *>.keyHashString(): String =
    "${configuration::class.qualifiedName ?: configuration::class.simpleName}_" +
            key.hashCode().toString(radix = 36)

private fun ChildPages<*, *>.getKeys(): Set<String> =
    items.mapTo(HashSet()) { it.keyHashString() }

@Composable
private fun SaveableStateHolder.retainStates(currentKeys: Set<String>) {
    val keys = remember(this) { Keys(currentKeys) }

    DisposableEffect(this, currentKeys) {
        keys.set.forEach {
            if (it !in currentKeys) {
                removeState(it)
            }
        }

        keys.set = currentKeys

        onDispose {}
    }
}

private class Keys(
    var set: Set<Any>,
)
