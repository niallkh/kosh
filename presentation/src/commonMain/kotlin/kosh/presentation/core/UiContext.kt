package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.GenericComponentContext
import kosh.presentation.di.UiScope
import kotlin.reflect.KType

interface UiContext : GenericComponentContext<UiContext> {
    val logger: Logger
    val uiScope: UiScope
    val container: MutableMap<KType, Any>
}

