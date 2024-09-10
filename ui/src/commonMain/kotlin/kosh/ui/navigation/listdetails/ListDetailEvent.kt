package kosh.ui.navigation.listdetails

import com.arkivanov.decompose.router.children.SimpleNavigation

class ListDetailEvent<C : Any>(
    val transform: (ListDetailState<C>) -> ListDetailState<C>,
    val onComplete: (newState: ListDetailState<C>, oldState: ListDetailState<C>) -> Unit = { _, _ -> },
)

fun <C : Any> SimpleNavigation<ListDetailEvent<C>>.push(
    detail: C,
    success: (Boolean) -> Unit = {},
) {
    navigate(
        ListDetailEvent(
            transform = { it.copy(detail = detail) },
            onComplete = { new, old -> success(new.detail != null && new.detail != old.detail) }
        )
    )
}

fun <C : Any> SimpleNavigation<ListDetailEvent<C>>.popOr(or: () -> Unit = {}) {
    navigate(
        ListDetailEvent(
            transform = { it.copy(detail = null) },
            onComplete = { new, old -> if (new.detail != null || old.detail == null) or() }
        )
    )
}

fun <C : Any> SimpleNavigation<ListDetailEvent<C>>.multipane(multipane: Boolean) {
    navigate(
        ListDetailEvent(
            transform = { it.copy(multipane = multipane) },
        )
    )
}
