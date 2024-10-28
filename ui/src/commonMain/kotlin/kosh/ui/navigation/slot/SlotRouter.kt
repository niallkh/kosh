package kosh.ui.navigation.slot

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigator
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.UiContext
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable


interface SlotRouter<R : Route> : SlotNavigator<R> {
    val slot: Value<ChildSlot<R, UiContext>>
}

fun SlotRouter<Slot>.activate() {
    activate(Slot)
}

@Serializable
data object Slot : Route
