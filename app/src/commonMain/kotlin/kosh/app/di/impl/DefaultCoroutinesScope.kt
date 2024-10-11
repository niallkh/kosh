package kosh.app.di.impl

import kosh.app.di.CoroutinesComponent
import kosh.domain.core.provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

internal class DefaultCoroutinesComponent : CoroutinesComponent {

    override val applicationScope: CoroutineScope by provider {
        CoroutineScope(Dispatchers.Main + SupervisorJob())
    }
}
