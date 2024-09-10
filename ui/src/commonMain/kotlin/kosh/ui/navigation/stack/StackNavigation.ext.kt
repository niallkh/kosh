package kosh.ui.navigation.stack

import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop

inline fun <C : Any> StackNavigation<C>.popOr(
    crossinline or: () -> Unit,
) = pop { if (it.not()) or() }
