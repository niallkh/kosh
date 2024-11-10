package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.GenericComponentContext
import kosh.presentation.di.PresentationScope

interface PresentationContext : GenericComponentContext<PresentationContext> {
    val logger: Logger
    val presentationScope: PresentationScope
    val container: MutableMap<String, Any>
}
