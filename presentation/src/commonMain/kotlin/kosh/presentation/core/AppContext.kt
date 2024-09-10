package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.GenericComponentContext
import kotlinx.coroutines.CoroutineScope

interface AppContext : GenericComponentContext<AppContext>, CoroutineScope {
    val logger: Logger
}
