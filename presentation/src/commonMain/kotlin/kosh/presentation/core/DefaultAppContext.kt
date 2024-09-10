package kosh.presentation.core

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ComponentContextFactory
import com.arkivanov.essenty.backhandler.BackHandlerOwner
import com.arkivanov.essenty.instancekeeper.InstanceKeeperOwner
import com.arkivanov.essenty.lifecycle.LifecycleOwner
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.statekeeper.StateKeeperOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class DefaultAppContext(
    componentContext: ComponentContext,
    coroutineScope: CoroutineScope,
    override val logger: Logger,
) : AppContext,
    LifecycleOwner by componentContext,
    StateKeeperOwner by componentContext,
    InstanceKeeperOwner by componentContext,
    BackHandlerOwner by componentContext,
    CoroutineScope by coroutineScope {

    init {
        lifecycle.doOnDestroy { cancel() }
    }

    override val componentContextFactory: ComponentContextFactory<AppContext> =
        ComponentContextFactory { lifecycle, stateKeeper, instanceKeeper, backHandler ->
            val ctx = componentContext.componentContextFactory(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
                instanceKeeper = instanceKeeper,
                backHandler = backHandler
            )

            DefaultAppContext(
                componentContext = ctx,
                coroutineScope = CoroutineScope(coroutineContext + SupervisorJob()),
                logger = logger,
            )
        }
}

fun defaultAppContext(
    componentContext: ComponentContext,
    coroutineContext: CoroutineContext = EmptyCoroutineContext,
): AppContext = DefaultAppContext(
    componentContext = componentContext,
    coroutineScope = CoroutineScope(coroutineContext + Dispatchers.Main.immediate + SupervisorJob()),
    logger = Logger.withTag("[K]VM")
)
