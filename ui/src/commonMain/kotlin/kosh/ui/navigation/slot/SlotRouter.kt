package kosh.ui.navigation.slot

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigator
import com.arkivanov.decompose.value.Value
import kosh.presentation.core.UiContext
import kosh.ui.navigation.routes.Route
import kotlinx.serialization.Serializable


interface SlotRouter : SlotNavigator<Slot> {
    val slot: Value<ChildSlot<Slot, UiContext>>

    fun activate()
}

@Serializable
data object Slot : Route
